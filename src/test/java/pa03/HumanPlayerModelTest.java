package pa03;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipOrientation;
import cs3500.pa03.ShipType;
import cs3500.pa03.model.HumanPlayerModel;
import cs3500.pa03.model.Player;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests HumanPlayerModel
 */
public class HumanPlayerModelTest {
  Random rand;
  List<Coord> lastTakenShots = new ArrayList<>();
  List<Coord> lastTakenShots2 = new ArrayList<>();
  Map<ShipType, Integer> sampleSpecs = new LinkedHashMap<>();
  Player human;
  Player human2;
  Player human3;
  String humanBoard;

  /**
   * sets up models and lists
   */
  @BeforeEach
  public void createModelsAndLists() {
    rand = new Random(1);
    // lastTakenShots also acts as opponentShotsOnBoard for reportDamage()
    lastTakenShots.add(new Coord(0, 0));
    lastTakenShots.add(new Coord(0, 2));
    lastTakenShots.add(new Coord(2, 2));
    lastTakenShots.add(new Coord(4, 4));

    lastTakenShots2.add(new Coord(2, 3));
    lastTakenShots2.add(new Coord(3, 2));

    sampleSpecs.put(ShipType.CARRIER, 1);
    sampleSpecs.put(ShipType.BATTLESHIP, 1);
    sampleSpecs.put(ShipType.DESTROYER, 1);
    sampleSpecs.put(ShipType.SUBMARINE, 1);

    human = new HumanPlayerModel(rand, lastTakenShots);
    human2 = new HumanPlayerModel(rand, lastTakenShots);
    human3 = new HumanPlayerModel(rand, lastTakenShots2);

    humanBoard = "D 0 0 0 0 0 " + System.lineSeparator()
            + "D B B B B B " + System.lineSeparator()
            + "D 0 0 0 0 0 " + System.lineSeparator()
            + "D 0 0 0 0 0 " + System.lineSeparator()
            + "C C C C C C " + System.lineSeparator()
            + "S S S 0 0 0 ";
  }

  /**
   * tests name
   */
  @Test
  public void testName() {
    assertEquals("Human Player", human.name());
  }

  /**
   * tests setup
   */
  @Test
  public void testSetup() {
    List<Ship> expected = new ArrayList<>();
    Ship s1 = new Ship(ShipType.CARRIER, ShipOrientation.HORIZONTAL, new Coord(0, 4));
    Ship s2 = new Ship(ShipType.BATTLESHIP, ShipOrientation.HORIZONTAL, new Coord(1, 1));
    Ship s3 = new Ship(ShipType.DESTROYER, ShipOrientation.VERTICAL, new Coord(0, 0));
    Ship s4 = new Ship(ShipType.SUBMARINE, ShipOrientation.HORIZONTAL, new Coord(0, 5));
    expected.add(s1);
    expected.add(s2);
    expected.add(s3);
    expected.add(s4);

    Map<ShipType, Integer> specs = new LinkedHashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    List<Ship> actual = human.setup(6, 6, specs);

    // checks if occupied coordinates of each ship are the same, which implicitly
    // verifies that ships are same type and orientation, this testing if the ship lists
    // are "equal"
    for (int i = 0; i < actual.size(); i++) {
      assertArrayEquals(expected.get(i).getCurrentlyOccupiedSpaces().toArray(),
          actual.get(i).getCurrentlyOccupiedSpaces().toArray());
    }

    expected.clear();
    specs.clear();
    actual.clear();

    expected.add(new Ship(ShipType.CARRIER, ShipOrientation.HORIZONTAL, new Coord(3, 2)));
    expected.add(new Ship(ShipType.CARRIER, ShipOrientation.VERTICAL, new Coord(1, 0)));
    expected.add(new Ship(ShipType.BATTLESHIP, ShipOrientation.HORIZONTAL, new Coord(3, 1)));
    expected.add(new Ship(ShipType.BATTLESHIP, ShipOrientation.HORIZONTAL, new Coord(2, 5)));
    expected.add(new Ship(ShipType.DESTROYER, ShipOrientation.HORIZONTAL, new Coord(5, 6)));
    expected.add(new Ship(ShipType.DESTROYER, ShipOrientation.HORIZONTAL, new Coord(5, 4)));
    expected.add(new Ship(ShipType.SUBMARINE, ShipOrientation.VERTICAL, new Coord(2, 0)));
    specs.put(ShipType.CARRIER, 2);
    specs.put(ShipType.BATTLESHIP, 2);
    specs.put(ShipType.DESTROYER, 2);
    specs.put(ShipType.SUBMARINE, 1);
    actual = human2.setup(7, 10, specs);

    for (int i = 0; i < actual.size(); i++) {
      assertArrayEquals(expected.get(i).getCurrentlyOccupiedSpaces().toArray(),
          actual.get(i).getCurrentlyOccupiedSpaces().toArray());
    }
  }

