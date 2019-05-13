package video;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.LibVlcConst;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import uk.co.caprica.vlcj.filefilters.swing.SwingFileFilterFactory;

@SuppressWarnings("serial")
public class PlayerControlsPanel extends JPanel {

    private static final int SKIP_TIME_MS = 10 * 1000;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	@SuppressWarnings("unused")
	private final MediaPlayerFactory factory;

    private final EmbeddedMediaPlayer mediaPlayer;

    private JLabel timeLabel;
    private JSlider positionSlider;
    private JLabel chapterLabel;
    private JButton previousChapterButton;    
    private JButton rewindButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton playButton;
    private JButton fastForwardButton;
    private JButton nextChapterButton;
    private JButton toggleMuteButton;
    private JSlider volumeSlider;
    private JButton captureButton;
    private JButton ejectButton;
    private JButton connectButton;
    private JButton fullScreenButton;
    private JButton subTitlesButton;
    private JFileChooser fileChooser;

    private boolean mousePressedPlaying = false;

    public PlayerControlsPanel(MediaPlayerFactory factory, EmbeddedMediaPlayer mediaPlayer) {
        this.factory = factory;
        this.mediaPlayer = mediaPlayer;

        createUI();

        executorService.scheduleAtFixedRate(new UpdateRunnable(mediaPlayer), 0L, 1L, TimeUnit.SECONDS);
    }

    private void createUI() {
        createControls();
        layoutControls();
        registerListeners();
    }

    private void createControls() {
        timeLabel = new JLabel("hh:mm:ss");

        // positionProgressBar = new JProgressBar();
        // positionProgressBar.setMinimum(0);
        // positionProgressBar.setMaximum(1000);
        // positionProgressBar.setValue(0);
        // positionProgressBar.setToolTipText("Time");

        positionSlider = new JSlider();
        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);
        positionSlider.setToolTipText("Position");

        chapterLabel = new JLabel("00/00");

        previousChapterButton = new JButton("Anterior");
        //previousChapterButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_start_blue.png"));
        previousChapterButton.setToolTipText("Go to previous chapter");
        
        rewindButton = new JButton("Atras");
        //rewindButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_rewind_blue.png"));
        rewindButton.setToolTipText("Skip back");

        stopButton = new JButton("Stop");
        //stopButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_stop_blue.png"));
        stopButton.setToolTipText("Stop");

        pauseButton = new JButton("Play/Pause");
        //pauseButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_pause_blue.png"));
        pauseButton.setToolTipText("Play/pause");

        //Icon play = new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_play_blue.png");
        playButton = new JButton("Play");
        playButton.setToolTipText("Play");

        fastForwardButton = new JButton("Saltar");
        //fastForwardButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_fastforward_blue.png"));
        fastForwardButton.setToolTipText("Skip forward");

        nextChapterButton = new JButton("Siguiente");
        //nextChapterButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_end_blue.png"));
        nextChapterButton.setToolTipText("Go to next chapter");

        toggleMuteButton = new JButton("Mute");
        //toggleMuteButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/sound_mute.png"));
        toggleMuteButton.setToolTipText("Toggle Mute");

        volumeSlider = new JSlider();
        volumeSlider.setOrientation(JSlider.HORIZONTAL);
        volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
        volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);
        volumeSlider.setPreferredSize(new Dimension(100, 40));
        volumeSlider.setToolTipText("Cambiar Volumen");

        captureButton = new JButton("Pantallazo");
        //captureButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/camera.png"));
        captureButton.setToolTipText("Take picture");

        ejectButton = new JButton("Load/eject media");
        //ejectButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/control_eject_blue.png"));
        ejectButton.setToolTipText("Load/eject media");

        connectButton = new JButton("Connect to media");
        //connectButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/connect.png"));
        connectButton.setToolTipText("Connect to media");

