package esisa.ac.ma.projet_natif.dal;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esisa.ac.ma.projet_natif.entities.Sms;

public class SmsDao {
    private Context context;

    public SmsDao(Context context) {
        this.context = context;
    }

    public Map<String, List<Sms>> getAllSmsGroupedByContact() {
        Map<String, List<Sms>> smsMap = new HashMap<>();
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int personIndex = cursor.getColumnIndex(Telephony.Sms.PERSON);
            int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int bodyIndex = cursor.getColumnIndex(Telephony.Sms.BODY);
            int dateIndex = cursor.getColumnIndex(Telephony.Sms.DATE);

            do {
                String senderName = cursor.getString(personIndex);
                String senderPhoneNumber = cursor.getString(addressIndex);
                String message = cursor.getString(bodyIndex);
                long timestamp = cursor.getLong(dateIndex);

                // Check if column indices are valid
                if (personIndex != -1 && addressIndex != -1 && bodyIndex != -1 && dateIndex != -1) {
                    Sms sms = new Sms(senderPhoneNumber, senderName, message, timestamp);
                    if (!smsMap.containsKey(senderPhoneNumber)) {
                        smsMap.put(senderPhoneNumber, new ArrayList<>());
                    }
                    smsMap.get(senderPhoneNumber).add(sms);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
        return smsMap;
    }
}
