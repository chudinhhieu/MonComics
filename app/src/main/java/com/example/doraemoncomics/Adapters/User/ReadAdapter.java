package com.example.doraemoncomics.Adapters.User;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comment;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadAdapter extends RecyclerView.Adapter<ReadAdapter.ComicsViewHolder> {
    private List<String> list;
    private final Context context;

    public ReadAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read, parent, false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        String anh = list.get(position);
        holder.trang.setText(position+"");
        Glide.with(context)
                .load(MainActivity.ip_pixe4_image+"comic_images/"+anh)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ComicsViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView trang;
        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_read);
            trang = itemView.findViewById(R.id.trang);
        }
    }
}
