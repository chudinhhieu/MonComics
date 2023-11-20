package com.example.doraemoncomics.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Api.RealPathUtil;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreActivity extends AppCompatActivity {
    private ImageView btn_exit,anh, anhTL;
    private TextView ten,moTa,soLuong;
    private Button capNhap,xoa;
    private Genre genre;
    private Uri uri;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        btn_exit = findViewById(R.id.btn_ag_exit);
        anh = findViewById(R.id.ag_image_genre);
        ten = findViewById(R.id.tv_ag_ten);
        moTa = findViewById(R.id.tv_moTa_ag);
        soLuong = findViewById(R.id.tv_sl_ag);
        capNhap = findViewById(R.id.btn_sua_ag);
        xoa = findViewById(R.id.btn_xoa_ag);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedUserId = sharedPreferences.getString("userId","1");
        String savedUsername = sharedPreferences.getString("username","chuhieu");
        if (!savedUsername.equals("admin")){
            capNhap.setVisibility(View.GONE);
            xoa.setVisibility(View.GONE);
        }
        intent = getIntent();
        loadSL();
        loadData();
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        capNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GenreActivity.this);
                dialog.setContentView(R.layout.dialog_edit_genre);
                TextInputLayout layoutTen = dialog.findViewById(R.id.edL_dlg_ten);
                TextInputLayout layoutMota = dialog.findViewById(R.id.edL_dlg_mota);
                TextInputEditText inputTen = dialog.findViewById(R.id.ed_dlg_ten);
                TextInputEditText inputMota = dialog.findViewById(R.id.ed_dlg_mota);
                anhTL =  dialog.findViewById(R.id.dlg_image);
                Button xacNhan = dialog.findViewById(R.id.btn_xacNhan_dlg);
                Button huy = dialog.findViewById(R.id.btn_huy_dlg);
                Glide.with(GenreActivity.this)
                        .load(MainActivity.ip_pixe4_image+"genre_images/"+genre.getImage())
                        .into(anhTL);
                inputTen.setText(genre.getName());
                inputMota.setText(genre.getDescription());
                anhTL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);

                    }
                });
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestBody reqTen = RequestBody.create(MediaType.parse("multipart/form-data"),inputTen.getText().toString().trim());
                        RequestBody reqMota = RequestBody.create(MediaType.parse("multipart/form-data"),inputMota.getText().toString().trim());
                        String strRealPath = RealPathUtil.getRealPath(GenreActivity.this, uri);
                        File file = new File(strRealPath);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                        ApiService.apiService.patchGenre(genre.get_id(),reqTen,reqMota,part).enqueue(new Callback<Genre>() {
                            @Override
                            public void onResponse(Call<Genre> call, Response<Genre> response) {
                                Toast.makeText(GenreActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                                loadData();
                                dialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Genre> call, Throwable t) {
                                Toast.makeText(GenreActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(GenreActivity.this);
                dialog.setContentView(R.layout.dialog_delete);
                TextView tieuDe = dialog.findViewById(R.id.tv_xoa);
                TextView noiDungXoa = dialog.findViewById(R.id.tv_nd_xoa);
                Button btn_xoa = dialog.findViewById(R.id.btn_dialog_xoa);
                Button btn_huy = dialog.findViewById(R.id.btn_dialog_huy);
                tieuDe.setText("Xóa thể loại truyện");
                noiDungXoa.setText("Bạn chắc chắn muốn xóa thể loại truyện?");
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiService.apiService.deleteGenre(intent.getStringExtra("genre_id")).enqueue(new Callback<Genre>() {
                            @Override
                            public void onResponse(Call<Genre> call, Response<Genre> response) {
                                Toast.makeText(GenreActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Genre> call, Throwable t) {
                                Toast.makeText(GenreActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
            }
        });
    }

    private void loadSL() {
        ApiService.apiService.getListComic(intent.getStringExtra("genre_id")).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                soLuong.setText(response.body().size()+"");
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });
    }

    private void loadData() {

        ApiService.apiService.getGenre(intent.getStringExtra("genre_id")).enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                genre = response.body();
                ten.setText(genre.getName());
                moTa.setText(genre.getDescription());
                Glide.with(GenreActivity.this)
                        .load(MainActivity.ip_pixe4_image+"genre_images/"+genre.getImage())
                        .into(anh);
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            uri = selectedImage;
            Glide.with(GenreActivity.this)
                    .load(uri)
                    .into(anhTL);
        }
    }
}