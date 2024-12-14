package esisa.ac.ma.projet_natif.dal;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

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
                String senderPhoneNumber = cursor.getString(addressIndex);
                String senderName = getContactNameFromNumber(senderPhoneNumber);
                if(senderName == null){
                    senderName = senderPhoneNumber;
                }
                Log.e("SmsDao", "Sender name : " + senderName);
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

    public String getContactNameFromNumber(String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                if (nameIndex != -1) {
                    String senderName = cursor.getString(nameIndex);
                    cursor.close();
                    return senderName;
                }
            }
            cursor.close();
        }

        return null;
    }
}
