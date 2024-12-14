package esisa.ac.ma.projet_natif.entities;

public class Call {
    private String contactNumber;
    private long datetime;
    private long duration;
    private int callType;

    public Call(String contactNumber, long datetime, long duration, int callType) {
        this.contactNumber = contactNumber;
        this.datetime = datetime;
        this.duration = duration;
        this.callType = callType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }
}
