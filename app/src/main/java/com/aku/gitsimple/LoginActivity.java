package com.aku.gitsimple;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etToken = findViewById(R.id.etToken);
        Button btnLogin = findViewById(R.id.btnLogin);
        SharedPreferences prefs = getSharedPreferences("gitsimple", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String token = etToken.getText().toString().trim();
            if (token.isEmpty()) {
                Toast.makeText(this, "Masukkan token dulu!", Toast.LENGTH_SHORT).show();
                return;
            }
            prefs.edit().putString("token", token).apply();
            startActivity(new Intent(this, RepoListActivity.class));
            finish();
        });
    }
}
