package esisa.ac.ma.projet_natif.views;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
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

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.adapters.CallAdapter;
import esisa.ac.ma.projet_natif.entities.Call;

public class CallFragment extends Fragment {

    private RecyclerView recyclerView;
    private CallAdapter callAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_call, container, false);
        recyclerView = rootView.findViewById(R.id.call_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadCallLogs();
        return rootView;
    }

    private void loadCallLogs() {
        List<Call> callList = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);

            do {
                String contactNumber = cursor.getString(numberIndex);
                long datetime = cursor.getLong(dateIndex);
                long duration = cursor.getLong(durationIndex);
                int callType = cursor.getInt(typeIndex);

                Call call = new Call(contactNumber, datetime, duration, callType);
                callList.add(call);
            } while (cursor.moveToNext());

            cursor.close();
        }

        callAdapter = new CallAdapter(callList, getContext());
        recyclerView.setAdapter(callAdapter);
    }
}
