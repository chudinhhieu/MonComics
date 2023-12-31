package com.example.doraemoncomics.Fragments.User;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doraemoncomics.Adapters.User.ComicAdapter;
import com.example.doraemoncomics.Adapters.User.CommentAdapter;
import com.example.doraemoncomics.Adapters.User.StatisticalAdapter;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.Comic;
import com.example.doraemoncomics.Models.Statistical;
import com.example.doraemoncomics.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView rcvn;
    RecyclerView rcvf;
    RecyclerView rcvc;
    TextView td_n,td_y,td_c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvn = view.findViewById(R.id.rcv_new);
        rcvf = view.findViewById(R.id.rcv_topf);
        td_n = view.findViewById(R.id.td_n);
        td_y = view.findViewById(R.id.td_y);
        td_c = view.findViewById(R.id.td_c);
        rcvc = view.findViewById(R.id.rcv_topc);
        checkDarkMode();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagerf = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager layoutManagern = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvf.setLayoutManager(layoutManagerf);
        rcvc.setLayoutManager(layoutManager);
        rcvn.setLayoutManager(layoutManagern);
        loadData();
    }

    private void checkDarkMode() {
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        if (isNightMode){
            td_n.setTextColor(Color.WHITE);
            td_c.setTextColor(Color.WHITE);
            td_y.setTextColor(Color.WHITE);
        }

    }

    private void loadData() {

        ApiService.apiService.getTop3ComicNew().enqueue(new Callback<List<Comic>>() {
            @Override
            public void onResponse(Call<List<Comic>> call, Response<List<Comic>> response) {
                List<Comic> list = new ArrayList<>();
                list = response.body();
                ComicAdapter adapter = new ComicAdapter(list, getContext());
                rcvn.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Comic>> call, Throwable t) {

            }
        });
        ApiService.apiService.getTop3Favorites().enqueue(new Callback<List<Statistical>>() {
            @Override
            public void onResponse(Call<List<Statistical>> call, Response<List<Statistical>> response) {
                List<Statistical> list = new ArrayList<>();
                list = response.body();
                StatisticalAdapter adapter = new StatisticalAdapter(list, getContext());
                rcvf.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Statistical>> call, Throwable t) {

            }
        });
        ApiService.apiService.getTop3Comments().enqueue(new Callback<List<Statistical>>() {
            @Override
            public void onResponse(Call<List<Statistical>> call, Response<List<Statistical>> response) {
                List<Statistical> list = new ArrayList<>();
                list = response.body();
                StatisticalAdapter adapter = new StatisticalAdapter(list, getContext());
                rcvc.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Statistical>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
