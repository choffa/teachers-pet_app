package backend.threadsnshit;
import backend.*;
import backend.database.*;
import backend.tests.ReadWriteTest;

import java.util.ArrayList;

public class WriterThread implements Runnable, ServerListener{
	InputDatabase idb;
	volatile ArrayList<StudentInfo> infoStack;
	public WriterThread(InputDatabase db){
		idb = db;
		infoStack = new ArrayList<StudentInfo>();
	}
	
	@Override
	public void run() {
		while(true){
			if(infoStack.size()>0){
				if(infoStack.get(0)!=null){
					write(infoStack.get(0));
					infoStack.remove(0);
				}
			}
		}
	}

	@Override
	public synchronized void addInfo(StudentInfo info) {
		infoStack.add(info);
		
	}
	
	
	private synchronized void write(StudentInfo s){
		if(s!= null){
			if(s.getOldRank()==0){
				addNewRank(s);
			} else {
				updateOldRank(s);
			}
		} 
	}
	
	private void addNewRank(StudentInfo s){
		float oldSnitt = idb.getGjennomsnitt();
		int oldAnt = idb.getAntall();
		int newAnt = oldAnt+1;
		float newSnitt = (oldSnitt*oldAnt+s.getRank())/newAnt;
		idb.setGjennomsnitt(newSnitt);
		idb.setAntall(newAnt);
	}
	private void updateOldRank(StudentInfo s){
		float oldSnitt = idb.getGjennomsnitt();
		int oldAnt = idb.getAntall();
		float newSnitt = (oldSnitt*oldAnt+s.getRank()-s.getOldRank())/oldAnt;
		if(newSnitt<0){
			idb.setGjennomsnitt(0);
			return;
		}
		idb.setGjennomsnitt(newSnitt);
		
	}
	
}
