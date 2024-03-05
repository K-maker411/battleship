package cs3500.pa03.model;

import cs3500.pa03.BoardMarking;
import cs3500.pa03.Coord;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * represents a human player
 */
public class HumanPlayerModel extends AbstractPlayer {

  /**
   * constructor
   *
   * @param rand random number generator
   * @param lastTakenShots the list of shots last taken by this player
   */
  public HumanPlayerModel(Random rand, List<Coord> lastTakenShots) {
    super(rand);
    this.lastTakenShots = lastTakenShots;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "Human Player";
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
    return this.lastTakenShots;
  }

  /**
   * returns the human's player's board and the opponent's board from the human player's
   * point of view as a string
   *
   * @return string display of boards
   */
  @Override
  public String toString() {
    return this.createOpponentBoardVisual()
        + this.createThisPlayerBoardVisual() + System.lineSeparator();
  }

  /**
   * creates board visual of opponent from the human's perspective (since that's all that's
   * shown on the board)
   *
   * @return board visual as string
   */
  private String createOpponentBoardVisual() {
    StringBuilder output = new StringBuilder();
    output.append("Opponent Board Data:").append(System.lineSeparator());
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Coord coord = new Coord(j, i);
        if (this.shotsOnOpponent.containsKey(coord)) {
          if (this.shotsOnOpponent.get(coord).equals(BoardMarking.HIT)) {
            output.append(BoardMarking.HIT.getMarkingAsString()).append(" ");
          } else {
            output.append(BoardMarking.MISS.getMarkingAsString()).append(" ");
          }
        } else {
          output.append(BoardMarking.NOT_YET_HIT.getMarkingAsString()).append(" ");
        }
      }
      output.append(System.lineSeparator());
    }
    output.append(System.lineSeparator());
    return output.toString();
  }

  /**
   * creates board visual for the human player
   *
   * @return string with board visual
   */
  private String createThisPlayerBoardVisual() {
    StringBuilder output = new StringBuilder();
    output.append("Your Board:").append(System.lineSeparator());

    Map<Coord, Ship> coordShipMap = new LinkedHashMap<>();
    for (Ship ship : this.shipsList) {
      for (Coord coord : ship.getCurrentlyOccupiedSpaces()) {
        coordShipMap.put(coord, ship);
      }
    }

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        Coord coord = new Coord(j, i);
        // if the current coord is occupied by a ship (where the ship has not been hit
        // on this coord yet), mark with letter corresponding to ship type
        // otherwise, shot hasn't been fired here nor is there a ship
        if (coordShipMap.containsKey(coord)) {
          output.append(coordShipMap.get(coord).getType()).append(" ");
        } else {
          output.append(
            // if the opponent shot at this coordinate, mark it as either hit or miss, depending
            // on what actually happened (which is labeled in the opponentShotsOnThisPlayer map)
            // otherwise, default to '0' because spot is empty and not yet hit
            this.opponentShotsOnThisPlayer.getOrDefault(coord, BoardMarking.NOT_YET_HIT)
                .getMarkingAsString()).append(" ");
        }
      }
      output.append(System.lineSeparator());
    }
    return output.toString();
  }


}

