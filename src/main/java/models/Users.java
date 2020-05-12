package models;

import java.util.Objects;

public class Users {

    private int id;
    private String name;
    private String gender;
    private String position;
    private String role;
    private int departmentid;

    public Users(String name, String gender, String position, String role, int departmentid) {
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        return departmentid == users.departmentid &&
                name.equals(users.name) &&
                gender.equals(users.gender) &&
                position.equals(users.position) &&
                role.equals(users.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, position, role, departmentid);
    }
}
