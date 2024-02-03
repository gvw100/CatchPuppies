package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class PuppyCallerTest {
    Puppy a;
    Puppy john;
    Puppy bob;
    Puppy alice;
    Puppy robert;
    Puppy george;
    Puppy james;
    Puppy jack;
    ArrayList<Puppy> puppies;
    ArrayList<Puppy> onePuppy;
    PuppyCaller pc1;
    PuppyCaller pc2;

    @BeforeEach
    void runBefore() {
        a = new Puppy("a");
        john = new Puppy("John");
        bob = new Puppy("Bob");
        alice = new Puppy("Alice");
        george = new Puppy("George");
        robert = new Puppy("Robert");
        james = new Puppy("James");
        jack = new Puppy("Jack");

        puppies = new ArrayList<Puppy>();
        puppies.add(a);
        puppies.add(john);
        puppies.add(bob);
        puppies.add(alice);
        puppies.add(george);
        puppies.add(robert);
        puppies.add(james);
        puppies.add(jack);

        // one element list
        onePuppy = new ArrayList<Puppy>();
        onePuppy.add(john);

        // currentPuppy is null
        pc1 = new PuppyCaller();
        
        // currentPuppy is john
        pc2 = new PuppyCaller();
        pc2.setCurrentPuppy(john);
    }

    @Test
    void typeNameForCurrentPuppyTest() {
        // "John" does not start with 'B'
        assertFalse(pc2.typeNameForCurrentPuppy('B'));
        // check that index did not change
        assertEquals("", john.getTypedPortion());
        // "John" not completely typed
        assertNotEquals(null, pc2.getCurrentPuppy());
        
        // "John" starts with 'J'
        assertTrue(pc2.typeNameForCurrentPuppy('J'));
        // index increased by 1
        assertEquals("J", john.getTypedPortion());
        assertNotEquals(null, pc2.getCurrentPuppy());

        // "John" second letter is 'o'
        assertTrue(pc2.typeNameForCurrentPuppy('o'));
        assertEquals("Jo", john.getTypedPortion());
        assertNotEquals(null, pc2.getCurrentPuppy());

        // "John" third letter is not 'a'
        assertFalse(pc2.typeNameForCurrentPuppy('a'));
        assertEquals("Jo", john.getTypedPortion());
        assertNotEquals(null, pc2.getCurrentPuppy());

        // "John" third letter is 'h'
        assertTrue(pc2.typeNameForCurrentPuppy('h'));
        assertEquals("Joh", john.getTypedPortion());
        assertNotEquals(null, pc2.getCurrentPuppy());

        // "John" fourth letter is 'n'
        assertTrue(pc2.typeNameForCurrentPuppy('n'));
        assertEquals("John", john.getTypedPortion());
        // current puppy is null after being called
        assertEquals(null, pc2.getCurrentPuppy());
    }

    @Test
    void findPuppyTest() {
        // none of the puppies have a name starting with 'Z'
        assertEquals(null, pc1.findPuppy(puppies, 'Z'));
        // find a puppy "John" at beginning of list
        assertEquals(john, pc1.findPuppy(puppies, 'J'));
        // find a puppy "Alice" in middle of list
        assertEquals(alice, pc1.findPuppy(puppies, 'A'));
        // find a puppy "George" with a nonzero index of 3
        for (int i = 0; i < 3; i++) {
            george.typeName();
        }
        assertEquals(george, pc1.findPuppy(puppies, 'r'));
        // find a puppy "John" in a one element list
        assertEquals(john, pc1.findPuppy(onePuppy, 'J'));
    }

    @Test
    void handleInputTest() {
        // none of the puppies have 'z' in their names
        assertFalse(pc1.handleInput(puppies, 'z')); 
        // testing for puppy with one letter name
        assertTrue(pc1.handleInput(puppies, 'a'));
        assertEquals("a", a.getTypedPortion());
        assertEquals(null, pc1.getCurrentPuppy());
        // requires clause does not allow puppy that has been called
        puppies.remove(0);
        // first puppy starting with 'J' john is selected
        assertTrue(pc1.handleInput(puppies, 'J'));  
        assertEquals(john, pc1.getCurrentPuppy()); 
        assertEquals("J", john.getTypedPortion());
        // 'h' is not the next letter in "John"
        assertFalse(pc1.handleInput(puppies, 'h'));
        assertEquals("J", john.getTypedPortion());
        // 'o', 'h' are the next letters in "John"
        assertTrue(pc1.handleInput(puppies, 'o'));
        assertEquals("Jo", john.getTypedPortion());
        assertTrue(pc1.handleInput(puppies, 'h'));
        assertEquals("Joh", john.getTypedPortion());
        // typing not yet completed
        assertNotEquals(null, pc1.getCurrentPuppy());
        // 'n' is the final letter in "John"
        assertTrue(pc1.handleInput(puppies, 'n'));
        assertEquals("John", john.getTypedPortion());
        // john has been called
        assertEquals(null, pc1.getCurrentPuppy());
    }

    @Test
    void testOneElementList() {
        // single element list next character is 'J'
        assertFalse(pc1.handleInput(onePuppy, 'A'));
        assertTrue(pc1.handleInput(onePuppy, 'J'));
        // john is selected
        assertEquals(john, pc1.getCurrentPuppy());
        // index increased by 1
        assertEquals("J", john.getTypedPortion());
    }
}