  /**
   * tests reportDamage
   */
  @Test
  public void testReportDamage() {
    Map<ShipType, Integer> specs = new LinkedHashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    human.setup(6, 6, specs);
    // assume that the AI shot at the list of coords in lastTakenShots
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(0, 0));
    expected.add(new Coord(0, 2));
    expected.add(new Coord(4, 4));
    List<Coord> hitCoords = human.reportDamage(lastTakenShots);

    assertArrayEquals(expected.toArray(), hitCoords.toArray());
    lastTakenShots.clear();

    lastTakenShots.add(new Coord(2, 2));
    lastTakenShots.add(new Coord(0, 1));
    lastTakenShots.add(new Coord(4, 4));
    lastTakenShots.add(new Coord(5, 5));

    expected.clear();
    expected.add(new Coord(0, 1));
    hitCoords.clear();
    hitCoords = new ArrayList<>(human.reportDamage(lastTakenShots));
    assertArrayEquals(expected.toArray(),
        hitCoords.toArray());
  }

  /**
   * tests takeShots
   */
  @Test
  public void testTakeShots() {
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(2, 3));
    expected.add(new Coord(3, 2));
    human3.setup(6, 6, sampleSpecs);
    assertArrayEquals(expected.toArray(), human3.takeShots().toArray());

    // illustrates that HumanPlayerModel contains reference to list of coordinates
    lastTakenShots2.remove(1);
    expected.remove(1);
    assertArrayEquals(expected.toArray(), human3.takeShots().toArray());

  }

  /**
   * tests endGame (just uses it, not relevant for PA03)
   */
  @Test
  public void testEndGame() {
    human.endGame(GameResult.WIN, "Example ending");
  }

  /**
   * tests successfulHits
   */
  @Test
  public void testSuccessfulHits() {
    human.setup(6, 6, sampleSpecs);

    List<Coord> successful = new ArrayList<>();
    successful.add(new Coord(0, 0));
    successful.add(new Coord(2, 2));
    human.successfulHits(successful);

    String updatedOpponentBoard =
        "H 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "M 0 H 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 M 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator();

    StringBuilder expected = new StringBuilder();
    expected.append("Opponent Board Data:").append(System.lineSeparator())
        .append(updatedOpponentBoard)
        .append(System.lineSeparator());

    expected.append("Your Board:").append(System.lineSeparator())
        .append(humanBoard).append(System.lineSeparator()).append(System.lineSeparator());

    assertEquals(expected.toString(), human.toString());
  }

  /**
   * tests toString on initial display
   */
  @Test
  public void testInitialToString() {

    String opponentBoard =
        "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator()
            + "0 0 0 0 0 0 " + System.lineSeparator();

    human.setup(6, 6, sampleSpecs);

    StringBuilder expected = new StringBuilder();
    expected.append("Opponent Board Data:").append(System.lineSeparator()).append(opponentBoard)
        .append(System.lineSeparator());
    expected.append("Your Board:").append(System.lineSeparator())
        .append(humanBoard).append(System.lineSeparator()).append(System.lineSeparator());

    assertEquals(expected.toString(), human.toString());

  }



}
