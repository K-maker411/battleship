package pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Coord;
import cs3500.pa03.ShipType;
import cs3500.pa03.model.AiPlayerModel;
import cs3500.pa03.model.Player;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests AiPlayerModel
 */
public class AiPlayerModelTest {
  Player aiPlayer;
  Player aiPlayer2;

  /**
   * sets up Ai Player(s)
   */
  @BeforeEach
  public void setUpAiPlayers() {
    aiPlayer = new AiPlayerModel(new Random(1));
    aiPlayer2 = new AiPlayerModel(new Random(1));

    Map<ShipType, Integer> specs = new LinkedHashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    aiPlayer.setup(6, 6, specs);

    specs.clear();
    specs.put(ShipType.CARRIER, 2);
    specs.put(ShipType.BATTLESHIP, 2);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    aiPlayer2.setup(8, 7, specs);
  }

  /**
   * tests name
   */
  @Test
  public void testName() {
    assertEquals("K-maker411", aiPlayer.name());
  }

  /**
   * tests takeShots
   */
  @Test
  public void testTakeShots() {
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(4, 1));
    expected.add(new Coord(2, 2));
    expected.add(new Coord(1, 4));
    expected.add(new Coord(0, 4));
    List<Coord> actual = aiPlayer.takeShots();
    assertArrayEquals(expected.toArray(), actual.toArray());

    expected.clear();
    actual.clear();

    expected.add(new Coord(5, 0));
    expected.add(new Coord(0, 2));
    expected.add(new Coord(3, 5));
    expected.add(new Coord(5, 7));
    expected.add(new Coord(6, 4));
    expected.add(new Coord(0, 7));
    actual = aiPlayer2.takeShots();
    assertArrayEquals(expected.toArray(), actual.toArray());

    List<Coord> fired = new ArrayList<>();
    fired.add(new Coord(0, 4));
    fired.add(new Coord(1, 4));
    fired.add(new Coord(2, 4));
    fired.add(new Coord(3, 4));
    fired.add(new Coord(4, 4));
    fired.add(new Coord(5, 4));
    aiPlayer.reportDamage(fired);

    assertEquals(3, aiPlayer.takeShots().size());

  }
}
