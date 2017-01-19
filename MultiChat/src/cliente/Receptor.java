package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receptor extends Thread {

	private Socket conexion;
	private DataInputStream dis;
	
	public Receptor(Socket conexion, DataInputStream dis){
		this.conexion = conexion;
		this.dis = dis;
	}
	
	public void run(){
		while(conexion!=null){
			try {
				String mensaje = dis.readUTF();
				if(mensaje!=null && !(mensaje.isEmpty()))
					ChatGUI.setChatMensajes(mensaje);
			} catch (IOException ioe) { //Caso de salida, usuasrio desconectado o expulsado.
				try {
					conexion.close();
				} catch (IOException e) {
					System.err.println("[Error]" + Receptor.class.getName() + " " + e.getMessage());
				}
			}
		}
	}
}
