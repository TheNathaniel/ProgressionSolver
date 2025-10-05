import DisplayGUI.ProgressionGUI;
import Music.Key;
import Music.Progression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Chord progressions runner class
 */
public class ProgressionSolver {

    /**
     *
     */
    public static final String[] sampleInput = new String[]{"C4", "I", "V6/5", "I", "V6/5/ii", "ii", "I", "It+6", "Ger+6", "V", "V7", "vi"};

    /**
     * Whether or not to display each chord the backtracker explores
     */
    private static boolean animate;

    /**
     * The default length the animation sleeps before showing the next chord
     */
    public static final int DEFAULT_SLEEPTIME = 200;

    /**
     * How long the user wants the animation to pause
     */
    private static int sleepTime;

    /**
     * The progression GUI
     */
    private static ProgressionGUI pgui;

    /**
     * The furthest progression when solving the progression
     * Used for displaying the progression if there is an error
     */
    private static Progression furthestProgression = null;

    /**
     * The number of chord progressions generated
     */
    private static int totalProgressionsGenerated;

    /**
     * The number of chord progressions visited
     */
    private static int totalProgressionsVisisted;

    /**
     * Backtracker solve method
     * @param begin starting progression
     * @return a solved progression if it is solvable null otherwise
     */
    public static Progression solve(Progression begin) {
        totalProgressionsVisisted++;
        if (begin.isGoal()) {
            return begin;
        } else {
            for (Progression p : begin.getSuccessors()) {
                if (animate) {
                    pgui.setProgression(p);
                    pgui.updateGUI();
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
                totalProgressionsGenerated++;
                if (p.isValid()) {
                    if (p.depth() > furthestProgression.depth()) {
                        furthestProgression = p;
                    }
                    Progression end = solve(p);
                    if (end != null) {
                        return end;
                    }
                }
            }
        }
        return null;
    }

    private static void printUsage() {
        System.err.println("Usage: ProgressionSolver.java [-t [sleep time]]");
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].equals("-t")) {
                animate = true;
                sleepTime = DEFAULT_SLEEPTIME;
            } else {
                System.err.println(args[0] + " is not a valid input.");
                printUsage();
                System.exit(1);
            }
        } else if (args.length == 2) {
            if (args[0].equals("-t")) {
                if (isNumber(args[1])) {
                    int sleep = Integer.parseInt(args[1]);
                    if (sleep > 0) {
                        animate = true;
                        sleepTime = sleep;
                    } else {
                        System.err.println("Sleep time must be a positive non-zero integer.");
                        System.exit(1);
                    }
                } else {
                    System.err.println(args[1] + " is not a positive non-zero integer.");
                    System.exit(1);
                }
            } else {
                System.err.println(args[0] + " is not a valid input.");
                printUsage();
                System.exit(1);
            }
        } else if (args.length > 2){
            printUsage();
            System.exit(1);
        } else {
            animate = false;
        }
        pgui = new ProgressionGUI();
        runMenu();
    }

    private static void solveProgression(String key, ArrayList<String> chords) {
        String[] figuredBass = new String[chords.size() + 1];
        figuredBass[0] = key + "4";
        for (int i = 1; i < figuredBass.length; i++) {
            figuredBass[i] = chords.get(i - 1);
        }
        Progression p = new Progression(figuredBass);
        furthestProgression = p;
        totalProgressionsVisisted = 0;
        totalProgressionsGenerated = 0;
        Progression solved = solve(p);
        if (solved == null) {
            pgui.setProgression(furthestProgression);
            pgui.updateGUI();
            System.out.println(furthestProgression);
            int furthestChord = p.getFurthestChord();
            System.out.println("Problem with chord #" + furthestChord + ": " + p.getChordName(furthestChord - 1));
            System.out.println(p.getChordName(furthestChord - 2) +  " cannot resolve to " + p.getChordName(furthestChord - 1));
        } else {
            pgui.setProgression(solved);
            pgui.updateGUI();
            System.out.println(solved);
        }
        System.out.println("\nTotal Progressions Generated: " + totalProgressionsGenerated);
        System.out.println("Total Progressions Visisted: " + totalProgressionsVisisted);
        System.out.println("\nPress enter to return to the main menu.");
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    /**
     * Checks if the given user input is a proper chord
     * @param input the user's input
     * @return true if the input is a valid chord in the database
     */
    public static boolean isChord(String input) {
        String[] inputChords = input.split(" ");
        for (String chord : inputChords) {
            if (!Key.checkDatabase(chord)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given user input is a key change
     * @param input the user's input
     * @return true if the input is a valid key in the database
     */
    public static boolean isKeyChange(String input) {
        return Key.checkKey(input);
    }

    /**
     * Checks if the given user input is a number
     * @param input the user's input
     * @return true if the input is a number
     */
    public static boolean isNumber(String input) {
        try {
            int intInput = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Runs the menu for the program
     *
     * Responsible for displaying the menu, accepting user input
     * and calling the appropriate methods on user command
     */
    private static void runMenu() {
        int intInput = 0;
        int quitKey = 6;
        String input = "";
        Scanner scanner = new Scanner(System.in);
        String key = "C";
        ArrayList<String> chords = new ArrayList<>();
        while (intInput != quitKey) {
            printMainMenu();
            printFiguredBass(key, chords);
            System.out.print("> ");
            input = scanner.nextLine();
            if (isChord(input)) {
                chords.addAll(Arrays.asList(input.split(" ")));
            } else if (isKeyChange(input)) {
                key = input;
            } else if (isNumber(input)) {
                intInput = Integer.parseInt(input);
                switch (intInput) {
                    case 1:
                        printInstructions();
                        break;
                    case 2:
                        replaceChord(chords);
                        break;
                    case 3:
                        if (!chords.isEmpty()) {
                            chords.removeLast();
                        }
                        break;
                    case 4:
                        chords.clear();
                        break;
                    case 5:
                        solveProgression(key, chords);
                        break;
                    case 6:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Menu Option {" + input + "}");
                        break;
                }
            } else {
                System.out.println("Invalid Input: {" + input + "}");
            }
        }
    }


    private static void printMainMenu() {
        System.out.println("================MENU================");
        System.out.println("1) Program Instructions and Notation");
        System.out.println("2) Replace Chord at Index");
        System.out.println("3) Delete Last Chord");
        System.out.println("4) Clear Progression");
        System.out.println("5) Solve");
        System.out.println("6) Quit");
        System.out.println("====================================");
    }

    private static void printInstructions() {
        File file = new File("Instructions.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Instructions file not found.");
        }
        System.out.println();
    }

    private static void replaceChord(ArrayList<String> chords) {
        System.out.print("Chord Number: ");
        Scanner scanner = new Scanner(System.in);
        String indexInput = scanner.nextLine();
        if (isNumber(indexInput)) {
            int chordIndex = Integer.parseInt(indexInput) - 1;
            System.out.println("Current Chord: " + chords.get(chordIndex));
            System.out.print("New Chord: ");
            String chordInput = scanner.nextLine();
            if (isChord(chordInput)) {
                chords.set(chordIndex, chordInput);
            }
        }

    }

    /**
     * Prints the current figured bass of the
     * @param key
     * @param chords
     */
    private static void printFiguredBass(String key, ArrayList<String> chords) {
        System.out.print(key + ":  ");
        for (String chordName : chords) {
            System.out.print(chordName + "  ");
        }
        System.out.println();
    }
}
