package esisa.ac.ma.projet_natif.views;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
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
                // When an item is clicked, retrieve the full conversation and navigate to ConversationFragment
                Sms clickedSms = lastSmsList.get(position);
                String phoneNumber = clickedSms.getNumberPhone();
                if (smsMap.containsKey(phoneNumber)) {
                    // Navigate to ConversationFragment and pass the full conversation as arguments
                    navigateToConversationFragment(smsMap.get(phoneNumber));
                }
            }
            private void navigateToConversationFragment(List<Sms> fullConversation) {
                ConversationFragment conversationFragment = new ConversationFragment();
                Bundle args = new Bundle();

                args.putSerializable("fullConversation", (Serializable) fullConversation);
                conversationFragment.setArguments(args);

                Button exportButton = requireActivity().findViewById(R.id.exportButton);
                exportButton.setVisibility(View.GONE);

                View fragmentContainer = requireActivity().findViewById(R.id.fragment_container);
                if (fragmentContainer != null) {
                    fragmentContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.black));
                }

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, conversationFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}