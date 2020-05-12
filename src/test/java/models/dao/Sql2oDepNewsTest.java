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
    private static Sql2oNews sql2oNews;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        sql2oDepNews = new Sql2oDepNews(sql2o);
        sql2oNews = new Sql2oNews(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void canAddDepNewsToDatabase() {
        DepNews depNews = setUpDepNews();
            sql2oDepNews.addDep(depNews);
        DepNews retrieved = sql2oDepNews.getAllDep().get(0);
        assertEquals("Tech", retrieved.getTitle());
    }

    @Test
    public void canAddMoreThanOneDepNews(){
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();

            sql2oDepNews.addDep(depNews);
            sql2oDepNews.addDep(depNews1);

        int length = sql2oDepNews.getAllDep().size();

        assertEquals(2, length);
    }

    @Test
    public void canGetOnlyThoseWhoseTypeIsDepartment() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();

            sql2oDepNews.addDep(depNews);
            sql2oDepNews.addDep(depNews1);

        News news = setUpNews();

            sql2oNews.add(news);

        int depLength = sql2oDepNews.getAllDep().size();
        int compLength = sql2oNews.getAll().size();
        assertEquals(2, depLength);
        assertEquals(1, compLength);
    }

    @Test
    public void canFindDepNewsById() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();

        sql2oDepNews.addDep(depNews);
        sql2oDepNews.addDep(depNews1);

        DepNews retrieved = sql2oDepNews.findByIdDep(depNews1.getId());
        assertEquals(depNews1.getTitle(), retrieved.getTitle());
    }

    @Test
    public void canGetAllNewsForASingleDepartment() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();
        DepNews depNews2 =  setUpAnotherDepNews();

        sql2oDepNews.addDep(depNews);
        sql2oDepNews.addDep(depNews1);
        sql2oDepNews.addDep(depNews2);

        int dep1Length = sql2oDepNews.getAllNewsForDepartment(1).size();
        int dep2Length = sql2oDepNews.getAllNewsForDepartment(2).size();

        assertEquals(2, dep1Length);
        assertEquals(1, dep2Length);
    }

    @Test
    public void canDeleteDepNewsUsingId() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();

        sql2oDepNews.addDep(depNews);
        sql2oDepNews.addDep(depNews1);

        int fLength = sql2oDepNews.getAllDep().size();

        sql2oDepNews.deleteByIdDep(depNews.getId());

        int lLength = sql2oDepNews.getAllDep().size();

        assertEquals(2, fLength);
        assertEquals(1, lLength);
    }

    @Test
    public void canDeleteAllDepNews() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpAltDepNews();

        sql2oDepNews.addDep(depNews);
        sql2oDepNews.addDep(depNews1);

        assertTrue(sql2oDepNews.getAllDep().size() == 2);

        sql2oDepNews.deleteAllDep();

        assertTrue(sql2oDepNews.getAllDep().size() == 0);
    }


    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing the database");
        sql2oNews.deleteAll();
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

    public static DepNews setUpAnotherDepNews(){
        return new DepNews("Human Resource", "Not urgent", "Do not harass the other staff", 2, 2);
    }

    public static News setUpNews() {
        return new News("Human Resource", "Not urgent", "Do not harass the other staff", 1);
    }
}