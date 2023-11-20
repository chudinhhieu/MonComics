package com.example.doraemoncomics.Adapters.Admin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doraemoncomics.Activity.Admin.GenreActivity;
import com.example.doraemoncomics.Adapters.User.ComicAdapter;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContainerComicAdapter extends RecyclerView.Adapter<ContainerComicAdapter.ComicViewHolder> {
    private List<Genre> list;
    private Context context;
    List<Comic> listComic;
    private ComicAdapter adapter;
    public ContainerComicAdapter(List<Genre> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_comic, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewHolder holder, int position) {
        Genre genre = list.get(position);
        holder.tenTheLoai.setText(genre.getName());
        holder.tenTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GenreActivity.class);
                intent.putExtra("genre_id", genre.get_id());
                context.startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(layoutManager);
        listComic = new ArrayList<>();
        ApiService.apiService.getListComic(genre.get_id()).enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                listComic = response.body();
                adapter = new ComicAdapter(listComic,context);
                holder.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder {
        TextView tenTheLoai;
        RecyclerView recyclerView;
        public ComicViewHolder(@NonNull View itemView) {
            super(itemView);
            tenTheLoai = itemView.findViewById(R.id.tenTheLoai);
            recyclerView = itemView.findViewById(R.id.rcv_comic);
        }
    }
}
