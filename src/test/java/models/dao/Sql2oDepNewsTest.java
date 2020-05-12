package models.dao;

import models.DepNews;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oDepNewsTest {

    private static Connection conn;
    private static Sql2oDepNews sql2oDepNews;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        sql2oDepNews = new Sql2oDepNews(sql2o);
        conn = sql2o.open();
    }

    @Test


    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing the database");
        sql2oDepNews.deleteAllDep();
    }

    @AfterClass
    public static void shutDown() throws Exception{

        System.out.println("Closing database connection");
        conn.close();
    }

    //    Helper classes
    public static DepNews setUpDepNews(){
        return new DepNews("Tech",  "Urgent", "Make sure to submit all commits", 1, 1);
    }

    public static DepNews setUpAltDepNews(){
        return new DepNews("Human Resource", "Not urgent", "Do not harass the other staff", 1, 1);
    }

    public static News setUpNews() {
        return new News("Human Resource", "Not urgent", "Do not harass the other staff", 1);
    }
}