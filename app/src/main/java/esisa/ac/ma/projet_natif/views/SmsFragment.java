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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.adapters.SmsAdapter;
import esisa.ac.ma.projet_natif.dal.SmsDao;
import esisa.ac.ma.projet_natif.entities.Sms;

public class SmsFragment extends Fragment {

    private SmsDao smsDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_sms, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.smsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        smsDao = new SmsDao(requireContext());
        Map<String, Sms> smsMap = smsDao.getAllSmsGroupedByContact();

        List<Sms> smsList = new ArrayList<>(smsMap.values());
        SmsAdapter smsAdapter = new SmsAdapter(smsList);
        recyclerView.setAdapter(smsAdapter);

        smsAdapter.setOnItemClickListener(new SmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Sms clickedSms = smsList.get(position);
                // Example: Log the sender and message of the clicked SMS
                Log.d("SmsFragment", "Clicked SMS Sender: " + clickedSms.getSender());
                Log.d("SmsFragment", "Clicked SMS Message: " + clickedSms.getMessage());
            }
        });
    }
}
