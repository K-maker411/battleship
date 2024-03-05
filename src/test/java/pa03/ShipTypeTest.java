package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.ShipType;
import org.junit.jupiter.api.Test;

/**
 * tests ShipType
 */
public class ShipTypeTest {

  /**
   * test getSize
   */
  @Test
  public void testGetSize() {
    assertEquals(6, ShipType.CARRIER.getSize());
    assertEquals(5, ShipType.BATTLESHIP.getSize());
    assertEquals(4, ShipType.DESTROYER.getSize());
    assertEquals(3, ShipType.SUBMARINE.getSize());
  }

  /**
   * tests toString
   */
  @Test
  public void testToString() {
    assertEquals("C", ShipType.CARRIER.toString());
    assertEquals("B", ShipType.BATTLESHIP.toString());
    assertEquals("D", ShipType.DESTROYER.toString());
    assertEquals("S", ShipType.SUBMARINE.toString());
  }
}
