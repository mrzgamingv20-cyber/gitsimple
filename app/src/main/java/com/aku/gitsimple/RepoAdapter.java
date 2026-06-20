package com.aku.gitsimple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONObject;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private List<JSONObject> repos;

    public RepoAdapter(List<JSONObject> repos) {
        this.repos = repos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_repo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            JSONObject repo = repos.get(position);
            holder.tvName.setText(repo.getString("name"));
            String desc = repo.optString("description", "Tidak ada deskripsi");
            holder.tvDesc.setText(desc);
            int stars = repo.optInt("stargazers_count", 0);
            holder.tvStars.setText("⭐ " + stars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvStars;
        public ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvRepoName);
            tvDesc = v.findViewById(R.id.tvRepoDesc);
            tvStars = v.findViewById(R.id.tvRepoStars);
        }
    }
}
