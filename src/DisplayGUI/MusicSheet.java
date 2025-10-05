package DisplayGUI;

import Enums.Accidental;
import Enums.N;
import Enums.Staff;
import Enums.Voice;
import Music.Chord;
import Music.Interval;
import Music.Note;
import Music.Progression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls the Graphics on the music sheet
 * If given a chord progression, it will plot it on a staff
 */
public class MusicSheet extends JPanel {

    /**
     * The progression that is displayed
     * GUI copies chords from progression to user interface
     */
    private Progression p;

    /**
     * the distance from each note to the next
     */
    private int noteDist;

    /**
     * The current accidentals on notes from key signature
     */
    private Map<N, Accidental> keySignature;

    /**
     * The amount of y direction the space in between the lines take
     */
    public static final int LINESPACE = 24;

    /**
     * The amount of x direction the clef takes
     */
    public static final int CLEFSPACE = LINESPACE * 6;


    /**
     * The length of the staff
     */
    public static final int STAFFLENGTH = 1200;

    /**
     * Distance between the treble and bass staves
     */
    public static final int STAFFDISTANCE = (int) (LINESPACE * 5.5);

    /**
     * Starting x position for the staff
     */
    public static final int STARTX = 100;

    /**
     * Starting y position for the staff
     */
    public static final int STARTY = 100;

    /**
     * The note at the top of the treble clef that determines position
     */
    public static final Note trebleTop = new Note(N.E, 5, Accidental.NATURAL);

    /**
     * The note at the top of the bass clef that determines position
     */
    public static final Note bassTop = new Note(N.G, 3, Accidental.NATURAL);

    /**
     * Size of font for the accidentals
     */
    public static final int FONTSIZE = 5 * LINESPACE / 4;

    /**
     * Constructor for MusicSheet
     * Creates the GUI with a given progression
     * @param p progression to save
     */
    public MusicSheet(Progression p) {
        super();
        this.p = p;
    }

    /**
     * Constructor for MusicSheet
     * Creates an empty sheet of music
     */
    public MusicSheet() {
        super();
        this.p = null;
    }

