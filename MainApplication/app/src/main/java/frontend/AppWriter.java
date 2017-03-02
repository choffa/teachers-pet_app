package frontend;

import java.net.*;

import backend.StudentInfo;

import java.io.*;

public class AppWriter implements Runnable {

	private Socket socket;
	private StudentInfo si = null;
	private ObjectOutputStream out;
	private final String URL = "doktor.pvv.org";
	private final int port = 4279;
	
	public AppWriter(){
		try {
			socket = new Socket(URL, port);
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AppWriter(StudentInfo si){
		this();
		this.si = si;
	}
	
	@Override
	public void run() {
		System.out.println(socket.getInetAddress().getHostAddress());
		System.out.println(socket.getInetAddress().getHostName());
		System.out.println(socket.isConnected());
		try {
			out.writeObject(si);
			socket.close();
			Thread.sleep(100);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setInfo(StudentInfo si){
		this.si = si;
	}
	
	public static void main(String[] args) {
		AppWriter aw = new AppWriter(new StudentInfo("Hello", (byte) Math.floor(Math.random()*6), (byte) (0)));
		aw.run();
	}

}
