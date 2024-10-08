package esisa.ac.ma.projet_natif;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import esisa.ac.ma.projet_natif.adapters.FragmentAdapter;
import esisa.ac.ma.projet_natif.databinding.ActivityMainBinding;
import esisa.ac.ma.projet_natif.entities.Favorite;
import esisa.ac.ma.projet_natif.export.FirebaseExporter;
import esisa.ac.ma.projet_natif.services.ManageFavorite;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    private TabLayout tabLayout;
    private String[] titles;
    private ActivityMainBinding binding;
    private ManageFavorite manageFavorite;
    static final int[] icon_id={R.drawable.contact,R.drawable.sms,R.drawable.call_log, R.drawable.favorite};

    private FirebaseExporter firebaseExporter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manageFavorite=new ManageFavorite((this));
        viewPager=binding.viewPager;
        tabLayout=binding.tableLayout;
        fragmentAdapter=new FragmentAdapter(this);
        binding.viewPager.setAdapter(fragmentAdapter);
        titles=getResources().getStringArray(R.array.titles);
        init();
        new TabLayoutMediator(binding.tableLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                Log.d("room_data_test",titles[i]);

                tab.setText(titles[i]);
                tab.setIcon(icon_id[i]);
            }
        }).attach();

        Log.d("room_data_test",manageFavorite.getAll().toString());
    }

    public void exportData(View view) {
        Log.d("MainActivity", "Export Data button pressed");
        //firebaseExporter.exportDataToFirebase();
        //Toast.makeText(this, "Data exported to Firebase", Toast.LENGTH_SHORT).show();

    }

    public void init(){
        List<Favorite> results=manageFavorite.getAll();
        if (results.isEmpty()){
            Favorite favorite=new Favorite();
            favorite.setPhone("0600000000");
            favorite.setName("contact_0");
            manageFavorite.add(favorite);
        }
    }
}