package frontend;

/**
 * Created by Choffa on 10-Mar-17.
 */
public class Subject {

    private float averageRating =0;
    private int id;
    private String name;
    private String comment;

    public Subject(int id, String name, String comment){
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public Subject(String name, String comment){
        this(-1, name, comment);
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAverageRating(float avg){
        averageRating = avg;
    }

    public float getAverageRating() {
        return averageRating;
    }

    @Override
    public String toString() {
        return name + "\n" + comment;
    }

    /*
    Should the subject class contain subject rating?
    I don't think so, just because it can change so often
     */


}
