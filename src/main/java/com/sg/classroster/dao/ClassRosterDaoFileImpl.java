/*********************************
* The Software Guild
* Copyright (C) 2020 Wiley edu LLC - All Rights Reserved
*********************************/
package com.sg.classroster.dao;

/**
 * TSG Official Implementation of the UserIO interface
 * May your view be ever in your favor!
 * @author ahill
 */
import com.sg.classroster.dto.Student;
import com.sg.classroster.ui.UserIO;

import java.io.*;
import java.util.*;

public class ClassRosterDaoFileImpl implements UserIO, ClassRosterDao {

    final private Scanner console = new Scanner(System.in);

    private Map<String, Student> students = new HashMap<>();

    private final String ROSTER_FILE;
    public static final String DELIMITER = "::";

    public ClassRosterDaoFileImpl(){
    ROSTER_FILE = "roster.txt";
    }

    public ClassRosterDaoFileImpl(String rosterTextFile){
        ROSTER_FILE = rosterTextFile;
    }
    
    private Student unmarshallStudent(String studentAsText){
        // studentAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // 1234::Ada::Lovelace::Java-September1842
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in studentTokens.
        // Which should look like this:
        // ______________________________________
        // |    |   |        |                  |
        // |1234|Ada|Lovelace|Java-September1842|
        // |    |   |        |                  |
        // --------------------------------------
        //  [0]  [1]    [2]         [3]
        String[] studentTokens = studentAsText.split(DELIMITER);

        // Given the pattern above, the student Id is in index 0 of the array.
        String studentId = studentTokens[0];

        // Which we can then use to create a new Student object to satisfy
        // the requirements of the Student constructor.
        Student studentFromFile = new Student(studentId);

        // However, there are 3 remaining tokens that need to be set into the
        // new student object. Do this manually by using the appropriate setters.

        // Index 1 - FirstName
        studentFromFile.setFirstName(studentTokens[1]);

        // Index 2 - LastName
        studentFromFile.setLastName(studentTokens[2]);

        // Index 3 - Cohort
        studentFromFile.setCohort(studentTokens[3]);

        // We have now created a student! Return it!
        return studentFromFile;
    }

    private String marshallStudent(Student aStudent){
        // We need to turn a Student object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // 4321::Charles::Babbage::Java-September1842

        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer.

        // Start with the student id, since that's supposed to be first.
        String studentAsText = aStudent.getStudentId() + DELIMITER;

        // add the rest of the properties in the correct order:

        // FirstName
        studentAsText += aStudent.getFirstName() + DELIMITER;

        // LastName
        studentAsText += aStudent.getLastName() + DELIMITER;

        // Cohort - don't forget to skip the DELIMITER here.
        studentAsText += aStudent.getCohort();

        // We have now turned a student to text! Return it!
        return studentAsText;
    }

