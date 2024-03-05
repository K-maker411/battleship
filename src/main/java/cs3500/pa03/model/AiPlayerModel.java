package cs3500.pa03.model;

import cs3500.pa03.BoardMarking;
import cs3500.pa03.Coord;
import cs3500.pa03.GameUtility;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipOrientation;
import cs3500.pa03.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents AI Player that plays against the Human
 */
public class AiPlayerModel extends AbstractPlayer {
  private List<Coord> remainingCoordsToShoot;

  /**
   * constructor
   *
   * @param rand random number generator
   */
  public AiPlayerModel(Random rand) {
    super(rand);
    this.lastTakenShots = new ArrayList<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "K-maker411";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.remainingCoordsToShoot = GameUtility.getFullCoordsList(height, width);
    return this.createShips(height, width, specifications);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> shotsToTake = new ArrayList<>();
    int numShotsThisTurn = this.numShots();
    for (int i = 0; i < numShotsThisTurn; i++) {
      int randIndex = this.rand.nextInt(this.remainingCoordsToShoot.size());
      shotsToTake.add(this.remainingCoordsToShoot.remove(randIndex));
    }

    this.lastTakenShots = shotsToTake;
    return shotsToTake;

  }

  /**
   * determines number of shots the ai can do this turn
   *
   * @return number of shots
   */
  private int numShots() {
    int remainingCoordsToShootSize = this.remainingCoordsToShoot.size();
    int numShots = 0;
    for (Ship ship : this.shipsList) {
      if (!ship.isSunk()) {
        numShots += 1;
      }
    }

    return Math.min(numShots, remainingCoordsToShootSize);

  }







}
