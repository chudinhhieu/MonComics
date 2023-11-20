package com.example.doraemoncomics.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doraemoncomics.Api.ApiService;
import com.example.doraemoncomics.Models.User;
import com.example.doraemoncomics.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout edL_dkHoTen, edL_dkTaiKhoan, edL_dkEmail, edL_dkMatKhau, edL_dkrMatKhau;
    private TextInputEditText ed_dkHoTen, ed_dkTaiKhoan, ed_dkEmail, ed_dkMatKhau, ed_dkrMatKhau;
    private Button btn_dangKy;
    private TextView btn_reDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edL_dkHoTen = findViewById(R.id.edL_dkHoTen);
        edL_dkTaiKhoan = findViewById(R.id.edL_dkTaiKhoan);
        edL_dkEmail = findViewById(R.id.edL_dkEmail);
        edL_dkMatKhau = findViewById(R.id.edL_dkMatKhau);
        edL_dkrMatKhau = findViewById(R.id.edL_dkrMatKhau);
        ed_dkHoTen = findViewById(R.id.ed_dkHoTen);
        ed_dkTaiKhoan = findViewById(R.id.ed_dkTaiKhoan);
        ed_dkEmail = findViewById(R.id.ed_dkEmail);
        ed_dkMatKhau = findViewById(R.id.ed_dkMatKhau);
        ed_dkrMatKhau = findViewById(R.id.ed_dkrMatKhau);
        btn_dangKy = findViewById(R.id.btn_dangKy);
        btn_reDangNhap = findViewById(R.id.btn_reDangNhap);
        btn_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDangKy();
            }
        });
        btn_reDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
    private void checkDangKy(){
        if(validateInput()){
            String hoTen = ed_dkHoTen.getText().toString().trim();
            String taiKhoan = ed_dkTaiKhoan.getText().toString().trim();
            String email = ed_dkEmail.getText().toString().trim();
            String matKhau = ed_dkMatKhau.getText().toString().trim();
            String rMatKhau = ed_dkrMatKhau.getText().toString().trim();
            User user = new User(hoTen,matKhau,email,taiKhoan,"avatar_default.png");
            ApiService.apiService.postUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userSignUp",user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(SignUpActivity.this, "Đăng kí thành công!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean validateInput() {
        String hoTen = ed_dkHoTen.getText().toString();
        String taiKhoan = ed_dkTaiKhoan.getText().toString();
        String email = ed_dkEmail.getText().toString();
        String matKhau = ed_dkMatKhau.getText().toString();
        String rMatKhau = ed_dkrMatKhau.getText().toString();

        if (hoTen.isEmpty() || taiKhoan.isEmpty() || email.isEmpty() || matKhau.isEmpty() || rMatKhau.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hoTen.length() < 4) {
            Toast.makeText(this, "Họ và tên phải có ít nhất 4 ký tự", Toast.LENGTH_SHORT).show();
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
        if (hoTen.length() > 20) {
            Toast.makeText(this, "Họ và tên không được vượt quá 20 ký tự", Toast.LENGTH_SHORT).show();
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
        if (!matKhau.equals(rMatKhau)) {
            Toast.makeText(this, "Mật khẩu và nhập lại mật khẩu phải giống nhau", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}