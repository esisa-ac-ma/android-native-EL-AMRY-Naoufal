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
        Map<String, List<Sms>> smsMap = smsDao.getAllSmsGroupedByContact();

        List<List<Sms>> smsConversations = new ArrayList<>(smsMap.values());
        List<Sms> allSms = new ArrayList<>();
        for (List<Sms> conversation : smsConversations) {
            allSms.addAll(conversation);
        }

        SmsAdapter smsAdapter = new SmsAdapter(allSms);
        recyclerView.setAdapter(smsAdapter);

        smsAdapter.setOnItemClickListener(new SmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                List<Sms> conversation = smsConversations.get(position);
                // Example: Log the full conversation
                for (Sms sms : conversation) {
                    Log.d("SmsFragment", "Sender: " + sms.getSender() + ", Message: " + sms.getMessage());
                }
            }
        });
    }
}
