package esisa.ac.ma.projet_natif.dal;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esisa.ac.ma.projet_natif.entities.Call;

public class CallDao {
    private Context context;

    public CallDao(Context context) {
        this.context = context;
    }

    public Map<String, List<Call>> getAllCallsGroupedByContact() {
        Map<String, List<Call>> callMap = new HashMap<>();
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

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

                if (!callMap.containsKey(contactNumber)) {
                    callMap.put(contactNumber, new ArrayList<>());
                }
                callMap.get(contactNumber).add(call);
            } while (cursor.moveToNext());

            cursor.close();
        }
        return callMap;
    }
}
