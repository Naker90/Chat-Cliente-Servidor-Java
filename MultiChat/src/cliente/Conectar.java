package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Conectar{

	private Socket conexion;

	public Conectar(String direccion_ip, int puerto, String nick){
		try {
			conexion = new Socket(direccion_ip, puerto);

			DataInputStream dis = new DataInputStream(conexion.getInputStream());
			DataOutputStream dos = new DataOutputStream(conexion.getOutputStream());

			dos.writeUTF(nick);
			
			int resultado_conexion = dis.readInt();
			
			if(resultado_conexion==0){//[Error]Servidor lleno.
				JOptionPane.showMessageDialog(null, "Servidor lleno, pruebe a conectarse mas tarde.", "Info", JOptionPane.INFORMATION_MESSAGE);
				conexion.close();
			}else if (resultado_conexion==1){//[Error]Nick en uso.
				JOptionPane.showMessageDialog(null, "El nombre de ususario " + nick + " ya está en uso.", "Info", JOptionPane.INFORMATION_MESSAGE);
				conexion.close();
			}else{//Todo correcto.
				ClienteGUI.setVisibilidad(false);
				ChatGUI chatgui = new ChatGUI(conexion, dos, dis);
				chatgui.show();
			}
		} catch (UnknownHostException uhe) {
			System.err.println("[Error]" + Conectar.class.getName() + uhe.getMessage());
			JOptionPane.showMessageDialog(null, "No se puede establecer conexion con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException ioe) {
			System.err.println("[Error]" + Conectar.class.getName() + ioe.getMessage());
			JOptionPane.showMessageDialog(null, "No se puede establecer conexion con el servidor.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	} 

	public void descontar(){
		try {
			if(conexion!=null)
				conexion.close();
		} catch (IOException ioe) {
			System.err.println("[Error] " + Conectar.class.getName() + " " + ioe.getMessage());
		}

	}

}