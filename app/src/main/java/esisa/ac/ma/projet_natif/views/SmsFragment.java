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

        List<Sms> lastSmsList = new ArrayList<>();
        for (List<Sms> smsList : smsMap.values()) {
            if (!smsList.isEmpty()) {
                Sms lastSms = smsList.get(smsList.size() - 1);
                lastSmsList.add(lastSms);
            }
        }

        SmsAdapter smsAdapter = new SmsAdapter(lastSmsList);
        recyclerView.setAdapter(smsAdapter);

        smsAdapter.setOnItemClickListener(new SmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // When an item is clicked, retrieve the full conversation
                List<Sms> fullConversation = new ArrayList<>();
                Sms clickedSms = lastSmsList.get(position);
                String phoneNumber = clickedSms.getNumberPhone();
                if (smsMap.containsKey(phoneNumber)) {
                    fullConversation.addAll(smsMap.get(phoneNumber));
                }

                for (Sms sms : fullConversation) {
                    Log.d("FullConversation", "Sender Phone Number: " + sms.getNumberPhone());
                    Log.d("FullConversation", "Message: " + sms.getMessage());
                    Log.d("FullConversation", "Timestamp: " + sms.getTimestamp());
                }
            }
        });
    }
}