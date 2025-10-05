package Music;

import Enums.Voice;

import java.util.*;

/**
 * A representation of an entire chord progression
 */
public class Progression {

    /**
     * Array of chord names provided by the user
     */
    private static String[] input;

    /**
     * The current chords in the progresson
     * The chord at index i matches the chord in input
     */
    private ArrayList<Chord> chords;

    /**
     * Whether or not to randomize the output selection
     * Set to false for testing
     * When set to true, the final chord progression found can be different on each run
     */
    public static final boolean RANDOM = true;

    /**
     * For purposes of debugging
     * Figures out where the progression is having trouble
     */
    public static int furthestChord = 0;

    /**
     *Initial constructor for progression
     * Sets the input progression
     * @param input array of chords provided by the user
     */
    public Progression(String[] input) {
        Key.transposeDatabase(new Note(input[0]));
        Progression.input = new String[input.length - 1];
        System.arraycopy(input, 1, Progression.input, 0, input.length - 1);
        furthestChord = 0;
        chords = new ArrayList<>();
    }

    /**
     * Copy constructor
     * Makes a deep copy of the progression then adds the next chord
     * @param p progression to copy
     * @param next the next chord to add
     */
    public Progression(Progression p, Chord next) {
        this.chords = new ArrayList<>();
        for (Chord c : p.chords) {
            this.chords.add(new Chord(c));
        }
        this.chords.add(new Chord(next));
        if (chords.size() > furthestChord) {
            furthestChord = chords.size();
        }
    }

    /**
     * Checks if the current chord progression is valid
     * Uses four part harmonization rules to determine if the current chord and
     * previous chord work
     * @return whether or not the current progression is valid
     */
    public boolean isValid() {
        if (chords.size() > 1) {
            return checkFourPart(chords.getLast(), chords.get(chords.size() - 2));
        } else if (chords.size() == 1) {
            return checkFourPart(chords.getLast());
        } else {
            return true;
        }
    }

    /**
     * Checks if the four part harmonization is correct
     * @param cur the current chord
     * @param prev the previous chord
     * @return true if the harmonization is correct
     */
    public boolean checkFourPart(Chord cur, Chord prev) {
        boolean voiceCrossing = checkVoiceCrossing(cur, prev);
        boolean distancing = checkDistancing(cur);
        boolean parallels = checkParallels(cur, prev);
        boolean inflection = checkInflection(cur, prev);
        boolean jumps = checkJumps(cur, prev);
        return distancing && voiceCrossing && parallels && inflection && jumps;
    }

    /**
     * Checks the four part for a singe chord
     * @param cur the chord to check
     * @return true if the four part is correct
     */
    public boolean checkFourPart(Chord cur) {
        return checkDistancing(cur) && checkVoiceCrossing(cur, cur);
    }

    /**
     * Checks if there are parallels between the chords
     * Ignores parallels between double inflections
     * @param cur the current chord
     * @param prev the previous chord
     * @return true if there are no parallels
     */
    public boolean checkParallels(Chord cur, Chord prev) {
        Interval soprano = prev.getSoprano().getInterval(cur.getSoprano());
        Interval alto = prev.getAlto().getInterval(cur.getAlto());
        Interval tenor = prev.getTenor().getInterval(cur.getTenor());
        Interval bass = prev.getBass().getInterval(cur.getBass());
        boolean hasSopAltoParallels = cur.getSoprano().getInterval(cur.getAlto()).isPerfect() &&
                (soprano.equals(alto) && soprano.moved()) && !(prev.getSoprano().hasInflection() && prev.getAlto().hasInflection());
        boolean hasSopTenorParallels = cur.getSoprano().getInterval(cur.getTenor()).isPerfect() &&
                (soprano.equals(tenor) && soprano.moved()) && !(prev.getSoprano().hasInflection() && prev.getTenor().hasInflection());
        boolean hasSopBassParallels = cur.getSoprano().getInterval(cur.getBass()).isPerfect() &&
                (soprano.equals(bass) && soprano.moved())&& !(prev.getSoprano().hasInflection() && prev.getBass().hasInflection());
        boolean hasAltoTenorParallels = cur.getAlto().getInterval(cur.getTenor()).isPerfect() &&
                (alto.equals(tenor) && alto.moved()) && !(prev.getAlto().hasInflection() && prev.getTenor().hasInflection());
        boolean hasAltoBassParallels = cur.getAlto().getInterval(cur.getBass()).isPerfect() &&
                (alto.equals(bass) && alto.moved()) && !(prev.getAlto().hasInflection() && prev.getBass().hasInflection());
        boolean hasTenorBassParallels = cur.getTenor().getInterval(cur.getBass()).isPerfect() &&
                (tenor.equals(bass) && tenor.moved()) && !(prev.getTenor().hasInflection() && prev.getBass().hasInflection());
        return !(hasSopAltoParallels || hasSopTenorParallels || hasSopBassParallels || hasAltoTenorParallels ||
                hasAltoBassParallels || hasTenorBassParallels);
    }

