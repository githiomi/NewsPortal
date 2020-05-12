package models.dao;

import models.Users;
import org.junit.After;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oUsersTest {

    private static Connection conn;
    private static Sql2oUsers sql2oUsers;

    @BeforeClass
    public static void setUp() throws Exception{
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "dhosio", "MaFaD@niel2019");
        sql2oUsers = new Sql2oUsers(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void canAddNewUserToDatabase() {
        Users users = setUpUser();
            sql2oUsers.add(users);
        Users retrieved = sql2oUsers.getAll().get(0);
        assertEquals(retrieved.getGender(), "Male");
    }

    @Test
    public void canAddMultipleUsersToDatabase() {
        Users users = setUpUser();
        Users users1 = setUpAltUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);

        int length = sql2oUsers.getAll().size();
        assertEquals(2, length);
    }

    @Test
    public void canGetAUserUsingTheirId(){
        Users users = setUpUser();
        Users users1 = setUpAltUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);

        Users retrieved = sql2oUsers.findById(users1.getId());
        assertEquals("Female", retrieved.getGender());
    }

    @Test
    public void canGetUsersAccordingToTheirDepartment() {
        Users users = setUpUser();
        Users users1 = setUpAltUser();
        Users users2 = setUpAnotherUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);
        sql2oUsers.add(users2);

        int dep1 = sql2oUsers.getAllInDepartment(1).size();
        int dep2 = sql2oUsers.getAllInDepartment(2).size();

        assertEquals(2, dep1);
        assertEquals(1, dep2);
    }

    @Test
    public void canGetAllUsersRegardlessOfDepId(){
        Users users = setUpUser();
        Users users1 = setUpAltUser();
        Users users2 = setUpAnotherUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);
        sql2oUsers.add(users2);

        int length = sql2oUsers.getAll().size();
        assertEquals(3, length);
    }


    @Test
    public void itIsPossibleToUpdateAUserInfo(){
        Users users = setUpUser();
            sql2oUsers.add(users);

        Users users1 = setUpAnotherUser();

        sql2oUsers.update(users.getId(), users1.getName(), users1.getPosition(), users1.getRole(), users1.getDepartmentid());
        Users retrieved = sql2oUsers.findById(users.getId());

        assertNotEquals(users.getName(), retrieved.getName());
        assertNotEquals(users.getPosition(), retrieved.getPosition());
        assertEquals("Cleaning", retrieved.getRole());
        assertEquals(2, retrieved.getDepartmentid());
    }

    @Test
    public void oneUserCanBeDeletedUsingId() {
        Users users = setUpUser();
        Users users1 = setUpAltUser();
        Users users2 = setUpAnotherUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);
        sql2oUsers.add(users2);

        int length = sql2oUsers.getAll().size();

        sql2oUsers.deleteById(users1.getId());

        int newLength = sql2oUsers.getAll().size();

        assertEquals(3, length);
        assertEquals(2, newLength);
    }

    @Test
    public void allUsersCanBeDeletedAtOnce() {
        Users users = setUpUser();
        Users users1 = setUpAltUser();
        Users users2 = setUpAnotherUser();

        sql2oUsers.add(users);
        sql2oUsers.add(users1);
        sql2oUsers.add(users2);

        sql2oUsers.deleteAll();

        assertEquals(0, sql2oUsers.getAll().size());
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database data");
        sql2oUsers.deleteAll();
    }

    @AfterClass
    public static void shutDown() throws Exception {
        System.out.println("Closing down the database");
        conn.close();
    }


//    Helper classes
    public static Users setUpUser(){
        return new Users("Daniel", "Male", "Manager", "Assigning roles", 1);
    }

    public static Users setUpAltUser() {
        return new Users ("Faith", "Female", "CEO", "Running the whole thing", 1);
    }

    public static Users setUpAnotherUser() {
        return new Users("Dhosio", "Male", "Worker", "Cleaning", 2);
    }
}