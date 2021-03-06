package frontend;

import android.widget.Toast;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import backend.Lecture;
import no.teacherspet.mainapplication.AddSubject;

public class Connection implements Closeable {

	private static final int PORT = 4728;
	private static final String HOST = "doktor.pvv.org";
	private Socket socket;
	private PrintWriter out;
	private Scanner in;
	private boolean isClosed;

	//------------------------------------------------------------------------
	//The connection stuff

	/**
	 * A method that sets up the connection to the server
	 *
	 * @throws IOException
	 */
	public Connection() throws IOException {
		this(new Socket(HOST, PORT));
	}

	/**
	 * Mostly for testing purposes, this constructor allows you to set the object Socket to a
	 * specified socket
	 * @param socket - The socket to bind the object to
	 * @throws IOException
	 */
	public Connection(Socket socket) throws IOException {
		isClosed = false;
		this.socket = socket;
		this.out = new PrintWriter(socket.getOutputStream());
		this.in = new Scanner(socket.getInputStream());
		in.useLocale(Locale.ENGLISH);
	}

	/**
	 * A method that closes the connection to the server
	 *
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (isClosed) {
			return;
		}
		isClosed = true;
		out.println("CLOSE");
		out.flush();
		out.close();
		in.close();
		socket.close();
	}

	/**
	 * A method that checks is the connection has been closed
	 *
	 * @return True if the connection has been closed
	 */
	public boolean isClosed() {
		return this.isClosed;
	}

	//------------------------------------------------------------------------
	//The subject rating stuff

	/**
	 * A method for sending a subject rating to the server
	 *
	 * @param subjectID The ID of the subject that is to be rated
	 * @param studentID The ID of the student that sent this rating
	 * @param rating    The actual rating of the subject, this is a number between 1 and 5 (inclusive)
	 * @throws IllegalArgumentException This is thrown if the ranking is wrong
	 */
	public void sendSubjectRating(int subjectID, String studentID, int rating, String comment)
			throws IllegalArgumentException {
		checkState();
		if (rating < 0 || rating > 5) {
			throw new IllegalArgumentException("Rating must be between 0 and 5");
		}

		out.println("SET_SUBJECTRATING " + subjectID + " " + studentID + " " + rating + " " + comment);
		out.flush();
	}

	/**
	 * A method that requests the subjectRating from a specific subject
	 *
	 * @param subjectID The subjectID of the subject in question
	 * @return A float that represents the average rating for the subject
	 */
	public float getAverageSubjectRating(int subjectID) {
		checkState();
		out.println("GET_AVERAGESUBJECTRATING " + subjectID);
		out.flush();
		float res = in.nextFloat();
		return res;
	}

	/**
	 * A method that requests the distribution of ratings for a specific subject
	 * @param subjectId The subjectID of the subject in question
	 * @return A list of integers representing the amount of votes of that degree.
	 */
	public int[] getSubjectStats(int subjectId){
		checkState();
		out.println("GET_STATS "+subjectId);
		out.flush();

		int[] res = new int[6];
		int counter=0;
		while (in.next().compareTo("NEXT") == 0){
			int count = in.nextInt();
			res[counter]=count;
			counter++;
		}
		return res;
	}

	public int getStudentSubjectRating(String studentId, int subjectId){
        checkState();
        out.println("GET_STUDENTSUBJECTRATING " + studentId +" "+ subjectId);
        out.flush();
        String rat = in.next();
        if("NOSTRING".equals(rat)) return 0;
        int rating = Integer.parseInt(rat);
        return rating;
    }

    public int getStudentSpeedRating(String studentId, int lectureId){
        checkState();
        out.println("GET_STUDENTSPEEDRATING " + studentId +" "+ lectureId);
        out.flush();
        String rat = in.next();
        if("NOSTRING".equals(rat)) return -1;
        int rating = Integer.parseInt(rat);
        return rating;
    }

	//------------------------------------------------------------------------
	//The lecture stuff

