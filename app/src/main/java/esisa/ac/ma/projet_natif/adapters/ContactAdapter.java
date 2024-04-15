package esisa.ac.ma.projet_natif.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Vector;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.dal.AppDatabase;
import esisa.ac.ma.projet_natif.dal.ContactDao;
import esisa.ac.ma.projet_natif.dal.FavoriteDao;
import esisa.ac.ma.projet_natif.entities.Contact;
import esisa.ac.ma.projet_natif.entities.Favorite;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Item> {

    private Vector<Contact> model = new Vector<>();
    private Vector<Contact> cache = new Vector<>();
    private ContactDao contactDao;
    private Context context;
    private FavoriteDao favoriteDao;

    public Vector<Contact> getModel() {
        return model;
    }

    public ContactAdapter(Context context) {

        this.context = context;
        contactDao = new ContactDao(context);
        favoriteDao = AppDatabase.getInstance(context).favoriteDao();
        model = contactDao.getVcontact();
       System.out.println(model);
    }



    class Item extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        TextView phones;
        ImageButton favoriteIcon;

        public Item(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            phones = itemView.findViewById(R.id.phones);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
        }
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        Contact contact = model.get(position);
        holder.name.setText(model.get(position).getName());
        holder.date.setText(model.get(position).getDate());
        StringBuilder phones = new StringBuilder();
        for (String p : model.get(position).getPhones()) {
            phones.append(p).append("\n");
        }
        holder.phones.setText(phones);

        boolean isFavorite = checkIfFavorite(contact);
        if (isFavorite) {
            holder.favoriteIcon.setImageResource(R.drawable.favorite);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.favorite);
        }

        holder.favoriteIcon.setOnClickListener(v -> {
            toggleFavorite(contact);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    private boolean checkIfFavorite(Contact contact) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                // Perform database operation asynchronously
                List<Favorite> favorites = favoriteDao.getAll();
                // Check if the contact is a favorite
                for (Favorite favorite : favorites) {
                    Log.d("ToggleFavorite", "Contact: " + favorite.getName() + ", Phone: " + favorite.getPhone());
                    if (favorite.getPhone().equals(contact.getPhones().get(0))) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                // Handle the result here
                // Update UI if needed
                // For example:
                if (isFavorite) {
                    // Set favorite icon
                } else {
                    // Set non-favorite icon
                }
            }
        };
        // Execute the AsyncTask
        task.execute();
        return false; // Return default value
    }

    private void toggleFavorite(Contact contact) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Access the database and perform operations asynchronously
                FavoriteDao favoriteDao = AppDatabase.getInstance(context).favoriteDao();
                boolean isFavorite = checkIfFavorite(contact);
                if (isFavorite) {
                    // Remove contact from favorites
                    Favorite favorite = new Favorite();
                    favorite.setPhone(contact.getPhones().get(0));
                    favoriteDao.delete(favorite);
                } else {
                    // Add contact to favorites only if it's not already a favorite
                    if (!isFavoriteInDatabase(contact)) {
                        Favorite favorite = new Favorite();
                        favorite.setPhone(contact.getPhones().get(0));
                        favorite.setName(contact.getName());
                        favoriteDao.add(favorite);
                    }
                }
            }
        }).start();
    }

    private boolean isFavoriteInDatabase(Contact contact) {
        FavoriteDao favoriteDao = AppDatabase.getInstance(context).favoriteDao();
        List<Favorite> favorites = favoriteDao.getAll();
        for (Favorite favorite : favorites) {
            if (favorite.getPhone().equals(contact.getPhones().get(0))) {
                return true;
            }
        }
        return false;
    }
}