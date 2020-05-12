package models;

import models.News;

import java.util.Objects;

public class DepNews extends News {

    public int departmentid;

    public static final String DEPARTMENT_TYPE = "Department";


    public DepNews(String title, String urgency, String content, int departmentid, int userid) {
        super(title, urgency, content, userid);

        this.type = DEPARTMENT_TYPE;
        this.departmentid = departmentid;
    }

    public int getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(int departmentid) {
        this.departmentid = departmentid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DepNews depNews = (DepNews) o;
        return departmentid == depNews.departmentid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), departmentid);
    }
}
