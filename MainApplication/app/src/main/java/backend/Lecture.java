package backend;

/**
 * Created by magnus on 03.03.2017.
 */

public class Lecture {
    private int start;
    private int end;
    private String fagkode;
    private String room;
    private String lectureID;


    public Lecture(String fagkode, int start, int end, String room) {
        this.start = start;
        this.end = end;
        this.fagkode = fagkode;
        this.room = room;
        setID();
    }

    @Override
    public String toString() {
        return this.fagkode+"\t | \t" + this.room + "\n"+Integer.toString(this.start)+":15-"+Integer.toString(this.end)+":00";
    }

    private void setID(){
        this.lectureID = this.fagkode+":"+this.start+":"+this.end+":"+this.room;
    }

    public String getID(){
        return lectureID;
    }

    public void setFagkode(String fagkode) {
        this.fagkode = fagkode;
    }

    public String getFagkode() {
        return fagkode;
    }
}
