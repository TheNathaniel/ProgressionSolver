package Music;

import java.util.Map;

/**
 * Represents an interval between two notes
 * Used for tracking how much notes move and distance between them
 */
public class Interval {

    /**
     * Distance alphabetically to the next note
     * Negative if the interval is downward
     */
    private int noteDist;

    /**
     * Distance halfstepwise to the next note
     * Negative if the interval is downward
     */
    private int halfstepDist;

    /**
     * Dictionary of music terminology mapped to the indicated interval
     * For purposes of easier user testing
     */
    private static final Map<String, Interval> notationInterval = Map.ofEntries(
            Map.entry("DU", new Interval(0, -1)),
            Map.entry("PU", new Interval(0, 0)),
            Map.entry("AU", new Interval(0, 1)),
            Map.entry("D2", new Interval(1, 0)),
            Map.entry("m2", new Interval(1, 1)),
            Map.entry("M2", new Interval(1, 2)),
            Map.entry("A2", new Interval(1, 3)),
            Map.entry("D3", new Interval(2, 2)),
            Map.entry("m3", new Interval(2, 3)),
            Map.entry("M3", new Interval(2, 4)),
            Map.entry("A3", new Interval(2, 5)),
            Map.entry("D4", new Interval(3, 4)),
            Map.entry("P4", new Interval(3, 5)),
            Map.entry("A4", new Interval(3, 6)),
            Map.entry("D5", new Interval(4, 6)),
            Map.entry("P5", new Interval(4, 7)),
            Map.entry("A5", new Interval(4, 8)),
            Map.entry("D6", new Interval(5, 7)),
            Map.entry("m6", new Interval(5, 8)),
            Map.entry("M6", new Interval(5, 9)),
            Map.entry("A6", new Interval(5, 10)),
            Map.entry("D7", new Interval(6, 9)),
            Map.entry("m7", new Interval(6, 10)),
            Map.entry("M7", new Interval(6, 11)),
            Map.entry("A7", new Interval(6, 12)),
            Map.entry("D8", new Interval(7, 11)),
            Map.entry("P8", new Interval(7, 12)),
            Map.entry("A8", new Interval(7, 13)),
            Map.entry("-DU", new Interval(0, 1)),
            Map.entry("-PU", new Interval(0, 0)),
            Map.entry("-AU", new Interval(0, -1)),
            Map.entry("-D2", new Interval(-1, 0)),
            Map.entry("-m2", new Interval(-1, -1)),
            Map.entry("-M2", new Interval(-1, -2)),
            Map.entry("-A2", new Interval(-1, -3)),
            Map.entry("-D3", new Interval(-2, -2)),
            Map.entry("-m3", new Interval(-2, -3)),
            Map.entry("-M3", new Interval(-2, -4)),
            Map.entry("-A3", new Interval(-2, -5)),
            Map.entry("-D4", new Interval(-3, -4)),
            Map.entry("-P4", new Interval(-3, -5)),
            Map.entry("-A4", new Interval(-3, -6)),
            Map.entry("-D5", new Interval(-4, -6)),
            Map.entry("-P5", new Interval(-4, -7)),
            Map.entry("-A5", new Interval(-4, -8)),
            Map.entry("-D6", new Interval(-5, -7)),
            Map.entry("-m6", new Interval(-5, -8)),
            Map.entry("-M6", new Interval(-5, -9)),
            Map.entry("-A6", new Interval(-5, -10)),
            Map.entry("-D7", new Interval(-6, -9)),
            Map.entry("-m7", new Interval(-6, -10)),
            Map.entry("-M7", new Interval(-6, -11)),
            Map.entry("-A7", new Interval(-6, -12)),
            Map.entry("-D8", new Interval(-7, -11)),
            Map.entry("-P8", new Interval(-7, -12)),
            Map.entry("-A8", new Interval(-7, -13))
    );

    /**
     * Constructor for the Interval
     * Creates an interval with the values passed in
     * @param noteDist distance to the next note alphabetically
     * @param halfstepDist distance in halfsteps
     */
    public Interval(int noteDist, int halfstepDist) {
        this.noteDist = noteDist;
        this.halfstepDist = halfstepDist;
    }

    /**
     * Creates a new instance of an interval copying from another interval
     * @param i interval to copy
     */
    public Interval(Interval i) {
        this.noteDist = i.noteDist;
        this.halfstepDist = i.halfstepDist;
    }

    /**
     * Creates an interval based on the music notation for the interval
     * Throws an error if an invalid interval is passed in
     * @param interval string notation of interval
     */
    public Interval(String interval) {
        if (!notationInterval.containsKey(interval)) {
            throw new MusicTheoryException("No such interval: " + interval);
        }
        Interval i = notationInterval.get(interval);
        this.noteDist = i.noteDist;
        this.halfstepDist = i.halfstepDist;
    }

    /**
     * @return note distance
     */
    public int getNoteDist() {
        return noteDist;
    }

    /**
     * @return halfstep distance
     */
    public int getHalfstepDist() {
        return halfstepDist;
    }

    /**
     * Returns true if the Intervals are the same
     * @param other other interval
     * @return true if the note distance and halfstep distance match
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Interval) {
            Interval i = (Interval) other;
            return this.noteDist == i.noteDist && this.halfstepDist == i.halfstepDist;
        }
        return false;
    }

    /**
     * Returns true if the interval is perfect
     * @return wheter or not the interval is perfect
     */
    public boolean isPerfect() {
        return halfstepDist == 0 ||
                halfstepDist == 5 ||
                halfstepDist == 7 ||
                halfstepDist == 12 ||
                halfstepDist == -5 ||
                halfstepDist == -7 ||
                halfstepDist == -12;
    }

    /**
     * True if the note moved
     * @return whether or not the note has changed
     */
    public boolean moved() {
        return halfstepDist != 0;
    }

    /**
     * Returns string representation of interval
     * @return Interval{Note=NOTEDIST, HS=HALFSTEPDIST}
     */
    @Override
    public String toString() {
        return "Interval{Note=" + noteDist + ", HS=" + halfstepDist + "}";
    }
}
