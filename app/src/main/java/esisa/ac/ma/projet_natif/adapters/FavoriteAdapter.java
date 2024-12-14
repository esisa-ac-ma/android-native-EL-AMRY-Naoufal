package esisa.ac.ma.projet_natif.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.dal.AppDatabase;
import esisa.ac.ma.projet_natif.dal.FavoriteDao;
import esisa.ac.ma.projet_natif.entities.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<Favorite> favorites;
    private FavoriteDao favoriteDao;

    public FavoriteAdapter() {
    }

    public FavoriteAdapter(Context context) {
        favoriteDao = AppDatabase.getInstance(context).favoriteDao();
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(itemView, favoriteDao, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);
        holder.bind(favorite);
    }

    @Override
    public int getItemCount() {
        return favorites != null ? favorites.size() : 0;
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
        private TextView callsTextView;
        private TextView smsTextView;
        private ImageButton unfavoriteButton;
        private FavoriteDao favoriteDao;

        private FavoriteAdapter adapter;
        public FavoriteViewHolder(@NonNull View itemView, FavoriteDao favoriteDao, FavoriteAdapter adapter) {
            super(itemView);
            this.favoriteDao = favoriteDao;
            this.adapter = adapter;
            nameTextView = itemView.findViewById(R.id.favorite_name);
            phoneTextView = itemView.findViewById(R.id.favorite_phone);
            callsTextView = itemView.findViewById(R.id.favorite_calls);
            smsTextView = itemView.findViewById(R.id.favorite_sms);
            unfavoriteButton = itemView.findViewById(R.id.unfavorite_button);
        }

        public void bind(Favorite favorite) {
            nameTextView.setText(favorite.getName());
            phoneTextView.setText(favorite.getPhone());
            callsTextView.setText("Calls: " + favorite.getCalls());
            smsTextView.setText("SMS: " + favorite.getSms());

            if (unfavoriteButton != null) {
                unfavoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (favoriteDao != null) {
                            removeFavoriteFromDatabase(favorite);
                        } else {
                            Log.e("FavoriteViewHolder", "FavoriteDao is null");
                        }
                    }
                });
            }
        }

        private void removeFavoriteFromDatabase(Favorite favorite) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    favoriteDao.delete(favorite);
                    // Update UI after removing the favorite
                    List<Favorite> updatedFavorites = favoriteDao.getAll();
                    ((Activity)itemView.getContext()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setFavorites(updatedFavorites);
                        }
                    });
                }
            }).start();
        }
    }
}
