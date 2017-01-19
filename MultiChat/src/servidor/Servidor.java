package servidor;

import java.io.IOException;
import java.net.ServerSocket;

public class Servidor {

	private int puertoEscucha;
	private ServerSocket server_socket;
		
	private static String[] usuarios_conectados;
	private static int puntero_ususarios = 0;
	
	private static Ususario[] vector_ususarios;
	private static int puntero_vector = 0;
		
	public Servidor(int puertoEscucha, int limite_conexiones){
		this.puertoEscucha = puertoEscucha;
		usuarios_conectados = new String[limite_conexiones];
		vector_ususarios = new Ususario[limite_conexiones];
	}

	public void iniciarServidor(){
		try {
			server_socket = new ServerSocket(puertoEscucha);
			ServidorGUI.log_add("[OK] Servidor inciado en " + puertoEscucha);
			(new Escucha(server_socket)).start();
		} catch (IOException ioe) {
			System.err.println("[Error] " + Servidor.class.getName() + " " + ioe.getMessage());
			ServidorGUI.log_add("[Error] Es posible que el puerto especificado este en uso por otra aplicacion.");
			ServidorGUI.log_add("[INFO] Detenga el servidor y vuelva a iniciarlo.");
		}
	}
	
	public void detenerServidor(){
		try {
			if(server_socket!=null){
				server_socket.close();
				puntero_ususarios = 0;
				puntero_vector = 0;
			}
		} catch (IOException ioe) {
			System.err.println("[Error] " + Servidor.class.getName() + " " + ioe.getMessage());
			ServidorGUI.log_add("[Error] Problema interno deteniendo el servidor.");
		}
	}
	
	public static void add_usuario(String nick){
		usuarios_conectados[puntero_ususarios] = nick;
		puntero_ususarios++;
		ServidorGUI.actualizarUsusarios(usuarios_conectados, puntero_ususarios);
	}
	
	public static int[] getNumeroconexiones(){
		int[] datos = {puntero_ususarios, usuarios_conectados.length};
		return datos;
	}
	
	public static void insertar_ususario(Ususario user){
		vector_ususarios[puntero_vector] = user;
		puntero_vector++;
	}
	
	public static boolean echar_usuario(String nick){
		for(int i=0; i < vector_ususarios.length; i++)
			if(vector_ususarios[i].getNick().equals(nick)){
				boolean rtn = vector_ususarios[i].desconectar();
				if(rtn){
					borrarUsusario(nick);
					reordenar_ususasrios();
					ServidorGUI.actualizarUsusarios(usuarios_conectados, puntero_ususarios);
					return true;
				}else{
					return false;
				}
			}
		return false;
	}
	
	public static void borrarUsusario(String nick){
		for(int i=0; i < puntero_ususarios; i++){
			if(usuarios_conectados[i].equals(nick)){
				usuarios_conectados[i] = null;
			}
		}
		for(int e=0; e < puntero_vector; e++){
			if(vector_ususarios[e].getNick().equals(nick)){
				vector_ususarios[e] = null;
			}
		}
	}
	
	public static void reordenar_ususasrios(){
		for(int i=0; i < puntero_ususarios; i++){//vector de nombres (String)
			if(usuarios_conectados[i]==null){
				for(int x=i ; x < puntero_ususarios; x++){
					if((x+1)<puntero_ususarios){
						String nick = usuarios_conectados[x+1];
						usuarios_conectados[x] = nick;
					}else{
						i = x;
					}
				}
				puntero_ususarios--;
				usuarios_conectados[puntero_ususarios] = ""; //Eliminar ultimo ususario (el eliminado) para que no aparezca en la lista.
			}
		}
		for(int e=0; e < puntero_vector; e++){//vector de ususarios (Usuario)
			if(vector_ususarios[e]==null){
				for(int x2=e ; x2 < puntero_vector; x2++){
					if((x2+1)<puntero_vector){
						Ususario user = vector_ususarios[x2+1];
						vector_ususarios[x2] = user;
					}else{
						e = x2;
					}
				}
				puntero_vector--;
			}
		}
	}

	public static boolean comprobarNick(String nick) {
		for(int i=0; i < puntero_ususarios; i++){
			if(usuarios_conectados[i].equals(nick)){
				return false;
			}
		}
		return true;
	}
	
	public static Ususario[] getVecUsusarios(){return vector_ususarios;}
	public static int getPunteroUsusario() {return puntero_ususarios;}
}