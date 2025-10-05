package Music;

import Enums.Voice;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A util class for solving progressions
 * Stores information related to how chords work
 */
public class Key {

    /**
     * What key the progression is in
     */
    private static Note curKey = new Note("C4");

    /**
     * The list of possible keys the progression can be in.
     * Double sharp and double flat keys are left out to avoid
     * notes with triple accidentals.
     */
    private static final Set<String> possibleKeys = Set.of(
             "A",  "B",  "C",  "D",  "E",  "F",  "G",
            "bA", "bB", "bC", "bD", "bE", "bF", "bG",
            "sA", "sB", "sC", "sD", "sE", "sF", "sG"
    );

    /**
     * The database of chords
     * The keys are figured bass notation and values are generic chords
     */
    private static final Map<String, Chord> database = Map.ofEntries(
            Map.entry("I", new Chord("C4", "E4", "G4", "C4", "I")),
            Map.entry("I6", new Chord("E4", "C4", "G4", "C4", "I6")),
            Map.entry("I6/4", new Chord("G4", "E4", "G4", "C4", "I6/4")),
            Map.entry("ii", new Chord("D4", "F4", "A4", "D4", "ii")),
            Map.entry("ii6", new Chord("F4", "D4", "A4", "D4", "ii6")),
            Map.entry("ii6/4", new Chord("A4", "F4", "A4", "D4", "ii6/4")),
            Map.entry("iii", new Chord("E4", "G4", new Note("B4", "C4"), "E4", "iii")),
            Map.entry("iii6", new Chord("G4", "E4", new Note("B4", "C4"), "E4", "iii6")),
            Map.entry("iii6/4", new Chord(new Note("B4", "C4"), "G4", "E4", "E4", "iii6/4")),
            Map.entry("IV", new Chord("F4", "A4", "C4", "F4", "IV")),
            Map.entry("IV6", new Chord("A4", "F4", "C4", "F4", "IV6")),
            Map.entry("IV6/4", new Chord("C4", "A4", "C4", "F4", "IV6/4")),
            Map.entry("V", new Chord("G4", new Note("B4", "C4"), "D4", "G4", "V")),
            Map.entry("V6", new Chord(new Note("B4", "C4"), "G4", "D4", "G4", "V6")),
            Map.entry("V6/4", new Chord("D4", new Note("B4", "C4"), "D4", "G4", "V6/4")),
            Map.entry("V7", new Chord(new Note("G4"), new Note("B4", "C4"), new Note("D4"), new Note("F4", "E4"), "V7")),
            Map.entry("V6/5", new Chord(new Note("B4", "C4"), new Note("G4"), new Note("D4"), new Note("F4", "E4"), "V6/5")),
            Map.entry("V4/3", new Chord(new Note("D4"), new Note("G4"), new Note("B4", "C4"), new Note("F4", "E4"), "V4/3")),
            Map.entry("V2", new Chord(new Note("F4", "E4"), new Note("G4"), new Note("B4", "C4"), new Note("D4"), "V2")),
            Map.entry("vi", new Chord("A4", "C4", "E4", "A4", "vi")),
            Map.entry("vi6", new Chord("C4", "A4", "E4", "A4", "vi6")),
            Map.entry("vi6/4", new Chord("E4", "C4", "A4", "A4", "vi6/4")),
            Map.entry("viio", new Chord(new Note("B4", "C4"), "D4","F4", "D4", "viio")),
            Map.entry("viio6", new Chord("D4", new Note("B4", "C4"), "F4", "D4", "viio6")),
            Map.entry("viio6/4", new Chord("F4", new Note("B4", "C4"), "D4", "D4", "viio")),
            Map.entry("V/ii", new Chord("A4", new Note("sC4", "D4"), "E4", "A4", "V/ii")),
            Map.entry("V6/ii", new Chord(new Note("sC4", "D4"), "A4", "E4", "A4", "V6/ii")),
            Map.entry("V6/4/ii", new Chord("E4", "E4", new Note("sC4", "D4"), "A4", "V6/4/ii")),
            Map.entry("V7/ii", new Chord("A4", new Note("sC4", "D4"), "E4", new Note("G4", "F4"), "V7/ii")),
            Map.entry("V6/5/ii", new Chord(new Note("sC4", "D4"), "A4", "E4", new Note("G4", "F4"), "V6/5/ii")),
            Map.entry("V4/3/ii", new Chord("E4", "A4", new Note("sC4", "D4"), new Note("G4", "F4"), "V4/3/ii")),
            Map.entry("V2/ii", new Chord(new Note("G4", "F4"), "A4", new Note("sC4", "D4"), "E4", "V2/ii")),
            Map.entry("V/iii", new Chord("B4", new Note("sD4", "E4"), "sF4", "B4", "V/iii")),
            Map.entry("V6/iii", new Chord(new Note("sD4", "E4"), "B4", "sF4", "B4", "V6/iii")),
            Map.entry("V6/4/iii", new Chord("sF4", new Note("sD4", "E4"), "sF4", "B4", "V6/4/iii")),
            Map.entry("V7/iii", new Chord("B4", new Note("sD4", "E4"), "sF4", new Note("A4", "G4"), "V7/iii")),
            Map.entry("V6/5/iii", new Chord(new Note("sD4", "E4"), "B4", "sF4", new Note("A4", "G4"), "V6/5/iii")),
            Map.entry("V4/3/iii", new Chord("sF4", "B4", new Note("sD4", "E4"), new Note("A4", "G4"), "V4/3/iii")),
            Map.entry("V2/iii", new Chord(new Note("A4", "G4"), "B4", new Note("sD4", "E4"), "sF4", "V2/iii")),
            Map.entry("V/III", new Chord("B4", new Note("sD4", "E4"), "sF4", "B4", "V/III")),
            Map.entry("V6/III", new Chord(new Note("sD4", "E4"), "B4", "sF4", "B4", "V6/III")),
            Map.entry("V6/4/III", new Chord("sF4", new Note("sD4", "E4"), "sF4", "B4", "V6/4/III")),
            Map.entry("V7/III", new Chord("B4", new Note("sD4", "E4"), "sF4", new Note("A4", "G4"), "V7/III")),
            Map.entry("V6/5/III", new Chord(new Note("sD4", "E4"), "B4", "sF4", new Note("A4", "G4"), "V6/5/III")),
            Map.entry("V4/3/III", new Chord("sF4", "B4", new Note("sD4", "E4"), new Note("A4", "G4"), "V4/3/III")),
            Map.entry("V2/III", new Chord(new Note("A4", "G4"), "B4", new Note("sD4", "E4"), "sF4", "V2/III")),
            Map.entry("V/IV", new Chord("C4", new Note("E4", "F4"), "G4", "C4", "V/IV")),
            Map.entry("V6/IV", new Chord(new Note("E4", "F4"), "C4", "G4", "C4", "V6/IV")),
            Map.entry("V6/4/IV", new Chord("G4", new Note("E4", "F4"), "G4", "C4", "V6/4/IV")),
            Map.entry("V7/IV", new Chord("C4", new Note("E4", "F4"), "G4", new Note("bB4", "A4"), "V7/IV")),
            Map.entry("V6/5/IV", new Chord(new Note("E4", "F4"), "C4", "G4", new Note("bB4", "A4"), "V6/5/IV")),
            Map.entry("V4/3/IV", new Chord("G4", "C4", new Note("E4", "F4"), new Note("bB4", "A4"), "V4/3/IV")),
            Map.entry("V2/IV", new Chord(new Note("bB4", "A4"), "C4", new Note("E4", "F4"), "G4", "V2/IV")),
            Map.entry("V/iv", new Chord("C4", new Note("E4", "F4"), "G4", "C4", "V/iv")),
            Map.entry("V6/iv", new Chord(new Note("E4", "F4"), "C4", "G4", "C4", "V6/iv")),
            Map.entry("V6/4/iv", new Chord("G4", new Note("E4", "F4"), "G4", "C4", "V6/4/iv")),
            Map.entry("V7/iv", new Chord("C4", new Note("E4", "F4"), "G4", new Note("bB4", "A4"), "V7/iv")),
            Map.entry("V6/5/iv", new Chord(new Note("E4", "F4"), "C4", "G4", new Note("bB4", "A4"), "V6/5/iv")),
            Map.entry("V4/3/iv", new Chord("G4", "C4", new Note("E4", "F4"), new Note("bB4", "A4"), "V4/3/iv")),
            Map.entry("V2/iv", new Chord(new Note("bB4", "A4"), "C4", new Note("E4", "F4"), "G4", "V2/iv")),
            Map.entry("V/V", new Chord("D4", new Note("sF4", "G4"), "A4", "D4", "V/V")),
            Map.entry("V6/V", new Chord(new Note("sF4", "G4"), "D4", "A4", "D4", "V6/V")),
            Map.entry("V6/4/V", new Chord("A4", new Note("sF4", "G4"), "D4", "D4", "V6/4/V")),
            Map.entry("V7/V", new Chord("D4", new Note("sF4", "G4"), "A4", new Note("C4", "B4"), "V7/V")),
            Map.entry("V6/5/V", new Chord(new Note("sF4", "G4"), "D4", "A4", new Note("C4", "B4"), "V6/5/V")),
            Map.entry("V4/3/V", new Chord("A4", "D4", new Note("sF4", "G4"), new Note("C4", "B4"), "V4/3/V")),
            Map.entry("V2/V", new Chord(new Note("C4", "B4"), "D4", new Note("sF4", "G4"), "A4", "V2/V")),
            Map.entry("V/vi", new Chord("E4", new Note("sG4", "A4"), "B4", "E4", "V/vi")),
            Map.entry("V6/vi", new Chord(new Note("sG4", "A4"), "E4", "B4", "E4", "V6/vi")),
            Map.entry("V6/4/vi", new Chord("B4", new Note("sG4", "A4"), "B4", "E4", "V6/4/vi")),
            Map.entry("V7/vi", new Chord("E4", new Note("sG4", "A4"), "B4", new Note("D4", "C4"), "V7/vi")),
            Map.entry("V6/5/vi", new Chord(new Note("sG4", "A4"), "E4", "B4", new Note("D4", "C4"), "V6/5/vi")),
            Map.entry("V4/3/vi", new Chord("B4", "E4", new Note("sG4", "A4"), new Note("D4", "C4"), "V4/3/vi")),
            Map.entry("V2/vi", new Chord(new Note("D4", "C4"), "E4", new Note("sG4", "A4"), "B4", "V2/vi")),
            Map.entry("V/VI", new Chord("E4", new Note("sG4", "A4"), "B4", "E4", "V/VI")),
            Map.entry("V6/VI", new Chord(new Note("sG4", "A4"), "E4", "B4", "E4", "V6/VI")),
            Map.entry("V6/4/VI", new Chord("B4", new Note("sG4", "A4"), "B4", "E4", "V6/4/VI")),
            Map.entry("V7/VI", new Chord("E4", new Note("sG4", "A4"), "B4", new Note("D4", "C4"), "V7/VI")),
            Map.entry("V6/5/VI", new Chord(new Note("sG4", "A4"), "E4", "B4", new Note("D4", "C4"), "V6/5/VI")),
            Map.entry("V4/3/VI", new Chord("B4", "E4", new Note("sG4", "A4"), new Note("D4", "C4"), "V4/3/VI")),
            Map.entry("V2/VI", new Chord(new Note("D4", "C4"), "E4", new Note("sG4", "A4"), "B4", "V2/VI")),
            Map.entry("N6", new Chord("F4", new Note("bD4", "C4"), "F4", new Note("bA4", "G4"), "N6")),
            Map.entry("It+6", new Chord(new Note("bA4", "G4"), "C4", "C4", new Note("sF4", "G4"), "It+6")),
            Map.entry("Fr+6", new Chord(new Note("bA4", "G4"), "C4", "D4", new Note("sF4", "G4"), "Fr+6")),
            Map.entry("Ger+6", new Chord(new Note("bA4", "G4"), new Note("C4"), new Note("bE4", "D4"), new Note("sF4", "G4"), "Ger+6")),
            Map.entry("i", new Chord("C4", "bE4", "G4", "C4", "i")),
            Map.entry("i6", new Chord("bE4", "C4", "G4", "C4", "i6")),
            Map.entry("i6/4", new Chord("G4", "bE4", "G4", "C4", "i6/4")),
            Map.entry("iio", new Chord("D4", "F4", "bA4", "D4", "iio")),
            Map.entry("iio6", new Chord("F4", "D4", "bA4", "D4", "iio6")),
            Map.entry("iio6/4", new Chord("bA4", "F4", "bA4", "D4", "iio6/4")),
            Map.entry("III+", new Chord("bE4", "G4", new Note("B4", "C4"), "bE4", "III+")),
            Map.entry("III+6", new Chord("G4", "bE4", new Note("B4", "C4"), "bE4", "III+6")),
            Map.entry("III+6/4", new Chord(new Note("B4", "C4"), "G4", "bE4", "bE4", "III+6/4")),
            Map.entry("III", new Chord("bE4", "G4", "bB4", "bE4", "III")),
            Map.entry("III6", new Chord("G4", "bE4", "bB4", "bE4", "III6")),
            Map.entry("III6/4", new Chord("bB4", "G4", "bE4", "bE4", "III6/4")),
            Map.entry("iv", new Chord("F4", "bA4", "C4", "F4", "iv")),
            Map.entry("iv6", new Chord("bA4", "F4", "C4", "F4", "iv6")),
            Map.entry("iv6/4", new Chord("C4", "bA4", "C4", "F4", "iv6/4")),
            Map.entry("v", new Chord("G4", "bB4", "D4", "G4", "v")),
            Map.entry("v6", new Chord("bB4", "G4", "D4", "G4", "v6")),
            Map.entry("v6/4", new Chord("D4", "bB4", "D4", "G4", "v6/4")),
            Map.entry("vm7", new Chord("G4", "bB4", new Note("D4"), new Note("F4", "bE4"), "vm7")),
            Map.entry("vm6/5", new Chord("bB4", "G4", new Note("D4"), new Note("F4", "bE4"), "vm6/5")),
            Map.entry("vm4/3", new Chord(new Note("D4"), "G4", "bB4", new Note("F4", "bE4"), "vm4/3")),
            Map.entry("vm2", new Chord(new Note("F4", "bE4"), "G4", "bB4", new Note("D4"), "vm2")),
            Map.entry("VI", new Chord("bA4", "C4", "bE4", "bA4", "VI")),
            Map.entry("VI6", new Chord("C4", "bA4", "bE4", "bA4", "VI6")),
            Map.entry("VI6/4", new Chord("bE4", "C4", "bA4", "bA4", "VI6/4")),
            Map.entry("VII", new Chord("bB4", "D4","F4", "D4", "VII")),
            Map.entry("VII6", new Chord("D4", "bB4", "F4", "D4", "VII6")),
            Map.entry("VII6/4", new Chord("F4", "bB4", "D4", "D4", "VII6/4"))
    );

