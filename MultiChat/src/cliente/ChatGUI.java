package cliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGUI {

	private Socket conexion;
	static JTextArea txtchatarea;
	JFrame frame;
	
	private DataOutputStream dos;
	
	public ChatGUI(Socket conexion, DataOutputStream dos, DataInputStream dis){
		this.conexion = conexion;
		this.dos = dos;
		new Receptor(this.conexion, dis).start();
	}
	
	public void show(){
		frame = new JFrame();
		frame.setLayout(null);
		frame.setTitle("ChatGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(405, 355);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		JLabel lblchat = new JLabel("[Chat]");
		lblchat.setBounds(10, 5, 50, 20);
		
		txtchatarea = new JTextArea();
		txtchatarea.setLineWrap(true);
		txtchatarea.setEnabled(false);
		txtchatarea.setDisabledTextColor(Color.BLACK);
		JScrollPane scroll = new JScrollPane(txtchatarea);
		scroll.setBounds(10, 30, 375, 250);
		
		JTextField txtmensaje = new JTextField();
		txtmensaje.setBounds(10, 285, 330, 30);
		
		JButton btnenviar = new JButton(">");
		btnenviar.setBounds(340, 285, 45, 29);
		btnenviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mensaje = txtmensaje.getText();
				enviarMensaje(mensaje);
				txtmensaje.setText("");
			}
		});
		
		frame.add(lblchat);
		frame.add(scroll);
		frame.add(txtmensaje);
		frame.add(btnenviar);;
		
		frame.setVisible(true);
	}
	
	public void enviarMensaje(String mensaje){
		if(!mensaje.isEmpty()){
			try {
				if(conexion!=null)
					dos.writeUTF(mensaje);
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "El administrador te ha expulsado del chat.", "Info", JOptionPane.INFORMATION_MESSAGE);
				ClienteGUI.setVisibilidad(true);
				frame.setVisible(false);
				try {
					if(conexion!=null)
						conexion.close();
				} catch (IOException ioe2) {
					System.err.println("[Error]" + ChatGUI.class.getName() + " " + ioe2.getMessage());
				}	
			}
		}
	}
	
	public static void setChatMensajes(String mensaje){
		txtchatarea.setText(txtchatarea.getText() + "\n" + mensaje);
	}
}