package jkinect;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import bbdd.BaseDeDatos;
import bbdd.BaseDeDatosMySQL;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;


public class VentJuego extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel panelExt = new JPanel();
	private static VentRegistro dialog = null;

	private String xml = null;
	private String exe = null;
	private int especialista;
	private int paciente;
	private JTable tabla;
	JList<String> list = null;
	DefaultListModel<String> dlm = new DefaultListModel<String>();

	Process proceso = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new VentRegistro(null, null);
			dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @param db 
	 */
	public VentJuego(JKinect j, BaseDeDatosMySQL db) {
		super(j.jframeConfig, true);

		// Establecemos los path necesarios para el ejecutable, los ficheros xml de configuracion y el especialista
		exe = j.globalExe;
		File exef = new File(exe);
		xml = j.globalXML;
		File xmlf = new File(xml);

		try {
			tabla = accederBBDD(db);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rellenarListas(j.globalXML, j);


		setTitle("Lanzar juego desde JKinect");
		setResizable(false);
		setModal(true);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setBounds(400, 150, 700, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		//JPanel panelExt = new JPanel();
		getContentPane().add(panelExt);
		panelExt.setLayout(new BorderLayout(0, 0));

		JPanel panelSuperior = new JPanel();
		panelExt.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelSupIzq = new JPanel();
		panelSuperior.add(panelSupIzq);
		panelSupIzq.setLayout(new BorderLayout(0, 0));

		JLabel lblIcono = new JLabel("");
		lblIcono.setIconTextGap(0);
		lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcono.setIcon(new ImageIcon(j.imgLogosEPSG));
		panelSupIzq.add(lblIcono, BorderLayout.CENTER);

		JLabel lblIcono2 = new JLabel("");
		lblIcono2.setIconTextGap(0);
		lblIcono2.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcono2.setIcon(new ImageIcon(j.imgLogosUCO));

		JPanel panelSupDer = new JPanel();
		panelSuperior.add(panelSupDer);
		panelSupDer.add(lblIcono2, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel();
		panelInferior.setMaximumSize(new Dimension(32767, 30));
		panelExt.add(panelInferior);
		panelInferior.setLayout(new BorderLayout(0, 0));

		JPanel panel_10 = new JPanel();
		panelInferior.add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSeleccionaUnPaciente = new JLabel("Seleccionar un paciente y una configuracion");
		panel_10.add(lblSeleccionaUnPaciente);
		lblSeleccionaUnPaciente.setHorizontalTextPosition(SwingConstants.LEFT);
		lblSeleccionaUnPaciente.setToolTipText("Ruta hacía el directorio que contiene los ficheros XML de configuración de actividades");

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Pacientes", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelInferior.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane(tabla);
		panel_2.add(scrollPane_1);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Configuraciones", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelInferior.add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		list = new JList<String>(dlm);
		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane);

		JPanel panel_3 = new JPanel();
		panelExt.add(panel_3, BorderLayout.SOUTH);

		JButton buttonAceptar = new JButton("Lanzar");
		buttonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				/** RELLENAR**/			
				if(tabla.getSelectedRow() != -1 && list.getSelectedValue() != null )
				{
					paciente = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
					especialista = j.idRegistrado;
					xml = xmlf+list.getSelectedValue().trim();
					
					try {
						System.out.println("El comando que vamos a lanzar es: \n");
						System.out.println();
						System.out.println("ProcessBuilder(\""+exef.getAbsolutePath()+"\""+", "+"\""+xmlf.getAbsolutePath()+", "+((Integer)paciente).toString()+", "+((Integer)especialista).toString()+")" );
						System.out.println();
						
						
						// Orden a lanzar --> System.exec(Kinect.exe, config.xml, id_pac, id_espec, rutaResultado)
						/*String[] vars = {};
						proceso = Runtime.getRuntime().exec("./"+exe+" "+xml+" "+((Integer)paciente).toString()+" "+((Integer)especialista).toString(), vars, null);
						*/
						ProcessBuilder pb = new ProcessBuilder(exef.getAbsolutePath(), xmlf.getAbsolutePath(), ((Integer)paciente).toString(), ((Integer)especialista).toString() );
						proceso = pb.start();
						int errCode = proceso.waitFor();
						
						System.out.println("Command executed, any errors? " + (errCode == 0 ? "No" : "Yes"));
						System.out.println("Comand InputStream:\n" + output(proceso.getInputStream()));
						System.out.println("Comand ErrorStream:\n" + output(proceso.getErrorStream()));
						
						dispose();
					} catch (IOException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}else
				{
					JOptionPane optionPane = new JOptionPane("Error!!! Debe seleccionar un paciente de la tabla y una configuración "
															+ "de la lista para lanzar el juego!!!");
					optionPane.setIcon(j.iconoError);
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Registro Correcto");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}//end if-else tabla.getSelectedRow() != -1

			}//end actionPerformed()
		});
		buttonAceptar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonAceptar.setActionCommand("Entrar");
		panel_3.add(buttonAceptar);

		JButton buttonCancelar = new JButton("Cancelar");
		buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
		buttonCancelar.setActionCommand("Cancelar");
		panel_3.add(buttonCancelar);


	}//end Constructor VentConfiguracion()
	public static JTable accederBBDD(BaseDeDatos db) throws SQLException{

		ResultSet rset = null;
		int rowCount = 0;
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();

		//try{
		// Step 1: Allocate a database 'Connection' object
		//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/terapiaKinectInterfaceDB?useSSL=false", "myuser", "ramon_sm");
		// MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"

		// Step 2: Allocate a 'Statement' object in the Connection
		//stmt = conn.createStatement();

		// Step 3: Execute a SQL SELECT query, the query result
		//  is returned in a 'ResultSet' object.
		//String strSelect = "select title, price, qty from books";
		String strSelect = "select id_paciente, nombre, apellidos from paciente";
		System.out.println("The SQL query is: " + strSelect); // Echo For debugging
		System.out.println();

		rset = db.select(strSelect);

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

		while (rset.next())
		{
			Vector<Object> row = new Vector<Object>(columns);

			for (int i = 1; i <= columns; i++)
			{
				if( (rset.getObject(i)).getClass().equals(String.class) && (i > 0)  && (i < 4))
				{
					row.addElement( convert((String) rset.getObject(i)) );
				}else if((rset.getObject(i)).getClass().equals(String.class) && (i == 4))
				{
					row.addElement( ((String) rset.getObject(i)).toLowerCase() );
				}else
				{
					row.addElement( rset.getObject(i) );
				}
			}

			data.addElement( row );
		}

		//  Create table with database data

		DefaultTableModel model = new DefaultTableModel(data, columnNames)
		{
			private static final long serialVersionUID = 1L;

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Class getColumnClass(int column)
			{
				for (int row = 0; row < getRowCount(); row++)
				{
					Object o = getValueAt(row, column);

					if (o != null)
					{
						return o.getClass();
					}
				}

				return Object.class;
			}
		};


		// Autoajustamos ancho de las columnas para que quepan los datoss
		JTable table = new JTable(model){
			private static final long serialVersionUID = 1L;

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Ordenar tuplas por columnas
		table.setAutoCreateRowSorter(true);

		/** Celdas editables y llamada a la funcion UPDATE**/
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);




		System.out.println("Total number of records = " + rowCount);


		return table;
	}//accederBBDD()

	static String convert(String str) 
	{ 

		// Create a char array of given String 
		char ch[] = str.toCharArray(); 
		for (int i = 0; i < str.length(); i++) { 

			// If first character of a word is found 
			if (i == 0 && ch[i] != ' ' ||  
					ch[i] != ' ' && ch[i - 1] == ' ') { 

				// If it is in lower-case 
				if (ch[i] >= 'a' && ch[i] <= 'z') { 

					// Convert into Upper-case 
					ch[i] = (char)(ch[i] - 'a' + 'A'); 
				} 
			} 

			// If apart from first character 
			// Any one is in Upper-case 
			else if (ch[i] >= 'A' && ch[i] <= 'Z')  

				// Convert into Lower-Case 
				ch[i] = (char)(ch[i] + 'a' - 'A');             
		} 

		// Convert the char array to equivalent String 
		String st = new String(ch); 
		return st; 
	}//convert()

	public void rellenarListas(String path, JKinect j){

		File fer = new File(path);
		//XMLFileFilter xmlFilter = new XMLFileFilter();
		File[] listOfFiles = fer.listFiles();

		for(int i = 0; i < listOfFiles.length; i++)
		{
			if( !listOfFiles[i].isHidden() )
			{
				dlm.addElement("\t\t\t\t"+listOfFiles[i].getName());

			}//end if subFichero != null
		}//end for i

		ordenarModelo(dlm);
		//ordenarModelo(dlm2);
	}//end rellenarLista()

	public void ordenarModelo(DefaultListModel<String> modelo) {

		for(int i = 0; i < modelo.getSize()-1; i++)
		{
			for(int j = 1; j < modelo.getSize()-2; j++)
			{
				if(modelo.getElementAt(i).compareTo(modelo.getElementAt(j)) > 0 ) {

					// String "i" precede a String "j"

					// BIEN ORDENADO 

				}else{
					
					String aux = modelo.getElementAt(i);
					modelo.setElementAt(modelo.getElementAt(j), i);
					modelo.setElementAt(aux, j);
				}//end if-else
			}//end for j
		}//end for i
	}//end ordenarModelo()
	
	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}
	
}// end clas VentConfiguracion