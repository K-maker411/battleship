package pa03;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.Coord;
import org.junit.jupiter.api.Test;

/**
 * tests Coord
 */
public class CoordTest {

  /**
   * tests equals
   */
  @Test
  public void testEquals() {
    assertEquals(new Coord(1, 0), new Coord(1, 0));
    assertNotEquals(new Coord(1, 0), new Coord(1, 1));
    assertNotEquals(new Coord(1, 1), "String");
  }

  /**
   * tests hashCode
   */
  @Test
  public void testHashCode() {
    assertEquals(0, new Coord(0, 0).hashCode());
    assertEquals(37, new Coord(1, 0).hashCode());
    assertEquals(29, new Coord(0, 1).hashCode());
    assertEquals(66, new Coord(1, 1).hashCode());
  }

  /**
   * tests getX
   */
  @Test
  public void testGetX() {
    assertEquals(1, new Coord(1, 0).getCoordX());
  }

  /**
   * tests getY
   */
  @Test
  public void testGetY() {
    assertEquals(0, new Coord(1, 0).getCoordY());
  }

  /**
   * (indirectly) tests validateCoordPart
   */
  @Test
  public void testValidateCoordPart() {
    assertDoesNotThrow(() -> new Coord(1, 1));
  }

  /**
   * (indirectly) tests invalid input for validateCoordPart
   */
  @Test
  public void testInvalidValidateCoordPart() {
    assertThrows(IllegalArgumentException.class, () -> new Coord(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> new Coord(1, -1));
  }
}
