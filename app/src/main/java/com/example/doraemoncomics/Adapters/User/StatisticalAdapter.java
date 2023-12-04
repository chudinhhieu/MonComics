package com.example.doraemoncomics.Adapters.User;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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
import com.example.doraemoncomics.Models.Statistical;
import com.example.doraemoncomics.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalAdapter extends RecyclerView.Adapter<StatisticalAdapter.ComicsViewHolder> {
    private final List<Statistical> list;
    private final Context context;
    private View view;

    public StatisticalAdapter(List<Statistical> list, Context context) {
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
        Statistical statistical = list.get(position);
        ApiService.apiService.getOneComic(statistical.get_id()).enqueue(new Callback<Comic>() {
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {
                Comic comic = response.body();
                int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                boolean isNightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
                if (isNightMode){
                    holder.tenTruyen.setTextColor(Color.WHITE);
                }
                holder.tenTruyen.setText(comic.getName());
                Glide.with(context)
                        .load(MainActivity.ip_pixe4_image + "comic_images/cover_images/" + comic.getCoverImage())
                        .into(holder.anhBia);
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService.apiService.getOneComic(statistical.get_id()).enqueue(new Callback<Comic>() {
                    @Override
                    public void onResponse(Call<Comic> call, Response<Comic> response) {
                        Comic comic = response.body();
                        Intent intent = new Intent(context, ComicActivity.class);
                        intent.putExtra("comic_id", comic.get_id());
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Comic> call, Throwable t) {

                    }
                });

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
