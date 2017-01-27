package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Escucha extends Thread {

	private ServerSocket server_socket;

	public Escucha(ServerSocket server_socket) {
		this.server_socket = server_socket;
	}

	public void run(){
		try {
			while(true){
				Socket conexion;
				conexion = server_socket.accept();
				new Ususario(conexion).start();
			}
		} catch (IOException ioe) {
			System.err.println("[Error] " + Escucha.class.getName() + " " + ioe.getMessage());	
		}
	}
}
