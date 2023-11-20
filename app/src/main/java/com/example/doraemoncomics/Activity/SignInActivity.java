package com.example.doraemoncomics.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doraemoncomics.Activity.Admin.MainActivityAdmin;
import com.example.doraemoncomics.Activity.User.MainActivity;
import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private TextView btn_redangKy;
    private Button btn_dangNhap;
    private TextInputLayout edL_taiKhoan, edL_matKhau;
    private TextInputEditText ed_taiKhoan, ed_matKhau;

    private List<User> list;
    private User userSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edL_taiKhoan = findViewById(R.id.edL_taiKhoan);
        edL_matKhau = findViewById(R.id.edL_matKhau);
        ed_taiKhoan = findViewById(R.id.ed_taiKhoan);
        ed_matKhau = findViewById(R.id.ed_matKhau);
        btn_redangKy = findViewById(R.id.btn_redangKy);
        btn_dangNhap = findViewById(R.id.btn_dangNhap);
        ed_taiKhoan.setText("chuhieu");
        ed_matKhau.setText("111111");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            User user = (User) bundle.get("userSignUp");
            if (user != null) {
                ed_taiKhoan.setText(user.getUsername());
                ed_matKhau.setText(user.getPassword());
            }
        }
        list = new ArrayList<>();
        getListUser();
        btn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDangNhap();
            }
        });
        btn_redangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void checkDangNhap() {
        if (validateInput()) {
            String taiKhoan = ed_taiKhoan.getText().toString().trim();
            String matKhau = ed_matKhau.getText().toString().trim();
            if (list == null || list.isEmpty()) {
                return;
            }
            boolean checkUser = false;
            for (User user : list) {
                if (taiKhoan.equals(user.getUsername()) && matKhau.equals(user.getPassword())) {
                    checkUser = true;
                    userSignIn = user;
                    break;
                }
            }
            if (checkUser) {
                Intent intent;
                if (userSignIn.getUsername().equals("admin")) {
                    // Lưu id và username vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", userSignIn.get_id());
                    editor.putString("username", userSignIn.getUsername());
                    editor.apply();
                    intent = new Intent(SignInActivity.this, MainActivityAdmin.class);

                } else {
                    // Lưu id và username vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId", userSignIn.get_id().toString());
                    editor.putString("username", userSignIn.getUsername());
                    editor.apply();
                    intent = new Intent(SignInActivity.this, MainActivity.class);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("userSignIn", userSignIn);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tài khoản và mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInput() {
        String taiKhoan = ed_taiKhoan.getText().toString();
        String matKhau = ed_matKhau.getText().toString();

        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (taiKhoan.length() < 5) {
            Toast.makeText(this, "Tài khoản phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (matKhau.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (taiKhoan.length() > 20) {
            Toast.makeText(this, "Tài khoản không được vượt quá 20 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (matKhau.length() > 20) {
            Toast.makeText(this, "Mật khẩu không được vượt quá 20 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getListUser() {
        ApiService.apiService.getListUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                list = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Tải dữ liệu người dùng thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}