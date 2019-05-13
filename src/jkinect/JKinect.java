package jkinect;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;  
import javax.swing.JInternalFrame;  
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.jfree.ui.ApplicationFrame;

import graficas.VentGraficas;
import manejoXML.ManejadorXML;
import video.DirectRendering;
import video.VentVideos;

import javax.imageio.ImageIO;
import javax.swing.DesktopManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class JKinect extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// CONFIGURACION
	public String globalXML 	= "CONFIGURACIONESXML/";
	public String globalVideo 	= "RESULTADOS/";
	public String globalResul 	= "RESULTADOS/";
	public String globalExe 	= "EJECUTABLES/Kinect.exe";

	
	// REGISTRO
	private JLabel reg		= null;
	Boolean registrado = false;						// Variable que indica si hay algun usuario registrado en el sistema
	String usuarioReg = null;						// Variable que indica que usuario está registrado en el sistema
	public enum TipoUsuario{ ESPECIALISTA, PACIENTE, ADMIN} // ENUM de los tipos de usuarios
	TipoUsuario tipoU = TipoUsuario.ADMIN;			// Variable que indica del tipo de usuario registrado
	int idRegistrado = -1;							// Variable que indica el id de usuario registrado


	// ICONOS JOPTIONPANE
	Icon iconoOk = new ImageIcon("images/toolbarButtonGraphics/general/Properties24.gif");
	Icon iconoError = new ImageIcon("images/toolbarButtonGraphics/general/Zoom24.gif");
	Icon iconoForbid = new ImageIcon("images/toolbarButtonGraphics/general/Stop24.gif");
	Icon iconoInfo = new ImageIcon("images/toolbarButtonGraphics/general/Information24.gif");


	// BARRA DE MENU
	private JMenuBar barra		= null;
	private JMenu archivo		= null;
	private JMenu usuarios		= null;
	private JMenu terapias		= null;
	private JMenu tratamientos	= null;
	private JMenu actividades	= null;
	private JMenu sesiones		= null;
	private JMenu juego			= null;
	private JMenu resultados	= null;
	private JMenu ayuda			= null;
	private JMenuItem salir		= null;
	private JMenuItem abrir		= null;
	private JMenuItem guardar	= null;
	private JMenuItem acercaDe	= null;
	private JMenuItem config	= null;
	private JMenuItem espec		= null;
	private JMenuItem pacie		= null;
	private JMenuItem admin		= null;
	private JMenuItem listaT	= null;
	private JMenuItem crearT	= null;
	private JMenuItem lista		= null;
	private JMenuItem crear		= null;
	private JMenuItem listaA	= null;
	private JMenuItem crearA	= null;
	private JMenuItem listaS	= null;
	private JMenuItem crearS	= null;
	private JMenuItem lanzar	= null;
	private JMenuItem graficas	= null;
	private JMenuItem videos	= null;


	// TOOLBAR
	//public ToolBarDemo tdb;


	// DESKTOP
	public JDesktopPane desktopPane = null;		// Ventana de ESCRITORIO


	// INTERNAL FRAMES
	public JDialog jframe = null;							// Ventana de REGISTRO
	public JDialog jframeConfig = null;						// Ventana de CONFIGURACION
	public JInternalFrame jframeEsp = null;					// Ventana de ESPECIALISTAS
	public JInternalFrame jframePac = null;					// Ventana de PACIENTES
	public JInternalFrame jframeAct = null; 				// Ventana de la lista de ACTIVIDADES
	public VentActividades2 jframeAct2 = null; 				// Ventana para crear ACTIVIDADES 2
	public VentVideos jframeVid = null; 					// Ventana para listar VIDEOS de RESULTADOS
	public DirectRendering jframeVid2 = null; 				// Ventana para visualizar VIDEOS de RESULTADOS
	public JInternalFrame jframeRes = null; 				// Ventana de la lista de RESULTADOS
	public ArrayList<ApplicationFrame> jframeRes2 = null; 	// Ventana de las graficas de RESULTADOS
	public VentJuego jframeLanzar;							// Ventana para lanzar el juego desde JKinect
	
	// FLAGS para controlar las ventanas abiertas
	public boolean espAbierto 	= false;		// Flag que indica si ya esta instanciado la ventana de especialistas
	public boolean pacAbierto 	= false;		// Flag que indica si ya esta instanciado la ventana de pacientes
	public boolean actAbierto 	= false;		// Flag que indica si ya esta instanciado la ventana de listar actividades
	public boolean actAbierto2 	= false;		// Flag que indica si ya esta instanciado la ventana de crear actividades
	public boolean vidAbierto 	= false;		// Flag que indica si ya esta instanciado la ventana de listar videos
	public boolean vidAbierto2 	= false;		// Flag que indica si ya esta instanciado la ventana de visualizar video
	public boolean resAbierto 	= false;		// Flag que indica si ya esta instanciado la ventana de listar resultados
	public boolean resAbierto2 	= false;		// Flag que indica si ya esta instanciado la ventana de ver resultados
	
	// MANEJADOR XML
	public ManejadorXML xml = null;

	// IMAGEN DE FONDO
	public BufferedImage img = null;
	public Image dimg;

	// RUTAS RECURSOS IMAGENES
	public String imgLogos 		= "images/Logos/";
	public String imgLogosEPSG	= "images/Logos/Logo-EPS2.png";
	public String imgLogosEPS	= "images/Logos/Logo-EPS3.png";
	public String imgLogosUCOG	= "images/Logos/uco_0.png";
	public String imgLogosUCO	= "images/Logos/uco_3.png";
	public String imgFondo 		= "images/Logos/fondoPantalla.png";
	public String imgFondos 	= "images/Logos/FONDOS_PANTALLA/";
	public String imgPacientes 	= "images/Logos/FotosPac/";
	public String imgIconos		= "images/toolbarButtonGraphics/";
	public String imgPaleta		= "images/Logos/paletaC.jpg";


	/**
	 * Create the application.
	 */
	public JKinect(){
		initialize(this);

	}//Constructor

	public void setFinRegistro()
	{
		jframe.dispose();
	}//finRegistro()

	/**
	 * Initialize the contents of the frame.
	 * @param d 
	 */
	private void initialize(JKinect j) {


		desktopPane = new JDesktopPane();  
		Container contentPane = getContentPane();  
		getContentPane().setLayout(new BorderLayout(0, 0));
		desktopPane.setDoubleBuffered(true);
		contentPane.add(desktopPane);

		DesktopManager dm = null;
		desktopPane.setDesktopManager(dm);


		agregarMenu(j);
		@SuppressWarnings("unused")
		JInternalFrame registro = null;
		registro = new JInternalFrame();

		//agregarToolBar();


		/*****/

		jframe = new VentRegistro(j);
		jframe.setModal(true);
		jframe.setAlwaysOnTop(true);
		jframe.setVisible(true);


		//Add toolbar to the window.
		//desktopPane.add(tdb.toolBar);


		setTitle("JKinect");  
		setExtendedState(Frame.MAXIMIZED_BOTH);   
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);  

		InputStream foto1 = this.getClass().getResourceAsStream(j.imgFondo);
		Fondo f = new Fondo(img);
		f.cargarImagen(desktopPane, foto1);

		if(registrado == false)
		{
			reg = new JLabel("NO hay ningun usuario registrado en el sistema");
			//reg.setHorizontalAlignment(SwingConstants.CENTER);
			reg.setBounds(1100, 30, 300, 20);
			desktopPane.add(reg);
		}
		else
		{
			reg = new JLabel("USUARIO: " + usuarioReg);
			reg.setBounds(1250, 20, 300, 20);
			desktopPane.add(reg);
			ajustarMenu();
		}//if-else

	}//initialize()


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					@SuppressWarnings("unused")
					JKinect window = new JKinect();  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//main()


	// MENU SUPERIOR DE LA INTERFAZ
	public void agregarMenu(JKinect j){


		barra		= new JMenuBar();
		archivo		= new JMenu("Archivo");
		usuarios	= new JMenu("Usuarios");
		terapias	= new JMenu("Terapias");
		tratamientos= new JMenu("Tratamientos");
		actividades	= new JMenu("Actividades");
		sesiones	= new JMenu("Sesiones");
		juego		= new JMenu("Juego");
		resultados	= new JMenu("Resultados");
		ayuda		= new JMenu("Ayuda");
		salir		= new JMenuItem("Salir");
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(DISPOSE_ON_CLOSE); 
			}
		});
		abrir		= new JMenuItem("Abrir");
		guardar		= new JMenuItem("Guardar");
		acercaDe	= new JMenuItem("Acerca de");
		config		= new JMenuItem("Configuracion");
		config.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					jframeConfig = new VentConfiguracion(j);
					jframeConfig.setModal(true);
					jframeConfig.setAlwaysOnTop(true);
					jframeConfig.setVisible(true);
			}//actionPerformed()
		});
		espec		= new JMenuItem("Especialistas");
		espec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(espAbierto == false)
				{
					jframeEsp = new VentEspecialistas(j);
					desktopPane.add(jframeEsp);
					jframeEsp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					jframeEsp.setVisible(true);
					espAbierto = true;
				}//if
			}//actionPerformed()
		});
		pacie		= new JMenuItem("Pacientes");
		pacie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pacAbierto == false)
				{
					jframePac = new VentPacientes(j);
					desktopPane.add(jframePac);
					jframePac.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					jframePac.setVisible(true);		
					pacAbierto = true;
				}//if
			}//actionPerformed()
		});
		admin		= new JMenuItem("Administrador");
		listaT		= new JMenuItem("Lista");
		crearT		= new JMenuItem("Crear");
		lista		= new JMenuItem("Lista");
		crear		= new JMenuItem("Crear");
		listaA		= new JMenuItem("Lista");
		listaA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(actAbierto == false)
				{
					jframeAct = new VentActividades(j);
					desktopPane.add(jframeAct);
					jframeAct.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					jframeAct.setVisible(true);		
					actAbierto = true;
				}//if
			}//actionPerformed()
		});
		crearA		= new JMenuItem("Crear");
		crearA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(actAbierto2 == false)
				{
					jframeAct2 = new VentActividades2(j);
					desktopPane.add(jframeAct2);
					jframeAct2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					jframeAct2.setVisible(true);		
					actAbierto2 = true;
					actAbierto = true;
				}//if
			}//actionPerformed()
		});
		listaS		= new JMenuItem("Lista");
		crearS		= new JMenuItem("Crear");
		lanzar		= new JMenuItem("Lanzar");
		lanzar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					jframeLanzar = new VentJuego(j);
					jframeLanzar.setModal(true);
					jframeLanzar.setAlwaysOnTop(true);
					jframeLanzar.setVisible(true);
			}//actionPerformed()
		});
		graficas	= new JMenuItem("Ver Graficas");
		graficas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jframeRes = new VentGraficas(j);
				desktopPane.add(jframeRes);
				jframeRes.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				jframeRes.setVisible(true);		
				resAbierto = true;

			}//actionPerformed()
		});

		videos		= new JMenuItem("Ver Videos");
		videos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jframeVid = new VentVideos(j);
				desktopPane.add(jframeVid);
				jframeVid.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				jframeVid.setVisible(true);		
				vidAbierto = true;

			}//actionPerformed()
		});
		archivo.add(salir);
		archivo.add(abrir);
		archivo.add(guardar);
		archivo.add(config);

		usuarios.add(espec);
		usuarios.add(pacie);
		usuarios.add(admin);

		terapias.add(listaT);
		terapias.add(crearT);

		tratamientos.add(lista);
		tratamientos.add(crear);

		actividades.add(listaA);
		actividades.add(crearA);

		sesiones.add(listaS);
		sesiones.add(crearS);

		juego.add(lanzar);

		resultados.add(graficas);
		resultados.add(videos);

		ayuda.add(acercaDe);

		barra.add(archivo);
		barra.add(usuarios);
		barra.add(terapias);
		barra.add(tratamientos);
		barra.add(actividades);
		barra.add(sesiones);
		barra.add(juego);
		barra.add(resultados);
		barra.add(ayuda);

		setJMenuBar(barra);

	}//agregarMenu()

	// AJUSTAR EL MENU SUPERIOR DE LA INTERFAZ EN FUNCION DEL TIPO DE USUARIO
	public void ajustarMenu()
	{
		if(tipoU == TipoUsuario.ESPECIALISTA)
		{
			config.setEnabled(false);
			guardar.setEnabled(false);
			admin.setEnabled(false);
		}else if(tipoU == TipoUsuario.PACIENTE)
		{
			config.setEnabled(false);
			guardar.setEnabled(false);
			espec.setEnabled(false);
			pacie.setEnabled(false);
			admin.setEnabled(false);
			listaT.setEnabled(false);
			crearT.setEnabled(false);
			lista.setEnabled(false);
			crear.setEnabled(false);
			listaA.setEnabled(false);
			crearA.setEnabled(false);
			listaS.setEnabled(false);
			crearS.setEnabled(false);
			graficas.setEnabled(false);
			videos.setEnabled(false);
		}//if -else
	}//ajustarMenu()

	/*
// TOOLBAR SUPERIOR DE LA INTERFAZ
public void agregarToolBar(){

	tdb = new ToolBarDemo();

}//agregarToolBar()
	 */

	public Icon getIconoInfo() {
		return iconoInfo;
	}

	public void setIconoInfo(Icon iconoInfo) {
		this.iconoInfo = iconoInfo;
	}

	public class Fondo implements Border{

		private   BufferedImage image ;

		public Fondo(BufferedImage image ) {
			this.image=image;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			int x0 = x+ (width-image.getWidth())/2;
			int y0 = y+ (height-image.getHeight())/2;
			g.drawImage(image,x0,y0,null); }

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(0,0,0,0);}

		@Override
		public boolean isBorderOpaque() {
			return true; }

		public  void cargarImagen(JDesktopPane jDeskp, InputStream fileImagen){   
			try{   
				BufferedImage image = ImageIO.read(new File("images/Logos/fondoPantalla.png"));

				Image imagen = image.getScaledInstance((int)(image.getWidth() * 0.75), (int)(image.getHeight() * 0.75), Image.SCALE_SMOOTH);

				BufferedImage bimage = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);

				// Draw the image on to the buffered image
				Graphics2D bGr = bimage.createGraphics();
				bGr.drawImage(imagen, 0, 0, null);
				bGr.dispose();

				image = bimage;

				jDeskp.setBorder(new Fondo(image)); }
			catch (Exception e){   System.out.println("Imagen no disponible");   }        
		}//cargarImagen()


	}// class Fondo

}//class JKinect
