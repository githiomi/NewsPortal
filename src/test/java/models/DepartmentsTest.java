package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void canCreateAnInstanceOfDepartment() {
        Departments departments = new Departments("Management");
        assertTrue(departments instanceof Departments);
    }

    @Test
    public void checksIfTwoInstancesAreSimilar() {
        Departments departments = new Departments("Management");
        Departments departments1 = new Departments("Management");

        assertTrue(departments.equals(departments1));
    }

    @After
    public void tearDown() throws Exception {
    }
}