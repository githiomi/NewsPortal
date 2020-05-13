package models.dao;

import models.Departments;
import org.sql2o.*;

import java.util.List;

public class Sql2oDepartmentsDao {

    private final Sql2o sql2o;

    public Sql2oDepartmentsDao (Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public List<Departments> getAll(){
        String sql = "SELECT * FROM departments;";
        try( Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .executeAndFetch(Departments.class);
        }
    }

    public Departments findById(int id){
        String sql = "SELECT * FROM departments WHERE id = :id;";
        try (Connection conn = sql2o.open()){
            Departments departments = conn.createQuery(sql, true)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
            return departments;
        }
    }

}
