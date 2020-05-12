package models.dao;

import models.News;

import java.util.List;

public interface NewsDao {

//    add new object
    void add(News news);

////    read from database
    List<News> getAllNewsForCompany();
    List<News> getAll();
//
//    find an object
    News findById(int id);
//
////    delete
    void deleteById(int id);
    void deleteAll();

}
