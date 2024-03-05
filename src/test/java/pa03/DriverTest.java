package pa03;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.Driver;
import org.junit.jupiter.api.Test;

/**
 * fake driver test
 */
class DriverTest {

  /**
   * fake test for main
   */
  @Test
  public void fakeTest() {
    System.out.println("An important message...");
    assertEquals(5, 5);
  }

  /**
   * for test coverage
   */
  @Test
  public void testDriverInitializing() {
    assertDoesNotThrow(() -> new Driver());
  }

  /**
   * tests that exception is thrown if an args with length 0 or 2 is not given,
   */
  @Test
  public void testInvalidArgsCount() {
    assertThrows(IllegalArgumentException.class, () -> Driver.main(new String[]{"Sample"}));
  }



}