package esisa.ac.ma.projet_natif.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import esisa.ac.ma.projet_natif.R;
import esisa.ac.ma.projet_natif.entities.Sms;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {
    private List<Sms> smsList;
    private OnItemClickListener onItemClickListener;

    public SmsAdapter(List<Sms> smsList) {
        this.smsList = smsList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sms, parent, false);
        return new SmsViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        Sms sms = smsList.get(position);
        holder.senderTextView.setText(sms.getSender());
        holder.messageTextView.setText(sms.getMessage());
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    static class SmsViewHolder extends RecyclerView.ViewHolder {
        TextView senderTextView;
        TextView messageTextView;

        SmsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.senderTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
