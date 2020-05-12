package models.dao;

import models.DepNews;
import models.News;
import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oNewsTest {

    private static Connection conn;
    private static Sql2oNews sql2oNews;


    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        sql2oNews = new Sql2oNews(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void canAddToDatabase(){
        News news = setUpNews();
        sql2oNews.add(news);

        News news1 = setUpAltNews();
        sql2oNews.add(news1);

        int id = news.getId();
        int id1 = news1.getId();
        assertEquals(id, news.getId());
        assertEquals(id1, news1.getId());
    }

    @Test
    public void canAddToDatabaseAndNoticed() {
        News news = setUpNews();
        sql2oNews.add(news);
        News retrieved = sql2oNews.getAll().get(0);
        assertEquals(retrieved.getContent(), news.getContent());
    }

    @Test
    public void canDifferentiateTwoNewsItems(){
        News news = setUpNews();
        News news1 = setUpAltNews();

            sql2oNews.add(news);
            sql2oNews.add(news1);

        assertEquals("Tech", sql2oNews.findById(news.getId()).getTitle());
        assertEquals("Human Resource", sql2oNews.findById(news1.getId()).getTitle());
    }

    @Test
    public void canFindAnObjectFromDatabase(){
        News news = setUpNews();
        sql2oNews.add(news);
        News retrieved = sql2oNews.getAll().get(0);
        assertEquals(retrieved.getContent(), news.getContent());
    }

    @Test
    public void canUseTheFindMethod(){
        News news = setUpNews();
            sql2oNews.add(news);
        News retrieved = sql2oNews.findById(news.getId());
        assertEquals(retrieved.getTitle(), news.getTitle());
    }

    @Test
    public void canGetAllInArray() {
        News news = setUpNews();
        News news1 = setUpAltNews();

        sql2oNews.add(news);
        sql2oNews.add(news1);

        assertEquals(2, sql2oNews.getAll().size());
    }

    @Test
    public void canDeleteOne() {
        News news = setUpNews();
        News news1 = setUpAltNews();

        sql2oNews.add(news);
        sql2oNews.add(news1);

        int fLength = sql2oNews.getAll().size();

        sql2oNews.deleteById(news1.getId());

        int lLength = sql2oNews.getAll().size();

        assertEquals(2, fLength);
        assertEquals(1, lLength);
    }

    @Test
    public void canGetNewsForACompany() {
        News news = setUpNews();
        News news1 = setUpDepNews();

        sql2oNews.add(news);
        sql2oNews.add(news1);

        int lLength = sql2oNews.getAllNewsForCompany().size();

        assertEquals(1, lLength);
    }



    @After
    public void tearDown() throws  Exception{
        System.out.println("Clearing the database");
        sql2oNews.deleteAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {

        System.out.println("Closing database connection!");
        conn.close();

    }

    //    Helper classes
    public static News setUpNews(){
        return new News("Tech", "Urgent", "Make sure to submit all commits", 1);
    }

    public static News setUpAltNews(){
        return new News("Human Resource", "Not urgent", "Do not harass the other staff", 1);
    }

    public static DepNews setUpDepNews() {
        return new DepNews("Human Resource",  "Not urgent", "Do not harass the other staff", 1, 1);
    }
}