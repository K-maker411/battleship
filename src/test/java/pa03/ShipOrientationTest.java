package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.ShipOrientation;
import org.junit.jupiter.api.Test;

/**
 * tests ShipOrientation
 */
public class ShipOrientationTest {

  /**
   * tests getOpposite
   */
  @Test
  public void testGetOpposite() {
    assertEquals(ShipOrientation.HORIZONTAL, ShipOrientation.VERTICAL.getOpposite());
    assertEquals(ShipOrientation.VERTICAL, ShipOrientation.HORIZONTAL.getOpposite());
  }
}
