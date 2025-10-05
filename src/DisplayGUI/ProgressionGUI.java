package DisplayGUI;

import Music.Progression;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * Controller for the GUI
 * Creates the MusicSheet and communicates between the progression and the GUI
 */
public class ProgressionGUI {

    /**
     * The displayed panel of music
     */
    public MusicSheet panel;

    /**
     * Window frame for the music
     */
    public JFrame frame;

    /**
     * Constructor for ProgressionGUI
     * Creates a MusicSheet GUI and adds the current chord progression
     * @param p current chord progression
     */
    public ProgressionGUI(Progression p) {
        panel = new MusicSheet(p);
        frame = new JFrame("Chord Progression");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1400, 600);
        frame.add(panel);
    }

    /**
     * Constructor for ProgressionGUI
     * Creates a MusicSheet GUI with a null progression
     */
    public ProgressionGUI() {
        panel = new MusicSheet();
        frame = new JFrame("Chord Progression");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1400, 600);
        frame.add(panel);
    }

    /**
     * Updates the gui with the current progression it has
     */
    public void updateGUI() {
        panel.repaint();
        frame.setVisible(true);
    }

    /**
     * Sets a new progression for the GUI and updates the GUI
     * @param p the new progression
     */
    public void setProgression(Progression p) {
        panel.setProgression(p);
    }

    public void endGUI() {
        frame.setVisible(false);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }




    public static void main(String[] args) {
        JFrame frame = new JFrame("Music Sheet Test");
        MusicSheet panel = new MusicSheet();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(1400, 600);
        frame.add(panel);
        frame.setVisible(true);
        panel.repaint();
    }
}