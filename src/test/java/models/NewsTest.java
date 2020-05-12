package models;

import models.dao.Sql2oNews;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp(){

    }

    @Test
    public void createsAnInstanceOfNews(){
        News news = setUpNews();
        assertTrue(news instanceof News);
    }

    @Test
    public void checksToSeeIfItCanSpotSimilarNews(){
        News news = setUpNews();
        News news1 = setUpNews();
        assertTrue(news.equals(news1));
        assertEquals(news.getFormattedCreatedat(), news1.getFormattedCreatedat());
        assertEquals(news.getContent(), news1.getContent());
    }

    @Test
    public void confirmsDateIsInTheDesiredFormat() {
        News news = setUpNews();
        assertEquals(news.getFormattedCreatedat(), news.getFormattedCreatedat());
    }

    @After
    public void tearDown() throws Exception {
    }


//    Helper classes
    public static News setUpNews(){
        return new News("Tech", "Urgent", "Make sure to submit all commits", 1);
    }

    public static News setUpAltNews(){
        return new News("Human Resource", "Not urgent", "Do not harass the other staff", 1);
    }
}