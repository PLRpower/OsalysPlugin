package fr.exolia.plugin.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    private String uuid;
    private String date;
    private String author;
    private String reason;

    public Report(String uuid, String date, String author, String reason) {
        this.uuid = uuid;
        this.date = date;
        this.author = author;
        this.reason = reason;
    }

    public Report(String uuid, String author, String reason) {
        this(uuid, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), author, reason);
    }

    public Report(String uniqueId) {
    }

    public String getUUID() {
        return uuid;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getReason() {
        return reason;
    }
}
