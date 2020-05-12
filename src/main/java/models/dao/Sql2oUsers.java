package models.dao;

import models.Users;
import org.sql2o.*;

import java.util.List;

public class Sql2oUsers implements UsersDao {

    private Sql2o sql2o;

    public Sql2oUsers (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Users users){
        String sql = "INSERT INTO users (name, gender, position, role, departmentid) VALUES (:name, :gender, :position, :role, :departmentid);";
        try (Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(users)
                    .executeUpdate()
                    .getKey();
            users.setId(id);
        }
        catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Users findById(int id){
        String sql = "SELECT * FROM users WHERE id = :id;";
        try (Connection conn = sql2o.open()){
            Users user = conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Users.class);
            return user;
        }
    }

    @Override
    public List<Users> getAllInDepartment(int departmentid){
        String sql = "SELECT * FROM users WHERE departmentid = :departmentid;";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql, true)
                    .addParameter("departmentid", departmentid)
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public List<Users> getAll() {
        String sql = "SELECT * FROM users;";
        try (Connection conn = sql2o.open()){
            return conn.createQuery(sql, true)
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public void deleteById(int id){
        String sql = "DELETE FROM users WHERE id = :id;";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql, true)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    @Override
    public void deleteAll(){
        String sql = "DELETE FROM users *;";
        try (Connection conn = sql2o.open()){
            conn.createQuery(sql, true)
            .executeUpdate();
        }
    }
}
