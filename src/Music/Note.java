package Music;

import Enums.Accidental;
import Enums.N;

import java.util.*;

/**
 * The note storage class
 * Stores all the information about an individual note
 */
public class Note implements Comparable<Note> {

    /**
     * The note name (A, B, C, etc.)
     */
    private N note;

    /**
     * What accidental is placed on the note (Sharp, Flat, Natural)
     */
    private Accidental a;

    /**
     * Which octave the note is in (A4, C3, etc.)
     */
    private int octave;

    /**
     * Which note this not has an inflection towards
     * if there is one.
     * Null if there is no inflection
     */
    private Note inflection;

    /**
     * String representation of the different music symbols
     * Used in the toString method and GUI
     */
    public static final String SHARP_STRING = "♯";
    public static final String FLAT_STRING = "♭";
    public static final String NATURAL_STRING = "♮";
    public static final String DOUBLE_FLAT_STRING = "\uD834\uDD2B";
    public static final String DOUBLE_SHARP_STRING = "\uD834\uDD2A";
    public static final String TREBLE_CLEF = "\uD834\uDD1E";
    public static final String BASS_CLEF = "\uD834\uDD22";



    /**
     * Converts a note to the number of halfsteps from the letter A
     */
    private static final Map<N, Integer> noteMap = Map.of(
            N.A, 0,
            N.B, 2,
            N.C, 3,
            N.D, 5,
            N.E, 7,
            N.F, 8,
            N.G, 10
    );

    /**
     * Converts the accidental to a number of halfsteps from the natural note
     */
    private static final Map<Accidental, Integer> accidentalMap = Map.of(
            Accidental.FLAT, -1,
            Accidental.SHARP, 1,
            Accidental.NATURAL, 0,
            Accidental.DOUBLE_FLAT, -2,
            Accidental.DOUBLE_SHARP, 2
    );

    /**
     * Maps a note distance to an accidental
     */
    private static final Map<Integer, Accidental> intToAccidental = Map.of(
            -1, Accidental.FLAT,
            1, Accidental.SHARP,
            0, Accidental.NATURAL,
            -2, Accidental.DOUBLE_FLAT,
            2, Accidental.DOUBLE_SHARP
    );

    /**
     * Scale of notes used in the transpose method to find the next note name
     */
    private static final ArrayList<N> scale = new ArrayList<N>(List.of(
            N.A, N.B, N.C, N.D, N.E, N.F, N.G,
            N.A, N.B, N.C, N.D, N.E, N.F, N.G,
            N.A, N.B, N.C, N.D, N.E, N.F, N.G));

    /**
     * Converts a string note name to the enum note name
     */
    private static final Map<String, N> stringToNote = Map.of(
            "A", N.A, "B", N.B, "C", N.C,
            "D", N.D, "E", N.E, "F", N.F, "G", N.G
    );

    /**
     * Converts a string accidental to the enum accidental
     */
    private static final Map<String, Accidental> stringToAccidental = Map.of(
            "s", Accidental.SHARP,
            "b", Accidental.FLAT,
            "x", Accidental.DOUBLE_SHARP,
            "f", Accidental.DOUBLE_FLAT
    );

    /**
     * Creates an instance of a note using the given values
     * @param note note name
     * @param octave the octave of the note
     * @param a the accidental
     * @param inflection the note this note wants to inflect towards
     */
    public Note(N note, int octave, Accidental a, Note inflection) {
        this.note = note;
        this.a = a;
        this.inflection = inflection;
        this.octave = octave;
    }

    /**
     * Constructor for note with no inflection
     * @param note note name
     * @param octave the octave of the note
     * @param a the accidental
     */
    public Note(N note, int octave, Accidental a) {
        this.note = note;
        this.a = a;
        this.inflection = null;
        this.octave = octave;
    }
    /**
     * Copies another instance of a note object
     * @param n other note to be copied
     */
    public Note(Note n) {
        this.note = n.note;
        this.a = n.a;
        this.octave = n.octave;
        this.inflection = n.inflection;
    }

