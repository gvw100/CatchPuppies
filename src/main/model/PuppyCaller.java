package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles input from the player and calls puppies based on their input.
 * For your convenience, the description of the problem has been provided below.
 *
 * <br> <br>
 * In the game, each puppy has a name and are running away from their owner.
 * In order to stop the puppies from running away, the owner has to 'call' them by typing their name.
 * <br> <br>
 * The game runs under the following sequence: <br>
 * 1. The player starts calling a puppy by typing the first letter of its name. This 'selects' the puppy. <br>
 * 2. The player must type the rest of its name to call it. Any incorrect input (e.g. typing 'a' when the next letter is
 *    'c' is ignored) <br>
 * 3. Once the player has typed the last character, the puppy is now 'called' and will stop running away. This also
 *      unselects the puppy, returning back to step 1.
 * <br> <br>
 * This logic has been broken down into a few methods in this class: <br>
 * - Getting and setting the current selected puppy (useful for tests!) <br>
 * - Typing the name for the current puppy - corresponding to step 2 and 3 above. <br>
 * - Finding a puppy whose next character starts with an input character c - helpful for step 1. <br>
 * <br> <br>
 * These pieces are finally put together in the handleInput method, where you can use your work earlier
 * to handle the full logic of the game! As you write this method, think about where and how the other
 * helper methods might be useful.
 */
public class PuppyCaller {
    private ArrayList<Puppy> selectedPuppies = new ArrayList<>();

    // EFFECTS: creates an instance of this class with no current puppy selected.
    public PuppyCaller() {
    }

    // EFFECTS: returns the current selected puppy, null if none is selected
    public ArrayList<Puppy> getSelectedPuppies() {
        return selectedPuppies;
    }

    // REQUIRES: !currentPuppy.hasBeenCalled()
    // MODIFIES: this
    // EFFECTS: sets the current puppy to the provided puppy
    public void setSelectedPuppies(ArrayList<Puppy> selectedPuppies) {
        this.selectedPuppies = selectedPuppies;
    }

    // REQUIRES: getCurrentPuppies() != null and all puppies have not been called
    // MODIFIES: this
    // EFFECTS: Types name for each currentPuppy in list, returns whether progress was made on any of the puppies.
    public boolean typeNameForCurrentPuppies(char c) {
        boolean isProgress = false;
        for (int i = 0; i < selectedPuppies.size(); i++) {
            Puppy puppy = selectedPuppies.get(i);
            if (c != puppy.getNextChar()) {
                puppy.resetProgress();
                selectedPuppies.remove(puppy);
            }
        }
        for (int i = 0; i < selectedPuppies.size(); i++) {
            isProgress = true;
            Puppy selectedPuppy = selectedPuppies.get(i);
            selectedPuppy.typeName();
            if (selectedPuppy.hasBeenCalled()) {
                for (Puppy puppy : selectedPuppies) {
                    if (!puppy.hasBeenCalled()) {
                        puppy.resetProgress();
                    }
                }
                selectedPuppies.clear();
            }
        }
        return isProgress;
    }

    // REQUIRES: currentPuppies.size() > 0
    // EFFECTS: returns any puppy in the list whose next character is c
    //          returns null if none was found
    public ArrayList<Puppy> findPuppies(List<Puppy> currentPuppies, char c) {
        boolean isFound = false;
        ArrayList<Puppy> puppies = new ArrayList<>();
        for (Puppy puppy : currentPuppies) {
            if (c == puppy.getNextChar()) {
                puppies.add(puppy);
                isFound = true;
            }
        }
        if (isFound) {
            return puppies;
        } else {
            return null;
        }
    }

    // REQUIRES: currentPuppies.size() > 0 AND for all puppies in the list, puppy has not been called
    // MODIFIES: this
    // EFFECTS: Handles the given input for the player, where:
    //          If there is a current puppy selected:
    //              type its name according to typeNameForCurrentPuppy
    //              return whether progress was made on calling the puppy
    //          If there is no current puppy:
    //              Find the puppy who matches the input character c
    //              Set this puppy as the current and if one was found, call it
    //              Return whether progress was made on calling the puppy
    public boolean handleInput(List<Puppy> currentPuppies, char c) {
        if (selectedPuppies.size() != 0) {
            return typeNameForCurrentPuppies(c);
        } else {
            ArrayList<Puppy> newPuppies = findPuppies(currentPuppies, c);
            if (newPuppies != null) {
                selectedPuppies.addAll(newPuppies);
                return typeNameForCurrentPuppies(c);
            } else {
                return false;
            }
        }
    }
}
