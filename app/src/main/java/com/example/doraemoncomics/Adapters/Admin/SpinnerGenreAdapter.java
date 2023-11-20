package com.example.doraemoncomics.Adapters.Admin;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.doraemoncomics.Models.Genre;
import com.example.doraemoncomics.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerGenreAdapter extends BaseAdapter {
    Context mContext;
    List<Genre> list;

    public SpinnerGenreAdapter(Context mContext, List<Genre> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = ((Activity) mContext).getLayoutInflater();
        ViewOfItem viewOfItem = null;
        if (view == null){
            view = layoutInflater.inflate(R.layout.item_genre_spinner, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvTen = view.findViewById(R.id.tvTen);
            view.setTag(viewOfItem);
        }else {
            viewOfItem = (ViewOfItem) view.getTag();
        }
        viewOfItem.tvTen.setText(list.get(i).getName());
        return view;
    }

    public static class ViewOfItem {
        TextView tvTen;
    }
}
