package com.example.doraemoncomics.Adapters.User;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.doraemoncomics.Activity.Admin.ComicActivity;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Comment;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ComicsViewHolder>{
    private final List<Comment> list;
    private final Context context;
    private View view;
    private String id_user;
    public CommentAdapter(List<Comment> list, Context context,String id_user) {
        this.list = list;
        this.context = context;
        this.id_user = id_user;
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Comment comment = list.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_user.equals(comment.getId_user())){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_edit_account);
                    TextInputEditText input = dialog.findViewById(R.id.ed_editAccount_dal);
                    TextInputLayout inputLayout = dialog.findViewById(R.id.edL_editAccount_dal);
                    Button btn_XacNhan = dialog.findViewById(R.id.btn_xacNhan_dal);
                    Button btn_Huy = dialog.findViewById(R.id.btn_huy_dal);
                    TextView tieuDe = dialog.findViewById(R.id.tv_tieuDe_dal);
                    tieuDe.setText("Cập nhật bình luận");
                    inputLayout.setHint("Bình luận");
                    input.setHint("Viết bình luận...");
                    input.setText(comment.getNoiDung());
                    btn_Huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_XacNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            comment.setNoiDung(input.getText().toString().trim());
                            ApiService.apiService.patchComment(comment.get_id(),comment).enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    Toast.makeText(context, "Bình luận thành công!", Toast.LENGTH_SHORT).show();
                                    holder.noidung.setText(input.getText().toString());
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Toast.makeText(context, "Bình luận thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    dialog.show();

                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (id_user.equals(comment.getId_user())){
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_delete);
                    TextView tieuDe = dialog.findViewById(R.id.tv_xoa);
                    TextView noiDungXoa = dialog.findViewById(R.id.tv_nd_xoa);
                    Button btn_xoa = dialog.findViewById(R.id.btn_dialog_xoa);
                    Button btn_huy = dialog.findViewById(R.id.btn_dialog_huy);
                    tieuDe.setText("Xóa bình luận");
                    noiDungXoa.setText("Bạn chắc chắn muốn xóa bình luận?");
                    btn_huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btn_xoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ApiService.apiService.deleteComment(comment.get_id()).enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    list.remove(comment);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });
        ApiService.apiService.getOneUser(comment.getId_user()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                holder.ten.setText(response.body().getFullname());
                Glide.with(context)
                        .load(MainActivity.ip_pixe4_image+"user_avatar/"+response.body().getAvatar())
                        .into(holder.avatar);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        holder.noidung.setText(comment.getNoiDung());
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
        private final TextView ten,noidung,thoiGian;
        public ComicsViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_ucm);
            ten = itemView.findViewById(R.id.ten_ucm);
            noidung = itemView.findViewById(R.id.noidung_ucm);
            thoiGian = itemView.findViewById(R.id.time_ucm);
        }
    }
}
