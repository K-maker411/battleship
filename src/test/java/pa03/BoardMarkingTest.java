package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.BoardMarking;
import org.junit.jupiter.api.Test;

/**
 * tests BoardMarking
 */
public class BoardMarkingTest {
  /**
   * tests getMarkingAsString
   */
  @Test
  public void testGetMarkingAsString() {
    assertEquals("H", BoardMarking.HIT.getMarkingAsString());
    assertEquals("M", BoardMarking.MISS.getMarkingAsString());
    assertEquals("0", BoardMarking.NOT_YET_HIT.getMarkingAsString());
  }
}
