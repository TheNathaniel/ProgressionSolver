
Project: ProgressionSolver

Usage: ProgressionSolver.java [-t [sleepTime]]

    -t [sleepTime]  turns on animations. When solving the progression,
                    the display will pause for sleepTime ms after
                    generating each chord. Default: 200ms


When the program is run, the user will be shown a menu for using the solver:
    (1) Prints the full instructions for how to write a progression
        using figured bass.
    (2) Replaces a chord at the index the user gives with a new chord
    (3) Deletes the last chord in the progression
    (4) Clears the entire progression
    (5) The program solves the progression given
    (6) Quit out of the application

To build a progression, the user may enter chords separated by spaces or <Enter> while
on the menu. If the chord is valid, it will be added to the end of the progression and
the figured bass will be displayed to the user. The user may also enter a key to change
the key of the entire progression.

    Example Inputs:
    ----------------------------
    I ii V I
    D
    5
    Resulting Chord Progression:
    D: I ii V I
    ----------------------------
    C
    I V I
    G
    ii V/V V V7 I
    5
    Resulting Chord Progression:
    G: I V I ii V/V V V7 I
    ----------------------------
*Note that in the second one G overwrites C. Only the last key entered will be used
 when solving a chord progression. This program does not support changing keys
 mid-progression.

When the program is asked to solve the progression, it will attempt to find a chord
progression to follow the given figured bass. The completed chord progression will be
printed to console and drawn on a grand staff. If animations were enabled, the grand staff
will display each step the program takes to find the final progression.

If the figured bass forces a rule to be broken making a valid progression impossible, the
program will print a progression up to the problem chord and indicate which chord makes
the progression impossible. The most common cause of a chord progression being impossible
is improper resolution in the figured bass. (Ex: not allowing a leading tone to resolve
to the tonic V-ii).

