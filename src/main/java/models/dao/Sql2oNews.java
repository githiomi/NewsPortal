package models.dao;

import models.News;
import org.sql2o.*;

import java.util.List;

public class Sql2oNews implements NewsDao{

    private final Sql2o sql2o;

    public Sql2oNews (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(News news){
        String sql = "INSERT INTO news (title, type, urgency, content, createdat, userid) VALUES (:title, :type, :urgency, :content, :createdat, :userid);";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public News findById(int id){
        String sql = "SELECT * FROM news WHERE id = :id AND type = 'Company';";
        try (Connection conn = sql2o.open()){
            News news = conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(News.class);
            return news;
        }
    }

    @Override
    public List<News> getAll(){
        String sql = "SELECT * FROM news";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public List<News> getAllNewsForCompany(){
        String sql = "SELECT * FROM news WHERE type = 'Company';";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE FROM news WHERE id = :id;";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(){
        String sql = "DELETE FROM news *;";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }
    }

}
