package backend;

/**
 * Created by magnus on 03.03.2017.
 */

public class Lecture {
    private static String fagkode;
    private static String professorId;
    private static int start;
    private static int end;
    private static String room;
    private static String ID;

    public Lecture(String fagkode, int start, int end, String room){
        setFagkode(fagkode);
        setStart(start);
        setEnd(end);
        setRoom(room);
        setID();
    }

    public static void setFagkode(String fagkode) {
        Lecture.fagkode = fagkode;
    }

    public static void setStart(int hour){
        start=hour;
    }
    public static void setEnd(int hour){
        end=hour;
    }
    public static void setRoom(String roomName){
        room=roomName;
    }
    public static void setProfessorId(String Id){
        professorId=Id;
    }

    public static String getFagkode() {
        return fagkode;
    }

    public static String getProfessorId() {
        return professorId;
    }

    public static int getStart() {
        return start;
    }

    public static int getEnd() {
        return end;
    }

    public static String getRoom() {
        return room;
    }
    private static void setID(){
        Lecture.ID=getFagkode() + ":" + Integer.toString(getStart()) + ":" + Integer.toString(getEnd()) + ":" + getRoom();
    }
    public static String getID(){
        return ID;
    }
}
