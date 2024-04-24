package esisa.ac.ma.projet_natif.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import esisa.ac.ma.projet_natif.R;

import esisa.ac.ma.projet_natif.adapters.SmsAdapter;
import esisa.ac.ma.projet_natif.entities.Sms;

public class ConversationFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Sms> fullConversation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        recyclerView = view.findViewById(R.id.conversationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve full conversation from arguments
        Bundle args = getArguments();
        if (args != null) {
            fullConversation = (List<Sms>) args.getSerializable("fullConversation");
            if (fullConversation != null) {
                // Display full conversation
                SmsAdapter smsAdapter = new SmsAdapter(fullConversation);
                recyclerView.setAdapter(smsAdapter);
            }
        }

        Button backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to SmsFragment
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Button exportButton = requireActivity().findViewById(R.id.exportButton);
        exportButton.setVisibility(View.VISIBLE);
        View fragmentContainer = requireActivity().findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            fragmentContainer.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent));
        }
    }
}