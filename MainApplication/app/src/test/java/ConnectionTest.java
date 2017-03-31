package tests;

import frontend.Connection;
import frontend.Lecture;
import frontend.Subject;
import org.junit.*;

import java.io.*;
import java.net.InterfaceAddress;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Choffa on 28-Mar-17.
 */
public class ConnectionTest {

    private Connection c;
    private Socket s;
    private InputStream in;
    private OutputStream out;

    private static double average;
    private static int lectureID;
    private static int subjectID;
    private static int rating;
    private static int badRating;
    private static int ratings;

    private static String averageString;
    private static String lectureIDString;
    private static String ratingsString;

    private static final String userName = "djfsakløfjkløsjaklfø";
    private static final String professorID = "djfsakløfjkløsjaklfø";
    private static final String password = "trwuiodsjoivdsgl325u485";
    private static final String studentID = "kdigfur64990#jdfydkwmdur&&jkd/daeb";
    private static final String lectureResponse = "END";
    private static final String subjectResponse = "END";
    private static final String subjectName = "EULERS_THEROEM";
    private static final String badSubjectName = "EULERS THEOREM";
    private static final String comment = "THISISNICE";
    private static final String room = "R4";
    private static final String badRoom = "R4 134";
    private static final String courseID = "TDT4100";
    @SuppressWarnings("deprecation")
    private static final Date date = new Date(120, 1, 1);
    private static final int start = 12;
    private static final int end = 17;

    @BeforeClass
    public static void setUpClass() {
        Random r = new Random();
        average = r.nextDouble() * 5.0;
        lectureID = r.nextInt();
        subjectID = r.nextInt();
        ratings = r.nextInt(300);
        do{
            rating = r.nextInt(6);
        }while(rating < 1);
        do{
            badRating = r.nextInt();
        }while(badRating > 0 && badRating < 6);
        averageString = Double.toString(average);
        lectureIDString = Integer.toString(lectureID);
        ratingsString = Integer.toString(ratings);
    }

    public void setUp(String response) throws IOException {
        s = mock(Socket.class);
        byte[] b = response.getBytes();
        in = new ByteArrayInputStream(b);
        out = new ByteArrayOutputStream();
        when(s.getOutputStream()).thenReturn(out);
        when(s.getInputStream()).thenReturn(in);
        when(s.isConnected()).thenReturn(true);
        c = new Connection(s);

    }


