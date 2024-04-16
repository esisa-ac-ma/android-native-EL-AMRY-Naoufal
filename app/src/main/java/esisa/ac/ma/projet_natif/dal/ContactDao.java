package esisa.ac.ma.projet_natif.dal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import androidx.loader.content.CursorLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import esisa.ac.ma.projet_natif.entities.Contact;

import android.provider.CallLog;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.Telephony;
import android.util.Log;

public class ContactDao {
    private final Vector<Contact> vcontact = new Vector<>();
    private final Context ctx;
    private SimpleDateFormat simpleDateFormat;
    public ContactDao(Context ctx) {
        this.ctx = ctx;
        load();
    }
    public Vector<Contact> getVcontact() {
        return vcontact;
    }
    public void load() {
        try (Cursor contacts = new CursorLoader(ctx, Contacts.CONTENT_URI, null,
                null, null, null).loadInBackground()) {
            Contact c;
            if (contacts != null && contacts.getCount() != 0) {
                while (contacts.moveToNext()) {
                    c = new Contact();
                    @SuppressLint("Range") String id = contacts.getString(contacts.getColumnIndex(Contacts._ID));
                    @SuppressLint("Range") String name = contacts.getString(contacts.getColumnIndex(Contacts.DISPLAY_NAME));
                    //date in ms
                    @SuppressLint("Range") long date = contacts.getLong(contacts.getColumnIndex(Contacts.CONTACT_LAST_UPDATED_TIMESTAMP));
                    c.setName(name);
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                    c.setDate(simpleDateFormat.format(new Date(date)));
                    c.setPhones(getPhones(id));
                    c.setEmails(getEmails(id));

                    vcontact.add(c);
                    }
                }
            }
        }
    public Vector<String> getPhones(String contact_id){
        Vector<String> phones = new Vector<>();
        String[] phone = {Phone.NUMBER};
        try (Cursor cursor = ctx.getContentResolver().query(Phone.CONTENT_URI,
                phone, contact_id + "=" + Phone.CONTACT_ID, null, null)) {
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    phones.add(cursor.getString(0));
                }
            }
        }
        return phones;
    }
    public Vector<String> getEmails(String contact_id){
        Vector<String> emails = new Vector<>();
        String[] email = {Email.ADDRESS};
        try (Cursor cursor = ctx.getContentResolver().query(Email.CONTENT_URI,
                email, contact_id + "=" +Email.CONTACT_ID, null, null)) {
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    emails.add(cursor.getString(0));
                }
            }
        }
        return emails;
    }

    public int getSMSCountForContact(String phoneNumber) {
        int smsCount = 0;
        Cursor cursor = ctx.getContentResolver().query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int addressIndex = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            if (addressIndex != -1) {
                while (cursor.moveToNext()) {
                    String address = cursor.getString(addressIndex);
                    if (phoneNumber.equals(address)) {
                        smsCount++;
                    }
                }
            }
            cursor.close();
        }
        return smsCount;
    }

    public int getCallsCountForContact(String phoneNumber) {
        int callsCount = 0;
        Cursor cursor = ctx.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            if (numberIndex != -1) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(numberIndex);
                    if (phoneNumber.equals(number)) {
                        callsCount++;
                    }
                }
            }
            cursor.close();
        }
        return callsCount;
    }
}