	/**
	 * A method that request the currently active lectures
	 *
	 * @return An ArrayList with the lectures in it
	 */
	public ArrayList<Lecture> getLectures() {
		checkState();
		out.println("GET_ALLLECTURES");
		out.flush();
		return readLectureInput();
	}

	/**
	 * A method that request the lectures by a specific professor
	 *
	 * @param professorID The ID of the professor that held the lectures
	 * @return An ArrayList with the lectures in it
	 */
	public ArrayList<Lecture> getLectures(String professorID) {
		checkState();
		out.println("GET_LECTURE " + professorID);
		out.flush();
		return readLectureInput();
	}


	private ArrayList<Lecture> readLectureInput(){
		ArrayList<Lecture> res = new ArrayList<>();
		while (in.next().compareTo("NEXT") == 0){
			int lectureID = in.nextInt();
			String date = in.next();
			int start = in.nextInt();
			int end = in.nextInt();
			String professorID = in.next();
			String room = convertFromServer(in.next());
			String courseID = convertFromServer(in.next());
			String[] components = date.split("-");
			Date d= new Date();
			d.setYear(Integer.parseInt(components[0])-1900);
			d.setMonth(Integer.parseInt(components[1])-1);
			d.setDate(Integer.parseInt(components[2]));

			res.add(new Lecture(lectureID, professorID, courseID, start, end, room, d));
		}
		return res;
	}

	/**
	 * A method for creating a new lecture in the database
	 *  @param date  The date that the lecture takes place
	 * @param start The time the lecture starts
	 * @param end   The end time the lecture ends
	 * @param room  The room that the lecture takes place
	 */
	public int createLecture(String professorID, String courseID, Date date, int start, int end, String room) {
		checkState();
		String resCourseID = convertToServer(courseID);
		String resRoom = convertToServer(room);
		checkLectureInput(professorID, resCourseID, start, end, resRoom);
		out.println("SET_LECTURE " + professorID + " " + resCourseID + " " + (date.getYear()+1900) + "-" + (date.getMonth() +1) + "-" + date.getDate() + " " + start + " "
			+ end + " " + resRoom);
		out.flush();
		int res = in.nextInt();
		return res;
	}

	/**
	 * A method that creates a new lecture in the database,
	 * it simply reads the relevant data from the object and
	 * calls the other createLecture method with that data
	 *
	 * @param lecture	The Lecture object to create instance for
	 */
	public int createLecture(Lecture lecture){
		return createLecture(lecture.getProfessorID(), lecture.getCourseID(), lecture.getDate(), lecture.getStart(),
				lecture.getEnd(), lecture.getRoom());
	}


	public ArrayList<String> getLectureComments(int lectureId){
		checkState();
		out.println("GET_LECTURECOMMENTS "+lectureId);
		out.flush();
		ArrayList<String> comments = new ArrayList<String>();
		while("NEXT".equals(in.next())){
			comments.add(convertFromServer(in.next()));
		}
		return comments;
	}


	public void setLectureComment(int lectureId, String comment){
		checkState();
		out.println("SET_LECTURECOMMENT "+lectureId+" "+convertToServer(comment));
		out.flush();
	}

	private void checkLectureInput(String professorID, String courseID, int start, int end, String room){
		boolean flag = false;
		if (professorID.contains(" ") || courseID.contains(" ") || room.contains(" ")){
			flag = true;
		} else if (start < 0 || start > 23 || end < 1 || end > 24){
			flag = true;
		}

		if (flag){ throw new IllegalArgumentException("This is not a valid lecture"); }
	}

	//------------------------------------------------------------------------
	//The speed rating stuff

	/**
	 * A method for sending a speed rating to the server
	 *
	 * @param lectureID The ID of the lecture being rated on
	 * @param studentID The ID of the student performing the rating
	 * @param rating    The actual rating of the speed. This should be a number between 1 and 5 (inclusive)
	 * @throws IllegalArgumentException if the rating is not between 1 and 5
	 */
	public void sendSpeedRating(int lectureID, String studentID, int rating) throws IllegalArgumentException {
		if (rating < 1 | rating > 5) { throw new IllegalArgumentException(); }
		out.println("SET_SPEEDRATING " + lectureID + " " + rating + " " + studentID );
		out.flush();
		//Should the server respond with boolean?
	}

