package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.CoreView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests CoreView
 */
public class CoreViewTest {
  Appendable output;
  CoreView view;

  /**
   * sets up variables
   */
  @BeforeEach
  public void setUp() {
    output = new StringBuilder();
    view = new CoreView(output);
  }

  /**
   * tests writeToOutput
   */
  @Test
  public void testWriteToOutput() {
    view.writeToOutput("Sample Output String");
    assertEquals("Sample Output String", output.toString());
  }
}
