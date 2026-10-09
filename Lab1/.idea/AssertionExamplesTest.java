import java.time.Duration;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssertionExamplesTest {
    // Integer.clone() in the original is inaccessible. This small class
    // exposes Object.clone() but deliberately does not implement Cloneable.
    static class UnclonableInteger {
        private int value;
        UnclonableInteger(int value) { this.value = value; }
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Test
    public void testCloneNotSupportedException() throws CloneNotSupportedException {
        assertThrows(CloneNotSupportedException.class, () -> {
            UnclonableInteger I = new UnclonableInteger(3151);
            UnclonableInteger J = (UnclonableInteger) I.clone();
        });
    }

    @Test
    public void testEficientSolve() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            BestFirst s = new BestFirst();
            Iterator<BestFirst.State> it = s.solve(new Board("023145678"),
                new Board("123405678"));
        });
    }
}
