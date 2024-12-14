package esisa.ac.ma.projet_natif.adapters;

import android.content.Context;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.entities.Call;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {
    private List<Call> callList;
    private Context context;

    public CallAdapter(List<Call> callList, Context context) {
        this.callList = callList;
        this.context = context;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_call, parent, false);
        return new CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        Call call = callList.get(position);
        holder.bind(call);
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

    static class CallViewHolder extends RecyclerView.ViewHolder {
        private TextView contactNumberTextView;
        private TextView datetimeTextView;
        private TextView durationTextView;
        private TextView callTypeTextView;

        CallViewHolder(@NonNull View itemView) {
            super(itemView);
            contactNumberTextView = itemView.findViewById(R.id.contact_number_text_view);
            datetimeTextView = itemView.findViewById(R.id.datetime_text_view);
            durationTextView = itemView.findViewById(R.id.duration_text_view);
            callTypeTextView = itemView.findViewById(R.id.call_type_text_view);
        }

        void bind(Call call) {
            contactNumberTextView.setText(call.getContactNumber());
            datetimeTextView.setText(formatDatetime(call.getDatetime()));
            durationTextView.setText(formatDuration(call.getDuration()));
            callTypeTextView.setText(getCallTypeString(call.getCallType()));
        }

        private String formatDatetime(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }

        private String formatDuration(long duration) {
            long minutes = (duration / 60);
            long seconds = (duration % 60);
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }

        private String getCallTypeString(int callType) {
            switch (callType) {
                case CallLog.Calls.INCOMING_TYPE:
                    return "Incoming";
                case CallLog.Calls.OUTGOING_TYPE:
                    return "Outgoing";
                case CallLog.Calls.MISSED_TYPE:
                    return "Missed";
                default:
                    return "Unknown";
            }
        }
    }
}
