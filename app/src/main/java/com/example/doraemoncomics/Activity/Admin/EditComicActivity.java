package com.example.doraemoncomics.Activity.Admin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Adapters.Admin.ImageAdapter;
import com.example.doraemoncomics.Adapters.Admin.ImageDownLoadAdapter;
import com.example.doraemoncomics.Adapters.Admin.SpinnerGenreAdapter;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Api.RealPathUtil;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditComicActivity extends AppCompatActivity {
    private Spinner spinner;
    private ArrayList<Uri> selectedImages;
    private ImageView anhBia;
    private SpinnerGenreAdapter adapter;
    private static final int PICK_IMAGES_REQUEST = 1;
    private List<Genre> list;
    private TextInputLayout edL_aac_tenTruyen,edL_aac_tacGia,edL_aac_linkTruyen,edL_aac_namXuatBan,edL_aac_moTa;
    private TextInputEditText ed_aac_tenTruyen,ed_aac_tacGia,ed_aac_linkTruyen,ed_aac_namXuatBan,ed_aac_moTa;
    private Button btn_XacNhan,btn_Huy;
    private Uri uri;
    private Comic comic;
    private TextView tv_add_anh;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private ImageDownLoadAdapter imageDownLoadAdapter;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comic);
        edL_aac_linkTruyen = findViewById(R.id.edL_aac_linkTruyen);
        edL_aac_tenTruyen = findViewById(R.id.edL_aac_tenTruyen);
        edL_aac_tacGia = findViewById(R.id.edL_aac_tacGia);
        edL_aac_namXuatBan = findViewById(R.id.edL_aac_namXuatBan);
        edL_aac_moTa = findViewById(R.id.edL_aac_moTa);
        ed_aac_tenTruyen= findViewById(R.id.ed_aac_tenTruyen);
        ed_aac_tacGia= findViewById(R.id.ed_aac_tacGia);
        ed_aac_linkTruyen= findViewById(R.id.ed_aac_linkTruyen);
        ed_aac_namXuatBan= findViewById(R.id.ed_aac_namXuatBan);
        ed_aac_moTa= findViewById(R.id.ed_aac_moTa);
        btn_XacNhan = findViewById(R.id.btn_xacNhan_aac);
        btn_Huy = findViewById(R.id.btn_huy_aac);
        recyclerView = findViewById(R.id.recyclerView_aec);
        tv_add_anh = findViewById(R.id.tv_aec_themAnh);
        spinner = findViewById(R.id.spn_genre);
        anhBia = findViewById(R.id.image_comic);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        selectedImages = new ArrayList<>();
        intent = getIntent();
        ApiService.apiService.getOneComic(intent.getStringExtra("comic_id")).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                comic = response.body();
                Glide.with(EditComicActivity.this)
                        .load(MainActivity.ip_pixe4_image+"comic_images/cover_images/"+comic.getCoverImage())
                        .into(anhBia);
                ed_aac_tenTruyen.setText(comic.getName());
                ed_aac_moTa.setText(comic.getDescription());
                ed_aac_namXuatBan.setText(comic.getPublicationDate());
                ed_aac_tacGia.setText(comic.getAuthor());
                ed_aac_linkTruyen.setText(comic.getLinkCM());
                loadDataGenre();
                imageDownLoadAdapter = new ImageDownLoadAdapter(comic.getContentImage(),EditComicActivity.this);
                recyclerView.setAdapter(imageDownLoadAdapter);
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

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
                finish();
            }
        });
        btn_XacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre genre = (Genre) spinner.getSelectedItem();
                RequestBody reqLoai = RequestBody.create(MediaType.parse("multipart/form-data"),genre.get_id());
                RequestBody reqTen = RequestBody.create(MediaType.parse("multipart/form-data"),ed_aac_tenTruyen.getText().toString().trim());
                RequestBody reqMota = RequestBody.create(MediaType.parse("multipart/form-data"),ed_aac_moTa.getText().toString().trim());
                RequestBody reqNXB = RequestBody.create(MediaType.parse("multipart/form-data"),ed_aac_namXuatBan.getText().toString().trim());
                RequestBody reqTacGia = RequestBody.create(MediaType.parse("multipart/form-data"),ed_aac_tacGia.getText().toString().trim());
                RequestBody reqLink = RequestBody.create(MediaType.parse("multipart/form-data"),ed_aac_linkTruyen.getText().toString().trim());
                List<MultipartBody.Part> imageParts = new ArrayList<>();
                for (Uri selectedUri : selectedImages) {
                    String realPath = RealPathUtil.getRealPath(EditComicActivity.this, selectedUri);
                    File imageFile = new File(realPath);
                    RequestBody imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("contentImage", imageFile.getName(), imageRequestBody);
                    imageParts.add(imagePart);
                }
                String strRealPath = RealPathUtil.getRealPath(EditComicActivity.this, uri);
                File file = new File(strRealPath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("coverImage", file.getName(), requestBody);
                ApiService.apiService.patchComic(comic.get_id(),reqTen,reqLoai,reqMota,reqNXB,reqTacGia,reqLink,part,imageParts).enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Toast.makeText(EditComicActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {
                        Log.d("onFailure", "onFailure: "+t);
                        Toast.makeText(EditComicActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    private void loadDataGenre() {
        ApiService.apiService.getListGenre().enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                list = response.body();
                adapter = new SpinnerGenreAdapter(EditComicActivity.this,list);
                spinner.setAdapter(adapter);
                for (int i = 0; i < list.size(); i++) {
                    Genre genre =  list.get(i);
                    if (genre.get_id().equals(comic.getIdGenre())) {
                        spinner.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {

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
        }
    }
}