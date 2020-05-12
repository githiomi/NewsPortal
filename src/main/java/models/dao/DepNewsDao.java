package models.dao;

import models.DepNews;

import java.util.List;

public interface DepNewsDao {

    //    add new object
    void addDep(DepNews depNews);


    //    read from database
    List<DepNews> getAllNewsForDepartment(int departmentid);
    List<DepNews> getAllDep();

//    find an object
    DepNews findByIdDep(int id);


//    delete
    void deleteByIdDep(int id);
    void deleteAllDep();

}