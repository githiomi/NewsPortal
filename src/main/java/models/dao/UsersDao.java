package models.dao;

import models.Users;

import java.util.List;

public interface UsersDao {

//    Add a new User
    void add(Users user);

//    Get all the users in the company
    List<Users> getAll();

//    Get users in a department
    List<Users> getAllInDepartment(int departmentid);

//    Find a user by id
    Users findById(int id);

//    Update a user's info
    void update(int id, String name, String position, String role, int departmentid);

//    delete
    void deleteById(int id);

    void deleteAll();
}