    @Test
    public void sendSubjectRating() throws IOException {
        setUp("");
        String expectedCommand = "SET_SUBJECTRATING " + subjectID +  " " + studentID + " " + rating + " " + comment +"\r\n";
        c.sendSubjectRating(subjectID, studentID, rating, comment);

        assertEquals(expectedCommand, out.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badRatingSendSubjectRating() throws IOException {
        setUp("");
        c.sendSubjectRating(subjectID, studentID, badRating, comment);
    }

    @Test
    public void getAverageSubjectRating() throws IOException {
        setUp(averageString);
        String expectedCommands = "GET_AVERAGESUBJECTRATING " + subjectID + "\r\n";
        double rating = c.getAverageSubjectRating(subjectID);

        assertEquals(expectedCommands, out.toString());
        assertEquals(average, rating, 0.001);
    }

    //TODO: these test needs fixing
    @Test
    public void getLectures() throws IOException {
        setUp(lectureResponse);
        String expectedCommands = "GET_ALLLECTURES\r\n";
        ArrayList<Lecture> lectureList = c.getLectures();

        assertEquals(expectedCommands, out.toString());
        assertTrue(lectureList.isEmpty());
    }

    @Test
    public void getLecturesFromProfessorID () throws IOException {
        setUp(lectureResponse);
        String expectedCommands = "GET_LECTURE " + professorID + "\r\n";
        ArrayList<Lecture> lectureList = c.getLectures(professorID);

        assertEquals(expectedCommands, out.toString());
        assertTrue(lectureList.isEmpty());
    }

    //TODO: END TODO

    @Test
    public void createLecture() throws IOException {
        setUp("");
        String expectedCommands = "SET_LECTURE " + professorID + " " + courseID + " " + date + " " + start + " " + end + " " + room + "\r\n";
        c.createLecture(professorID, courseID, date, start, end, room);

        assertEquals(expectedCommands, out.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badRoomCreateLecture() throws IOException {
        setUp("");
        c.createLecture(professorID, courseID, date, start, end, badRoom);
    }

    @Test
    public void createLectureFromObject() throws IOException {
        setUp("");
        Lecture lecture = new Lecture(-1, professorID, courseID, start, end, room, date);
        String expectedCommands = "SET_LECTURE " + professorID + " " + courseID + " " + date + " " + start + " " + end + " " + room + "\r\n";
        c.createLecture(lecture);

        assertEquals(expectedCommands, out.toString());
    }

    @Test
    public void sendSpeedRating() throws IOException {
        setUp("");
        String expectedCommands = "SET_SPEEDRATING " + lectureID + " " + rating + " " + studentID + "\r\n";
        c.sendSpeedRating(lectureID, studentID, rating);

        assertEquals(expectedCommands, out.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badRatingSendSpeedRating() throws IOException {
        setUp("");
        c.sendSpeedRating(lectureID, studentID, badRating);
    }

    @Test
    public void getAverageSpeedRating() throws IOException {
        setUp(averageString);
        String expectedCommands = "GET_AVERAGESPEEDRATING " + lectureID + "\r\n";
        double response = c.getAverageSpeedRating(lectureID);

        assertEquals(expectedCommands, out.toString());
        assertEquals(average, response, 0.01);
    }

    //TODO: must be fixed
    @Test
    public void getSubjects() throws IOException {
        setUp(subjectResponse);
        String expectedCommands = "GET_SUBJECTS\r\n";
        ArrayList<Subject> response = c.getSubjects(lectureID);

        assertEquals(expectedCommands, out.toString());
        assertTrue(response.isEmpty());
    }

    @Test
    public void createSubject() throws IOException {
        setUp("");
        String expectedCommands = "SET_SUBJECT " + lectureID + " " + subjectName + "\r\n";
        c.createSubject(lectureID, subjectName);

        assertEquals(expectedCommands, out.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badNameCreateSubject() throws IOException {
        setUp("");
        c.createSubject(lectureID, badSubjectName);
    }

    @Test
    public void validateUser() throws IOException {
        setUp("true false");
        String expectedCommands = "VALIDATE " + userName + " " + password + "\r\n";
        boolean response = c.validateUser(userName, password);

        assertEquals(expectedCommands, out.toString());
        assertTrue(response);

        expectedCommands = expectedCommands + expectedCommands;
        response = c.validateUser(userName, password);

        assertEquals(expectedCommands, out.toString());
        assertFalse(response);
    }

    @Test
    public void createUser() throws IOException {
        setUp("");
        String expectedCommands = "SET_USER " + userName + " " + password + "\r\n";
        c.createUser(userName, password);

        assertEquals(expectedCommands, out.toString());
    }

    @Test
    public void checkUserName() throws IOException {
        setUp("false true");
        String expectedCommands = "CHECK_USER " + userName + "\r\n";
        boolean response = c.checkUsername(userName);

        assertEquals(expectedCommands, out.toString());
        assertFalse(response);

        expectedCommands = expectedCommands + expectedCommands;
        response = c.checkUsername(userName);

        assertEquals(expectedCommands, out.toString());
        assertTrue(response);
    }

    @Test
    public void getTempoVotesInLecture() throws IOException {
        setUp(ratingsString);
        String expectedCommands = "GET_NUMBEROFUSERS " + lectureID + "\r\n";
        int response = c.getTempoVotesInLecture(lectureID);

        assertEquals(expectedCommands, out.toString());
        assertEquals(ratings, response);
    }

    @After
    public void tearDown() throws IOException {
        c.close();
    }
}








