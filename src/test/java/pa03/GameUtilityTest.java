package pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import cs3500.pa03.Coord;
import cs3500.pa03.GameUtility;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * tests GameUtility
 */
public class GameUtilityTest {
  // for test coverage
  GameUtility utility = new GameUtility();
  int height;
  int width;

  /**
   * tests getFullCoordsList
   */
  @Test
  public void testGetFullCoordsList() {
    height = 6;
    width = 6;
    List<Coord> expected = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        expected.add(new Coord(j, i));
      }
    }

    assertArrayEquals(expected.toArray(),
        GameUtility.getFullCoordsList(6, 6).toArray());

    height = 7;
    width = 8;
    List<Coord> expected2 = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 8; j++) {
        expected2.add(new Coord(j, i));
      }
    }

    assertArrayEquals(expected2.toArray(),
        GameUtility.getFullCoordsList(7, 8).toArray());

  }
}
