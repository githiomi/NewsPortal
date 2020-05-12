package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepNewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void newObjectInstanceOfDepNews(){
        DepNews depNews = setUpDepNews();
        assertTrue(depNews instanceof DepNews);
    }

    @Test
    public void checksTheContentOfANewObject() {
        DepNews depNews = setUpDepNews();
        assertEquals("Tech", depNews.getTitle());
    }

    @Test
    public void checksThatTypeIsNotConfused() {
        News news = setUpNews();
        DepNews depNews = setUpDepNews();

        assertEquals(news.getFormattedCreatedat(), depNews.getFormattedCreatedat());
        assertNotEquals(news.getType(), depNews.getType());
    }

    @Test
    public void checksIfSimilarObjectsHAveBeenCreated() {
        DepNews depNews = setUpDepNews();
        DepNews depNews1 = setUpDepNews();

        assertTrue(depNews.equals(depNews1));
    }


    @After
    public void tearDown() throws Exception {
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