package jkinect;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;


public class VentConfiguracion extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel panelExt = new JPanel();
	private static VentRegistro dialog = null;
	
	
	File fXML = new File("CONFIGURACIONESXML/");
	File fVid = new File("Videos/");
	File fExe = new File("EJECUTABLES/Kinect.exe");
	private final String xmlReset = fXML.toString();
	private final String vidReset = fVid.toString();
	private final String exeReset = fExe.toString();
	
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
	 */
	@SuppressWarnings("deprecation")
	public VentConfiguracion(JKinect j) {
		super(j.jframeConfig, true);
		setTitle("Configuración de JKinect");
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
		panelSupDer.setLayout(new GridLayout(3, 3, 0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		panelSupDer.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		panelSupDer.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		panelSupDer.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		panelSupDer.add(lblNewLabel_3);
		
		JButton btnNewButton = new JButton("Reset");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(xmlReset);
				textField_1.setText(vidReset);
				textField_2.setText(exeReset);
				
				j.globalXML 	= xmlReset;
				j.globalVideo	= vidReset;
				j.globalExe		= exeReset;
			}
		});
		panelSupDer.add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("");
		panelSupDer.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		panelSupDer.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("");
		panelSupDer.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("");
		panelSupDer.add(lblNewLabel_7);

		JPanel panelInferior = new JPanel();
		panelInferior.setMaximumSize(new Dimension(32767, 30));
		panelExt.add(panelInferior);
		panelInferior.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panelInferior.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField(j.globalXML);
		textField.setToolTipText("Ruta hacía el directorio que contiene los ficheros XML de configuración de actividades");
		textField.setColumns(25);
		panel_2.add(textField);
		
		textField_2 = new JTextField(j.globalExe);
		textField_2.setToolTipText("Ejecutable que se empleará para lanzar el juego correspondiente");
		textField_2.setColumns(25);
		panel_2.add(textField_2);
		
		textField_1 = new JTextField(j.globalVideo);
		textField_1.setToolTipText("Ruta hacía el directorio que contiene los videos grabados durante las sesiones");
		textField_1.setColumns(25);
		panel_2.add(textField_1);
		textField_1.hide();
		
		JPanel panel_10 = new JPanel();
		panelInferior.add(panel_10, BorderLayout.WEST);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_10.add(panel_4);
		
		JLabel label = new JLabel("Directorio XML");
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setToolTipText("Ruta hacía el directorio que contiene los ficheros XML de configuración de actividades");
		panel_4.add(label);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_8.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		panel_10.add(panel_8);
		
		JLabel lblEjecutable = new JLabel("Ejecutable");
		lblEjecutable.setToolTipText("Ejecutable que se empleará para lanzar el juego correspondiente");
		panel_8.add(lblEjecutable);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_6.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_10.add(panel_6);
		
		JLabel label_1 = new JLabel("Directorio Vid.");
		label_1.setToolTipText("Ruta hacía el directorio que contiene los videos grabados durante las sesiones");
		panel_6.add(label_1);
		label_1.hide();
		
		JPanel panel_3 = new JPanel();
		panelInferior.add(panel_3, BorderLayout.SOUTH);
		
		JButton buttonAceptar = new JButton("Aceptar");
		buttonAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textField.equals("") && (new File(textField.getText())).isDirectory())
				{
					j.globalXML = textField.getText();
				}else
				{
					JOptionPane optionPane = new JOptionPane("Configuración erronea!! Ruta XML debe ser un directorio válido del sistema.");
					optionPane.setIcon(j.iconoError);
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Registro Correcto");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
				
				/*if(!textField_1.equals("") && (new File(textField_1.getText())).isDirectory())
				{
					j.globalVideo = textField_1.getText();
				}else
				{
					JOptionPane optionPane = new JOptionPane("Configuración erronea!! Ruta de los videos debe ser un directorio válido del sistema.");
					optionPane.setIcon(j.iconoError);
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Registro Correcto");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}*/
				
				if(!textField_2.equals("") && !(new File(textField_2.getText())).isDirectory()) 
				{
					j.globalExe = textField_2.getText();
					dispose();
				}else
				{
					JOptionPane optionPane = new JOptionPane("Configuración erronea!! El fichero ejecutable debe ser un ejecutable válido del sistema.");
					optionPane.setIcon(j.iconoError);
					JDialog dialog = optionPane.createDialog("");
					dialog.setTitle("Registro Correcto");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
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
	
	
}// end clas VentConfiguracion