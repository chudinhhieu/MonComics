package com.example.doraemoncomics.Activity.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.SignInActivity;
import com.example.doraemoncomics.Adapters.Admin.ImageAdapter;
import com.example.doraemoncomics.Adapters.Admin.SpinnerGenreAdapter;
import com.example.doraemoncomics.Api.ApiNotification;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Api.NotificationData;
import com.example.doraemoncomics.Api.RealPathUtil;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.Models.MyDate;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComicActivity extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST = 1;
    private ArrayList<Uri> selectedImages;
    private RecyclerView recyclerView;
    private Spinner spinner;
    private ImageView anhBia, btn_exit_aac, anhTL;
    private SpinnerGenreAdapter adapter;
    private List<Genre> list;
    private TextInputLayout edL_aac_tenTruyen, edL_aac_tacGia, edL_aac_linkTruyen, edL_aac_namXuatBan, edL_aac_moTa;
    private TextInputEditText ed_aac_tenTruyen, ed_aac_tacGia, ed_aac_linkTruyen, ed_aac_namXuatBan, ed_aac_moTa;
    private Button btn_XacNhan, btn_Huy, btn_addG_aac;
    private Uri uri, uriTL;
    private TextView tv_add_anh;
    private ImageAdapter imageAdapter;
    MyDate myDate;
    String namSX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comic);
        edL_aac_linkTruyen = findViewById(R.id.edL_aac_linkTruyen);
        edL_aac_tenTruyen = findViewById(R.id.edL_aac_tenTruyen);
        edL_aac_tacGia = findViewById(R.id.edL_aac_tacGia);
        edL_aac_namXuatBan = findViewById(R.id.edL_aac_namXuatBan);
        edL_aac_moTa = findViewById(R.id.edL_aac_moTa);
        ed_aac_tenTruyen = findViewById(R.id.ed_aac_tenTruyen);
        ed_aac_tacGia = findViewById(R.id.ed_aac_tacGia);
        ed_aac_linkTruyen = findViewById(R.id.ed_aac_linkTruyen);
        ed_aac_namXuatBan = findViewById(R.id.ed_aac_namXuatBan);
        ed_aac_moTa = findViewById(R.id.ed_aac_moTa);
        btn_XacNhan = findViewById(R.id.btn_xacNhan_aac);
        btn_exit_aac = findViewById(R.id.btn_exit_aac);
        btn_addG_aac = findViewById(R.id.btn_addG_aac);
        btn_Huy = findViewById(R.id.btn_huy_aac);
        spinner = findViewById(R.id.spn_genre);
        anhBia = findViewById(R.id.image_comic);
        recyclerView = findViewById(R.id.recyclerView);
        tv_add_anh = findViewById(R.id.tv_aac_themAnh);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        selectedImages = new ArrayList<>();
        loadDataGenre();
        myDate = new MyDate();
        btn_addG_aac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(AddComicActivity.this);
                dialog.setContentView(R.layout.dialog_add_genre);
                TextInputLayout layoutTen = dialog.findViewById(R.id.edL_dlg_ten_ag);
                TextInputLayout layoutMota = dialog.findViewById(R.id.edL_dlg_mota_ag);
                TextInputEditText inputTen = dialog.findViewById(R.id.ed_dlg_ten_ag);
                TextInputEditText inputMota = dialog.findViewById(R.id.ed_dlg_mota_ag);
                anhTL = dialog.findViewById(R.id.dlg_image_ag);
                Button xacNhan = dialog.findViewById(R.id.btn_xacNhan_dlg_ag);
                Button huy = dialog.findViewById(R.id.btn_huy_dlg_ag);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                anhTL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                });
                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestBody reqTen = RequestBody.create(MediaType.parse("multipart/form-data"), inputTen.getText().toString().trim());
                        RequestBody reqMota = RequestBody.create(MediaType.parse("multipart/form-data"), inputMota.getText().toString().trim());
                        if (uriTL == null) {
                            Toast.makeText(AddComicActivity.this, "Chưa chọn ảnh!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String strRealPath = RealPathUtil.getRealPath(AddComicActivity.this, uriTL);
                        File file = new File(strRealPath);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                        ProgressDialog progressDialog = new ProgressDialog(AddComicActivity.this);
                        progressDialog.setMessage("Đang thêm thể loại...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ApiService.apiService.postGenre(reqTen, reqMota, part).enqueue(new Callback<Genre>() {
                            @Override
                            public void onResponse(Call<Genre> call, Response<Genre> response) {
                                Toast.makeText(AddComicActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                progressDialog.dismiss();
                                loadDataGenre();
                            }

                            @Override
                            public void onFailure(Call<Genre> call, Throwable t) {
                                Toast.makeText(AddComicActivity.this, "Thêm thất bại!" + t, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });
        ed_aac_namXuatBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddComicActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        ed_aac_namXuatBan.setText(myDate.toStringVn(calendar.getTime()));
                        namSX = myDate.toString(calendar.getTime());
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btn_exit_aac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_add_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
        anhBia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre genre = (Genre) spinner.getSelectedItem();
                RequestBody reqLoai = RequestBody.create(MediaType.parse("multipart/form-data"), genre.get_id());
                RequestBody reqTen = RequestBody.create(MediaType.parse("multipart/form-data"), ed_aac_tenTruyen.getText().toString().trim());
                RequestBody reqMota = RequestBody.create(MediaType.parse("multipart/form-data"), ed_aac_moTa.getText().toString().trim());
                if (namSX == null) {
                    edL_aac_namXuatBan.setError("Chưa chọn năm xuất bản!");
                    return;
                }
                RequestBody reqNXB = RequestBody.create(MediaType.parse("multipart/form-data"), namSX);
                RequestBody reqTacGia = RequestBody.create(MediaType.parse("multipart/form-data"), ed_aac_tacGia.getText().toString().trim());
                RequestBody reqLink = RequestBody.create(MediaType.parse("multipart/form-data"), ed_aac_linkTruyen.getText().toString().trim());
                List<MultipartBody.Part> imageParts = new ArrayList<>();
                if (selectedImages.size() == 0){
                    Toast.makeText(AddComicActivity.this, "Chưa chọn ảnh truyện!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Uri selectedUri : selectedImages) {
                    String realPath = RealPathUtil.getRealPath(AddComicActivity.this, selectedUri);
                    File imageFile = new File(realPath);
                    RequestBody imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("contentImage", imageFile.getName(), imageRequestBody);
                    imageParts.add(imagePart);
                }
                if (uri == null) {
                    Toast.makeText(AddComicActivity.this, "Chưa chọn ảnh bìa!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String strRealPath = RealPathUtil.getRealPath(AddComicActivity.this, uri);
                File file = new File(strRealPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("coverImage", file.getName(), requestBody);
                ProgressDialog progressDialog = new ProgressDialog(AddComicActivity.this);
                progressDialog.setMessage("Đang thêm truyện...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                ApiService.apiService.postComic(reqTen, reqLoai, reqMota, reqNXB, reqTacGia, reqLink, part, imageParts).enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Toast.makeText(AddComicActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        sendNotification();
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {
                        Log.d("onFailure", "onFailure: " + t);
                        Toast.makeText(AddComicActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private void sendNotification() {
        NotificationData notificationData = new NotificationData();
        notificationData.setData(new NotificationData.NotificationPayload("Truyện mới", "Bạn ơi đang có truyện mới đó nha!"));
        notificationData.setTo("/topics/newcomic");
        ApiNotification.apiNotification.sendNotification(notificationData).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST);
        imageAdapter = new ImageAdapter(selectedImages, this);
        recyclerView.setAdapter(imageAdapter);
    }

    private void loadDataGenre() {
        ApiService.apiService.getListGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                list = response.body();
                adapter = new SpinnerGenreAdapter(AddComicActivity.this, list);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }
            imageAdapter.notifyDataSetChanged();
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uri = selectedImage;
            Glide.with(this).load(uri).into(anhBia);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uriTL = selectedImage;
            Glide.with(this).load(selectedImage).into(anhTL);
        }
    }

}