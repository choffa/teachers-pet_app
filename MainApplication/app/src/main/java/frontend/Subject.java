package frontend;

/**
 * Created by Choffa on 10-Mar-17.
 */
public class Subject {

    private int id;
    private String name;

    public Subject(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Subject(String name){
        this((Integer)null, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    /*
    Should the subject class contain subject rating?
    I don't think so, just because it can change so often
     */


}
