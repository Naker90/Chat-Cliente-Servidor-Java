package cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class ClienteGUI {

	static JFrame frame;
	static JTextField txtIP;
	static JSpinner txtPuerto;
	static JTextField txtNick;
	
	static Conectar conectar;
	
	public static void showConf(){
		
		frame = new JFrame();
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ClienteGUI");
		frame.setSize(285, 185);
		frame.setLocationRelativeTo(null);
		
		JLabel lblIP = new JLabel();
		lblIP.setText("Dirección IPv4 del servidor");
		lblIP.setBounds(10, 8, 200, 20);
		
		txtIP = new JTextField();
		txtIP.setBounds(10, 35, 260, 20);
		txtIP.setHorizontalAlignment(JTextField.CENTER);
		txtIP.setText("127.0.0.1");
		
		JLabel lblPuerto = new JLabel();
		lblPuerto.setText("Puerto de conexión");
		lblPuerto.setBounds(10, 60, 150, 20);
		
		SpinnerModel model = new SpinnerNumberModel();
		txtPuerto = new JSpinner(model);
		txtPuerto.setBounds(10, 85, 110, 20);
		txtPuerto.setValue(new Integer(55555));
		
		JLabel lblNick = new JLabel("Nick");
		lblNick.setBounds(130, 60, 100, 20);
		
		txtNick = new JTextField();
		txtNick.setBounds(130, 85, 140, 20);
		txtNick.setHorizontalAlignment(JTextField.CENTER);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(10, 118, 260, 25);
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				conectar();
			}
		});
		
		frame.add(lblIP);
		frame.add(txtIP);
		frame.add(lblPuerto);
		frame.add(txtPuerto);
		frame.add(lblNick);
		frame.add(txtNick);
		frame.add(btnConectar);
		
		frame.setVisible(true);
		
	}
	
	public static void conectar(){
		String direccion_ip = txtIP.getText();
		String puerto_str = txtPuerto.getValue().toString();
		String nick = txtNick.getText();
		if(!(direccion_ip.isEmpty()) && !(puerto_str.isEmpty()) && !(nick.isEmpty())){
			int puerto = Integer.parseInt(puerto_str);
			conectar = new Conectar(direccion_ip, puerto, nick);
		}else{
			JOptionPane.showMessageDialog(null, "Debe rellenar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void setVisibilidad(boolean estado){
		frame.setVisible(estado);
	}
}