    /**
     * Constructor for note with a string note name
     * @param name note name in format
     *             "{Accidental}{Name}{Octave}" or
     *             "{Name}{Octave}" (assumed natural)
     */
    public Note(String name) {
        String note;
        String accident;
        String octave;
        if (name.length() == 3) {
            accident = name.substring(0, 1);
            note = name.substring(1, 2);
            octave = name.substring(2, 3);
            this.a = stringToAccidental.get(accident);
        } else {
            note = name.substring(0, 1);
            octave = name.substring(1, 2);
            this.a = Accidental.NATURAL;
        }
        this.note = stringToNote.get(note);
        inflection = null;
        this.octave = Integer.parseInt(octave);
    }

    /**
     * Constructor for note with a string note name and inflection
     * @param name note name in format
     *             "{Accidental}{Name}{Octave}" or
     *             "{Name}{Octave}" (assumed natural)
     * @param inflection the note this note wants to inflect to
     *             "{Accidental}{Name}{Octave}" or
     *             "{Name}{Octave}" (assumed natural)
     */
    public Note(String name, String inflection) {
        String note;
        String accident;
        String octave;
        if (name.length() == 3) {
            accident = name.substring(0, 1);
            note = name.substring(1, 2);
            octave = name.substring(2, 3);
            this.a = stringToAccidental.get(accident);
        } else {
            note = name.substring(0, 1);
            octave = name.substring(1, 2);
            this.a = Accidental.NATURAL;
        }
        this.note = stringToNote.get(note);
        this.inflection = new Note(inflection);
        this.octave = Integer.parseInt(octave);
    }

    /**
     * @return true if this note has an inflection
     */
    public boolean hasInflection() {
        return !(inflection == null);
    }

    /**
     * @return note name
     */
    public N getNote() {
        return note;
    }

    /**
     * @return the accidental on the note
     */
    public Accidental getAccidental() {
        return a;
    }

    /**
     * @return the octave of the note
     */
    public int getOctave() {
        return octave;
    }

    /**
     * inflection is positive if has inflection upward
     * negative if it has inflection downward
     * 0 otherwise
     * @return inflection
     */
    public Note getInflection() {
        return inflection;
    }

    /**
     * Returns true if the inflection is satisfied
     * @param next the next note in the same voice
     * @return true if the note inflects properly
     */
    public boolean isProperInflection(Note next) {
        return this.equals(next) ||
                (inflection.isSameLetter(next) && (this.compareTo(next) <= 2 || this.compareTo(next) >= -2)) ||
                (inflection.equals(next.getInflection()));
    }

    /**
     * Transposes the current note up or down the interval given.
     * CHANGES THIS VALUE OF NOTE
     * Cannot transpose more than an octave or else it throws an error.
     * If the current note cannot be transposed to the next note
     * without the use of more than 2 accidentals, the program throws an error
     * @param i interval to the next note
     * @return old vale of the note
     * @throws MusicTheoryException if user tries to transpose more than an octave or use more than 2 accidentals
     * @postcond this note is transposed to the new note
     */
    public Note transposeTo(Interval i) throws MusicTheoryException{
        Note noteCopy = new Note(this);
        int index = scale.indexOf(this.note) + 7;
        int newNoteIndex = index + i.getNoteDist();
        if (i.getNoteDist() > 7) {
            throw new MusicTheoryException("Transpose is not able to transpose above an octave");
        }
        if (newNoteIndex < 7) {
            this.octave--;
        } else if (newNoteIndex > 13) {
            this.octave++;
        }
        this.note = scale.get(newNoteIndex);
        this.a = Accidental.NATURAL;
        Interval i2 = noteCopy.getInterval(this);
        int dist = i.getHalfstepDist() - i2.getHalfstepDist();
        this.a = intToAccidental.get(dist);
        if (Math.abs(dist) > 2) {
            throw new MusicTheoryException("Cannot go above two sharps or two flats.");
        }
        if (inflection != null) {
            inflection.transposeTo(i);
        }
        return noteCopy;
    }

