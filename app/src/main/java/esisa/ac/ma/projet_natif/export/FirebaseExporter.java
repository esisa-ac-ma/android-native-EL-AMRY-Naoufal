package esisa.ac.ma.projet_natif.export;
import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import esisa.ac.ma.projet_natif.dal.CallDao;
import esisa.ac.ma.projet_natif.dal.ContactDao;
import esisa.ac.ma.projet_natif.dal.SmsDao;
import esisa.ac.ma.projet_natif.entities.Call;
import esisa.ac.ma.projet_natif.entities.Contact;
import esisa.ac.ma.projet_natif.entities.Sms;

public class FirebaseExporter {
    private DatabaseReference databaseReference;
    private CallDao callDao;
    private ContactDao contactDao;
    private SmsDao smsDao;

    public FirebaseExporter(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        callDao = new CallDao(context);
        contactDao = new ContactDao(context);
        smsDao = new SmsDao(context);
    }

    public void exportDataToFirebase() {
        exportCallsToFirebase();
        exportContactsToFirebase();
        exportSmsToFirebase();
    }

    private void exportCallsToFirebase() {
        Map<String, List<Call>> callMap = callDao.getAllCallsGroupedByContact();
        for (Map.Entry<String, List<Call>> entry : callMap.entrySet()) {
            String contactNumber = entry.getKey();
            List<Call> callList = entry.getValue();
            for (Call call : callList) {
                String callId = databaseReference.child("calls").child(contactNumber).push().getKey();
                databaseReference.child("calls").child(contactNumber).child(callId).setValue(call);
            }
        }
    }

    private void exportContactsToFirebase() {
        List<Contact> contacts = contactDao.getVcontact();
        for (Contact contact : contacts) {
            String contactId = databaseReference.child("contacts").push().getKey();
            databaseReference.child("contacts").child(contactId).setValue(contact);
        }
    }

    private void exportSmsToFirebase() {
        Map<String, List<Sms>> smsMap = smsDao.getAllSmsGroupedByContact();
        for (Map.Entry<String, List<Sms>> entry : smsMap.entrySet()) {
            String phoneNumber = entry.getKey();
            List<Sms> smsList = entry.getValue();
            for (Sms sms : smsList) {
                String smsId = databaseReference.child("sms").child(phoneNumber).push().getKey();
                databaseReference.child("sms").child(phoneNumber).child(smsId).setValue(sms);
            }
        }
    }
}