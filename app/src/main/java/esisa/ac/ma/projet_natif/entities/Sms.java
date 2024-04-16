package esisa.ac.ma.projet_natif.entities;

public class Sms {
    private String sender;
    private String message;
    private long timestamp;

    public Sms() {
    }

    public Sms(String sender, String message, long timestamp) {
        this.sender = sender;
        this.timestamp = timestamp;
        this.message = message;
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
