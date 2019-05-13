package video;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import jkinect.JKinect;

import java.io.File;
import java.lang.reflect.InvocationTargetException;



public class VentVideos extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	File[] listOfFiles;
	String[] aux = new String[100];

	JList<String> list = null;
	JList<String> list2 = null;
	DefaultListModel<String> dlm = new DefaultListModel<String>();	//	Para mostrar en el listado de la ventana
	DefaultListModel<String> dlm2 = new DefaultListModel<String>();	//	Para almacenar ruta completa
	ListModel<String> model = null;

	File fichero = null;

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}


	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentVideos frame = new VentVideos(null);
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
	public VentVideos(JKinect j) {
		super("Ventana de selección de video", true, true, true, true);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		String path = j.globalResul;

		// VAMOS A RELLENAR LA LISTA CON EL CONTENIDO DEL DIRECTORIO DE LOS FICHEROS de video de prueba
		rellenarListas(path);

		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				j.vidAbierto = false;
				//j.jframeVid.dispose();
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

		JLabel lbletitulo = new JLabel("Listado de videos de las sesiones");
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
		panelNombre.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Videos", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCen.add(panelNombre, BorderLayout.CENTER);
		panelNombre.setLayout(new GridLayout(0, 1, 0, 0));
		list = new JList<String>(dlm);
		list2 = new JList<String>(dlm2);
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
				rellenarListas(path);
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

		JButton btnCrear = new JButton("Reproducir");
		btnCrear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(!list.isSelectionEmpty())
				{
					fichero = new File(dlm.get(list.getSelectedIndex()).trim());
					//fichero = new File( dlm2.get(list.getSelectedIndex()) );
				}

				if(j.vidAbierto2 == false)
				{
					// ABRIMOS LA NUEVA VENTACTIVIDADES2
					try {
						j.jframeVid2 = new DirectRendering(j);
						j.jframeVid2.setFichero(fichero);
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					j.desktopPane.add(j.jframeVid2);
					j.jframeVid2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					j.jframeVid2.setVisible(true);		
					j.vidAbierto2 = true;
					j.vidAbierto = true;

					// CERRAMOS ESTA VENTACTIVIDADES						
					//j.jframeVid.dispose();
					j.vidAbierto = false;
					doDefaultCloseAction();
				}//if

			}});
		panelInf.add(btnCrear);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//j.jframeVid.dispose();
				j.vidAbierto = false;
				doDefaultCloseAction();
			}
		});
		panelInf.add(btnSalir);

		JButton btnExplorar = new JButton("Explorar...");
		btnExplorar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// FILTRO XML
				JFileChooser fileChooser = new JFileChooser(j.globalResul);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Video files", "avi", "AVI", "MP4", "mp4", "MOV", "mov");
				fileChooser.setFileFilter(filter);

				int seleccion = fileChooser.showOpenDialog(getParent());

				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					fichero = fileChooser.getSelectedFile();

					if(j.vidAbierto2 == false)
					{
						// ABRIMOS LA NUEVA VENTVIDEOS2
						try {
							j.jframeVid2 = new DirectRendering(j);
							j.jframeVid2.setFichero(fichero);
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						j.desktopPane.add(j.jframeVid2);
						j.jframeVid2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						j.jframeVid2.setVisible(true);		
						j.vidAbierto2 = true;
						j.vidAbierto = true;


						// ABRIMOS INSTANCIA DEL MANEJADOR EN EL DESKTOP

						// CERRAMOS ESTA VENTACTIVIDADES						
						//j.jframeVid.dispose();
						j.vidAbierto = false;
						doDefaultCloseAction();
					}//if


				}//IF SELECCION
			}
		});
		panelInf.add(btnExplorar);

	}// end Constructor()

	public void rellenarListas(String path){

		File fer = new File(path);
		File[] listOfFiles = fer.listFiles();

		for(int i = 0; i < listOfFiles.length; i++)
		{
			File[] subFichero = listOfFiles[i].listFiles();


			if(subFichero != null)
			{
				for(int k = 0 ; k < subFichero.length; k++)
				{
					if(subFichero[k].getName().equals("videos"))
					{
						String[] subFichero2 = new String[1000];
						subFichero2 = subFichero[k].list();


						if(subFichero2 != null){	
							for(int j = 0; j < subFichero2.length; j++)
							{
								if(!subFichero2[j].equals(".DS_Store"))
								{
									dlm.addElement(subFichero[k]+"/"+subFichero2[j]);
								}//end if .DS_Store
							}//end for j
						}//end subFichero2 != null
					}//end directorio videos
				}//end for k
			}//end subFichero != null
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



}// end class VentVideos
