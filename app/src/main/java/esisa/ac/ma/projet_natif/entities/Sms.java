package esisa.ac.ma.projet_natif.entities;

public class Sms {
    private String numberPhone;
    private String sender;
    private String message;
    private long timestamp;

    public Sms() {
    }

    public Sms(String numberPhone, String sender, String message, long timestamp) {
        this.numberPhone = numberPhone;
        this.sender = sender;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Sms(String sender, String message, long timestamp) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