    /**
     * Database of voice ranges
     */
    private static final Map<Voice, Note[]> voiceRange = Map.ofEntries(
            Map.entry(Voice.SOPRANO, new Note[]{new Note("C4"), new Note("A6")}),
            Map.entry(Voice.ALTO, new Note[]{new Note("G3"), new Note("D5")}),
            Map.entry(Voice.TENOR, new Note[]{new Note("C3"), new Note("F4")}),
            Map.entry(Voice.BASS, new Note[]{new Note("E2"), new Note("C4")})
    );

    /**
     * Returns the generic chord related to the figured bass
     * @param chordName the figured bass notation for the chord
     * @return the generic chord associated with the name
     * @throws MusicTheoryException if the chord is not in the database
     */
    public static Chord getChord(String chordName) {
        if (!(database.containsKey(chordName))) {
            throw new MusicTheoryException("Chord " + chordName + " is not in database.");
        } else {
            return new Chord(database.get(chordName));
        }
    }

    /**
     * Checks if a chord is in the database
     * @param chordName the name of the chord
     * @return true the input is a valid chord
     */
    public static boolean checkDatabase(String chordName) {
        return database.containsKey(chordName);
    }

    /**
     * Checks if a given key is valid for chord progressions
     * @param keyName the name of the key
     * @return true if the key is valid
     */
    public static boolean checkKey(String keyName) {
        return possibleKeys.contains(keyName);
    }

    /**
     * Transposes the entire database of chords to a new key
     * @param newKey the key to transpose to
     */
    public static void transposeDatabase(Note newKey) {
        Interval dist = curKey.getInterval(newKey);
        curKey = newKey;
        for (String name : database.keySet()) {
            database.get(name).transposeTo(dist);
        }
    }

    /**
     * Returns the highest note a voice can sing
     * @param v the voice part
     * @return highest note the voice can sing
     */
    public static Note getTopRange(Voice v) {
        return voiceRange.get(v)[1];
    }

    /**
     * Returns the lowest note a voice can sing
     * @param v the voice part
     * @return the lowest note the voice can sing
     */
    public static Note getLowRange(Voice v) {
        return voiceRange.get(v)[0];
    }

 }

