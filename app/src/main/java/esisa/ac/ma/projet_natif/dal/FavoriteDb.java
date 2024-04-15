package esisa.ac.ma.projet_natif.dal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import esisa.ac.ma.projet_natif.entities.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDb extends RoomDatabase {
    private static volatile FavoriteDb INSTANCE;

    public abstract FavoriteDao favoriteDao();

    public static FavoriteDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FavoriteDb.class, "favorite_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