    /**
     * Writes all students in the roster out to a ROSTER_FILE.  See loadRoster
     * for file format.
     *
     * @throws ClassRosterPersistenceException if an error occurs writing to the file
     */
    private void writeRoster() throws ClassRosterPersistenceException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to
        // handle any errors that occur.
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ROSTER_FILE));
        } catch (IOException e) {
            throw new ClassRosterPersistenceException(
                    "Could not save student data.", e);
        }

        // Write out the Student objects to the roster file.
        // NOTE TO THE APPRENTICES: We could just grab the student map,
        // get the Collection of Students and iterate over them but we've
        // already created a method that gets a List of Students so
        // we'll reuse it.
        String studentAsText;
        List<Student> studentList = this.getAllStudents();
        for (Student currentStudent : studentList) {
            // turn a Student into a String
            studentAsText = marshallStudent(currentStudent);
            // write the Student object to the file
            out.println(studentAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    private void loadRoster() throws ClassRosterPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(ROSTER_FILE)));
        } catch (FileNotFoundException e) {
            throw new ClassRosterPersistenceException(
                    "-_- Could not load roster data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentStudent holds the most recent student unmarshalled
        Student currentStudent;
        // Go through ROSTER_FILE line by line, decoding each line into a
        // Student object by calling the unmarshallStudent method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Student
            currentStudent = unmarshallStudent(currentLine);

            // We are going to use the student id as the map key for our student object.
            // Put currentStudent into the map using student id as the key
            students.put(currentStudent.getStudentId(), currentStudent);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public Student addStudent(String studentId, Student student) throws ClassRosterPersistenceException {
        loadRoster();
        Student newStudent = students.put(studentId, student);
        writeRoster();
        return newStudent;
    }

    @Override
    public List<Student> getAllStudents() throws ClassRosterPersistenceException {
        loadRoster();
        return new ArrayList(students.values());
    }

    @Override
    public Student getStudent(String studentId) throws ClassRosterPersistenceException {
        loadRoster();
        return students.get(studentId);
    }

    @Override
    public Student removeStudent(String studentId) throws ClassRosterPersistenceException {
        loadRoster();
        Student removedStudent = students.remove(studentId);
        writeRoster();
        return removedStudent;
    }

    /**
     *
     * A very simple method that takes in a message to display on the console 
     * and then waits for a integer answer from the user to return.
     *
     * @param msg - String of information to display to the user.
     *
     */
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and then waits for an answer from the user to return.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as string
     */
    @Override
    public String readString(String msgPrompt) {
        System.out.println(msgPrompt);
        return console.nextLine();
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as integer
     */
    @Override
    public int readInt(String msgPrompt) {
        boolean invalidInput = true;
        int num = 0;
        while (invalidInput) {
            try {
                // print the message msgPrompt (ex: asking for the # of cats!)
                String stringValue = this.readString(msgPrompt);
                // Get the input line, and try and parse
                num = Integer.parseInt(stringValue); // if it's 'bob' it'll break
                invalidInput = false; // or you can use 'break;'
            } catch (NumberFormatException e) {
                // If it explodes, it'll go here and do this.
                this.print("Input error. Please try again.");
            }
        }
        return num;
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter an integer
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an integer value as an answer to the message prompt within the min/max range
     */
    @Override
    public int readInt(String msgPrompt, int min, int max) {
        int result;
        do {
            result = readInt(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a long
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as long
     */
    @Override
    public long readLong(String msgPrompt) {
        while (true) {
            try {
                return Long.parseLong(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an long value as an answer to the message prompt within the min/max range
     */
    @Override
    public long readLong(String msgPrompt, long min, long max) {
        long result;
        do {
            result = readLong(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as float
     */
    @Override
    public float readFloat(String msgPrompt) {
        while (true) {
            try {
                return Float.parseFloat(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a float
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an float value as an answer to the message prompt within the min/max range
     */
    @Override
    public float readFloat(String msgPrompt, float min, float max) {
        float result;
        do {
            result = readFloat(msgPrompt);
        } while (result < min || result > max);

        return result;
    }

    /**
     *
     * A simple method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @return the answer to the message as double
     */
    @Override
    public double readDouble(String msgPrompt) {
        while (true) {
            try {
                return Double.parseDouble(this.readString(msgPrompt));
            } catch (NumberFormatException e) {
                this.print("Input error. Please try again.");
            }
        }
    }

    /**
     *
     * A slightly more complex method that takes in a message to display on the console, 
     * and continually reprompts the user with that message until they enter a double
     * within the specified min/max range to be returned as the answer to that message.
     *
     * @param msgPrompt - String explaining what information you want from the user.
     * @param min - minimum acceptable value for return
     * @param max - maximum acceptable value for return
     * @return an double value as an answer to the message prompt within the min/max range
     */
    @Override
    public double readDouble(String msgPrompt, double min, double max) {
        double result;
        do {
            result = readDouble(msgPrompt);
        } while (result < min || result > max);
        return result;
    }

}
