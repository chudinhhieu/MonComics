package com.example.doraemoncomics.Adapters.Admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doraemoncomics.Activity.Admin.UserActivity;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ComicsViewHolder>{
    private final List<User> list;
    private final Context context;
    View view;
    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        User user = list.get(position);
        holder.taiKhoan.setText(user.getUsername());
        holder.hoTen.setText(user.getFullname());
        holder.email.setText(user.getEmail());
        Glide.with(context)
                .load(MainActivity.ip_pixe4_image+"user_avatar/"+user.getAvatar())
                .into(holder.avatar);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("UserItem",user);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public static  class ComicsViewHolder extends RecyclerView.ViewHolder{
        private final ImageView avatar;
        private final TextView taiKhoan,hoTen,email;
        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.itu_avatar);
            taiKhoan = itemView.findViewById(R.id.itu_tk);
            hoTen = itemView.findViewById(R.id.itu_hvt);
            email = itemView.findViewById(R.id.itu_email);
        }
    }
}
