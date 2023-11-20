package com.example.doraemoncomics.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.SignInActivity;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private User user;
    private TextView tv_id,tv_userName,tv_fullName,tv_email;
    private Button btn_xoa,btn_capNhat;
    private ImageView btn_exit,img_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tv_id = findViewById(R.id.au_id);
        tv_userName = findViewById(R.id.au_username);
        tv_fullName = findViewById(R.id.au_fullname);
        tv_email = findViewById(R.id.au_email);
        btn_exit = findViewById(R.id.au_exit);
        btn_xoa = findViewById(R.id.au_btn_xoa);
        btn_capNhat = findViewById(R.id.au_btn_sua);
        img_avatar = findViewById(R.id.au_avatar);
        Bundle bundle = getIntent().getExtras();
        user = (User) bundle.get("UserItem");
        tv_id.setText(user.get_id());
        tv_userName.setText(user.getUsername());
        tv_fullName.setText(user.getFullname());
        tv_email.setText(user.getEmail());
        Glide.with(this)
                .load(MainActivity.ip_pixe4_image + "user_avatar/" + user.getAvatar())
                .into(img_avatar);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(UserActivity.this);
                dialog.setContentView(R.layout.dialog_edit_user);
                TextInputLayout inputLayoutHVT = dialog.findViewById(R.id.edL_dlu_hvt);
                TextInputLayout inputLayoutEmail = dialog.findViewById(R.id.edL_dlu_email);
                TextInputEditText inputHVT = dialog.findViewById(R.id.ed_dlu_hvt);
                TextInputEditText inputEmail = dialog.findViewById(R.id.ed_dlu_email);
                Button btn_xacNhan = dialog.findViewById(R.id.btn_xacNhan_dlu);
                Button btn_huy = dialog.findViewById(R.id.btn_huy_dlu);
                inputHVT.setText(user.getFullname());
                inputEmail.setText(user.getEmail());
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hoVaTen = inputHVT.getText().toString().trim();
                        String email = inputEmail.getText().toString().trim();
                        User userEdit = user;
                        userEdit.setFullname(hoVaTen);
                        userEdit.setEmail(email);
                        ApiService.apiService.patchUser(userEdit.get_id(),userEdit).enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(UserActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                                tv_fullName.setText(userEdit.getFullname());
                                tv_email.setText(userEdit.getEmail());
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(UserActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(UserActivity.this);
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
                                Toast.makeText(UserActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                                }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(UserActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });
    }
}