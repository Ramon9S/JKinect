package video;

import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback;
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat;
import uk.co.caprica.vlcj.support.Info;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;


import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.sun.jna.NativeLibrary;

import jkinect.JKinect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * This simple test player shows how to get direct access to the video frame data.
 * <p>
 * This implementation uses the new (1.1.1) libvlc video call-backs function.
 * <p>
 * Since the video frame data is made available, the Java call-back may modify the contents of the
 * frame if required.
 * <p>
 * The frame data may also be rendered into components such as an OpenGL texture.
 */
public class DirectRendering extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static DirectRendering app;

	// The size does NOT need to match the mediaPlayer size - it's the size that the media will be scaled
	// to - matching the native size will be faster of course
	private final int width = 854;
	private final int height = 480;

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	/**
	 * Image to render the video frame data.
	 */
	private final BufferedImage image;

	private final MediaPlayerFactory factory;

	private final EmbeddedMediaPlayer mediaPlayer;

	private ImagePane imagePane;

	public String media;

	public File fichero;

	private static final String NATIVE_LIBRARY_SEARCH_PATH = null;
	//private static final String NATIVE_LIBRARY_SEARCH_PATH = "/Applications/VLC.app/Contents/MacOS/lib";

	/**
	 * Set to true to dump out native JNA memory structures.
	 */
	private static final String DUMP_NATIVE_MEMORY = "true";

	public DirectRendering(JKinect j) throws InterruptedException, InvocationTargetException {

		super();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height);
		image.setAccelerationPriority(1.0f);
		

		JInternalFrame frame = new JInternalFrame("Reproductor de Video de JKinect", true, true, true, true);
		j.desktopPane.add(frame);
		
		imagePane = new ImagePane(image);
		imagePane.setSize(width, height);
		imagePane.setMinimumSize(new Dimension(width, height));
		imagePane.setPreferredSize(new Dimension(width, height));
		
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(imagePane, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addInternalFrameListener(new InternalFrameAdapter(){
            public void internalFrameClosing(InternalFrameEvent e) {
                // do something
//            	mediaPlayer.release();
//				factory.release();
				j.vidAbierto2 = false;
				try {
					finalize();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

		media = j.jframeVid.getFichero().getAbsolutePath();

		factory = new MediaPlayerFactory();
		mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
		mediaPlayer.videoSurface().set(factory.videoSurfaces().newVideoSurface(new TestBufferFormatCallback(), new TestRenderCallback(), false));
		
		/**   PROBANDO BARRA DE CONTROLES   **/
		JPanel p = new PlayerControlsPanel(factory, mediaPlayer);
		frame.getContentPane().add(p, BorderLayout.SOUTH);
		frame.pack();
		
		
		mediaPlayer.media().play(media);
	}

	public static void main(String[] args) throws Exception {
		initializationInfo();

		app = new DirectRendering(null);

		//Thread.currentThread().join();
	}

	@SuppressWarnings("serial")
	private final class ImagePane extends JPanel {

		private final BufferedImage image;

		private final Font font = new Font("Sansserif", Font.BOLD, 36);

		public ImagePane(BufferedImage image) {
			this.image = image;
		}

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.drawImage(image, null, 0, 0);
			// You could draw on top of the image here... --> MARCA DE AGUA
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setColor(Color.MAGENTA);
			g2.setComposite(AlphaComposite.SrcOver.derive(0.3f));
			g2.fillRoundRect(10, 10, 100, 80, 32, 32);
			g2.setComposite(AlphaComposite.SrcOver);
			g2.setColor(Color.white);
			g2.setFont(font);
			g2.drawString("JKinect", 25, 60);
		}
	}

	private final int[] rgbBuffer = new int[width * height];

	private final class TestRenderCallback implements RenderCallback {

		// This is not optimal, see the CallbackMediaPlayerComponent for a way to render directly into the raster of a
		// Buffered image

		@Override
		public void display(MediaPlayer mediaPlayer, ByteBuffer[] nativeBuffers, BufferFormat bufferFormat) {
			ByteBuffer bb = nativeBuffers[0];
			IntBuffer ib = bb.asIntBuffer();
			ib.get(rgbBuffer);

			// The image data could be manipulated here...

			/* RGB to GRAYScale conversion example */
			/*for (int i=0; i < rgbBuffer.length; i++){
                int argb = rgbBuffer[i];
                int b = (argb & 0xFF);
                int g = ((argb >> 8 ) & 0xFF);
                int r = ((argb >> 16 ) & 0xFF);
                int grey = (r + g + b + g) >> 2 ; // performance optimized - not real grey!
                rgbBuffer[i] = (grey << 16) + (grey << 8) + grey;
            }*/

			image.setRGB(0, 0, width, height, rgbBuffer, 0, width);
			imagePane.repaint();
		}
	}

	public static void initializationInfo(){
		Info info = Info.getInstance();

		System.out.printf("vlcj             : %s%n", info.vlcjVersion() != null ? info.vlcjVersion() : "<version not available>");
		System.out.printf("os               : %s%n", val(info.os()));
		System.out.printf("java             : %s%n", val(info.javaVersion()));
		System.out.printf("java.home        : %s%n", val(info.javaHome()));
		System.out.printf("jna.library.path : %s%n", val(info.jnaLibraryPath()));
		System.out.printf("java.library.path: %s%n", val(info.javaLibraryPath()));
		System.out.printf("PATH             : %s%n", val(info.path()));
		System.out.printf("VLC_PLUGIN_PATH  : %s%n", val(info.pluginPath()));

		if (RuntimeUtil.isNix()) {
			System.out.printf("LD_LIBRARY_PATH  : %s%n", val(info.ldLibraryPath()));
		} else if (RuntimeUtil.isMac()) {
			System.out.printf("DYLD_LIBRARY_PATH          : %s%n", val(info.dyldLibraryPath()));
			System.out.printf("DYLD_FALLBACK_LIBRARY_PATH : %s%n", val(info.dyldFallbackLibraryPath()));
		}

		if (null != NATIVE_LIBRARY_SEARCH_PATH) {
			NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), NATIVE_LIBRARY_SEARCH_PATH);
		}

		System.setProperty("jna.dump_memory", DUMP_NATIVE_MEMORY);
	}

	private static String val(String val) {
		return val != null ? val : "<not set>";
	}

	private final class TestBufferFormatCallback implements BufferFormatCallback {

		@Override
		public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
			return new RV32BufferFormat(width, height);
		}

	}
}