    /**
     * Draws the entire staff and the current notes in the chord progression
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        drawGrandStaff(STARTX, STARTY, g);
        if (p == null) {
            return;
        }
        //Current key signature
        //If key signature is written in, this needs to change
        keySignature = Map.ofEntries(
                Map.entry(N.A, Accidental.NATURAL),
                Map.entry(N.B, Accidental.NATURAL),
                Map.entry(N.C, Accidental.NATURAL),
                Map.entry(N.D, Accidental.NATURAL),
                Map.entry(N.E, Accidental.NATURAL),
                Map.entry(N.F, Accidental.NATURAL),
                Map.entry(N.G, Accidental.NATURAL)
        );
        ArrayList<Chord> chords = p.getChords();
        noteDist = (STAFFLENGTH - CLEFSPACE) / (chords.size());
        HashMap<N, Accidental> accidentalTracker = new HashMap<>();
        for (int i = 0; i < chords.size(); i++) {
            Note soprano = chords.get(i).getSoprano();
            Note alto = chords.get(i).getAlto();
            Note tenor = chords.get(i).getTenor();
            Note bass = chords.get(i).getBass();

            drawNote(soprano, Staff.TREBLE, Voice.SOPRANO, i, g);
            drawLedgerLines(soprano, Staff.TREBLE, i, g);
            if (checkAccidental(soprano, accidentalTracker)) {
                drawAccidental(soprano, Staff.TREBLE, i, g);
            }

            drawNote(alto, Staff.TREBLE, Voice.ALTO, i, g);
            drawLedgerLines(alto, Staff.TREBLE, i, g);
            if (checkAccidental(alto, accidentalTracker)) {
                drawAccidental(alto, Staff.TREBLE, i, g);
            }

            drawNote(tenor, Staff.BASS, Voice.TENOR, i, g);
            drawLedgerLines(tenor, Staff.BASS, i, g);
            if (checkAccidental(tenor, accidentalTracker)) {
                drawAccidental(tenor, Staff.BASS, i, g);
            }

            drawNote(bass, Staff.BASS, Voice.BASS, i, g);
            drawLedgerLines(bass, Staff.BASS, i, g);
            if (checkAccidental(bass, accidentalTracker)) {
                drawAccidental(bass, Staff.BASS, i, g);
            }

            //Adjusts the accidentalTracker
            if (checkAccidental(soprano, accidentalTracker)) {
                accidentalTracker.put(soprano.getNote(), soprano.getAccidental());
            }
            if (checkAccidental(alto, accidentalTracker)) {
                accidentalTracker.put(alto.getNote(), alto.getAccidental());
            }
            if (checkAccidental(tenor, accidentalTracker)) {
                accidentalTracker.put(tenor.getNote(), tenor.getAccidental());
            }
            if (checkAccidental(bass, accidentalTracker)) {
                accidentalTracker.put(bass.getNote(), bass.getAccidental());
            }
        }

    }

    /**
     * Checks if an accidental needs to be drawn for a note
     * @param n the current note
     * @param accidentalTracker a map of all the notes that currently have accidentals
     * @return true if the note needs an accidental
     */
    public boolean checkAccidental(Note n, HashMap accidentalTracker) {
        N blankNote = n.getNote();
        if (accidentalTracker.containsKey(blankNote)) {
            if (n.getAccidental() == accidentalTracker.get(blankNote) || accidentalTracker.get(blankNote) == Accidental.NATURAL) {
                if (n.getAccidental() != keySignature.get(n.getNote())) {
                    return true;
                }
                return false;
            } else {
                return true;
            }
        } else {
            if (n.getAccidental() == Accidental.NATURAL) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * Draws an accidental next to the note
     * @param n note with accidental
     * @param s which staff to place it on
     * @param i chord number
     * @param g <code>Graphics</code> object to protect
     */
    public void drawAccidental(Note n, Staff s, int i, Graphics g) {
        Font f = new Font("serif", Font.PLAIN, FONTSIZE);
        g.setFont(f);
        int x = STARTX + CLEFSPACE + noteDist * i - LINESPACE;
        int y = getDistance(n, s) + LINESPACE;
        if (n.getAccidental() == Accidental.NATURAL) {
            drawNatural(x - LINESPACE / 4, y, g);
        } else if (n.getAccidental() == Accidental.FLAT) {
            drawFlat(x - LINESPACE / 3, y - LINESPACE / 6, g);
        } else if (n.getAccidental() == Accidental.SHARP) {
            drawSharp(x, y - LINESPACE / 7, g);
        } else if (n.getAccidental() == Accidental.DOUBLE_FLAT) {
            drawDoubleFlat(x - LINESPACE / 7, y - LINESPACE / 4, g);
        } else if (n.getAccidental() == Accidental.DOUBLE_SHARP) {
            drawDoubleSharp(x, y + LINESPACE / 8, g);
        }
    }

    /**
     * Draws a sharp
     * @param x lower left x coordinate
     * @param y lower left y coordinate
     * @param g <code>Graphics</code> object to protect
     */
    public void drawSharp(int x, int y, Graphics g) {
        g.drawString(Note.SHARP_STRING, x, y);
    }

    /**
     * Draws a flat
     * @param x lower left x coordinate
     * @param y lower left y coordinate
     * @param g <code>Graphics</code> object to protect
     */
    public void drawFlat(int x, int y, Graphics g) {
        g.drawString(Note.FLAT_STRING, x, y);
    }

    /**
     * Draws a natural
     * @param x lower left x coordinate
     * @param y lower left y coordinate
     * @param g <code>Graphics</code> object to protect
     */
    public void drawNatural(int x, int y, Graphics g) {
        g.drawString(Note.NATURAL_STRING, x, y);
    }

    /**
     * Draws a double flat
     * @param x lower left x coordinate
     * @param y lower left y coordinate
     * @param g <code>Graphics</code> object to protect
     */
    public void drawDoubleFlat(int x, int y, Graphics g) {
        Font f = new Font("serif", Font.PLAIN, FONTSIZE * 3 / 2);
        g.setFont(f);
        g.drawString(Note.DOUBLE_FLAT_STRING, x, y);
    }

    /**
     * Draws a double sharp
     * @param x lower left x coordinate
     * @param y lwer left y coordinate
     * @param g <code>Graphics</code> object to protect
     */
    public void drawDoubleSharp(int x, int y, Graphics g) {
        Font f = new Font("serif", Font.PLAIN, FONTSIZE * 3 / 2);
        g.setFont(f);
        g.drawString(Note.DOUBLE_SHARP_STRING, x, y);
    }

    /**
     * Checks if a note has ledger lines then draws them if the note does
     * @param n current note
     * @param s which staff the note is on
     * @param i chord number
     * @param g <code>Graphics</code> object to protect
     */
    public void drawLedgerLines(Note n, Staff s, int i, Graphics g) {
        if (s == Staff.TREBLE) {
            int top = n.getInterval(new Note("A6")).getNoteDist();
            int bot = n.getInterval(new Note("C4")).getNoteDist();
            if (top <= 0) {
                drawLedgerLines(s, Math.abs(top) / 2 + 1, true, i, g);
            } else if (bot >= 0) {
                drawLedgerLines(s, bot / 2 + 1, false, i, g);
            }
        }
        if (s == Staff.BASS) {
            int top = n.getInterval(new Note("C4")).getNoteDist();
            int bot = n.getInterval(new Note("E2")).getNoteDist();
            if (top <= 0) {
                drawLedgerLines(s, Math.abs(top) / 2 + 1, true, i, g);
            } else if (bot >= 0) {
                drawLedgerLines(s, bot / 2 + 1, false, i, g);
            }
        }
    }

    /**
     * Draws the ledger lines
     * @param s which staff to draw on
     * @param num the number of ledger lines
     * @param top whether the ledger lines are at the top or not; false if at bottom
     * @param i which chord it is on
     * @param g <code>Graphics</code> object to protect
     */
    public void drawLedgerLines(Staff s, int num, Boolean top, int i, Graphics g) {
        for(int j = 1; j <= num; j++) {
            if (top) {
                g.drawLine(STARTX + CLEFSPACE + noteDist * i - LINESPACE / 4, distanceToStaff(s) - j * LINESPACE, STARTX + CLEFSPACE + noteDist * i + (int) (LINESPACE * 1.25) + LINESPACE / 4, distanceToStaff(s) - j * LINESPACE);
            } else {
                g.drawLine(STARTX + CLEFSPACE + noteDist * i - LINESPACE / 4, distanceToStaff(s) + 4 * LINESPACE + j * LINESPACE, STARTX + CLEFSPACE + noteDist * i + (int) (LINESPACE * 1.25) + LINESPACE / 4, distanceToStaff(s) + 4 * LINESPACE + j * LINESPACE);
            }
        }
    }

    /**
     * Returns the distance from the top of the page to the top of the staff
     * @param s the staff to find distance to
     * @return the y distance to the staff
     */
    public int distanceToStaff(Staff s) {
        if (s == Staff.TREBLE) {
            return STARTX;
        } else {
            return STARTX + 4 * LINESPACE + STAFFDISTANCE;
        }
    }

    /**
     * Draws note
     * @param n note to plot
     * @param s which staff the note is on
     * @param v which voice is singing the note
     * @param i the chord number
     * @param g <code>Graphics</code> object to protect
     */
    public void drawNote(Note n, Staff s, Voice v, int i, Graphics g) {
        g.fillOval(STARTX + CLEFSPACE + noteDist * i, getDistance(n, s), (int) (LINESPACE * 1.25), LINESPACE);
        if (v == Voice.BASS || v == Voice.ALTO) {
            g.drawLine(STARTX + CLEFSPACE + noteDist * i, getDistance(n, s) + LINESPACE / 2, STARTX + CLEFSPACE + noteDist * i, getDistance(n, s) + LINESPACE / 2 + 3 * LINESPACE);
        } else {
            g.drawLine(STARTX + CLEFSPACE + noteDist * i + (int) (LINESPACE * 1.25), getDistance(n, s) + LINESPACE / 2, STARTX + CLEFSPACE + noteDist * i + (int) (LINESPACE * 1.25), getDistance(n, s) + LINESPACE / 2 - 3 * LINESPACE);
        }
    }

    /**
     * Gets the y value of the note position
     * @param n note to be plotted
     * @param s which staff the note is on
     * @return the y position of the note
     */
    public int getDistance(Note n, Staff s) {
        Note root;
        if (s == Staff.TREBLE) {
            root = trebleTop;
        } else {
            root = bassTop;
        }
        Note blankNote = new Note(n.getNote(), n.getOctave(), Accidental.NATURAL);
        int octaves = blankNote.compareTo(root) / 12;
        Interval distance = blankNote.getInterval(root);


        return (distance.getNoteDist() % 7 + 7 * octaves) * (LINESPACE / 2) + distanceToStaff(s);
    }

    /**
     * Draws grand staff with treble and bass
     * @param x upper left x position
     * @param y upper left y position
     * @param g <code>Graphics</code> object to protect
     */
    public void drawGrandStaff(int x, int y, Graphics g) {
        drawStaff(x, y, g);
        drawStaff(x, y + 4 * LINESPACE + STAFFDISTANCE, g);
        g.drawLine(x, y, x, y + 4 * LINESPACE + STAFFDISTANCE);
        g.drawLine(x + STAFFLENGTH, y, x + STAFFLENGTH, y + 4 * LINESPACE + STAFFDISTANCE);
        Font f = new Font("serif", Font.PLAIN, FONTSIZE * 7);
        g.setFont(f);
        g.drawString(Note.TREBLE_CLEF, STARTX + LINESPACE / 2, distanceToStaff(Staff.TREBLE) + 5 * LINESPACE);
        f = new Font("serif", Font.PLAIN, FONTSIZE * 5);
        g.setFont(f);
        g.drawString(Note.BASS_CLEF, STARTX + LINESPACE / 2, distanceToStaff(Staff.BASS) + 4 * LINESPACE);
    }

    /**
     * Draws a single staff
     * @param x upper left x position
     * @param y upper left y position
     * @param g <code>Graphics</code> object to protect
     */
    public void drawStaff(int x, int y, Graphics g) {
        for (int i = 0; i < 5 ; i++) {
            g.drawLine(x, y + i * LINESPACE, x + STAFFLENGTH, y + i * LINESPACE);
        }
        g.drawLine(x, y, x, y + 4 * LINESPACE);
        g.drawLine(x + STAFFLENGTH, y, x + STAFFLENGTH, y + 4 * LINESPACE);
    }

    /**
     * Sets the current progression to a new progression
     * @param p the new progression
     */
    public void setProgression(Progression p) {
        this.p = p;
    }


}
