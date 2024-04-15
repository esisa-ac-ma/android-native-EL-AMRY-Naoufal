package esisa.ac.ma.projet_natif.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.adapters.FavoriteAdapter;
import esisa.ac.ma.projet_natif.dal.AppDatabase;
import esisa.ac.ma.projet_natif.dal.FavoriteDao;
import esisa.ac.ma.projet_natif.dal.FavoriteDb;
import esisa.ac.ma.projet_natif.entities.Favorite;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteDao favoriteDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.favorite_recycler);
        favoriteAdapter = new FavoriteAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favoriteAdapter);
        favoriteDao = AppDatabase.getInstance(getContext()).favoriteDao();
        loadFavorites();
    }

    private void loadFavorites() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Check if favoriteDao is null
                if (favoriteDao != null) {
                    // Retrieve favorites from the database asynchronously
                    List<Favorite> favorites = favoriteDao.getAll();
                    Log.d("favorie ftratgmen,et", "Contact: " + favorites);


                    // Update UI on the main thread
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI with favorites
                            favoriteAdapter.setFavorites(favorites);
                        }
                    });
                } else {
                    Log.e("FavoriteFragment", "favoriteDao is null");
                }
            }
        }).start();
    }
}
