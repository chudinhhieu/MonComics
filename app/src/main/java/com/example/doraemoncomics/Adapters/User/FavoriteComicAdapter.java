package com.example.doraemoncomics.Adapters.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.Admin.ComicActivity;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Favorite;
import com.example.doraemoncomics.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteComicAdapter extends RecyclerView.Adapter<FavoriteComicAdapter.ComicsViewHolder> {
    private final List<Favorite> list;
    private final Context context;
    private View view;

    public FavoriteComicAdapter(List<Favorite> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic, parent, false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Favorite favorite = list.get(position);
        ApiService.apiService.getOneComic(favorite.getId_comic()).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                Comic comic = response.body();
                holder.tenTruyen.setText(comic.getName());
                Glide.with(context)
                        .load(MainActivity.ip_pixe4_image + "comic_images/cover_images/" + comic.getCoverImage())
                        .into(holder.anhBia);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ComicActivity.class);
                        intent.putExtra("comic_id", comic.get_id());
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView anhBia;
        private final TextView tenTruyen;

        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            anhBia = itemView.findViewById(R.id.imageComic);
            tenTruyen = itemView.findViewById(R.id.nameComic);
        }
    }
}
