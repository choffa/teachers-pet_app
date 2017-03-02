package backend.database;

import java.util.ArrayList;

import backend.StudentInfo;


//Databaseobjekt for tjenesten. Vil ligge på serversiden av backend
public class InputDatabase {
	volatile float gjennomsnitt;
	volatile int antall;
	
	
	public InputDatabase(){
		antall = 0;
		gjennomsnitt = 3;
	}

	public float getGjennomsnitt() {
		return gjennomsnitt;
	}
	public int getAntall() {
		return antall;
	}
	public void setGjennomsnitt(float gjennomsnitt) {
		this.gjennomsnitt = gjennomsnitt;
	}
	public void setAntall(int antall) {
		this.antall = antall;
	}
	
}
