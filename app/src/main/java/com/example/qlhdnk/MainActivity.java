package com.example.qlhdnk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.editTextUsername);
        passwordEditText = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Kiểm tra đăng nhập (đơn giản, có thể thay thế bằng kiểm tra từ server)
        User user = new User("1", "1");
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
            // Đăng nhập thành công, chuyển đến màn hình chính
            Intent intent = new Intent(MainActivity.this, QuanLyThongTin.class);
            startActivity(intent);
            finish(); // Đóng LoginActivity để người dùng không thể quay lại màn hình đăng nhập từ MainActivity
        } else {
            // Đăng nhập không thành công, hiển thị thông báo hoặc thực hiện các xử lý khác
            Toast.makeText(this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
        }
    }
}