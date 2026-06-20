package com.aku.gitsimple;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class RepoListActivity extends AppCompatActivity {
    private RecyclerView rvRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);
        rvRepos = findViewById(R.id.rvRepos);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = getSharedPreferences("gitsimple", MODE_PRIVATE);
        String token = prefs.getString("token", "");
        fetchRepos(token);
    }

    private void fetchRepos(String token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.github.com/user/repos?sort=updated&per_page=50")
            .header("Authorization", "token " + token)
            .header("Accept", "application/vnd.github.v3+json")
            .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(RepoListActivity.this,
                    "Gagal konek: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONArray arr = new JSONArray(body);
                        List<JSONObject> list = new ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            list.add(arr.getJSONObject(i));
                        }
                        rvRepos.setAdapter(new RepoAdapter(list));
                    } catch (Exception e) {
                        Toast.makeText(RepoListActivity.this,
                            "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
