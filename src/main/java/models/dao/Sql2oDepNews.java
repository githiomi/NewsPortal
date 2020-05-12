package models.dao;

import models.DepNews;
import models.News;
import org.sql2o.*;

import java.util.List;

public class Sql2oDepNews implements DepNewsDao{

    private final Sql2o sql2o;

    public Sql2oDepNews(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void addDep(DepNews depNews) {
        String sql = "INSERT INTO news (title, type, urgency, content, createdat, userid, departmentid) VALUES (:title, :type, :urgency, :content, :createdat, :userid, :departmentid);";
        try (Connection conn = sql2o.open()) {
            int id = (int) conn.createQuery(sql, true)
                    .bind(depNews)
                    .executeUpdate()
                    .getKey();
            depNews.setId(id);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public DepNews findByIdDep(int id){
        String sql = "SELECT * FROM news WHERE id = :id AND type = 'Department';";
        try (Connection conn = sql2o.open()){
            DepNews depNews = conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(DepNews.class);
            return depNews;
        }
    }

    @Override
    public List<DepNews> getAllDep(){
        String sql = "SELECT * FROM news WHERE type = 'Department';";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(DepNews.class);
        }
    }

    @Override
    public List<DepNews> getAllNewsForDepartment(int departmentid){
        String sql = "SELECT * FROM news WHERE departmentid = :departmentid AND type = 'Department';";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .addParameter("departmentid", departmentid)
                    .executeAndFetch(DepNews.class);
        }
    }

    @Override
    public void deleteByIdDep(int id){
        String sql = "DELETE FROM news WHERE id = :id AND type = 'Department';";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAllDep(){
        String sql = "DELETE FROM news * WHERE type = 'Department';";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }
    }


}
