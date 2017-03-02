package backend.sockets;


import java.io.*;
import java.net.Socket;

import backend.threadsnshit.ReaderThread;
import backend.threadsnshit.WriterThread;
import backend.StudentInfo;
import backend.TeacherInfo;


public class ConnectionHandeler implements Runnable {
	
	Socket socket;
	WriterThread wt;
	
	public ConnectionHandeler(Socket socket, WriterThread wt, ReaderThread rt){
		this.socket = socket;
		this.wt = wt;
		rt.listen(this);
	}
	@Override
	public void run() {

		ObjectInputStream in;
		StudentInfo input = new StudentInfo("", (byte)0, (byte)0); 		//Empty StudentInfo
		try {
			//while (input!=null){
				System.out.println("Running with:" + socket.getInetAddress().getHostName());
				in = new ObjectInputStream(socket.getInputStream());
				if(in!=null){
				input = (StudentInfo) in.readObject();
				System.out.println(input);
				wt.addInfo(input);
				}
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void push(TeacherInfo t) throws IOException{
		ObjectOutputStream out;
			if (socket!=null&&(!socket.isClosed())&&socket.isConnected()){
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(t);
			//out.close();
			}
		
		
	}
}
