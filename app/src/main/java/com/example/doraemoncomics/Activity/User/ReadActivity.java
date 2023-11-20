package com.example.doraemoncomics.Activity.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.doraemoncomics.Adapters.User.ReadAdapter;
import com.example.doraemoncomics.R;

import java.util.List;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        RecyclerView recyclerView = findViewById(R.id.rcv_read);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Bundle extras = getIntent().getExtras();
        List<String> list = extras.getStringArrayList("listImage");
        ReadAdapter adapter = new ReadAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }
}