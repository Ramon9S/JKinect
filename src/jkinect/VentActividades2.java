package jkinect;

import java.lang.Class;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import manejoXML.ManejadorXML;
import pintarEnCanvas.MiCanvas;
import pintarEnCanvas.MiCanvasColor;
import pintarEnCanvas.MiCanvasPreview;
import pintarEnCanvas.Objetos;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.util.*;

public class VentActividades2 extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private ManejadorXML xml;
	
	public ManejadorXML getXml() {
		return xml;
	}

	public void setXml(ManejadorXML xml) {
		this.xml = xml;
	}


	private JTextField textFieldNombre;
	private JTextField textFieldDescrip;
	public JTable tableAux;
	public JScrollPane scrollPane;

	private static Image img = null;
	private  Image imgPreview = null;
	private  MiCanvas canvas = null;
	@SuppressWarnings("unused")
	private  MiCanvasColor canvasColor = null;
	private  MiCanvasPreview canvasPreview = null;

	private ImageIcon paletaColor = null;

	public ArrayList<Objetos> obj = new ArrayList<Objetos>(); // ArrayList de objetos añadidos como tuplas

	public JDialog colorC = null; //JDialog oara el ColorChooser()

	private Color c;
	private JTextField txtseg;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField textFieldRutaDir;

	@SuppressWarnings("rawtypes")
	public JComboBox comboBox;
	File directorio = null;
	File[] archivos = null;
	File fichero = null;

	int orden = 0;			// Variable para controlar el orden de las tuplas insertadas
	int lastSelected = -1;	// Variable que guarda la ultima row seleccionado
	private JTextField textFieldDuracion;

	/** 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentActividades2 frame = new VentActividades2(null);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentActividades2(JKinect j) {
		super("Ventana de creacion de Actividades", true, true, true, true);
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				img = null;
				j.actAbierto2 = false;
				if(!obj.isEmpty()){obj.clear();}
			}
		});
		setSize(new Dimension(1000, 650));
		setMinimumSize(new Dimension(250, 95));
		setPreferredSize(new Dimension(1000, 650));

		setBounds(150, 50, 1000, 650);
		getContentPane().setLayout(new BorderLayout(0, 0));


		JPanel panelSup = new JPanel();
		getContentPane().add(panelSup, BorderLayout.NORTH);
		panelSup.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(j.imgLogosUCO));
		panelSup.add(lblNewLabel_1);

		JLabel lbletitulo = new JLabel("Configuración de nueva actividad");
		lbletitulo.setHorizontalTextPosition(SwingConstants.CENTER);
		lbletitulo.setHorizontalAlignment(SwingConstants.CENTER);
		panelSup.add(lbletitulo);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(j.imgLogosEPS));
		panelSup.add(lblNewLabel);

		JPanel panelCen = new JPanel();
		getContentPane().add(panelCen, BorderLayout.CENTER);
		panelCen.setLayout(new BorderLayout(0, 0));

		JPanel panelCSup = new JPanel();
		panelCSup.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCen.add(panelCSup, BorderLayout.NORTH);
		panelCSup.setLayout(new GridLayout(0, 6, 0, 0));

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalTextPosition(SwingConstants.LEFT);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setAlignmentX(0.5f);
		lblNombre.setSize(new Dimension(20, 16));
		lblNombre.setPreferredSize(new Dimension(20, 16));
		panelCSup.add(lblNombre);

		textFieldNombre = new JTextField();
		panelCSup.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		JLabel label = new JLabel("");
		panelCSup.add(label);

		JLabel lblDuracion = new JLabel("Duración Partida");
		lblDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		panelCSup.add(lblDuracion);

		textFieldDuracion = new JTextField();
		textFieldDuracion.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDuracion.setText("30");
		panelCSup.add(textFieldDuracion);
		textFieldDuracion.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		panelCSup.add(lblNewLabel_3);

		JLabel lblDescripcion = new JLabel("Descripción");
		lblDescripcion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripcion.setHorizontalTextPosition(SwingConstants.LEFT);
		lblDescripcion.setAlignmentX(0.5f);
		lblDescripcion.setSize(new Dimension(20, 16));
		lblDescripcion.setPreferredSize(new Dimension(20, 16));
		panelCSup.add(lblDescripcion);

		textFieldDescrip = new JTextField();
		panelCSup.add(textFieldDescrip);
		textFieldDescrip.setColumns(10);

		JPanel panelCC = new JPanel();
		panelCC.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCen.add(panelCC, BorderLayout.CENTER);
		panelCC.setLayout(new BorderLayout(0, 0));

		JPanel panelCCS = new JPanel();
		panelCC.add(panelCCS, BorderLayout.NORTH);

		JLabel lblNewLabel_11 = new JLabel("IMAGEN DE FONDO");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panelCCS.add(lblNewLabel_11);

		JButton btnCargarImagenEn = new JButton("elegir archivo...");
		panelCCS.add(btnCargarImagenEn);

		JPanel panelCCI = new JPanel();
		panelCC.add(panelCCI, BorderLayout.SOUTH);

		JPanel panelCCIZ = new JPanel();
		panelCC.add(panelCCIZ, BorderLayout.WEST);

		JPanel panelCCD = new JPanel();
		panelCC.add(panelCCD, BorderLayout.EAST);

		JPanel panelCCC = new JPanel();
		panelCCC.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCC.add(panelCCC, BorderLayout.CENTER);
		panelCCC.setLayout(new BorderLayout(0, 0));

		canvas =  new MiCanvas(obj, img);
		canvas.setBackground(Color.WHITE);
		panelCCC.add(canvas);

		btnCargarImagenEn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// FILTRO IMAGENES
				JFileChooser fileChooser = new JFileChooser(j.imgFondos);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "png", "PNG", "jpg", "JPG", "gif", "GIF");
				fileChooser.setFileFilter(filter);

				int seleccion = fileChooser.showOpenDialog(btnCargarImagenEn);

				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					fichero = fileChooser.getSelectedFile();

					BufferedImage imgb = null;
					try {
						imgb = ImageIO.read(fichero);
					} catch (IOException e1) {
					}

					canvas.setImagen(imgb);

					canvas.repaint();
				}//IF SELECCION
			}//end actionPerformed()
		});//end btnCargarImagenEn addActionListener()

		JPanel panelCIzq = new JPanel();
		panelCIzq.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCen.add(panelCIzq, BorderLayout.WEST);
		panelCIzq.setLayout(new BorderLayout(0, 0));


		JPanel panelCIzqSup = new JPanel();
		panelCIzq.add(panelCIzqSup, BorderLayout.NORTH);

		JButton buttonDir = new JButton("Cargar directorio");
		panelCIzqSup.add(buttonDir);


		buttonDir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// FILTRO DIRECTORIOS
				JFileChooser fileChooser = new JFileChooser(j.imgLogos);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int seleccion = fileChooser.showOpenDialog(buttonDir);

				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					directorio = fileChooser.getSelectedFile();
					archivos = directorio.listFiles();

					actualizarComboBox(comboBox, archivos);

					textFieldRutaDir.setText(directorio.toString());
				}//end if seleccion
			}//end actionPerformed()
		});//end addActionListener()


		textFieldRutaDir = new JTextField();
		textFieldRutaDir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// FILTRO DIRECTORIOS
				JFileChooser fileChooser = new JFileChooser(j.imgLogos);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int seleccion = fileChooser.showOpenDialog(buttonDir);

				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
					directorio = fileChooser.getSelectedFile();
					archivos = directorio.listFiles();

					actualizarComboBox(comboBox, archivos);

					textFieldRutaDir.setText(directorio.toString());
				}//end if seleccion

			}
		});

		textFieldRutaDir.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldRutaDir.setText("...");
		panelCIzqSup.add(textFieldRutaDir);
		textFieldRutaDir.setColumns(10);

		JPanel panelCIzqC = new JPanel();
		panelCIzq.add(panelCIzqC, BorderLayout.CENTER);
		panelCIzqC.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panelCIzqCSup = new JPanel();
		panelCIzqC.add(panelCIzqCSup);
		panelCIzqCSup.setLayout(new BorderLayout(0, 0));

		JPanel panelComb = new JPanel();
		panelCIzqCSup.add(panelComb, BorderLayout.NORTH);
		panelComb.setLayout(new BorderLayout(0, 0));

		comboBox = new JComboBox(new Object[]{});
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				File fichero2 = new File(directorio.toString()+"/"+(String)comboBox.getSelectedItem());

				BufferedImage imgb = null;
				try {
					imgb = ImageIO.read(fichero2);
				} catch (IOException e1) {
					System.out.println(fichero2);
				}

				canvasPreview.setImagen(imgb);

				canvasPreview.repaint();

			}//end actionPerformed()
		});//end comboBox addActionListener()
		panelComb.add(comboBox, BorderLayout.CENTER);

		JLabel lblElegirObjetoY = new JLabel("Seleccionar Objeto y Color");
		lblElegirObjetoY.setHorizontalAlignment(SwingConstants.CENTER);
		panelComb.add(lblElegirObjetoY, BorderLayout.NORTH);

		JButton paleta = new JButton("");
		paletaColor= new ImageIcon(j.imgPaleta);
		paleta.setIcon(paletaColor);
		panelComb.add(paleta, BorderLayout.EAST);


		JPanel panelCanv = new JPanel();
		panelCIzqCSup.add(panelCanv, BorderLayout.CENTER);
		panelCanv.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelCanvPrev = new JPanel();
		panelCanvPrev.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panelCanv.add(panelCanvPrev);
		panelCanvPrev.setLayout(new BorderLayout(0, 0));

		canvasPreview = new MiCanvasPreview(imgPreview);
		canvasPreview.setBackground(Color.WHITE);
		panelCanvPrev.add(canvasPreview);

		JPanel panelCanvColor = new JPanel();
		panelCanv.add(panelCanvColor);
		panelCanvColor.setLayout(new BorderLayout(0, 0));

		MiCanvasColor canvasColor = new MiCanvasColor(getC());
		panelCanvColor.add(canvasColor);

		paleta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color initialColor = Color.red;
				Color newColor = JColorChooser.showDialog(null, "Paleta de colores", initialColor);
				canvasColor.setC(newColor);
				canvasColor.repaint();
			}
		});
		JPanel panelCIzqCInf = new JPanel();
		panelCIzqCInf.setBorder(new TitledBorder(null, "Par\u00E1metros", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelCIzqC.add(panelCIzqCInf);
		panelCIzqCInf.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panelParamSup = new JPanel();
		panelParamSup.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		FlowLayout flowLayout = (FlowLayout) panelParamSup.getLayout();
		flowLayout.setVgap(15);
		panelCIzqCInf.add(panelParamSup);

		JLabel label_2 = new JLabel("Tiempo (seg)");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panelParamSup.add(label_2);

		txtseg = new JTextField();
		txtseg.setText("1");
		txtseg.setHorizontalAlignment(SwingConstants.CENTER);
		txtseg.setColumns(10);
		panelParamSup.add(txtseg);

		JPanel panelParamInf = new JPanel();
		panelParamInf.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		FlowLayout flowLayout_1 = (FlowLayout) panelParamInf.getLayout();
		flowLayout_1.setVgap(2);
		panelCIzqCInf.add(panelParamInf);

		JPanel panelX = new JPanel();
		panelParamInf.add(panelX);
		panelX.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblX = new JLabel("X");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		panelX.add(lblX);

		txtX = new JTextField();
		txtX.setText("...");
		txtX.setHorizontalAlignment(SwingConstants.CENTER);
		txtX.setColumns(10);
		panelX.add(txtX);

		JPanel panelY = new JPanel();
		panelParamInf.add(panelY);
		panelY.setLayout(new GridLayout(2, 0, 0, 0));

		JLabel lblY = new JLabel("Y");
		lblY.setHorizontalAlignment(SwingConstants.CENTER);
		panelY.add(lblY);

		txtY = new JTextField();
		txtY.setText("...");
		txtY.setHorizontalAlignment(SwingConstants.CENTER);
		txtY.setColumns(10);
		panelY.add(txtY);

		JPanel panelCIzqInf = new JPanel();
		panelCIzq.add(panelCIzqInf, BorderLayout.SOUTH);

		JButton button_1 = new JButton("AÑADIR");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = comboBox.getSelectedItem().toString();
				int x = Integer.parseInt(txtX.getText());
				int y = Integer.parseInt(txtY.getText());
				Point p = new Point(x, y);
				int t = Integer.parseInt(txtseg.getText());
				Objetos o = new Objetos(nombre, p, t, (orden+1), new File(directorio, comboBox.getSelectedItem().toString()), canvasColor.getC());
				
				
				if(obj == null){obj = new ArrayList<Objetos>();}
					

				++orden;

				canvas.repaint();
				
				// TABLEUPDATE;
				obj.add(o);
				añadirTupla(o, (DefaultTableModel)tableAux.getModel());
				//Collections.sort(obj, Objetos.ObjetosOrdenComparator);
			}
		});
		panelCIzqInf.add(button_1);

		/** Canvas MouseListener **/
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				//Usamos el paint() para "borrar" NO DUPLICAR los circulos clicados
				canvas.paint(canvas.getGraphics());

				if(canvas.getImagen() != null)
				{
					// get X and y position 
					int x, y;
					x = e.getX(); 
					y = e.getY(); 

					// draw a Oval at the point  
					// where mouse is moved
					Graphics g = canvas.getGraphics();
					g.setColor(canvasColor.getC());
					g.drawOval(x, y, 20, 20);
					//canvas.paint(canvas.getGraphics());

					// Update JTextField's
					txtX.setText(Integer.toString(x));
					txtY.setText(Integer.toString(y));
				}//if

			}//end mouseClicked()
		});//end canvas addMouseListener()

		/** END Canvas MouseListener **/


		JPanel panelCDer = new JPanel();
		panelCDer.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelCen.add(panelCDer, BorderLayout.EAST);
		panelCDer.setLayout(new BorderLayout(5, 23));

		JLabel lblNewLabel_10 = new JLabel("LISTA DE OBJETOS");
		lblNewLabel_10.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panelCDer.add(lblNewLabel_10, BorderLayout.NORTH);

		tableAux = crearTablaAux();

		scrollPane = new JScrollPane(tableAux);
		scrollPane.setPreferredSize(new Dimension(250, 100));
		panelCDer.add(scrollPane, BorderLayout.CENTER);
		/** FIN TABLEAUX **/

		JPanel panelCInf = new JPanel();
		panelCen.add(panelCInf, BorderLayout.SOUTH);

		JPanel panelInf = new JPanel();
		panelInf.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		getContentPane().add(panelInf, BorderLayout.SOUTH);
		panelInf.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int exito = -1;
				String resultados = j.globalXML;
				xml = new ManejadorXML(textFieldNombre.getText(), textFieldDescrip.getText(), Integer.parseInt(textFieldDuracion.getText()), fichero, new File(resultados), j.idRegistrado, obj, j);
				exito = xml.generarXML(j);

				// Si se ha creado el fichero XML sin problema cerramos la ventana
				if(exito == 0)
				{
					j.jframeAct2.dispose();
					j.actAbierto2 = false;
					j.actAbierto = false;
					obj.clear();
				}//if exito
			}
		});
		panelInf.add(btnCrear);

		JButton btnSalir = new JButton("Cancelar");
		btnSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//j.jframeAct2.dispose();
				j.actAbierto2 = false;
				j.actAbierto = false;
				obj.clear();
				doDefaultCloseAction();
			}
		});
		panelInf.add(btnSalir);
	}// VentActividades2()


	protected void actualizarComboBox(JComboBox<String> comboBox, File[] archivos) {

		String s = null;

		if(archivos != null && archivos.length > 1)
		{
			comboBox.removeAllItems(); //limpiamos el combo para actualizarlo

			for(File f : archivos){
				s = f.getName();
				if(s.toLowerCase().endsWith("jpg") || s.toLowerCase().endsWith("png") || s.toLowerCase().endsWith("gif")){
					comboBox.addItem(s);
				}//end if
			}//end for


			System.out.println(directorio.toString());
			File fichero3 = new File(directorio.toString(), (String)comboBox.getSelectedItem());

			BufferedImage imgb = null;
			try {
				imgb = ImageIO.read(fichero3);
			} catch (IOException e1) {
				System.out.println(fichero3);
			}

			canvasPreview.setImagen(imgb);

			canvasPreview.repaint();
		}//end if
	}//actualizarComboBox()


	public JTable crearTablaAux()
	{

		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> data = new Vector<Object>();

		columnNames.addElement("X");
		columnNames.addElement("Y");
		columnNames.addElement("TIEMPO");
		columnNames.addElement("ORDEN");
		columnNames.addElement("QUITAR");


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
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);

		model.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {

				//model.removeTableModelListener(this);
				updateArraList(e, model, table);
				//model.addTableModelListener(this);

			}

		});

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(lastSelected == -1){
					// PARA EL PRIMER SELECCIONADO NO HAY QUE CAMBIAR EL ELEMENTO LASTSELECTED
					if(table.getSelectedRow() != -1){obj.get(table.getSelectedRow()).setSelected(true);}
					if(table.getSelectedRow() != -1){lastSelected = table.getSelectedRow();}

					//REPINTAMOS
					canvas.repaint();
				}else{
					// PARA EL RESTO DE SELECCIONADOS SI HAY QUE CAMBIAR EL ELEMENTO LASTSELECTED
					obj.get(lastSelected).setSelected(false);
					if(table.getSelectedRow() != -1){obj.get(table.getSelectedRow()).setSelected(true);}

					if(table.getSelectedRow() != -1){lastSelected = table.getSelectedRow();}

					//REPINTAMOS					
					canvas.repaint();
				}//end if-else
			}
		});

		table.getColumn("QUITAR").setCellRenderer(new ButtonRenderer((DefaultTableModel)table.getModel()));
		table.getColumn("QUITAR").setCellEditor(new ButtonEditor(new JCheckBox(), (DefaultTableModel)table.getModel()));


		return table;

	}//crearTablaAux()

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		VentActividades2.img = img;
	}

	public Image getImgPre() {
		return imgPreview;
	}

	public void setImgPre(Image imgPreview) {
		this.imgPreview = imgPreview;
	}

	public MiCanvas getMiCanvas() {
		return canvas;
	}

	public void setMiCanvas(MiCanvas c) {
		this.canvas = c;
	}

	public  Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public void añadirTupla(Objetos o, DefaultTableModel model){

		Vector<String> aux = new Vector<String>();

		int[] c = {o.getX(), o.getY(), o.getTiempo(), o.getOrden()};

		for(int i = 0; i < c.length; i++)
		{
			aux.add(Integer.toString(c[i]));
		}//end for

		aux.add("Eliminar");
		
		model.insertRow(model.getRowCount(), aux);
	}//añadirTupla()

	public void quitarTuplas(ArrayList<Objetos> obj, DefaultTableModel model, int row){

		Objetos o = obj.get(row);
		// Eliminamos la fila del modelo
		model.removeRow(row);

		// Actualizamos la vista del modelo
		//scrollPane.setViewportView(table);

		System.out.println("Tamanno de obj es = " + obj.size());

		// Eliminamos objeto del ArrayList< >
		obj.remove(o);

		System.out.println("Tamanno de obj es = " + obj.size());

		// Repintamos el canvas
		canvas.repaint();

	}//quitarTuplas()

	public void updateArraList(TableModelEvent e, DefaultTableModel model, JTable table)
	{
		if (e.getType() == TableModelEvent.UPDATE) {

			model = (DefaultTableModel) ((e.getSource()));
			int fila = e.getFirstRow(); //table.getSelectedRow(); 
			System.out.println("LA FILA SELECCIONADA EN ESTE INSTANTE ES --> " + fila);
			System.out.println();

			int aux = obj.get(fila).getOrden();	// Guardamos el ORDEN que había antes DESDE OBJ POR SI ESTAMOS CAMBIANDO LA CELDA
			System.out.println("EL DATO QUE HABIA EN LA CELDA ES --> " + aux);
			System.out.println();

			int columna = e.getColumn(); //table.getSelectedColumn()

			// CAPTURAMOS EL DATO EDITADO MANUALMENTE EN LA TABLA
			String dato = String.valueOf(model.getValueAt(fila, columna));
			System.out.println("EL DATO QUE INTRODUCIMOS EN LA CELDA ES --> " + dato);
			System.out.println();


			// CAMBIAMOS EL CAMPO OBJETO DEPENDIENTO DE LA COLUMNA DE LA TABLA
			if( (columna == 0 ) && (Integer.parseInt(dato) < canvas.getWidth()) ){
				// X DEL OBJETO
				obj.get(fila).setP(new Point(Integer.parseInt(dato), (int)obj.get(fila).getP().getY()));

			}else if( (columna == 1) && (Integer.parseInt(dato) < canvas.getHeight())){
				// Y DEL OBJETO
				obj.get(fila).setP(new Point((int)obj.get(fila).getP().getX(), Integer.parseInt(dato)));

			}else if( (columna == 2 ) && (Integer.parseInt(dato) > 0)){
				// TIEMPO DEL OBJETO
				obj.get(fila).setTiempo(Integer.parseInt(dato));;

			}else if(columna == 3){
				// ORDEN DEL OBJETO

				System.out.println("MODIFICAMOS EN OBJ< > LA FILA --> " + fila + " CON EL NUEVO ORDEN --> " + dato);
				System.out.println();
				obj.get(fila).setOrden(Integer.parseInt(dato));	// Cambiamos el orden que había antes de cambiar la celda EN OBJ


				int pos = -1;	// Guardamos la posicion del elemento donde esta el orden repetido

				for(int i = 0; i < model.getRowCount(); i++)
				{
					// Si encontramos que se repite activamos la flag Y GUARDAMOS LA POS
					if( (((int) model.getValueAt(i, columna)) == Integer.parseInt(dato)) && ( i != fila )){

						System.out.println("ESTAMOS CAMBIANDO EL ORDEN DE UNA TUPLA.....");
						System.out.println();
						pos = i;

						System.out.println("ESTA REPETIDO EL ORDEN EN LA POSICION --> " + pos + " DE LA TABLA");

						// CAMBIAMOS EL VALOR EN LA TABLA Y EN EL ARRAYLIST< >
						model.setValueAt(aux, pos, columna);

						obj.get(pos).setOrden(aux);
						System.out.println("ACABAMOS DE COPIAR EN LA POSICION --> " + pos + " DE OBJ<> EL ANTIGUO ORDEN --> " + aux);
						System.out.println();

						break;
					}//end if
				}//end for obj

				// REORDENAMOS ARRAYLIST< > !!!!
				/*				Collections.sort(obj, Objetos.ObjetosOrdenComparator);
				System.out.println("REORDENAMOS OBJ< > EN FUNCION DEL ORDEN ASCENDENTE");
				System.out.println();
				 */
			}else if(table.getSelectedColumn() == 4){
				// QUITAR DEL OBJETO
				/** CASO ESPECIAL **/
			}//end if-else-if			

			// REPINTAMOS POR SI SE HA VARIADO LA X O LA Y DE UN OBJETO QUE VEAMOS EL CAMBIO
			canvas.repaint();
		}//end if
	}//updateArraList()


	/**
	 * @version 1.0 11/09/98
	 */

	class ButtonRenderer extends JButton implements TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		DefaultTableModel dtm;

		public ButtonRenderer(DefaultTableModel defaultTableModel) {
			setOpaque(true);
			setText("Eliminar");
			dtm = defaultTableModel;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			if (isSelected) {
				setBackground(table.getSelectionBackground());
			} else {
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}


	class ButtonEditor extends DefaultCellEditor {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected JButton button;

		private String label;

		private boolean isPushed;

		DefaultTableModel dtm;

		public ButtonEditor(JCheckBox checkBox, DefaultTableModel defaultTableModel) {
			super(checkBox);
			button = new JButton("eliminar");
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
			dtm = defaultTableModel;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value,
				boolean isSelected, int row, int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);


			/** PROBANDO ANDO**/

			// Eliminamos tupla de la tabla
			quitarTuplas(obj, (DefaultTableModel)table.getModel(), row);

			canvas.repaint();

			/** PROBANDO ANDO**/


			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {
				JOptionPane.showMessageDialog(button, label + ": Ouch!");
			}
			isPushed = false;
			return new String(label);
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}//class ButtonEditor


	public void cargarManejadorEnVentana(){

		if(xml != null)
		{
			// Rellenar campos de la parte superior de la ventana

			textFieldNombre.setText(xml.getNombre());
			textFieldDescrip.setText(xml.getDescripcion());
			textFieldDuracion.setText(Integer.toString(xml.getDuracion()));


			// Fondo de pantalla
			fichero = xml.getFondo();

			BufferedImage imgb = null;
			try {
				imgb = ImageIO.read(fichero);
			} catch (IOException e1) {
			}

			// Rellenar ArrayList<Objetos> con los objetos correspondientes
			obj = xml.getObj();
			
			
			// REFRESCAMOS EL FONDO DE PANTALLA DE LA VENTANA DE ACTIVIDADES 2
			canvas.setImagen(imgb);
			canvas.setObj(obj);
			canvas.repaint();

			
			// Rellenar tabla auxiliar con las tuplas correspondientes
			//ArrayList<Objetos> objAux = new ArrayList<Objetos>();

			
			for(Objetos o : obj)
			{
				añadirTupla(o, (DefaultTableModel)tableAux.getModel());
				
				canvas.repaint();
			}//end for

			
			/* VAMOS A PROBAR POR SI ES PROBLEMA DEL SCROLLPANE Y NO DEL MODELO DE LA TABLA --> NO ES APARENTEMENTE
			scrollPane = new JScrollPane(tableAux);
			scrollPane.setViewportView(tableAux);
			tableAux.setModel(new DefaultTableModel());
			scrollPane.revalidate();
			*/
		}else{
			
			System.out.println("El XML es igual a null en la funcion cargarManejadorEnVentana() !!!");
		}//end if xml != null
		
		canvas.repaint();
	}//end cargarManejadorEnVentana()

}// class

