package Music;

/**
 *A storage class of notes
 * Represents a single chord in a progression
 * Stores the information about the chord
 */
public class Chord {
    /**
     * Soprano note
     */
    private Note soprano;

    /**
     * Alto note
     */
    private Note alto;

    /**
     * Tenor note
     */
    private Note tenor;

    /**
     * Bass note
     */
    private Note bass;

    /**
     * Name of the chord in figured bass notation
     */
    private String chordName;

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(Note bass, Note tenor, Note alto, Note soprano, String chordName) {
        this.soprano = soprano;
        this.tenor = tenor;
        this.alto = alto;
        this.bass = bass;
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, String tenor, String alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(Note bass, String tenor, String alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, Note tenor, String alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, String tenor, Note alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, String tenor, String alto, Note soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, String tenor, Note alto, Note soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, Note tenor, String alto, Note soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(Note bass, String tenor, String alto, Note soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(String bass, Note tenor, Note alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(Note bass, String tenor, Note alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }

    /**
     * Creates a new chord with the given notes
     * @param bass bass note
     * @param tenor tenor note
     * @param alto alto note
     * @param soprano soprano note
     * @param chordName figured bass for the chord
     */
    public Chord(Note bass, Note tenor, String alto, String soprano, String chordName) {
        this.soprano = new Note(soprano);
        this.alto = new Note(alto);
        this.tenor = new Note(tenor);
        this.bass = new Note(bass);
        this.chordName = chordName;
    }


    /*END OF SIMILAR CONSTRUCTORS*/

    /**
     * Creates a deep copy of the given chord
     * Creates copies of the notes in the chord as well
     * @param c chord to copy
     */
    public Chord(Chord c) {
        this.soprano = new Note(c.soprano);
        this.alto = new Note(c.alto);
        this.tenor = new Note(c.tenor);
        this.bass = new Note(c.bass);
        this.chordName = c.chordName;
    }

    /**
     * @return soprano note
     */
    public Note getSoprano() {
        return soprano;
    }

    /**
     * @return alto note
     */
    public Note getAlto() {
        return alto;
    }

    /**
     * @return tenor note
     */
    public Note getTenor() {
        return tenor;
    }

    /**
     * @return bass note
     */
    public Note getBass() {
        return bass;
    }

    /**
     * Returns the chord's name
     * @return chordName
     */
    public String getChordName() {
        return chordName;
    }

    /**
     * Transposes the entire chord up or down the interval given
     * CHANGES THE VALUES IN THIS CHORD
     * @param i interval to transpose
     * @return the old version of the chord
     * @postcond all the notes in the chord are transposed
     */
    public Chord transposeTo(Interval i) {
        Chord oldChord = new Chord(this);
        soprano.transposeTo(i);
        alto.transposeTo(i);
        tenor.transposeTo(i);
        bass.transposeTo(i);
        return oldChord;
    }

    /**
     * Transposes the entire chord up or down the interval given
     * DOES NOT CHANGE THE VALUES IN THIS CHORD
     * @param i interval to transpose
     * @return the transposed version of the chord
     */
    public Chord transpose(Interval i) {
        Chord newChord = new Chord(this);
        newChord.transposeTo(i);
        return newChord;
    }

    /**
     * Returns string representation of chord
     * @return Name: bass tenor alto soprano
     */
    public String toString() {
        return chordName +  ": " + getBass() + " " + getTenor() + " " + getAlto() + " " + getSoprano();
    }
}