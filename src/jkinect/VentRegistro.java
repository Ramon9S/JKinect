package jkinect;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentRegistro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel panelExt = new JPanel();
	private static JTextField textFUsuario;
	private static JPasswordField passwordField;
	private static VentRegistro dialog = null;
	public enum TipoUsuario{ ESPECIALISTA, PACIENTE, ADMIN}//enum


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new VentRegistro(null);
			dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentRegistro(JKinect j) {
		super(j.jframe, true);
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setBounds(500, 200, 450, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		//JPanel panelExt = new JPanel();
		getContentPane().add(panelExt);
		panelExt.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelSuperior = new JPanel();
		panelExt.add(panelSuperior);
		panelSuperior.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelSupIzq = new JPanel();
		panelSuperior.add(panelSupIzq);
		panelSupIzq.setLayout(new BorderLayout(0, 0));

		JLabel lblIcono = new JLabel("");
		lblIcono.setIconTextGap(0);
		lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcono.setIcon(new ImageIcon(j.imgLogosEPSG));
		panelSupIzq.add(lblIcono, BorderLayout.CENTER);

		JPanel panelSupDer = new JPanel();
		panelSuperior.add(panelSupDer);
		panelSupDer.setLayout(new GridLayout(4, 1, 0, 0));

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		panelSupDer.add(lblUsuario);

		textFUsuario = new JTextField();
		textFUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		panelSupDer.add(textFUsuario);
		textFUsuario.setColumns(10);

		JLabel lblContra = new JLabel("Password");
		lblContra.setHorizontalAlignment(SwingConstants.CENTER);
		panelSupDer.add(lblContra);

		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		panelSupDer.add(passwordField);

		JPanel panelInferior = new JPanel();
		panelInferior.setMaximumSize(new Dimension(32767, 30));
		panelExt.add(panelInferior);
		panelInferior.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel = new JPanel();
		panelInferior.add(panel);
		panel.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_1 = new JPanel();
		panelInferior.add(panel_1);

		JButton btnEntrar = new JButton("Entrar");
		panel_1.add(btnEntrar);
		btnEntrar.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEntrar.setActionCommand("Entrar");

		btnEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					registrarse(j);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		JPanel panel_2 = new JPanel();
		panelInferior.add(panel_2);


		//Remove any existing WindowListeners
		for ( WindowListener wl : this.getWindowListeners())
			this.removeWindowListener(wl);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(j.registrado != true) {
					JOptionPane optionPane = new JOptionPane("La ventana no se cierra sin registrarse!!");
					optionPane.setIcon(j.getIconoInfo());
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Error de Registro");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}//if
			}//windowClosing()
		});
	}

	@SuppressWarnings("deprecation")
	public static void registrarse(JKinect j) throws SQLException{
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rset = null;
		ResultSet rset2 = null;
		Vector<Object> columnNames = new Vector<Object>();

		try{
			// Step 1: Allocate a database 'Connection' object
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/terapiaKinectInterfaceDB?useSSL=false", "myuser", "ramon_sm");
			// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

			// Step 2: Allocate a 'Statement' object in the Connection
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();

			// Step 3: Execute a SQL SELECT query, the query result
			//  is returned in a 'ResultSet' object.
			//String strSelect = "select title, price, qty from books";
			String strSelect = "select usuario, password, id_especialista from especialista";
			String strSelect2 = "select usuario, password, id_paciente from paciente";

			rset = stmt.executeQuery(strSelect);
			rset2 = stmt2.executeQuery(strSelect2);
			ResultSetMetaData md = rset.getMetaData();
			int columns = md.getColumnCount();


			// Step 4: Process the ResultSet by scrolling the cursor forward via next().
			// For each row, retrieve the contents of the cells with getXxx(columnName).

			//  Get column names

			for (int i = 1; i <= columns; i++)
			{
				columnNames.addElement( md.getColumnName(i) );
			}

			//  Get row data
			if( rset.next() == false )
			{ 
				JOptionPane optionPane2 = new JOptionPane("No hay ningún especilista en el sistema!");
				optionPane2.setIcon(j.getIconoInfo());
				JDialog dialog2 = optionPane2.createDialog("");
				dialog2.setTitle("Error de Registro");
				dialog2.setAlwaysOnTop(true);
				dialog2.setVisible(true);
			}else if( rset2.next() == false )
			{
				JOptionPane optionPane7 = new JOptionPane("No hay ningún paciente en el sistema!");
				optionPane7.setIcon(j.getIconoInfo());
				JDialog dialog7 = optionPane7.createDialog("");
				dialog7.setTitle("Error de Registro");
				dialog7.setAlwaysOnTop(true);
				dialog7.setVisible(true);
			}else if( textFUsuario.getText().equals("") )
			{
				JOptionPane optionPane4 = new JOptionPane("No se puede dejar el campo de usuario en blanco!");
				optionPane4.setIcon(j.iconoForbid);
				JDialog dialog4 = optionPane4.createDialog("");
				dialog4.setTitle("Error de Registro");
				dialog4.setAlwaysOnTop(true);
				dialog4.setVisible(true);
			}else if( passwordField.getText().equals("") )
			{
				JOptionPane optionPane5 = new JOptionPane("No se puede dejar el campo de password en blanco!");
				optionPane5.setIcon(j.iconoForbid);
				JDialog dialog5 = optionPane5.createDialog("");
				dialog5.setTitle("Error de Registro");
				dialog5.setAlwaysOnTop(true);
				dialog5.setVisible(true);
			}else
			{ 
				do{ 
					String user = rset.getString("usuario");
					String pass = rset.getString("password");
					Integer	idRegistrado = Integer.parseInt(rset.getString("id_especialista"));

					/** COMPROBAMOS SI SE HA REGISTRADO UN USUARIO DE TIPO ESPECIALISTA O EL ADMIN**/
					
					//if( (textFUsuario.getText().equals("Admin")) && (passwordField.getPassword().equals("Admin")) )   NO FUNCIONA CON getPassword()
					if( (textFUsuario.getText().equals("Admin")) && (passwordField.getText().equals("Admin"))  && (j.registrado == false) )
					{
						j.registrado = true;

						JOptionPane optionPane10 = new JOptionPane("Autentificación con exito! Bienvenido Admin!!");
						optionPane10.setIcon(j.iconoOk);
						JDialog dialog10 = optionPane10.createDialog("");
						dialog10.setTitle("Registro Correcto");
						dialog10.setAlwaysOnTop(true);
						dialog10.setVisible(true);

						j.tipoU = JKinect.TipoUsuario.ADMIN;
						j.idRegistrado = -1111;

						break;
					}//if

					//if( (textFUsuario.getText().equals(user)) && (passwordField.getPassword().equals(pass)) )   NO FUNCIONA CON getPassword()
					if( (textFUsuario.getText().equals(user)) && (passwordField.getText().equals(pass)) && (j.registrado == false) )
					{
						j.registrado = true;

						JOptionPane optionPane8 = new JOptionPane("Autentificación con exito! Bienvenido ESPECIALISTA " + user +"!!");
						optionPane8.setIcon(j.iconoOk);
						JDialog dialog8 = optionPane8.createDialog("");
						dialog8.setTitle("Registro Correcto");
						dialog8.setAlwaysOnTop(true);
						dialog8.setVisible(true);

						j.tipoU = JKinect.TipoUsuario.ESPECIALISTA;
						j.idRegistrado = idRegistrado;


						break;
					}//if					
				}while( rset.next() );//do-while 


				/** COMPROBAMOS SI SE HA REGISTRADO UN USUARIO DE TIPO PACIENTE**/
				if(j.registrado == false)
				{
					do{ 
						String user2 = rset2.getString("usuario");
						String pass2 = rset2.getString("password");
						Integer	idRegistrado = Integer.parseInt(rset.getString("id_paciente"));

						//if( (textFUsuario.getText().equals(user2)) && (passwordField.getPassword().equals(pass2)) )   NO FUNCIONA CON getPassword()
						if( (textFUsuario.getText().equals(user2)) && (passwordField.getText().equals(pass2)) && (j.registrado == false) )
						{
							j.registrado = true;

							JOptionPane optionPane9 = new JOptionPane("Autentificación con exito! Bienvenido PACIENTE" + user2 +"!!");
							optionPane9.setIcon(j.iconoOk);
							JDialog dialog9 = optionPane9.createDialog("");
							dialog9.setTitle("Registro Correcto");
							dialog9.setAlwaysOnTop(true);
							dialog9.setVisible(true);

							j.tipoU = JKinect.TipoUsuario.PACIENTE;
							j.idRegistrado = idRegistrado;

							break;
						}//if
					}while( rset2.next() );//do-while2
				}//end if registrado false
			}//if-else

			if(j.registrado == true)
			{
				j.usuarioReg = textFUsuario.getText();
				j.jframe.dispose();	

			}else
			{
				JOptionPane optionPane6 = new JOptionPane("Combinación usuario/password incorrecta!");
				optionPane6.setIcon(j.iconoError);
				JDialog dialog6 = optionPane6.createDialog("");
				dialog6.setTitle("Error de Registro");
				dialog6.setAlwaysOnTop(true);
				dialog6.setVisible(true);
			}

		} catch(SQLException ex) {
			ex.printStackTrace();
		}//Try-Catch

		// Step 5: Close the resources - Done automatically by try-with-resources
		// Closing
		rset.close();
		stmt.close();
		conn.close();
	}// registrarse()

}//class VentRegistro()

