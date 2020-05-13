package models.dao;

import models.Departments;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2ODepartmentsTest {

    private static Sql2oDepartments sql2oDepartmentsDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        sql2oDepartmentsDao = new Sql2oDepartments(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void getsAll5Departments(){
        sql2oDepartmentsDao.getAll();
        int length = sql2oDepartmentsDao.getAll().size();
        assertEquals(5, length);
    }

    @Test
    public void canGetASpecificDepartmentOnCommand() {
        Departments departments = sql2oDepartmentsDao.findById(3);
        assertEquals("Finance", departments.getName());
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Cannot clear the database");
    }

    @AfterClass
    public static void shutDown() {
        System.out.println("Closing database connection");
        conn.close();
    }
}