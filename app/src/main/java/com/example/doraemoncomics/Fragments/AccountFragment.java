package com.example.doraemoncomics.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Api.RealPathUtil;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.example.doraemoncomics.Activity.SignInActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    private TextView tv_hoVaTen, tv_email, btn_delete;
    private ImageView btn_editAvatar, btn_editHoVaTen, btn_editEmail, avatar;
    private CardView btn_dmk,btnSignOut;
    private User user;
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_email = view.findViewById(R.id.tv_email);
        tv_hoVaTen = view.findViewById(R.id.tv_fullname);
        btn_editEmail = view.findViewById(R.id.btn_edit_email);
        btn_editAvatar = view.findViewById(R.id.btn_edit_avatar);
        btn_editHoVaTen = view.findViewById(R.id.btn_edit_fullname);
        avatar = view.findViewById(R.id.avatar);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btn_delete = view.findViewById(R.id.btn_delete_account);
        btn_dmk = view.findViewById(R.id.btnDMK);
        Bundle bundle = getActivity().getIntent().getExtras();
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SignInActivity.class));
            }
        });
        if (bundle != null) {
            user = (User) bundle.get("userSignIn");
            if (user != null) {
                tv_email.setText(user.getEmail());
                tv_hoVaTen.setText(user.getFullname());
                Glide.with(getContext())
                        .load(MainActivity.ip_pixe4_image + "user_avatar/" + user.getAvatar())
                        .into(avatar);
                btn_editHoVaTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_edit_account);
                        TextInputEditText input = dialog.findViewById(R.id.ed_editAccount_dal);
                        TextInputLayout inputLayout = dialog.findViewById(R.id.edL_editAccount_dal);
                        Button btn_XacNhan = dialog.findViewById(R.id.btn_xacNhan_dal);
                        Button btn_Huy = dialog.findViewById(R.id.btn_huy_dal);
                        TextView tieuDe = dialog.findViewById(R.id.tv_tieuDe_dal);
                        tieuDe.setText("Cập nhật họ và tên");
                        inputLayout.setHint("Họ và tên");
                        input.setHint("Họ và tên...");
                        input.setText(user.getFullname());
                        btn_Huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (input.getText().toString().isEmpty()) {
                                    inputLayout.setError("Vui lòng nhập họ và tên!");
                                    return;
                                } else {
                                    inputLayout.setError("");
                                }
                                User userEdit = user;
                                userEdit.setFullname(input.getText().toString().trim());
                                ApiService.apiService.patchUser(user.get_id(), user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                        tv_hoVaTen.setText(user.getFullname());
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        dialog.show();
                    }
                });
                btn_editEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_edit_account);
                        TextInputEditText input = dialog.findViewById(R.id.ed_editAccount_dal);
                        TextInputLayout inputLayout = dialog.findViewById(R.id.edL_editAccount_dal);
                        Button btn_XacNhan = dialog.findViewById(R.id.btn_xacNhan_dal);
                        Button btn_Huy = dialog.findViewById(R.id.btn_huy_dal);
                        TextView tieuDe = dialog.findViewById(R.id.tv_tieuDe_dal);
                        tieuDe.setText("Cập nhật email");
                        input.setHint("Email...");
                        inputLayout.setHint("Email");
                        input.setText(user.getEmail());
                        btn_Huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (input.getText().toString().isEmpty()) {
                                    inputLayout.setError("Vui lòng nhập email!");
                                    return;
                                } else {
                                    inputLayout.setError("");
                                }
                                User userEdit = user;
                                userEdit.setEmail(input.getText().toString().trim());
                                ApiService.apiService.patchUser(user.get_id(), user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();
                                        tv_email.setText(user.getEmail());
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        dialog.show();
                    }
                });
                btn_dmk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_change_password);
                        TextInputEditText inputMKC = dialog.findViewById(R.id.ed_mkc);
                        TextInputEditText inputMKM = dialog.findViewById(R.id.ed_mkm);
                        TextInputEditText inputLMKM = dialog.findViewById(R.id.ed_lmkm);
                        TextInputLayout inputLayoutMKC = dialog.findViewById(R.id.edL_mkc);
                        TextInputLayout inputLayoutMKM = dialog.findViewById(R.id.edL_mkm);
                        TextInputLayout inputLayoutLMKM = dialog.findViewById(R.id.edL_lmkm);
                        Button btn_XacNhan = dialog.findViewById(R.id.btn_xacNhan_dmk);
                        Button btn_Huy = dialog.findViewById(R.id.btn_huy_dmk);
                        btn_Huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                    }
                });
                btn_editAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openImagePicker();
                    }
                });
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.dialog_delete);
                        TextView tieuDe = dialog.findViewById(R.id.tv_xoa);
                        TextView noiDungXoa = dialog.findViewById(R.id.tv_nd_xoa);
                        Button btn_xoa = dialog.findViewById(R.id.btn_dialog_xoa);
                        Button btn_huy = dialog.findViewById(R.id.btn_dialog_huy);
                        tieuDe.setText("Xóa tài khoản");
                        noiDungXoa.setText("Bạn chắc chắn muốn xóa tài khoản?");
                        btn_huy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        btn_xoa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ApiService.apiService.deleteAccount(user.get_id()).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getContext(), SignInActivity.class));
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(getContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        dialog.show();
                    }
                });
            }
        }
    }


    private void upLoadImage() {
        String strRealPath = RealPathUtil.getRealPath(getContext(), uri);
        File file = new File(strRealPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
        ApiService.apiService.postAvatar(user.get_id(), part).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getContext(), "Thành công!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("onFailure", "onFailure: " + t);
                Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openImagePicker() {
        // Mở activity thư viện ảnh
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uri = selectedImage;
            upLoadImage();
            Glide.with(getContext())
                    .load(uri)
                    .into(avatar);
        }
    }


}