//		  fileChooser = new JFileChooser();
//        fileChooser.setApproveButtonText("Play");
//        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newVideoFileFilter());
//        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newAudioFileFilter());
//        fileChooser.addChoosableFileFilter(SwingFileFilterFactory.newPlayListFileFilter());
//        FileFilter defaultFilter = SwingFileFilterFactory.newMediaFileFilter();
//        fileChooser.addChoosableFileFilter(defaultFilter);
//        fileChooser.setFileFilter(defaultFilter);

        fullScreenButton = new JButton("FullScreen");
        //fullScreenButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/image.png"));
        fullScreenButton.setToolTipText("Toggle full-screen");

        subTitlesButton = new JButton();
        //subTitlesButton.setIcon(new ImageIcon("/Users/ramonseoane/Desktop/IntefazTFG/images/icons/comment.png"));
        subTitlesButton.setToolTipText("Cycle sub-titles");
    }

    private void layoutControls() {
        setBorder(new EmptyBorder(4, 4, 4, 4));

        setLayout(new BorderLayout());

        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new GridLayout(1, 1));
        // positionPanel.add(positionProgressBar);
        positionPanel.add(positionSlider);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(8, 0));

        topPanel.add(timeLabel, BorderLayout.WEST);
        topPanel.add(positionPanel, BorderLayout.CENTER);
        //topPanel.add(chapterLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout());

        //bottomPanel.add(previousChapterButton);
        bottomPanel.add(rewindButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(pauseButton);
        //bottomPanel.add(playButton);
        bottomPanel.add(fastForwardButton);
        //bottomPanel.add(nextChapterButton);
        bottomPanel.add(volumeSlider);
        bottomPanel.add(toggleMuteButton);
        bottomPanel.add(captureButton);
        //bottomPanel.add(ejectButton);
        //bottomPanel.add(connectButton);
        //bottomPanel.add(fullScreenButton);
        //bottomPanel.add(subTitlesButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Broken out position setting, handles updating mediaPlayer
     */
    private void setSliderBasedPosition() {
        if(!mediaPlayer.status().isSeekable()) {
            return;
        }
        float positionValue = positionSlider.getValue() / 1000.0f;
        // Avoid end of file freeze-up
        if(positionValue > 0.99f) {
            positionValue = 0.99f;
        }
        mediaPlayer.controls().setPosition(positionValue);
    }

    private void updateUIState() {
        if(!mediaPlayer.status().isPlaying()) {
            // Resume play or play a few frames then pause to show current position in video
            mediaPlayer.controls().play();
            if(!mousePressedPlaying) {
                try {
                    // Half a second probably gets an iframe
                    Thread.sleep(500);
                }
                catch(InterruptedException e) {
                    // Don't care if unblocked early
                }
                mediaPlayer.controls().pause();
            }
        }
        long time = mediaPlayer.status().time();
        int position = (int)(mediaPlayer.status().position() * 1000.0f);
        int chapter = mediaPlayer.chapters().chapter();
        int chapterCount = mediaPlayer.chapters().count();
        updateTime(time);
        updatePosition(position);
        updateChapter(chapter, chapterCount);
    }

    private void skip(int skipTime) {
        // Only skip time if can handle time setting
        if(mediaPlayer.status().length() > 0) {
            mediaPlayer.controls().skipTime(skipTime);
            updateUIState();
        }
    }

    private void registerListeners() {
        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
//                updateVolume(mediaPlayer.getVolume());
            }
        });

        positionSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(mediaPlayer.status().isPlaying()) {
                    mousePressedPlaying = true;
                    mediaPlayer.controls().pause();
                }
                else {
                    mousePressedPlaying = false;
                }
                setSliderBasedPosition();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setSliderBasedPosition();
                updateUIState();
            }
        });

        previousChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.chapters().previous();
            }
        });
        
        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(-SKIP_TIME_MS);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.controls().stop();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.controls().pause();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.controls().play();
            }
        });

        fastForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skip(SKIP_TIME_MS);
            }
        });

        nextChapterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.chapters().next();
            }
        });

        toggleMuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.audio().mute();
            }
        });

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                // if(!source.getValueIsAdjusting()) {
                mediaPlayer.audio().setVolume(source.getValue());
                // }
            }
        });

        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.snapshots().setSnapshotDirectory("/Users/ramonseoane/Desktop/InterfazTFG/images/capturas_video/");
                mediaPlayer.snapshots().save();
            }
        });

        ejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.overlay().enable(false);
                if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(PlayerControlsPanel.this)) {
                    mediaPlayer.media().play(fileChooser.getSelectedFile().getAbsolutePath());
                }
                mediaPlayer.overlay().enable(true);
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.overlay().enable(false);
                String mediaUrl = JOptionPane.showInputDialog(PlayerControlsPanel.this, "Enter a media URL", "Connect to media", JOptionPane.QUESTION_MESSAGE);
                if(mediaUrl != null && mediaUrl.length() > 0) {
                    mediaPlayer.media().play(mediaUrl);
                }
                mediaPlayer.overlay().enable(true);
            }
        });

        fullScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.fullScreen().toggle();
            }
        });

        subTitlesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int spu = mediaPlayer.subpictures().track();
                if(spu > -1) {
                    spu ++ ;
                    if(spu > mediaPlayer.subpictures().trackCount()) {
                        spu = -1;
                    }
                }
                else {
                    spu = 0;
                }
                mediaPlayer.subpictures().setTrack(spu);
            }
        });
    }

    private final class UpdateRunnable implements Runnable {

        private final MediaPlayer mediaPlayer;

        private UpdateRunnable(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        @Override
        public void run() {
            final long time = mediaPlayer.status().time();
            final int position = (int)(mediaPlayer.status().position() * 1000.0f);
            final int chapter = mediaPlayer.chapters().chapter();
            final int chapterCount = mediaPlayer.chapters().count();

            // Updates to user interface components must be executed on the Event
            // Dispatch Thread
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer.status().isPlaying()) {
                        updateTime(time);
                        updatePosition(position);
                        updateChapter(chapter, chapterCount);
                    }
                }
            });
        }
    }

    private void updateTime(long millis) {
        String s = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        timeLabel.setText(s);
    }

    private void updatePosition(int value) {
        // positionProgressBar.setValue(value);
        positionSlider.setValue(value);
    }

    private void updateChapter(int chapter, int chapterCount) {
        String s = chapterCount != -1 ? (chapter + 1) + "/" + chapterCount : "-";
        chapterLabel.setText(s);
        chapterLabel.invalidate();
        validate();
    }

    @SuppressWarnings("unused")
	private void updateVolume(int value) {
        volumeSlider.setValue(value);
    }
}
