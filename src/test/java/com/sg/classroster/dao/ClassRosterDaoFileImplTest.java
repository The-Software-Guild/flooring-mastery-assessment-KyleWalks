/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.classroster.dao;

import com.sg.classroster.dto.Student;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Cosmos
 */
public class ClassRosterDaoFileImplTest {
    
    ClassRosterDao testDao;
    
    public ClassRosterDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testroster.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new ClassRosterDaoFileImpl(testFile);
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addStudent method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testAddGetStudent() throws Exception {
        // Create our method test inputs
        String studentId = "0001";
        Student student = new Student(studentId);
        student.setFirstName("Ada");
        student.setLastName("Lovelace");
        student.setCohort("Java-May-1845");

        //  Add the student to the DAO
        testDao.addStudent(studentId, student);
        // Get the student from the DAO
        Student retrievedStudent = testDao.getStudent(studentId);

        // Check the data is equal
        assertEquals(student.getStudentId(),
                    retrievedStudent.getStudentId(),
                    "Checking student id.");
        assertEquals(student.getFirstName(),
                    retrievedStudent.getFirstName(),
                    "Checking student first name.");
        assertEquals(student.getLastName(), 
                    retrievedStudent.getLastName(),
                    "Checking student last name.");
        assertEquals(student.getCohort(), 
                    retrievedStudent.getCohort(),
                    "Checking student cohort.");
    }

    /**
     * Test of getAllStudents method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testAddGetAllStudents() throws Exception {
        // Create our first student
        Student firstStudent = new Student("0001");
        firstStudent.setFirstName("Ada");
        firstStudent.setLastName("Lovelace");
        firstStudent.setCohort("Java-May-1845");

        // Create our second student
        Student secondStudent = new Student("0002");
        secondStudent.setFirstName("Charles");
        secondStudent.setLastName("Babbage");
        secondStudent.setCohort(".NET-May-1845");

        // Add both our students to the DAO
        testDao.addStudent(firstStudent.getStudentId(), firstStudent);
        testDao.addStudent(secondStudent.getStudentId(), secondStudent);

        // Retrieve the list of all students within the DAO
        List<Student> allStudents = testDao.getAllStudents();

        // First check the general contents of the list
        assertNotNull(allStudents, "The list of students must not null");
        assertEquals(2, allStudents.size(),"List of students should have 2 students.");

        // Then the specifics
        assertTrue(testDao.getAllStudents().contains(firstStudent),
                    "The list of students should include Ada.");
        assertTrue(testDao.getAllStudents().contains(secondStudent),
                "The list of students should include Charles.");
    }

    /**
     * Test of getStudent method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testGetStudent() throws Exception {
        System.out.println("getStudent");
        String studentId = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        Student expResult = null;
        Student result = instance.getStudent(studentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeStudent method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testRemoveStudent() throws Exception {
        // Create two new students
        Student firstStudent = new Student("0001");
        firstStudent.setFirstName("Ada");
        firstStudent.setLastName("Lovelace");
        firstStudent.setCohort("Java-May-1945");

        Student secondStudent = new Student("0002");
        secondStudent.setFirstName("Charles");
        secondStudent.setLastName("Babbage");
        secondStudent.setCohort(".NET-May-1945");

        // Add both to the DAO
        testDao.addStudent(firstStudent.getStudentId(), firstStudent);
        testDao.addStudent(secondStudent.getStudentId(), secondStudent);

        // remove the first student - Ada
        Student removedStudent = testDao.removeStudent(firstStudent.getStudentId());

        // Check that the correct object was removed.
        assertEquals(removedStudent, firstStudent, "The removed student should be Ada.");

        // Get all the students
        List<Student> allStudents = testDao.getAllStudents();

        // First check the general contents of the list
        assertNotNull( allStudents, "All students list should be not null.");
        assertEquals( 1, allStudents.size(), "All students should only have 1 student.");

        // Then the specifics
        assertFalse( allStudents.contains(firstStudent), "All students should NOT include Ada.");
        assertTrue( allStudents.contains(secondStudent), "All students should NOT include Charles.");    

        // Remove the second student
        removedStudent = testDao.removeStudent(secondStudent.getStudentId());
        // Check that the correct object was removed.
        assertEquals( removedStudent, secondStudent, "The removed student should be Charles.");

        // retrieve all of the students again, and check the list.
        allStudents = testDao.getAllStudents();

        // Check the contents of the list - it should be empty
        assertTrue( allStudents.isEmpty(), "The retrieved list of students should be empty.");

        // Try to 'get' both students by their old id - they should be null!
        Student retrievedStudent = testDao.getStudent(firstStudent.getStudentId());
        assertNull(retrievedStudent, "Ada was removed, should be null.");

        retrievedStudent = testDao.getStudent(secondStudent.getStudentId());
        assertNull(retrievedStudent, "Charles was removed, should be null.");

    }

    /**
     * Test of print method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        String msg = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        instance.print(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readString method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadString() {
        System.out.println("readString");
        String msgPrompt = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        String expResult = "";
        String result = instance.readString(msgPrompt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readInt method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadInt_String() {
        System.out.println("readInt");
        String msgPrompt = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        int expResult = 0;
        int result = instance.readInt(msgPrompt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readInt method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadInt_3args() {
        System.out.println("readInt");
        String msgPrompt = "";
        int min = 0;
        int max = 0;
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        int expResult = 0;
        int result = instance.readInt(msgPrompt, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readLong method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadLong_String() {
        System.out.println("readLong");
        String msgPrompt = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        long expResult = 0L;
        long result = instance.readLong(msgPrompt);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readLong method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadLong_3args() {
        System.out.println("readLong");
        String msgPrompt = "";
        long min = 0L;
        long max = 0L;
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        long expResult = 0L;
        long result = instance.readLong(msgPrompt, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFloat method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadFloat_String() {
        System.out.println("readFloat");
        String msgPrompt = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        float expResult = 0.0F;
        float result = instance.readFloat(msgPrompt);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readFloat method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadFloat_3args() {
        System.out.println("readFloat");
        String msgPrompt = "";
        float min = 0.0F;
        float max = 0.0F;
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        float expResult = 0.0F;
        float result = instance.readFloat(msgPrompt, min, max);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readDouble method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadDouble_String() {
        System.out.println("readDouble");
        String msgPrompt = "";
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        double expResult = 0.0;
        double result = instance.readDouble(msgPrompt);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readDouble method, of class ClassRosterDaoFileImpl.
     */
    @Test
    public void testReadDouble_3args() {
        System.out.println("readDouble");
        String msgPrompt = "";
        double min = 0.0;
        double max = 0.0;
        ClassRosterDaoFileImpl instance = new ClassRosterDaoFileImpl();
        double expResult = 0.0;
        double result = instance.readDouble(msgPrompt, min, max);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
