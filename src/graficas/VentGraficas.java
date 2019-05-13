package graficas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.ui.ApplicationFrame;

import bbdd.BaseDeDatos;
import bbdd.BaseDeDatosMySQL;
import indicadores.Aciertos;
import indicadores.Duracion;
import indicadores.Fallos;
import indicadores.Indicador;
import indicadores.Puntuacion;

import jkinect.JKinect;
import manejoXML.ResultadosXML;
import resultados.Resultado;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class VentGraficas extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTable tabla = null;
	File[] listOfFiles;
	String[] aux = new String[150];
	ResultadosXML res = null;
	BaseDeDatosMySQL db = null;

	JList<String> list = null;
	JList<String> list2 = null;
	DefaultListModel<String> dlm = new DefaultListModel<String>();	//	Para mostrar en el listado de la ventana
	DefaultListModel<String> dlm2 = new DefaultListModel<String>();	//	Para almacenar ruta completa
	ListModel<String> model = null;
	public JComboBox<String> comboBox;
	int lastSelected = -1;	// Variable que guarda la ultima row seleccionada

	
	File fichero = null;


	public ResultadosXML getRes() {
		return res;
	}

	public void setRes(ResultadosXML res) {
		this.res = res;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	Comparator<ResultadosXML> compareByFecha = new Comparator<ResultadosXML>() {
		@Override
		public int compare(ResultadosXML o1, ResultadosXML o2) {
			return o1.getFecha().compareTo(o2.getFecha());
		}
	};


	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentGraficas frame = new VentGraficas(null);
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
	@SuppressWarnings({ })
	public VentGraficas(JKinect j) {
		super("Ventana de selección de Graficas", true, true, true, true);

		String path = j.globalResul;
		j.jframeRes2 = new ArrayList<ApplicationFrame>();
		db = new BaseDeDatosMySQL();

		// VAMOS A RELLENAR LA LISTA CON EL CONTENIDO DEL DIRECTORIO DE LOS FICHEROS de video de prueba
		try {
			rellenarListas(path);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			tabla = crearTabla(db);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rellenarTabla(dlm, tabla, j);


		list = new JList<String>(dlm);
		list2 = new JList<String>(dlm2);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				j.resAbierto = false;
				db.finBaseDeDatos();
				//j.jframeRes.dispose();
				//dispose();
			}
		});
		setSize(new Dimension(900, 500));
		setMinimumSize(new Dimension(500, 300));
		setPreferredSize(new Dimension(900, 500));

		setBounds(250, 100, 900, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));


		JPanel panelSup = new JPanel();
		getContentPane().add(panelSup, BorderLayout.NORTH);
		panelSup.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(j.imgLogosUCO));
		panelSup.add(lblNewLabel_1);

		JLabel lbletitulo = new JLabel("Ficheros de resultados");
		lbletitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lbletitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelSup.add(lbletitulo);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(j.imgLogosEPS));
		panelSup.add(lblNewLabel);


		JPanel panelCen = new JPanel();
		getContentPane().add(panelCen, BorderLayout.CENTER);
		panelCen.setLayout(new BorderLayout(0, 0));

		JPanel panelNombre = new JPanel();
		panelNombre.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Resultados", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCen.add(panelNombre, BorderLayout.CENTER);
		panelNombre.setLayout(new GridLayout(0, 1, 0, 0));

		//JScrollPane scrollPane = new JScrollPane(list);
		JScrollPane scrollPane = new JScrollPane(tabla);
		panelNombre.add(scrollPane);

		JPanel panelIndicadores = new JPanel();
		panelIndicadores.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Indicadores de la Gr\u00E1fica", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCen.add(panelIndicadores, BorderLayout.SOUTH);

		// PARA PILLAS LOS NOMBRES DE LOS CHECKBOX AUTOMATICAMENTE
		ArrayList<Indicador> i = new ArrayList<Indicador>();
		i.add(new Aciertos());
		i.add(new Fallos());
		i.add(new Puntuacion());
		i.add(new Duracion());

		JCheckBox chckbxAciertos = new JCheckBox(i.get(0).getNombre());
		panelIndicadores.add(chckbxAciertos);

		JCheckBox chckbxFallos = new JCheckBox(i.get(1).getNombre());
		panelIndicadores.add(chckbxFallos);

		JCheckBox chckbxPuntuacion = new JCheckBox(i.get(2).getNombre());
		panelIndicadores.add(chckbxPuntuacion);

		JCheckBox chckbxDuracion = new JCheckBox(i.get(3).getNombre());
		panelIndicadores.add(chckbxDuracion);

		JPanel panelFiltro = new JPanel();
		panelCen.add(panelFiltro, BorderLayout.EAST);
		panelFiltro.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panelFiltro.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panelHistoExpl = new JPanel();
		panelHistoExpl.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCen.add(panelHistoExpl, BorderLayout.WEST);
		panelHistoExpl.setLayout(new GridLayout(4, 1, 0, 0));

		JCheckBox chckbxhistorico = new JCheckBox("Histórico");
		chckbxhistorico.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelHistoExpl.add(chckbxhistorico);

		JLabel lblNewLabel_5 = new JLabel("Elegir juego");
		lblNewLabel_5.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setEnabled(false);
		panelHistoExpl.add(lblNewLabel_5);

		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboBox.setName("Juego");
		comboBox.setEnabled(false);
		panelHistoExpl.add(comboBox);

		JButton btnNewButton = new JButton("Explorar...");
		panelHistoExpl.add(btnNewButton);

		JPanel panelInf = new JPanel();
		getContentPane().add(panelInf, BorderLayout.SOUTH);
		panelInf.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelRdoB = new JPanel();
		panelRdoB.setBorder(new TitledBorder(null, "Tipo de Gr\u00E1fica", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelInf.add(panelRdoB);

		JRadioButton radioButton = new JRadioButton("Circular");
		panelRdoB.add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("Barras");
		panelRdoB.add(radioButton_1);

		JRadioButton radioButton_2 = new JRadioButton("Puntos");
		panelRdoB.add(radioButton_2);
		radioButton_2.setEnabled(chckbxhistorico.isSelected());


		// 		OPCIONAL

		//		btngroup.add(radioButton);
		//		btngroup.add(radioButton_1);
		//		btngroup.add(radioButton_2);

		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton_1.setSelected(false);
				radioButton_2.setSelected(false);
			}
		});
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton.setSelected(false);
				radioButton_2.setSelected(false);
			}
		});		
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioButton_1.setSelected(false);
				radioButton.setSelected(false);
			}
		});
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(15);
		panelInf.add(panel_3);

		JButton button = new JButton("Ver");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//setFichero( new File(list.getSelectedValue().split(" - ")[0].trim()) );
				if( !chckbxhistorico.isSelected() )
				{
					setFichero( new File(tabla.getValueAt(tabla.getSelectedRow(), 0)+".xml") );
				}else
				{
					setFichero( new File(j.globalResul+tabla.getValueAt(tabla.getSelectedRow(), 0)) );
				}

				ArrayList<ResultadosXML> aux = new ArrayList<ResultadosXML>();
				ArrayList<Resultado> aux2 = new ArrayList<Resultado>();

				if( !chckbxhistorico.isSelected() )
				{
					setRes( new ResultadosXML() );

					res.cargarXML(getFichero(), j);

					aux.add(res);
				}else
				{
					//ResultadosXML aux3 = new ResultadosXML();

					setRes( new ResultadosXML() );
					File f = new File(getFichero()+"/xml/");
					File[] listOfFiles = f.listFiles();

					for(int k = 0; k < listOfFiles.length; k++)
					{
						//String juegoAux = listOfFiles[k].getName().split("-")[2];
						
						if( listOfFiles[k].getName().equals(".DS_Store") || !listOfFiles[k].getName().split("-")[2].equals(comboBox.getSelectedItem()) )
						{
							listOfFiles[k] = null;
						}
					}

					ordenarArchivos(listOfFiles);

					for(int i = 0; i < listOfFiles.length; i++)
					{	
						if(listOfFiles[i] != null){

							ResultadosXML r = new ResultadosXML();
							r.cargarXML(new File( listOfFiles[i].getName() ), j);
							res.cargarXML(new File( listOfFiles[i].getName() ), j);
							aux.add(r);

						}
					}//end for i


					// ORDENAR POR FECHA EL --> ArrayList< ResultadosXML >
					aux.sort(compareByFecha);
				}// end if-else


				if(fichero != null && radioButton.isSelected() && radioButton.isEnabled())
				{
					j.resAbierto2 = true;

					ArrayList<Indicador> i = new ArrayList<Indicador>();
					ArrayList<LocalDate> l = new ArrayList<LocalDate>();

					l.add(res.getFecha());

					if(chckbxAciertos.isSelected())
					{
						i.add(new Aciertos(res.getAciertos()));
					}else
					{
						i.add(new Aciertos(-1));
					}

					if(chckbxFallos.isSelected())
					{
						i.add(new Fallos(res.getFallos()));
					}else
					{
						i.add(new Fallos(-1));
					}

					if(chckbxPuntuacion.isSelected())
					{
						i.add(new Puntuacion(res.getPuntuacion()));
					}else
					{
						i.add(new Puntuacion(-1));
					}

					if(chckbxDuracion.isSelected())
					{
						i.add(new Duracion(res.getDuracion()));
					}else
					{
						i.add(new Duracion(-1));
					}//end if indicadores checkboxes

					aux2.add(new Resultado(i, l, res.getId_especialista(), res.getId_paciente()));

					new GraficaAppFrame("Grafica Circular de la sesion "+getRes().getNombre(), new GraficaCircular("", aux2), j);

				}else if(fichero != null && radioButton_1.isSelected())
				{
					j.resAbierto2 = true;

					for(int j = 0 ; j < aux.size(); j++)
					{
						ArrayList<Indicador> i = new ArrayList<Indicador>();
						ArrayList<LocalDate> l = new ArrayList<LocalDate>();

						l.add(aux.get(j).getFecha());

						if(chckbxAciertos.isSelected())
						{
							i.add(new Aciertos(aux.get(j).getAciertos() ));
						}else
						{
							i.add(new Aciertos(-1));
						}

						if(chckbxFallos.isSelected())
						{
							i.add(new Fallos(aux.get(j).getFallos()));
						}else
						{
							i.add(new Fallos(-1));
						}

						if(chckbxPuntuacion.isSelected())
						{
							i.add(new Puntuacion(aux.get(j).getPuntuacion()));
						}else
						{
							i.add(new Puntuacion(-1));
						}

						if(chckbxDuracion.isSelected())
						{
							i.add(new Duracion(aux.get(j).getDuracion()));
						}else
						{
							i.add(new Duracion(-1));
						}//end if indicadores checkboxes

						aux2.add(new Resultado(i, l, aux.get(j).getId_especialista(), aux.get(j).getId_paciente()));
					}//end for i aux

					new GraficaAppFrame("Grafica de Barras de la sesion "+getRes().getNombre(), new GraficaBarras("", aux2), j);

				}else if(fichero != null && radioButton_2.isSelected())
				{
					j.resAbierto2 = true;

					for(int j = 0 ; j < aux.size(); j++)
					{
						ArrayList<Indicador> i = new ArrayList<Indicador>();
						ArrayList<LocalDate> l = new ArrayList<LocalDate>();

						l.add(aux.get(j).getFecha());

						if(chckbxAciertos.isSelected())
						{
							i.add(new Aciertos(aux.get(j).getAciertos()));
						}else
						{
							i.add(new Aciertos(-1));
						}

						if(chckbxFallos.isSelected())
						{
							i.add(new Fallos(aux.get(j).getFallos()));
						}else
						{
							i.add(new Fallos(-1));
						}

						if(chckbxPuntuacion.isSelected())
						{
							i.add(new Puntuacion(aux.get(j).getPuntuacion()));
						}else
						{
							i.add(new Puntuacion(-1));
						}

						if(chckbxDuracion.isSelected())
						{
							i.add(new Duracion(aux.get(j).getDuracion()));
						}else
						{
							i.add(new Duracion(-1));
						}//end if indicadores checkboxes

						aux2.add(new Resultado(i, l, aux.get(0).getId_especialista(), aux.get(0).getId_paciente()));
					}//end for j aux

					new GraficaAppFrame("Grafica de Puntos de la sesion "+getRes().getNombre(), new GraficaPuntos("", aux2), j);

				}//end if

			}//en actionPerformed VER
		});
		panel_3.add(button);

		JButton button_1 = new JButton("Salir");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.resAbierto = false;
				db.finBaseDeDatos();
				doDefaultCloseAction();
			}
		});
		panel_3.add(button_1);

		chckbxhistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxhistorico.isSelected())
				{
					radioButton_2.setEnabled(true);
					radioButton.setEnabled(false);
					radioButton.setSelected(false);
					comboBox.setEnabled(true);
					lblNewLabel_5.setEnabled(true);

					try {
						tabla = crearTablaDir(db, j);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					scrollPane.setViewportView(tabla);

				}else
				{
					radioButton_2.setSelected(false);
					radioButton_2.setEnabled(false);
					radioButton.setEnabled(true);
					comboBox.setEnabled(false);
					lblNewLabel_5.setEnabled(false);

					try {
						tabla = crearTabla(db);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

					rellenarTabla(dlm, tabla, j);

					scrollPane.setViewportView(tabla);

				}//end if-else historico selected
			}//end actionPerformed()

		});//end addActionListener()

	}// end Constructor()

	protected void rellenarComboBox(JComboBox<String> comboBox, int seleccionada, JKinect j) {

		if(seleccionada != -1)
		{
			comboBox.removeAllItems();
			
			ArrayList<String> juegos = new ArrayList<String>();
			ResultadosXML r = new ResultadosXML();
			
			File f = new File(j.globalResul+seleccionada+"/xml/");
			File[] ficheros = f.listFiles();
			String aux = null;

			for(int i = 0; i < ficheros.length ; i++)
			{
				if(!ficheros[i].isHidden())
				{
					r.cargarXML(new File(ficheros[i].getName()), j);
					//aux = ficheros[i].getName().split("-")[2];
					aux = r.getNombre();
					
					if(juegos.isEmpty() || !juegos.contains(aux))
					{
						juegos.add(aux);
						comboBox.addItem(aux);
					}
					
				}//end if hidden
				
			}//end for i
		}//end if

	}//rellenarComboBox()

	private void rellenarTabla(DefaultListModel<String> dlm, JTable tabla, JKinect j) {
		for(int i = 0 ; i < dlm.size(); i++)
		{
			ResultadosXML r = new ResultadosXML();

			r.cargarXML(new File(dlm.get(i).trim()), j);

			try {
				añadirTupla(r, (DefaultTableModel) tabla.getModel(), dlm.get(i));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}//end for		
	}//end rellenarTabla()

	public void rellenarListas(String path) throws SQLException{

		File fer = new File(path);
		File[] listOfFiles = fer.listFiles();
		String auxID;

		dlm.clear();
		dlm2.clear();

		for(int i = 0; i < listOfFiles.length; i++)
		{
			File[] subFichero = listOfFiles[i].listFiles();

			if(subFichero != null)
			{
				for(int k = 0 ; k < subFichero.length; k++)
				{
					if(subFichero[k].getName().equals("xml"))
					{
						String[] subFichero2 = new String[1000];
						subFichero2 = subFichero[k].list();


						if(subFichero2 != null){	
							for(int j = 0; j < subFichero2.length; j++)
							{
								if(!subFichero2[j].equals(".DS_Store"))
								{
									auxID = subFichero2[j].split("-")[3]; // Pillamos el ...IDPACIENTE.xml
									auxID = auxID.split(".xml")[0];		 // Quitamos el .xml del IDPACIENTE
									String sentencia = "SELECT nombre, apellidos "
											+ "FROM paciente WHERE id_paciente = " + auxID;
									ResultSet r = db.select(sentencia);

									if(r.next()){auxID = r.getString(1)+" "+r.getString(2);}
									//dlm.addElement("\t\t\t\t"+subFichero[j]+" - "+auxID);

									dlm.addElement(subFichero2[j]);
									dlm2.addElement("\t\t\t\t"+subFichero2[j]);

								}//end for k
							}//end subFichero[j].getName().equals("xml")
						}//end for j 	
					}//end subFichero != null && subFichero[i].getName().equals("xml")
				}
			}//end for k
		}//end for i

		ordenarModelo(dlm);
		ordenarModelo(dlm2);
	}//end rellenarListas()


	public void filterModel(String filter) {

		for(int i = 0; i < dlm.getSize(); i++)
		{
			//SI EL ELEMENTO i DEL MODELO NO CONTIENE FILTER  --> LO BORRAMOS
			if(!dlm.getElementAt(i).contains(filter.trim())) {
				//SI EL MODELO YA CONTIENE FILTER
				//if(dlm.contains(dlm.getElementAt(i))) {
				//QUITAMOS ESE ELEMENTO
				dlm.removeElement(dlm.getElementAt(i));
				//}
			}else{
				//SI EL MODELO NO CONTIENE FILTER AUN --> LO AÑADIMOS
				if(!dlm.contains(dlm.getElementAt(i))) {
					//METEMOS ESE ELEMENTO
					dlm.addElement(dlm.getElementAt(i));
				}
			}//end if-else

		}//end for i
	}

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

	public void ordenarArchivos(File[] modelo) {

		for(int i = 0; i < modelo.length-1; i++)
		{
			for(int j = 1; j < modelo.length-2; j++)
			{
				if(modelo[i] != null && modelo[j] != null && modelo[i].toString().compareTo(modelo[j].toString()) > 0 ) {

					// String "i" precede a String "j"

					// BIEN ORDENADO 

				}else{

					File aux = modelo[i];
					modelo[i] = modelo[j];
					modelo[j] = aux;
				}//end if-else
			}//end for j
		}//end for i
	}//end ordenarArchivos()


	public class GroupButtonUtils {

		public String getSelectedButtonText(ButtonGroup buttonGroup) {
			for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (button.isSelected()) {
					return button.getText();
				}
			}

			return null;
		}
	}

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

	public JTable crearTabla(BaseDeDatos db) throws SQLException{

		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();

		columnNames.addElement("Fichero");
		columnNames.addElement("Id_Paciente");
		columnNames.addElement("Nombre");
		columnNames.addElement("Apellidos");
		columnNames.addElement("Fecha");


		DefaultTableModel model = new DefaultTableModel(data, columnNames)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}

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

		// SORTING ROWS
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);

		// Ordenar tuplas por columnas
		table.setAutoCreateRowSorter(true);

		/** Celdas editables y llamada a la funcion UPDATE**/
		table.setColumnSelectionAllowed(false);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);


		return table;

	}//accederBBDD()

	public JTable crearTablaDir(BaseDeDatos db, JKinect j) throws SQLException{

		ResultSet rset = null;
		int rowCount = 0;
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();

		String strSelect = "select id_paciente, nombre, apellidos from paciente";
		System.out.println("The SQL query is: " + strSelect); // Echo For debugging
		System.out.println();

		rset = db.select(strSelect);

		ResultSetMetaData md = rset.getMetaData();
		int columns = md.getColumnCount();

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

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				lastSelected = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
				rellenarComboBox(comboBox, lastSelected, j);
			}
			
		
		});
		
		System.out.println("Total number of records = " + rowCount);

		return table;

	}//accederBBDD()

	public void añadirTupla(ResultadosXML res, DefaultTableModel model, String file) throws SQLException{

		Vector<String> aux = new Vector<String>();


		String auxID = file.split(".xml")[0];
		aux.add(auxID);

		auxID = res.getId_paciente().toString();
		aux.add(auxID);

		String sentencia = "SELECT nombre, apellidos "
				+ "FROM paciente WHERE id_paciente = " + auxID;
		ResultSet r = db.select(sentencia);
		if( r.next() )
		{
			auxID = r.getString(1);
			aux.add(auxID);			

			auxID = r.getString(2);
			aux.add(auxID);
		}//end if


		//dlm.addElement("\t\t\t\t"+listOfFiles[i]+"/"+" - "+auxID);

		aux.add(res.getFecha().toString());


		model.insertRow(model.getRowCount(), aux);

	}//añadirTupla()

}//end class VentGraficas
