package com.example.doraemoncomics.Activity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doraemoncomics.Adapters.User.CommentAdapter;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comment;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Comment> list;
    private TextInputLayout inputLayout;
    private TextInputEditText inputEditText;
    private ImageView btn_binhLuan, exit;
    private String idComic,savedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView = findViewById(R.id.rcv_comment);
        inputLayout = findViewById(R.id.ipl_cm);
        inputEditText = findViewById(R.id.ip_cm);
        exit = findViewById(R.id.btn_acm_exit);
        btn_binhLuan = findViewById(R.id.btn_cm);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
         savedUserId = sharedPreferences.getString("userId", "1");
        Bundle extras = getIntent().getExtras();
        idComic = extras.getString("id_comic");
        list = new ArrayList<>();
        loadData();
        btn_binhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDung = inputEditText.getText().toString().trim();
                Comment comment = new Comment(savedUserId, idComic, noiDung);
                ApiService.apiService.postComment(comment).enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        Toast.makeText(CommentActivity.this, "Bình luận thành công!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Toast.makeText(CommentActivity.this, "Bình luận thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadData() {
        ApiService.apiService.getListComment(idComic).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                list = response.body();
                CommentAdapter adapter = new CommentAdapter(list, CommentActivity.this,savedUserId);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });
    }
}