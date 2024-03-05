package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.writer.Writer;
import cs3500.pa03.writer.WriterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pa03.mocks.MockAppendable;

/**
 * Tests for WriterImpl
 */
public class WriterImplTest {
  private Appendable appendable;
  private Writer writer;

  // this is a constant which will not be changed (static and written in UPPER_SNAKE_CASE)
  private static final String VALUE = "input";

  /**
   * Sets up appendable and writer
   */
  @BeforeEach
  public void setUp() {
    this.appendable = new StringBuilder();
    this.writer = new WriterImpl(this.appendable);
  }

  /**
   * tests success of writing to appendable
   */
  @Test
  public void testSuccess() {
    // check empty StringBuilder
    assertEquals(this.appendable.toString(), "");

    // write to it
    this.writer.write(VALUE);

    // check only that value appears in the StringBuilder
    assertEquals(this.appendable.toString(), VALUE);
  }

  /**
   * tests failure writing to appendable
   */
  @Test
  public void testFailure() {
    this.writer = new WriterImpl(new MockAppendable());
    Exception exc = assertThrows(RuntimeException.class,
        () -> this.writer.write(VALUE), "Mock throwing an error");
    assertEquals("java.io.IOException: Mock throwing an error", exc.getMessage());
  }
}
