package frontend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by Magnus on 03.03.2017.
 */

public class Lecture {
    private int lectureID;
    private String professorID;
    private String courseID;
    private Date date;
    private int start;
    private int end;
    private String room;


    public Lecture(){

    }

    public Lecture(int lectureID, String professorID, String courseID,
                   Date date, int start, int end, String room) {

        this.lectureID = lectureID;
        this.professorID = professorID;
        this.courseID = courseID;
        this.date = date;
        this.start = start;
        this.end = end;
        this.room = room;
        //setID();
    }

    @Override
    public String toString() {
        return Integer.toString(lectureID);
        //return this.fagkode+"\t | \t" + this.room + "\n"+Integer.toString(this.start)+":15-"+Integer.toString(this.end)+":00";
    }

    private void setID(int lectureID){
        this.lectureID = lectureID;
    }

    public int getID(){
        return lectureID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getProfessorID() {
        return professorID;
    }

    public void setProfessorID(String professorID) {
        this.professorID = professorID;
    }

    public String getCourseID() {
        return courseID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
