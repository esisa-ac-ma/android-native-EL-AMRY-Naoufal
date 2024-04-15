package esisa.ac.ma.projet_natif.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.entities.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>  {

    private List<Favorite> favorites;

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(itemView);
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

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.favorite_name);
            phoneTextView = itemView.findViewById(R.id.favorite_phone);
            callsTextView = itemView.findViewById(R.id.favorite_calls);
            smsTextView = itemView.findViewById(R.id.favorite_sms);
        }

        public void bind(Favorite favorite) {
            nameTextView.setText(favorite.getName());
            phoneTextView.setText(favorite.getPhone());
            callsTextView.setText("Calls: " + favorite.getCalls());
            smsTextView.setText("SMS: " + favorite.getSms());
        }
    }
}