    /**
     * Checks that the voices do not make jumps grater than 6ths
     * @param cur current chord
     * @param prev previous chord
     * @return true if all the voices do not have too large of a jump
     */
    public boolean checkJumps(Chord cur, Chord prev) {
        return Math.abs(cur.getSoprano().compareTo(prev.getSoprano())) <= 9 &&
                Math.abs(cur.getAlto().compareTo(prev.getAlto())) <= 9 &&
                Math.abs(cur.getTenor().compareTo(prev.getTenor())) <= 9 &&
                Math.abs(cur.getBass().compareTo(prev.getBass())) <= 9;
    }

    /**
     * Checks if the voices are properly distanced (within an octave of each other)
     * @param cur the current chord
     * @return true if the distancing is correct
     */
    public boolean checkDistancing(Chord cur) {
        return cur.getBass().compareTo(cur.getTenor()) >= 0 &&
                cur.getTenor().compareTo(cur.getAlto()) <= 12 &&
                cur.getTenor().compareTo(cur.getAlto()) >= 0 &&
                cur.getAlto().compareTo(cur.getSoprano()) <= 12 &&
                cur.getAlto().compareTo(cur.getSoprano()) >= 0;
    }

    /**
     * Checks if there is voice crossing between voices
     * @param cur the current chord
     * @param prev the previous chord
     * @return true if there is no voice crossing
     */
    public boolean checkVoiceCrossing(Chord cur, Chord prev) {
        boolean curChordWorks = cur.getAlto().compareTo(cur.getSoprano()) >= 0 &&
                cur.getTenor().compareTo(cur.getAlto()) >= 0 &&
                cur.getBass().compareTo(cur.getTenor()) >= 0;
        boolean prevChordWorks = cur.getAlto().compareTo(prev.getSoprano()) >= 0 &&
                cur.getTenor().compareTo(prev.getAlto()) >= 0 &&
                cur.getBass().compareTo(prev.getTenor()) >= 0 &&
                prev.getAlto().compareTo(cur.getSoprano()) >= 0 &&
                prev.getTenor().compareTo(cur.getAlto()) >= 0 &&
                prev.getBass().compareTo(cur.getTenor()) >= 0;
        return curChordWorks && prevChordWorks;
    }

    /**
     * Checks if all notes are resolved properly
     * @param cur the current chord
     * @param prev the previous chord
     * @return true if all the inflection is correct
     */
    public boolean checkInflection(Chord cur, Chord prev) {
        boolean bassNotInflect = prev.getBass().hasInflection() &&
                !(prev.getBass().isProperInflection(cur.getBass()));
        boolean tenorNotInflect = prev.getTenor().hasInflection() &&
                !(prev.getTenor().isProperInflection(cur.getTenor()));
        boolean altoNotInflect = prev.getAlto().hasInflection() &&
                !(prev.getAlto().isProperInflection(cur.getAlto()));
        boolean sopranoNotInflect = prev.getSoprano().hasInflection() &&
                !(prev.getSoprano().isProperInflection(cur.getSoprano()));
        return !(bassNotInflect || tenorNotInflect || altoNotInflect || sopranoNotInflect);
    }

    /**
     * Gets all the possible iterations of the next chord int eh progression
     * @return a list of neighboring progressions
     */
    public ArrayList<Progression> getSuccessors() {
        ArrayList<Progression> nbrs = new ArrayList<>();
        Chord next = Key.getChord(input[chords.size()]);
        ArrayList<Chord> possibilities = getIterations(next);
        for (Chord possible : possibilities) {
            nbrs.add(new Progression(this, possible));
        }
        if (RANDOM) {
            Collections.shuffle(nbrs);
        }
        return nbrs;
    }

