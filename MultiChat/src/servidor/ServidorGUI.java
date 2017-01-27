package servidor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServidorGUI {

	static JTextArea log;
	static JList<String> lista_usuarios;
	
	static JLabel lblususarios;
	JButton start;
	
	Servidor servidor;
	
	public void show(){

		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ServidorGUI");
		frame.setSize(490, 490);
		frame.setLocationRelativeTo(null);

		lblususarios = new JLabel("[Ususarios conectados (0)]");
		lblususarios.setBounds(10, 5, 250, 20);

		lista_usuarios = new JList<>();
		lista_usuarios.setBounds(10, 30, 460, 250);

		start = new JButton("Iniciar servidor");
		start.setBounds(10, 290, 130, 25);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inciarServidor();
			}
		});

		JButton stop = new JButton("Detener servidor");
		stop.setBounds(150, 290, 130, 25);
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detenerServidor();
			}
		});

		JButton kick = new JButton("Kick ass");
		kick.setBounds(340, 290, 130, 25);
		kick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user_nick = lista_usuarios.getSelectedValue();
				if(user_nick!=null)
					kick(user_nick);
				else
					log_add("[ERROR] No ha seleccionado ningún ususario.");
			}
		});

		JLabel lblLog = new JLabel("[Logs]");
		lblLog.setBounds(10, 320, 100, 20);

	    log = new JTextArea();
	    log.setText("Bienvenido administrador.");
		log.setOpaque(true);
		log.setBackground(Color.BLACK);
		log.setDisabledTextColor(Color.GREEN);
		log.setEnabled(false);

		JScrollPane scroll_log = new JScrollPane(log);
		scroll_log.setBounds(10, 350, 460, 100);

		frame.add(lblususarios);
		frame.add(lista_usuarios);
		frame.add(start);
		frame.add(stop);
		frame.add(kick);
		frame.add(lblLog);
		frame.add(scroll_log);

		frame.setVisible(true);

	}

	public void inciarServidor(){		
		String puerto_str = JOptionPane.showInputDialog("Puerto de conexión");
		String numero_conexiones_str = JOptionPane.showInputDialog("Numero límite de conexiones");
		try{
			int puerto = Integer.parseInt(puerto_str);
			int numero_conexiones = Integer.parseInt(numero_conexiones_str);
			servidor = new Servidor(puerto, numero_conexiones);
			start.setEnabled(false);
			servidor.iniciarServidor();
		}catch(NumberFormatException nfe){
			log_add("[ERROR] Datos de conexión incorrectos.");
		}
	}
	
	public void detenerServidor(){
		if(servidor!=null){
			servidor.detenerServidor();
			log_add("[OK] Servidor detenido.");
			start.setEnabled(true);
			actualizarUsusarios(new String[1], 0); //Limpiar lista de ususarios y contador de ususarios.
		}else{
			log_add("[ERROR] El servidor no está iniciado.");
		}
	}
	
	public void kick(String user_nick){
		boolean res = Servidor.echar_usuario(user_nick);
		if(res){log_add("[INFO] Ususario " + user_nick + " expulsado.");}else{log_add("[ERROR] No se ha podido expulsar al ususario " + user_nick);}
	}
	
	public static void log_add(String cadena){
		String contenido = log.getText();
		Calendar calendar = GregorianCalendar.getInstance();
		log.setText(contenido + "\n" + "[" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + "]-" + cadena);
	}
	
	public static void actualizarUsusarios(String[] usuarios, int puntero){
		lista_usuarios.setListData(usuarios);
		lblususarios.setText("[Ususarios conectados (" + puntero + ")]");
	}
}