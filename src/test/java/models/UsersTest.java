package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void canCreateAnInstanceOfUsers(){
        Users users = new Users("Daniel", "Male", "Manager", "Assigning roles", 1);
        assertTrue(users instanceof Users);
    }

    @Test
    public void checksToSeeIfTwoUsersAreSimilar() {
        Users users = setUpUser();
        Users users1 = setUpUser();

        assertTrue(users.equals(users1));
    }

    @Test
    public void checksTheContentOfDifferentUsers(){
        Users users = setUpUser();
        Users users1 = setUpAltUser();

        assertEquals("Daniel", users.getName());
        assertEquals("Faith", users1.getName());
    }

    @After
    public void tearDown() throws Exception {
    }

//    Helper classes
    public static Users setUpUser() {
        return new Users("Daniel", "Male", "Manager", "Assigning roles", 1);
    }

    public static Users setUpAltUser() {
        return new Users("Faith", "Female", "C.E.O", "Oversee-ing", 1);
    }
}