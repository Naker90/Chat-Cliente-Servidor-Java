package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Ususario extends Thread {

	private Socket conexion;
	private String user_nick;
	
	private DataInputStream dis;
	private DataOutputStream dos;

	public Ususario(Socket conexion){
		this.conexion = conexion;
		try {
			dis = new DataInputStream(this.conexion.getInputStream());
			dos = new DataOutputStream(this.conexion.getOutputStream());
		} catch (IOException ioe) {
			System.err.println("[Error]" + Ususario.class.getName() + ioe.getMessage());
		}
	}

	public void run(){
		if(conexion!=null){
			try {
				int[] datos_conexion = Servidor.getNumeroconexiones();
				if((datos_conexion[0]) >= datos_conexion[1]){ // (Puntero del vector) >= (Tamaño del vector)
					dos.writeInt(0); //[ERROR]Servidor lleno.
					desconectar();
				}else{
					String nick = dis.readUTF();
					boolean comprobar_nick = Servidor.comprobarNick(nick);
					if(comprobar_nick){
						dos.writeInt(3);//[OK]Aceptacion de ususario.
						user_nick = nick;
						ServidorGUI.log_add("[OK] Nuevo ususario conectado " + nick);
						Servidor.add_usuario(nick);
						Servidor.insertar_ususario(this);
						Escuchar();
					}else{
						dos.writeInt(1); //[ERROR]Nick es uso.
						desconectar();
					}
				}
			} catch (IOException ioe) {
				System.err.println("[Error]" + Ususario.class.getName() + ioe.getMessage());
			}
		}else{
			desconectar();
		}
	}
	
	public void Escuchar(){
		while(conexion!=null){
			try {
				String mensaje = dis.readUTF();
				if(mensaje!=null && !(mensaje.isEmpty()))
					Repetidor.difundir(user_nick, mensaje);
			} catch (IOException ioe) {}
		}
	}

	public boolean desconectar(){
		try {
			conexion.close();
			return true;
		} catch (IOException ioe) {
			System.err.println("[Error]" + Ususario.class.getName() + ioe.getMessage());
			return false;
		}
	}

	public String getNick(){return user_nick;}
	public DataOutputStream getCanalSalida(){return dos;}
}