	/**
	 * A method that request the speed rating for specific lecture
	 *
	 * @param lectureID The ID of the lecture to get the rating for
	 * @return A float representing the current average speed rating
	 */
	public float getAverageSpeedRating(int lectureID) {
		checkState();
		out.println("GET_AVERAGESPEEDRATING " + lectureID);
		out.flush();
		return in.nextFloat();
	}

	//------------------------------------------------------------------------
	//the subject stuff

	/**
	 * A method that requests the subjects associated with a specific lecture
	 *
	 * @param lectureID The ID of the lecture to get subjects for
	 * @return A ArrayList of integers representing the ID of the subjects
	 */
	public ArrayList<Subject> getSubjects(int lectureID) {
		checkState();
		out.println("GET_SUBJECTS "+lectureID);
		out.flush();
		ArrayList<Subject> res = new ArrayList<>();
		while (in.next().equals("NEXT")){
			int subInt = in.nextInt();
			String subName = in.next();
			subName = convertFromServer(subName);
			String subComment = in.next();
			subComment = convertFromServer(subComment);
			res.add(new Subject(subInt,subName,subComment));
		}
		return res;
	}

	public void updateSubject(int subjectID, String name, String comment){
		checkState();
		out.println("UPDATE_SUBJECT " + subjectID + " " + name + " " + comment);
		out.flush();
	}

	/**
	 * A method for creating a new subject associated to a lecture in the database
	 *
	 * @param lectureID The ID of the lecture to associate the subject with
	 */
	public void createSubject(int lectureID, String name, String comment) {
		checkState();
		String resName = convertToServer(name);
		String resComment = convertToServer(comment);
		checkSubjectInput(resName);
		out.println("SET_SUBJECT " + lectureID + " " + resName + " " + resComment);
		out.flush();
	}

	private void checkSubjectInput(String name){
		if (name.contains(" ")){
			throw new IllegalArgumentException("Subject name should not contain space");
		}
	}

	/**
	 * This method creates new users in the database
	 * @param username - Username for the new user
	 * @param password - Password for the new user
	 */
	public void createUser(String username, String password) {
		checkState();
		out.println("SET_USER " + username + " " + password);
		out.flush();
	}

	/**
	 * This method checks the availability of the username in the database
	 * @param username - The username that is to be checked in the databse
	 * @return A boolean value representing the
	 */
	public boolean checkUsername(String username) {
		checkState();
		out.println("CHECK_USER " + username);
		out.flush();
		return in.nextBoolean();
	}

	/**
	 * This methods gets the number of votes on speed i lecture
	 * @param LectureID - the ID of the lecture in question
	 * @return an int of the number of votes
	 */
	public int getTempoVotesInLecture(int LectureID) {
		checkState();
		out.println("GET_NUMBEROFUSERS"+" "+LectureID);
		out.flush();
		return readUsersInput();
	}

	private int readUsersInput() {
		int ret = in.nextInt();
		return ret;
	}


	public boolean validateUser(String username, String password) {
		checkState();
		out.println("VALIDATE " + username + " " + password);
		out.flush();
		return in.nextBoolean();
	}

	private void checkState() {
		if(this.isClosed |!socket.isConnected()){
			throw new IllegalStateException();
		}

	}

	private String convertToServer(String string){
		if(string.isEmpty()){
			return "NULL";
		}
		String res = string.replaceAll(" ", "£");
		res = res.replaceAll("\n","¥");
		res = res.replaceAll("'","''");
		return res;
	}

	private String convertFromServer(String string){
		if (string.equalsIgnoreCase("NULL")){
			return "";
		}
		String res = string.replaceAll("£"," ");
		res = res.replaceAll("¥", "\n");

		return res;
	}
}
