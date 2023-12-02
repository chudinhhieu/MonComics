package com.example.doraemoncomics.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.User.CommentActivity;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Activity.User.ReadActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Comment;
import com.example.doraemoncomics.Models.Favorite;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComicActivity extends AppCompatActivity {
    private Comic comic;
    private ImageView btn_exit,img_anhBia,img_fvr;
    private TextView tv_ten,tv_theLoai,tv_moTa,tv_NXB,tv_TacGia,tv_slbl,tv_slyt;
    private Button btn_capNhat,btn_xoa,btn_doc,btn_xemTruoc;
    private Genre genre;
    private Intent intent;
    private LinearLayout btn_nextBinhLuan,btn_yt;
    private Favorite favorite;
    private String savedUserId;
    private List<Favorite> favoriteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        btn_exit = findViewById(R.id.btn_ac_exit);
        img_anhBia = findViewById(R.id.ac_image_comic);
        tv_ten = findViewById(R.id.tv_ac_ten);
        tv_theLoai = findViewById(R.id.tv_theLoai);
        tv_moTa = findViewById(R.id.tv_moTa);
        tv_slyt = findViewById(R.id.tv_slyt);
        tv_NXB = findViewById(R.id.tv_NXB);
        tv_TacGia = findViewById(R.id.tv_tacGia);
        btn_capNhat = findViewById(R.id.btn_sua_ac);
        btn_yt = findViewById(R.id.btn_yt);
        tv_slbl = findViewById(R.id.tv_slbl);
        img_fvr = findViewById(R.id.img_fvr);
        btn_xoa = findViewById(R.id.btn_xoa_ac);
        btn_nextBinhLuan = findViewById(R.id.btn_nextBinhLuan);
        btn_xemTruoc = findViewById(R.id.btn_xemTruoc_ac);
        btn_doc = findViewById(R.id.btn_doc_ac);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        savedUserId = sharedPreferences.getString("userId","1");
        String savedUsername = sharedPreferences.getString("username","chuhieu");
        if (!savedUsername.equals("admin")){
            btn_capNhat.setVisibility(View.GONE);
            btn_xoa.setVisibility(View.GONE);
        }else {
            btn_xemTruoc.setVisibility(View.GONE);
            btn_doc.setVisibility(View.GONE);
        }
        intent = getIntent();
        loadData();
        btn_yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (favorite == null){
                   ApiService.apiService.postFavorite(new Favorite(savedUserId,comic.get_id())).enqueue(new Callback<Void>() {
                       @Override
                       public void onResponse(Call<Void> call, Response<Void> response) {
                           Toast.makeText(ComicActivity.this, "Đã yêu thích!", Toast.LENGTH_SHORT).show();
                           loadDataFavorite();
                           loadListFavorite();
                       }

                       @Override
                       public void onFailure(Call<Void> call, Throwable t) {
                           Toast.makeText(ComicActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                       }
                   });
               }else{
                    ApiService.apiService.deleteFavorite(favorite.get_id()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(ComicActivity.this, "Đã bỏ yêu thích!", Toast.LENGTH_SHORT).show();
                            loadDataFavorite();
                            loadListFavorite();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ComicActivity.this, "Thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    });
               }
            }
        });
        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_xemTruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComicActivity.this, ReadActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("listImage", (ArrayList<String>) comic.getContentImage());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_nextBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComicActivity.this, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_comic",comic.get_id());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_capNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComicActivity.this, EditComicActivity.class);
                intent.putExtra("comic_id", comic.get_id());
                startActivity(intent);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ComicActivity.this);
                dialog.setContentView(R.layout.dialog_delete);
                TextView tieuDe = dialog.findViewById(R.id.tv_xoa);
                TextView noiDungXoa = dialog.findViewById(R.id.tv_nd_xoa);
                Button btn_xoa = dialog.findViewById(R.id.btn_dialog_xoa);
                Button btn_huy = dialog.findViewById(R.id.btn_dialog_huy);
                tieuDe.setText("Xóa truyện");
                noiDungXoa.setText("Bạn chắc chắn muốn xóa truyện?");
                btn_huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiService.apiService.deleteComic(intent.getStringExtra("comic_id")).enqueue(new Callback<Comic>() {
                            @Override
                            public void onResponse(Call<Comic> call, Response<Comic> response) {
                                Toast.makeText(ComicActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<Comic> call, Throwable t) {
                                Toast.makeText(ComicActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });
    }


    private void loadData() {
        ApiService.apiService.getOneComic(intent.getStringExtra("comic_id")).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                comic = response.body();

                ApiService.apiService.getListComment(comic.get_id()).enqueue(new Callback<List<Comment>>() {
                    @Override
                    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                        tv_slbl.setText(response.body().size()+" bình luận");
                    }

                    @Override
                    public void onFailure(Call<List<Comment>> call, Throwable t) {

                    }
                });
                tv_ten.setText(comic.getName());
                tv_NXB.setText(comic.getPublicationDate());
                tv_moTa.setText(comic.getDescription());
                tv_TacGia.setText(comic.getAuthor());
                Glide.with(ComicActivity.this)
                        .load(MainActivity.ip_pixe4_image+"comic_images/cover_images/"+comic.getCoverImage())
                        .into(img_anhBia);
                setTenTheLoai();
                loadDataFavorite();
                loadListFavorite();
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

            }
        });


    }

    private void setTenTheLoai() {
        ApiService.apiService.getGenre(comic.getIdGenre()).enqueue(new Callback<Genre>() {
            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                genre = response.body();
                tv_theLoai.setText(genre.getName());
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {
                Log.d("onFailure", "onFailure: "+t.getMessage());
            }
        });
    }
    private void loadListFavorite(){
        ApiService.apiService.getListFavoriteComic(comic.get_id()).enqueue(new Callback<List<Favorite>>() {
            @Override
            public void onResponse(Call<List<Favorite>> call, Response<List<Favorite>> response) {
                int sl = response.body().size();
                tv_slyt.setText(sl+" yêu thích");
            }

            @Override
            public void onFailure(Call<List<Favorite>> call, Throwable t) {
                Toast.makeText(ComicActivity.this, "Lấy danh sách yêu thích thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataFavorite() {
        ApiService.apiService.checkFavorite(savedUserId,comic.get_id()).enqueue(new Callback<Favorite>() {
            @Override
            public void onResponse(Call<Favorite> call, Response<Favorite> response) {
                favorite = response.body();
                if (favorite == null){
                    img_fvr.setImageResource(R.drawable.outline_favorite_border_24);
                }else {
                    img_fvr.setImageResource(R.drawable.baseline_favorite_24);
                }
            }

            @Override
            public void onFailure(Call<Favorite> call, Throwable t) {
                Toast.makeText(ComicActivity.this, "Lấy dữ liệu yêu thích thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}