    /**
     * Finds all the possible iterations of a generic chord
     * Keeps the bass on the same note but shuffles the other voices around
     * @param next the next generic chord
     * @return a list of possible chords
     */
    public ArrayList<Chord> getIterations(Chord next) {
        ArrayList<Chord> possibilities = new ArrayList<>();
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getTenor(), next.getAlto(), next.getSoprano(), next.getChordName()));
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getAlto(), next.getSoprano(), next.getTenor(), next.getChordName()));
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getSoprano(), next.getTenor(), next.getAlto(), next.getChordName()));
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getTenor(), next.getSoprano(), next.getAlto(), next.getChordName()));
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getAlto(), next.getTenor(), next.getSoprano(), next.getChordName()));
        possibilities.addAll(generatePossibleChords(next.getBass(), next.getSoprano(), next.getAlto(), next.getTenor(), next.getChordName()));
        return possibilities;
    }

    /**
     * Generates all the iterations of the next chord with the voices in a given position
     * @param bass the base note
     * @param tenor the tenor note
     * @param alto the alto note
     * @param soprano the soprano note
     * @param name chord name
     * @return a list of possible chords with given notes for voices
     */
    public ArrayList<Chord> generatePossibleChords(Note bass, Note tenor, Note alto, Note soprano, String name) {
        ArrayList<Chord> possible = new ArrayList<>();
        if (!chords.isEmpty()) {
            Note prevSoprano = chords.getLast().getSoprano();
            Note prevAlto = chords.getLast().getAlto();
            Note prevTenor = chords.getLast().getTenor();
            Note prevBass = chords.getLast().getBass();
            for (Note bassNote : getPossibleNotes(Voice.BASS, bass, Key.getLowRange(Voice.BASS), prevTenor)) {
                for (Note tenorNote : getPossibleNotes(Voice.TENOR, tenor, prevBass, prevAlto)) {
                    for (Note altoNote : getPossibleNotes(Voice.ALTO, alto, prevTenor, prevSoprano)) {
                        for (Note sopranoNote : getPossibleNotes(Voice.SOPRANO, soprano, prevAlto, Key.getTopRange(Voice.SOPRANO))) {
                            possible.add(new Chord(bassNote, tenorNote, altoNote, sopranoNote, name));
                        }
                    }
                }
            }
        } else {
            for (Note bassNote : getPossibleNotes(Voice.BASS, bass, Key.getLowRange(Voice.BASS), Key.getTopRange(Voice.BASS))) {
                for (Note tenorNote : getPossibleNotes(Voice.TENOR, tenor, Key.getLowRange(Voice.TENOR), Key.getTopRange(Voice.TENOR))) {
                    for (Note altoNote : getPossibleNotes(Voice.ALTO, alto, Key.getLowRange(Voice.ALTO), Key.getTopRange(Voice.ALTO))) {
                        for (Note sopranoNote : getPossibleNotes(Voice.SOPRANO, soprano, Key.getLowRange(Voice.SOPRANO), Key.getTopRange(Voice.SOPRANO))) {
                            possible.add(new Chord(bassNote, tenorNote, altoNote, sopranoNote, name));
                        }
                    }
                }
            }
        }
        return possible;
    }

    /**
     * Returns the possible notes in the correct octave that a voice can singe
     * @param v the voice part
     * @param n the note to place in the correct octave
     * @param lowBound lower bound from previous chord
     * @param highBound upper bound from previous chord
     * @return a list of possible notes the voice can sing
     */
    public ArrayList<Note> getPossibleNotes(Voice v, Note n, Note lowBound, Note highBound) {
        Note absLowBound = Key.getLowRange(v);
        Note absUpBound = Key.getTopRange(v);
        ArrayList<Note> possible = new ArrayList<>();
        Note curNote = new Note(n.getNote(), absLowBound.getOctave() - 1, n.getAccidental(), n.getInflection());
        while (curNote.compareTo(absUpBound) >= 0 && curNote.compareTo(highBound) >= 0) {
            if (curNote.compareTo(lowBound) <= 0 && curNote.compareTo(absLowBound) <= 0) {
                possible.add(curNote);
            }
            curNote = new Note(curNote.getNote(), curNote.getOctave() + 1, curNote.getAccidental(), curNote.getInflection());
        }
        return possible;
    }

    /**
     * Returns whether the progression matches the user input
     * @return true if the progression is the final goal
     */
    public boolean isGoal() {
        if (chords.size() == input.length) {
            return isValid();
        }
        return false;
    }

    /**
     * Adds a chord to the progression
     * FOR TESTING PURPOSES
     */
    public void addChord(Chord c) {
        chords.add(c);
    }

    /**
     * Returns the current depth of the progression
     * @return how many chords are in the progression
     */
    public int depth() {
        return chords.size();
    }

    /**
     * Returns the farthest index the progression made it
     * FOR TESTING PURPOSES
     * @return the index of the problem chord
     */
    public int getFurthestChord() {
        return furthestChord;
    }

    /**
     * Returns the entire chord progression
     * @return a list of chords
     */
    public ArrayList<Chord> getChords() {
        return chords;
    }

    /**
     * Gets the chord name at index i
     * @param index index of the chord
     * @return the name of the chord
     */
    public String getChordName(int index) {
        return input[index];
    }

    /**
     * Returns a string representation of chord progression
     * @return chords in a vertical format with names underneath
     */
    public String toString() {
        String result = "Chord Progression:\n";
        for (Chord c : chords) {
            result += c.getSoprano().toString() + "      ";
        }
        result = result.substring(0, result.length() - 6) + "\n";
        for (Chord c : chords) {
            result += c.getAlto().toString() + "      ";
        }
        result = result.substring(0, result.length() - 6) + "\n";
        for (Chord c : chords) {
            result += c.getTenor().toString() + "      ";
        }
        result = result.substring(0, result.length() - 6) + "\n";
        for (Chord c : chords) {
            result += c.getBass().toString() + "      ";
        }
        result = result.substring(0, result.length() - 6) + "\n";
        for (int i = 0; i < input.length; i++) {
            String name = input[i];
            result += " " + name;
            for (int j = 0; j < 8 - name.length(); j++) {
                result += " ";
            }
        }
        return result;
    }
}
