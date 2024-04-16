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

                // Check if there's already a list of messages for this contact
                if (smsMap.containsKey(senderPhoneNumber)) {
                    smsMap.get(senderPhoneNumber).add(new Sms(senderName, message, timestamp));
                } else {
                    List<Sms> smsList = new ArrayList<>();
                    smsList.add(new Sms(senderName, message, timestamp));
                    smsMap.put(senderPhoneNumber, smsList);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
        return smsMap;
    }
}
