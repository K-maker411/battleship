package pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.Coord;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipOrientation;
import cs3500.pa03.ShipType;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests Ship
 */
public class ShipTest {
  Ship ship;
  Ship ship2;

  /**
   * sets up ships
   */
  @BeforeEach
  public void setUp() {
    ship = new Ship(ShipType.CARRIER, ShipOrientation.HORIZONTAL, new Coord(0, 0));
    ship2 = new Ship(ShipType.SUBMARINE, ShipOrientation.VERTICAL, new Coord(1, 1));
  }

  /**
   * tests getCurrentlyOccupiedSpace
   */
  @Test
  public void testGetCurrentlyOccupiedSpaces() {
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(0, 0));
    expected.add(new Coord(1, 0));
    expected.add(new Coord(2, 0));
    expected.add(new Coord(3, 0));
    expected.add(new Coord(4, 0));
    expected.add(new Coord(5, 0));

    assertArrayEquals(expected.toArray(), ship.getCurrentlyOccupiedSpaces().toArray());

    expected.clear();

    expected.add(new Coord(1, 1));
    expected.add(new Coord(1, 2));
    expected.add(new Coord(1, 3));

    assertArrayEquals(expected.toArray(), ship2.getCurrentlyOccupiedSpaces().toArray());
  }

  /**
   * tests removeOccupiedSpace
   */
  @Test
  public void testRemoveOccupiedSpace() {
    assertEquals(0, ship.getSunkSpaces().size());
    ship.removeOccupiedSpace(new Coord(1, 0));
    assertEquals(5, ship.getCurrentlyOccupiedSpaces().size());
    assertEquals(ship.getSunkSpaces().get(0), new Coord(1, 0));
  }

  /**
   * tests getType
   */
  @Test
  public void testGetType() {
    assertEquals(ShipType.CARRIER, ship.getType());
    assertEquals(ShipType.SUBMARINE, ship2.getType());
  }

  /**
   * tests isSunk
   */
  @Test
  public void testIsSunk() {
    assertFalse(ship.isSunk());
    ship.removeOccupiedSpace(new Coord(0, 0));
    ship.removeOccupiedSpace(new Coord(1, 0));
    ship.removeOccupiedSpace(new Coord(2, 0));
    ship.removeOccupiedSpace(new Coord(3, 0));
    ship.removeOccupiedSpace(new Coord(4, 0));
    ship.removeOccupiedSpace(new Coord(5, 0));
    assertTrue(ship.isSunk());
  }

  /**
   * tests getSunkSpaces
   */
  @Test
  public void testGetSunkSpaces() {
    assertEquals(new ArrayList<>(), ship.getSunkSpaces());

    ship.removeOccupiedSpace(new Coord(0, 0));
    ship.removeOccupiedSpace(new Coord(1, 0));
    ship.removeOccupiedSpace(new Coord(2, 0));

    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(0, 0));
    expected.add(new Coord(1, 0));
    expected.add(new Coord(2, 0));

    assertArrayEquals(expected.toArray(), ship.getSunkSpaces().toArray());

  }
}
