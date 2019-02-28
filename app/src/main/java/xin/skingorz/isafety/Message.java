package xin.skingorz.isafety;

import java.util.Date;

public class Message {

    private String message;
    private String to;
    private String fromEmail;
    private Date date;

    public Message(String message, String toEmail) {
        String email = new User().getUserFromCache().getEmail();
        this.fromEmail = email;
        this.message = message;
        this.to = toEmail;
        this.date = new Date(System.currentTimeMillis());
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

}