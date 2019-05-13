package jkinect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import manejoXML.ManejadorXML;

public class VentActividades extends JInternalFrame {


	File[] listOfFiles;
	String[] aux = new String[100];
	String[] aux2 = new String[100];
	String[] aux3 = new String[100];
	String[] aux4 = new String[100];



	JList<String> list = null;
	DefaultListModel<String> dlm = new DefaultListModel<String>();

	//ListModel<String> model = null;

	File fichero = null;
	ManejadorXML xml = new ManejadorXML();

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentActividades frame = new VentActividades(null);
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
	public VentActividades(JKinect j) {
		super("Ventana de Actividades", true, true, true, true);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		
		String path = j.globalXML;

		// VAMOS A RELLENAR LA LISTA CON EL CONTENIDO DEL DIRECTORIO DE LOS FICHEROS XML
		rellenarListas(path, j);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				j.actAbierto = false;
			}
		});
		setSize(new Dimension(900, 350));
		setMinimumSize(new Dimension(250, 95));
		setPreferredSize(new Dimension(900, 350));

		setBounds(250, 100, 900, 350);
		getContentPane().setLayout(new BorderLayout(0, 0));


		JPanel panelSup = new JPanel();
		getContentPane().add(panelSup, BorderLayout.NORTH);
		panelSup.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(j.imgLogosUCO));
		panelSup.add(lblNewLabel_1);

		JLabel lbletitulo = new JLabel("Listado de Actividades");
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
		panelNombre.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Ficheros XML", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCen.add(panelNombre, BorderLayout.CENTER);
		panelNombre.setLayout(new GridLayout(0, 1, 0, 0));
		list = new JList<String>(dlm);
		JScrollPane scrollPane = new JScrollPane(list);
		panelNombre.add(scrollPane);

		JPanel panel = new JPanel();
		panelCen.add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Filtrar Resultados", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)), "Filtrar Resultados", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_4, BorderLayout.SOUTH);

		lblNewLabel_4.setText("Número de Resultados: " + dlm.size());

		
		JLabel lblNewLabel_2 = new JLabel("");
		panel_4.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("");
		panel_4.add(lblNewLabel_3);

		JLabel label = new JLabel("Busqueda");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(label);

		textField = new JTextField();
		textField.setText("");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(new DocumentListener(){
			@Override public void insertUpdate(DocumentEvent e)
			{
				filter();
			}
			@Override public void removeUpdate(DocumentEvent e)
			{
				dlm.clear();
				rellenarListas(path, j);
				lblNewLabel_4.setText("Número de Resultados: " + dlm.size());
				//filter();
			}
			@Override public void changedUpdate(DocumentEvent e)
			{
				//filter();
			}
			private void filter() {
				String filter = textField.getText();
				filterModel(filter);
				lblNewLabel_4.setText("Número de Resultados: " + dlm.size());
			}
		});
		//*/
		panel_4.add(textField);

		JPanel panelInf = new JPanel();
		getContentPane().add(panelInf, BorderLayout.SOUTH);
		panelInf.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

		JButton btnCrear = new JButton("Cargar");
		btnCrear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(!list.isSelectionEmpty())
				{
					fichero = new File(path+(list.getSelectedValue()).trim()+"/"+(list.getSelectedValue()).trim());
				}


				if(j.actAbierto2 == false)
				{
					// ABRIMOS LA NUEVA VENTACTIVIDADES2
					j.jframeAct2 = new VentActividades2(j);
					j.desktopPane.add(j.jframeAct2);
					j.jframeAct2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					j.jframeAct2.setVisible(true);		
					j.actAbierto2 = true;
					j.actAbierto = true;


					// ABRIMOS INSTANCIA DEL MANEJADOR EN EL DESKTOP
					xml.cargarXML(fichero, j);
					j.jframeAct2.setXml(xml);
					j.jframeAct2.cargarManejadorEnVentana();


					// CERRAMOS ESTA VENTACTIVIDADES						
					j.jframeAct.dispose();
					j.actAbierto = false;

				}//if
			}});
		panelInf.add(btnCrear);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//j.jframeAct.dispose();
				j.actAbierto = false;
				doDefaultCloseAction();
			}
		});
		panelInf.add(btnSalir);

		JButton btnExplorar = new JButton("Explorar...");
		btnExplorar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// FILTRO XML
				JFileChooser fileChooser = new JFileChooser(j.globalXML);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("XML files", "xml", "XML");
				fileChooser.setFileFilter(filter);

				int seleccion = fileChooser.showOpenDialog(getParent());

				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					fichero = fileChooser.getSelectedFile();

					if(j.actAbierto2 == false)
					{
						// ABRIMOS LA NUEVA VENTACTIVIDADES2
						j.jframeAct2 = new VentActividades2(j);
						j.desktopPane.add(j.jframeAct2);
						j.jframeAct2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						j.jframeAct2.setVisible(true);		
						j.actAbierto2 = true;
						j.actAbierto = true;


						// ABRIMOS INSTANCIA DEL MANEJADOR EN EL DESKTOP
						xml.cargarXML(fichero, j);
						j.jframeAct2.setXml(xml);
						j.jframeAct2.cargarManejadorEnVentana();


						// CERRAMOS ESTA VENTACTIVIDADES						
						j.jframeAct.dispose();
						j.actAbierto = false;

					}//if


				}//IF SELECCION
			}
		});
		panelInf.add(btnExplorar);



	}// VentActividades()

	public void rellenarListas(String path, JKinect j){

		File fer = new File(path);
		//XMLFileFilter xmlFilter = new XMLFileFilter();
		File[] listOfFiles = fer.listFiles();

		for(int i = 0; i < listOfFiles.length; i++)
		{
			if( !listOfFiles[i].isHidden() )
			{
				dlm.addElement("\t\t\t\t"+listOfFiles[i].toString());
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

	
	public void filterModel(String filter) {

		for(int i=0; i < dlm.getSize(); i++)
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
}// class


