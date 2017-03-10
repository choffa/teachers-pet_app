package frontend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import backend.TeacherInfo;

import backend.StudentInfo;

public class AppReader implements Runnable {

	private Socket socket;
	private StudentInfo si = null;
	private ObjectInputStream in;
	private final String URL = "doktor.pvv.org";
	private final int port = 4279;

	public TeacherInfo ti;

	public void run(){
		ti = new TeacherInfo(0,0);
		//while(true){

		try {
			if(socket==null){
			socket = new Socket(URL, port);
			}
			in = new ObjectInputStream(socket.getInputStream());

			ti = (TeacherInfo) in.readObject();

			socket.getKeepAlive();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();

		//} continue;

		}
	}


}
