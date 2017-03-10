package backend.tests;

import backend.StudentInfo;
import backend.database.InputDatabase;
import backend.threadsnshit.MainListenerThread;
import backend.threadsnshit.ReaderThread;
import backend.threadsnshit.WriterThread;
import frontend.AppReader;

public class ReadWriteTest {
	static StudentInfo s;
	public volatile static InputDatabase  db;
	
	public static void main(String[] args) {
		db = new InputDatabase();
		MainListenerThread mlt = new MainListenerThread();
		ReaderThread rt = new ReaderThread();
		rt.init(db);
		mlt.init(db, rt);
		new Thread(mlt).start();
		System.out.println("mlt started");
		new Thread(rt).start();
		new Thread(new AppReader()).start();
		/*while(true){
			try{
			Thread.sleep((long) (Math.random()*200));
			//s = new StudentInfo("ikkerelevant", (byte) Math.floor(Math.random()*6), (byte) 0);
			wt.addInfo(new StudentInfo("ikkerelevant", (byte) Math.floor(Math.random()*6), (byte) (Math.round(Math.random())*Math.floor(Math.random()*6))));
			//System.out.println("test kjører");
			}catch(Exception e){e.printStackTrace();}
		}*/
	}

}

