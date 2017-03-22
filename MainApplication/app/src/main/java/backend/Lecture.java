package backend;

import java.util.Date;

public class Lecture {
    private String professorID;
    private int start;
    private int end;
    private String courseID;
    private String room;
    private int lectureID;
    private Date date;


    /**
     * Outdated, use the other constructor if at all possible
     * @param courseID (fagkode)
     * @param start Start hour
     * @param end End hour
     * @param room Room number
     * @param date Date of lecture
     */
    public Lecture(String courseID, int start, int end, String room, Date date) {
        this.start = start;
        this.end = end;
        this.courseID = courseID;
        this.room = room;
        this.date = date;
        //setID();
        this.lectureID = (int) (Math.random()*500);
    }

    /**
     *  @param lectureID int Lecture ID, get from server
     * @param professorID String professorID
     * @param courseID String courseID of class
     * @param start int Start hour
     * @param end int End hour
     * @param room String Room number
     * @param date Date of Lecture
     */
    public Lecture(int lectureID, String professorID , String courseID, int start, int end, String room, Date date) {
        this.start = start;
        this.end = end;
        this.courseID = courseID;
        this.room = room;
        this.date = date;
        this.lectureID = lectureID;
        this.professorID = professorID;
    }

    @Override
    public String toString() {
        return this.courseID +"\t | \t" + this.date.getDate() + ":" + (this.date.getMonth()+1) + ":" + (this.date.getYear()+1900) + "\t | \t" + this.room + "\n"+Integer.toString(this.start)+":15-"+Integer.toString(this.end)+":00";
    }

    public int getID(){
        return lectureID;
    }

    public void setFagkode(String fagkode) {
        this.courseID = fagkode;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public Date getDate() {
        return date;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getProfessorID() {
        return professorID;
    }

    public String getRoom() {
        return room;
    }
}
