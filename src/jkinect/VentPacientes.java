package jkinect;

import java.awt.EventQueue;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import bbdd.BaseDeDatos;
import bbdd.BaseDeDatosMySQL;
import pintarEnCanvas.MiCanvasPreview;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class VentPacientes extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable tablePacientes;
	private JTable tableAux;
	private  MiCanvasPreview canvas = null;
	private  Image imgPreview = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentPacientes frame = new VentPacientes(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentPacientes(JKinect j, BaseDeDatosMySQL db) {

		super("Ventana de Pacientes", true, true, true, true);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				j.pacAbierto = false;
			}
		});
		setSize(new Dimension(1000, 500));
		setMinimumSize(new Dimension(250, 95));
		setPreferredSize(new Dimension(1000, 500));

		setBounds(250, 150, 1000, 500);
		getContentPane().setLayout(new BorderLayout(5, 5));


		try {
			tablePacientes = accederBBDD(db);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JPanel panelSup = new JPanel();
		getContentPane().add(panelSup, BorderLayout.NORTH);
		panelSup.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(j.imgLogosUCO));
		panelSup.add(lblNewLabel_1);

		JLabel lbletitulo = new JLabel("Listado de Pacientes");
		lbletitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lbletitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelSup.add(lbletitulo);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(j.imgLogosEPS));
		panelSup.add(lblNewLabel);

		JPanel panelCen = new JPanel();
		panelCen.setBorder(new TitledBorder(null, "Pacientes", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		getContentPane().add(panelCen, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(tablePacientes);
		panelCen.setLayout(new GridLayout(0, 1, 0, 0));
		panelCen.add(scrollPane);

		JPanel panelInf = new JPanel();
		getContentPane().add(panelInf, BorderLayout.SOUTH);
		panelInf.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

		JButton btnNuevoEsp = new JButton("Añadir");
		panelInf.add(btnNuevoEsp);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				j.pacAbierto = false;
				doDefaultCloseAction();
				//j.jframePac.dispose();

			}
		});
		panelInf.add(btnSalir);

		JPanel panelD = new JPanel();
		panelD.setBorder(new TitledBorder(null, "Especialistas", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		getContentPane().add(panelD, BorderLayout.EAST);
		panelD.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(350, 404));
		panelD.add(scrollPane_1);

		JPanel panelIz = new JPanel();
		panelIz.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Foto", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panelIz, BorderLayout.WEST);
		panelIz.setLayout(new GridLayout(1, 0, 10, 10));

		canvas = new MiCanvasPreview(imgPreview);
		canvas.setPreferredSize(new Dimension(100, 175));
		panelIz.add(canvas);

		tablePacientes.setRowSelectionAllowed(true);												

		tablePacientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (tablePacientes.getSelectedRow() > -1) {

					//File fichero = new File("", (String)tablePacientes.getValueAt(tablePacientes.getSelectedRow(), 4));
					File fichero = new File(((String)tablePacientes.getValueAt(tablePacientes.getSelectedRow(), 4)));


					BufferedImage imgb = null;
					try {
						imgb = ImageIO.read(fichero);
					} catch (IOException e1) {
						System.out.println(fichero);
					}

					canvas.setImagen(imgb);

					canvas.repaint();

					try {
						tableAux = accederBBDDAux( (int) tablePacientes.getValueAt(tablePacientes.getSelectedRow(), 0), db);
						scrollPane_1.setViewportView(tableAux);
					} catch (SQLException e) {
						e.printStackTrace();
					}//try-catch

				}
			}
		});

	}// VentPacientes()

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
			String strSelect = "select id_paciente, nombre, apellidos, email, foto from paciente";
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
			table.setColumnSelectionAllowed(true);
			table.setRowSelectionAllowed(true);

			model.addTableModelListener(new TableModelListener(){
				@Override
				public void tableChanged(TableModelEvent e) {
					updateTable(e, model, table, db);

				}
			});            

			if (table.getCellEditor() != null) {
				table.getCellEditor().stopCellEditing();
			}//end if


			System.out.println("Total number of records = " + rowCount);


			return table;

//		} catch(SQLException ex) {
//			ex.printStackTrace();
//		}

		// Step 5: Close the resources - Done automatically by try-with-resources
		// Closing
//		rset.close();
//		stmt.close();
//		conn.close();

	}//accederBBDD()

	public static JTable accederBBDDAux(int paciente, BaseDeDatos db) throws SQLException{
		
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
			String strSelect = "SELECT id_especialista, nombre, apellidos, email "
					+ "FROM especialista "
					+ "WHERE id_especialista IN (SELECT id_especialista "
					+ "FROM especialista_paciente "
					+ "WHERE id_paciente = " + paciente + ")";
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
			table.setColumnSelectionAllowed(true);
			table.setRowSelectionAllowed(true);

			model.addTableModelListener(new TableModelListener(){
				@Override
				public void tableChanged(TableModelEvent e) {
					updateTable2(e, model, table, db);

				}
			});            

			if (table.getCellEditor() != null) {
				table.getCellEditor().stopCellEditing();
			}//end if

			System.out.println("Total number of records = " + rowCount);


			return table;

//		} catch(SQLException ex) {
//			ex.printStackTrace();
//		}

		// Step 5: Close the resources - Done automatically by try-with-resources
		// Closing
//		rset.close();
//		stmt.close();
//		conn.close();
//
//		return null;
	}//accederBBDDAux()

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

	protected static void updateTable(TableModelEvent e, DefaultTableModel model, JTable table, BaseDeDatos db)  {

		if (e.getType() == TableModelEvent.UPDATE) {

			model = (DefaultTableModel) ((e.getSource()));
			int fila = e.getFirstRow();
			int columna = e.getColumn();
			String sSQL = null;

			String dato = String.valueOf(model.getValueAt(table.getSelectedRow(),table.getSelectedColumn()));


			if(columna == 1)
			{
				sSQL = "UPDATE paciente SET nombre=UPPER('" + dato + "') WHERE id_paciente=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			if(columna == 2)
			{
				sSQL = "UPDATE paciente SET apellidos=UPPER('" + dato + "') WHERE id_paciente=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			if(columna == 3)
			{
				sSQL = "UPDATE paciente SET email=UPPER('" + dato + "') WHERE id_paciente=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			try
			{   	
				db.update(sSQL);
			}catch(SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "Error" + e1);
			}//end try-catch   

		}
	}//end updateTable2()

	protected static void updateTable2(TableModelEvent e, DefaultTableModel model, JTable table, BaseDeDatos db)  {

		if (e.getType() == TableModelEvent.UPDATE) {

			model = (DefaultTableModel) ((e.getSource()));
			int fila = e.getFirstRow();
			int columna = e.getColumn();
			String sSQL = null;

			String dato = String.valueOf(model.getValueAt(table.getSelectedRow(),table.getSelectedColumn()));


			if(columna == 1)
			{
				sSQL = "UPDATE especialista SET nombre=UPPER('" + dato + "') WHERE id_especialista=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			if(columna == 2)
			{
				sSQL = "UPDATE especialista SET apellidos=UPPER('" + dato + "') WHERE id_especialista=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			if(columna == 3)
			{
				sSQL = "UPDATE especialista SET email=UPPER('" + dato + "') WHERE id_especialista=" + table.getValueAt(fila, 0) + "";
				System.out.println(sSQL);
			}

			try
			{     	
				db.update(sSQL);
			}catch(SQLException e1)
			{
				JOptionPane.showMessageDialog(null, "Error" + e1);
			}//end try-catch   

		}
	}//end updateTable()


}//class
