package backend.threadsnshit;

import java.util.ArrayList;

import backend.TeacherInfo;
import backend.database.InputDatabase;
import backend.sockets.ConnectionHandeler;


/**
 * @author Mathias, aka the best Author. He got better Authoring then everyone. Cause he's the best author.
 *
 */
public class ReaderThread implements Runnable{

	InputDatabase idb;
	ArrayList<ConnectionHandeler> teachers = new ArrayList<ConnectionHandeler>();
	
	public void listen(ConnectionHandeler h){
		teachers.add(h);
	}
	
	public void init(InputDatabase db){
		idb = db;
	}
	
	@Override
	public void run() {
		float oldSnitt = idb.getGjennomsnitt();
		int oldAnt = idb.getAntall();
		TeacherInfo t;
		while(true){
			if(oldSnitt!=idb.getGjennomsnitt()||oldAnt!=idb.getAntall()){
				oldSnitt=idb.getGjennomsnitt();
				oldAnt=idb.getAntall();
			t = new TeacherInfo(idb.getGjennomsnitt(), idb.getAntall());
			for (ConnectionHandeler teacher:teachers){
				if(teacher!=null){
				try{
					teacher.push(t);
				}catch(Exception e) {}
				}
			Runtime.getRuntime().gc();
			}
			}
		}
	}
	
	public static void main(String[] args){
		ReaderThread r = new ReaderThread();
		r.run();
	}
}
