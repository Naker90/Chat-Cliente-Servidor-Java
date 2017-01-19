package servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Repetidor {

	private static Semaphore mutex = new Semaphore(1);
	private static Ususario[] vector_ususarios;
	
	public static void difundir(String ususario, String mensaje){
		try {
			mutex.acquire();
				vector_ususarios = Servidor.getVecUsusarios();
				int puntero_vector = Servidor.getPunteroUsusario();
				for(int i=0; i < puntero_vector; i++){
					DataOutputStream dos = vector_ususarios[i].getCanalSalida();
					dos.writeUTF(ususario + ": " + mensaje);
				}
			mutex.release();
		} catch (InterruptedException ie) {
			System.err.println("[Error]" + Repetidor.class.getName() + " " + ie.getMessage());
		} catch (IOException ioe) {
			System.err.println("[Error]" + Repetidor.class.getName() + " " + ioe.getMessage());
		}
	}
}
