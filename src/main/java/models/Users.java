package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Users {

    private int id;
    private String name;
    private String gender;
    private String position;
    private List<String> roles;
    private int departmentid;

    public Users(String name, String gender, String position, List roles, int departmentid) {
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.roles = new ArrayList();
        this.departmentid = departmentid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List getRoles() {
        return roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;
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
        Users users = (Users) o;
        return id == users.id &&
                departmentid == users.departmentid &&
                name.equals(users.name) &&
                gender.equals(users.gender) &&
                position.equals(users.position) &&
                roles.equals(users.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gender, position, roles, departmentid);
    }
}
