package frontend;

/**
 * Created by eirik on 31.03.2017.
 */

public class SubjectInfo {
    String name;
    String comment;
    int id;

    SubjectInfo(String name, String comment, int id){
        this.name = name;
        this.comment = comment;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return name + "\n" + comment;
    }
}
