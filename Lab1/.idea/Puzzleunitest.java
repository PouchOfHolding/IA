import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PuzzleUnitTests {

    @Test
    public void testConstructor() {
        Board b = new Board("023145678");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println(" 23");
        pw.println("145");
        pw.println("678");
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }

    @Test
    public void testConstructor2() {
        Board b = new Board("123485670");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        pw.println("123");
        pw.println("485");
        pw.println("67 ");
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }

    @Test
    public void testConstructorException() {
        assertThrows(IllegalStateException.class, () -> {
            new Board("123");
        });
    }

    @Test
    public void testEquals() {
        Board b1 = new Board("023145678");
        Board b2 = new Board("023145678");
        Board b3 = new Board("123485670");

        assertTrue(b1.equals(b2));
        assertFalse(b1.equals(b3));
        assertFalse(b1.equals(null));
    }

    @Test
    public void testHashCode() {
        Board b1 = new Board("023145678");
        Board b2 = new Board("023145678");

        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    public void testIsGoal() {
        Board b1 = new Board("023145678");
        Board goal = new Board("023145678");
        Board notGoal = new Board("123485670");

        assertTrue(b1.isGoal(goal));
        assertFalse(b1.isGoal(notGoal));
    }

    @Test
    public void testGetK() {
        Board b = new Board("023145678");
        assertEquals(1.0, b.getK());
    }

    @Test
    public void testChildren() {
        // '0' is at the top-left (index 0,0). It can only move right or down.
        Board b1 = new Board("023145678");
        List<Ilayout> children1 = b1.children();
        assertEquals(2, children1.size());

        // '0' is in the middle (index 1,1). It can move up, down, left, or right.
        Board b2 = new Board("123405678");
        List<Ilayout> children2 = b2.children();
        assertEquals(4, children2.size());
    }

    @Test
    public void testSolvePathAndCost() {
        BestFirst s = new BestFirst();
        Board initial = new Board("023145678");
        Board goal = new Board("123405678");

        Iterator<BestFirst.State> it = s.solve(initial, goal);
        assertNotNull(it);

        BestFirst.State lastState = null;
        while (it.hasNext()) {
            lastState = it.next();
        }

        assertNotNull(lastState);
        assertEquals(2.0, lastState.getG());
    }

    @Test
    public void testEfficientSolve() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            BestFirst s = new BestFirst();
            Iterator<BestFirst.State> it = s.solve(new Board("023145678"), new Board("123405678"));
            assertNotNull(it);
        });
    }
}