    /**
     * Finds the note this would transpose to in the interval i
     * DOES NOT CHANGE THIS VALUE OF NOTE
     * Cannot transpose more than an octave or else it throws an error
     * If the current note cannot be transposed to the next note
     *     without the use of more than 2 accidentals the program throws an error
     * @param i interval to the next note
     * @return the note that is i away
     * @throws MusicTheoryException if user tries to transpose more than an octave or use more than 2 accidentals
     */
    public Note transpose(Interval i) throws MusicTheoryException{
        Note noteCopy = new Note(this);
        noteCopy.transposeTo(i);
        return noteCopy;
    }

    /**
     * Returns the basic interval between this note and the next
     * Only does intervals up to and including an octave
     * @param n the note to compare to
     * @return the interval between the two notes
     */
    public Interval getInterval(Note n) {
        int halfsteps = this.compareTo(n);
        int noteDist = 0;
        if (halfsteps > 0) {
            int index = scale.indexOf(this.note);
            while (scale.get(index) != n.note) {
                noteDist++;
                index++;
            }
            //Allows for octave distancing
            if (noteDist == 0 && this.octave < n.octave) {
                noteDist += 7;
            }
        } else if (halfsteps < 0) {
            int index = scale.indexOf(n.note);
            while (scale.get(index) != this.note) {
                noteDist--;
                index++;
            }
            //Allows for octave distancing
            if (noteDist == 0 && this.octave > n.octave) {
                noteDist -= 7;
            }
        }
        //Keeps the halfsteps within an octave
        halfsteps = halfsteps % 12;
        //Corrects the halfsteps to allow octave intervals
        if (noteDist == -7 && !(halfsteps <= -8)) {
            halfsteps -= 12;
        } else if (noteDist == 7 && !(halfsteps >= 8)) {
            halfsteps += 12;
        }
        return new Interval(noteDist, halfsteps);
    }


    /**
     * Counts the number of halfsteps from A0
     * @return an integer value for the note
     */
    public int toInt() {
        int val = 0;
        // Assigns base value based on note name
        val += noteMap.get(this.note);
        // Adds octave
        val += octave * 12;
        // Adds/subtracts based on accidental
        val += accidentalMap.get(this.a);
        return val;
    }

    /**
     * Returns if the note is the same but the octave can be different
     * @param n
     * @return if the notes are similar
     */
    public boolean isSameNote(Note n) {
        return this.note == n.note && this.a == n.a;
    }

    /**
     * Checks if the notes have the same letter,
     * but not necessarily the same accidental or octave
     * @param n note to compare
     * @return if the notes have
     */
    public boolean isSameLetter(Note n) {
        return this.note == n.note;
    }

    /**
     * Returns 0 if the notes are equivalent
     * Returns a positive number of halfsteps if this < other
     * Returns a negative number of halfsteps if this > other
     * @param o the object to be compared.
     * @return the number of halfsteps to the other note
     */
    @Override
    public int compareTo(Note o) {
        return o.toInt() - this.toInt();
    }

    /**
     * Checks if two notes are equal
     * @param other other object to compare
     * @return true if the other note name accidental and octave are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Note) {
            Note n = (Note) other;
            return this.a.equals(n.a) && this.note.equals(n.note) && this.octave == n.octave;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, a, octave);
    }

    /**
     * Returns a string representation of the note
     * @return {Accidental}{Note name}{octave}
     */
    @Override
    public String toString() {
        String accidental = "";
        if (a == Accidental.NATURAL) {
            accidental += NATURAL_STRING;
        } else if (a == Accidental.FLAT) {
            accidental += FLAT_STRING;
        } else if (a == Accidental.SHARP) {
            accidental += SHARP_STRING;
        } else if (a == Accidental.DOUBLE_SHARP) {
            accidental += SHARP_STRING + SHARP_STRING;
        } else if (a == Accidental.DOUBLE_FLAT) {
            accidental += FLAT_STRING + FLAT_STRING;
        }
        int octaveInt = octave;
        // Corrects the notation to wrap around C instead of A
        if (note.equals(N.A) || note.equals(N.B)) {
            octaveInt--;
        }
        return accidental + note + octaveInt;
    }
}
