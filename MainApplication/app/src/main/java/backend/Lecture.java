package backend;

import java.util.Date;

/**
 * Created by magnus on 03.03.2017.
 */

public class Lecture {
    private int start;
    private int end;
    private String name;
    private String room;
    private String lectureID;
    private Date date;


    public Lecture(String name, int start, int end, String room, Date date) {
        this.start = start;
        this.end = end;
        this.name = name;
        this.room = room;
        this.date = date;
        setID();
    }

    @Override
    public String toString() {
        return this.name+"\t | \t" + this.date.getDate() + ":" + (this.date.getMonth()+1) + ":" + this.date.getYear() + "\t | \t" + this.room + "\n"+Integer.toString(this.start)+":15-"+Integer.toString(this.end)+":00";
    }

    private void setID(){
        this.lectureID = this.name+":"+this.start+":"+this.end+":"+this.room;
    }

    public String getID(){
        return lectureID;
    }

    public void setFagkode(String fagkode) {
        this.name = fagkode;
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

    public String getName() {
        return name;
    }
}
