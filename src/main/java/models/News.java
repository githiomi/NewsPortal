package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class News {

    public int id;
    public String title;
    public String type;
    public String urgency;
    public String content;
    public long createdat;
    public String formattedCreatedat;

    public static final String COMPANY_TYPE = "Company";
    public static final String dateFormat = "E dd/MM/yyyy @ K:mm a";

    public News(String title, String type, String urgency, String content, long createdat) {
        this.title = title;
        this.type = COMPANY_TYPE;
        this.urgency = urgency;
        this.content = content;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedat();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat() {
        this.createdat = System.currentTimeMillis();
    }

    public String getFormattedCreatedat() {
        Date date = new Date(createdat);
        String format = dateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public void setFormattedCreatedat() {
        Date date = new Date(createdat);
        String format = dateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        this.formattedCreatedat = sdf.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                createdat == news.createdat &&
                title.equals(news.title) &&
                type.equals(news.type) &&
                urgency.equals(news.urgency) &&
                content.equals(news.content) &&
                formattedCreatedat.equals(news.formattedCreatedat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, urgency, content, createdat, formattedCreatedat);
    }
}
