package backend;

import java.io.Serializable;

public class TeacherInfo implements Serializable{
    float snitt;
    int antall;

    public TeacherInfo(float snitt, int antall) {
        this.snitt = snitt;
        this.antall = antall;
    }

    public float getSnitt() {
        return snitt;
    }

    public void setSnitt(float snitt) {
        this.snitt = snitt;
    }

    public int getAntall() {
        return antall;
    }

    public void setAntall(int antall) {
        this.antall = antall;
    }
    
    public String toString(){
    	String ret = "Mean is: "+snitt+" and number of people is:  "+antall;
    	return ret;
    }
}