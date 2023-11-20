package com.example.doraemoncomics.Adapters.Admin;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.R;

import java.util.ArrayList;
import java.util.List;

public class ImageDownLoadAdapter extends RecyclerView.Adapter<ImageDownLoadAdapter.ImageViewHolder> {

    private List<String> list;
    private Context context;

    public ImageDownLoadAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String anh = list.get(position);
        Glide.with(context)
                .load(MainActivity.ip_pixe4_image+"comic_images/"+anh)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
