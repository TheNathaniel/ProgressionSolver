import Music.Note;

public class Tester {
    public static void main(String[] args) {
        /*

        Key Options:
            To change keys of the entire chord progression,
            enter any of the valid keys below:

             A,  B,  C,  D,  E,  F,  G
            bA, bB, bC, bD, bE, bF, bG
            sA, sB, sC, sD, sE, sF, sG

            b - Flat
            s - Sharp
        ________________________________________________

        Root Position Figured Bass Options:




        Root Position Figured Bass Options:
            Major - I, ii,  III, IV, V, vi, viio
            Minor - i, iio, iii, iv, V, VI, VII
                            III+,    v

        Inversion:
            1st Inversion - __6
            2nd Inversion - __6/4
        7th Chord Inversion:
            1st Inversion - __6/5
            2nd Inversion - __4/3
            3rd Inversion - __2

        Secondary Dominant Chords:
            Notation - V/__

            Examples: V/IV, V6/5/V, V6/ii, V2/vi

        Extra Chromatic Chords:
            Augmented Sixths:
                French -  Fr+6
                Italian - It+6
                German -  Ger+6
            Neapolitan Sixth: N6


     */
        /*Note n1 = new Note(N.A, 4, Accidental.NATURAL);
        Note n2 = new Note(N.C, 4, Accidental.SHARP);
        Note n3 = new Note(N.E, 4, Accidental.NATURAL);
        Note n4 = new Note(N.G, 4, Accidental.NATURAL);
        System.out.println(n1.toInt());
        System.out.println(n2.toInt());
        System.out.println(n1.compareTo(n2));
        System.out.println("♭");
        System.out.println("\u266F");
        System.out.println(n1 + " " + n2 + " " + n3);
        System.out.println(n1.getInterval(n3));
        System.out.println("Old: " + n1.transposeTo(new Interval("M6")) + " new: " + n1);
        System.out.println("Old: " + n1.transposeTo(new Interval("-M6")) + " new: " + n1);

        Chord c = new Chord(n1, n2, n3, n4, "V7");
        System.out.println(c.transposeTo(new Interval("P5")));
        System.out.println(c);
        n1 = new Note(N.E, 4, Accidental.NATURAL);
        System.out.println(n1.transpose(new Interval("P8")));

        System.out.println(n1.getInterval(new Note(N.A, 5, Accidental.NATURAL)));

        Chord chord1 = new Chord(new Note(N.D, 4, Accidental.NATURAL),
                new Note("A5"),
                new Note(N.D, 5, Accidental.NATURAL),
                new Note(N.G, 5, Accidental.NATURAL), "I");
        Chord chord2 = new Chord(new Note(N.D, 4, Accidental.NATURAL),
                new Note(N.B, 5, Accidental.NATURAL),
                new Note(N.D, 5, Accidental.NATURAL),
                new Note(N.G, 5, Accidental.NATURAL), "I");
        Progression p = new Progression(new String[]{"I"});
        p.addChord(new Chord(c));
        p.addChord(chord1);
        p.addChord(chord2);
        p.addChord(c.transposeTo(new Interval("M3")));
        System.out.println(p);
        Note inflectionTest = new Note("F4", "E4");
        Note inflector = new Note("E4");
        System.out.println(inflectionTest.isProperInflection(inflector));
        Progression p2 = new Progression(new String[]{"I"});
        Chord CM = new Chord("C3", "G3", "E4", "C5", "I");
        Chord next = Key.getChord("V7");
        p2.addChord(CM);
        ArrayList<Chord> test = p2.getIterations(next);
        System.out.println();
        for (Chord c25 : test) {
            System.out.println(c25);
        }*/
        Note g = new Note("G3");
        System.out.println(g.getInterval(new Note("F2")));

    }
}
