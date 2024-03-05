package cs3500.pa03.model;

import cs3500.pa03.BoardMarking;
import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.GameUtility;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipOrientation;
import cs3500.pa03.ShipType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * represents an abstract player
 */
public abstract class AbstractPlayer implements Player {
  protected Random rand;
  protected int height;
  protected int width;
  protected List<Ship> shipsList;
  protected Map<Coord, BoardMarking> shotsOnOpponent;
  protected Map<Coord, BoardMarking> opponentShotsOnThisPlayer;
  protected List<Coord> lastTakenShots;

  /**
   * constructor
   *
   * @param rand a random number generator
   */
  AbstractPlayer(Random rand) {
    this.rand = rand;
    this.shotsOnOpponent = new LinkedHashMap<>();
    this.opponentShotsOnThisPlayer = new LinkedHashMap<>();
  }

  /**
   * sets up the placement of the specified ships, sets the height and width for later use,
   * and creates the board for the user from the list of ships
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return list of ships with their "origin points" on the board
   */
  /*@Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> output = new ArrayList<>();
    List<Coord> remainingCoords = GameUtility.getFullCoordsList(height, width);
    List<ShipType> specsAsList = this.getSpecsAsList(specifications);

    // for every potential ship given by the user
    for (ShipType type : specsAsList) {
      ShipOrientation currentOrientation = this.getRandomOrientation();
      List<Coord> potentialOriginCoords =
          this.whereDoesShipFit(type, currentOrientation, remainingCoords);

      if (potentialOriginCoords.isEmpty()) {
        currentOrientation = currentOrientation.getOpposite();
        potentialOriginCoords = this.whereDoesShipFit(type, currentOrientation, remainingCoords);
      }

      int randomIndex = this.rand.nextInt(potentialOriginCoords.size());
      Ship ship = new Ship(type, currentOrientation, potentialOriginCoords.get(randomIndex));

      output.add(ship);

      remainingCoords.removeAll(ship.getCurrentlyOccupiedSpaces());
    }
    List<Ship> output = this.createShips(height, width, specifications);
    this.shipsList = output;
    return output;
  }*/

  /**
   * gets random orientation
   *
   * @return a randomly generated ShipOrientation (either vertical or horizontal)
   */
  private ShipOrientation getRandomOrientation() {
    int orientation = this.rand.nextInt(2);
    if (orientation == 0) {
      return ShipOrientation.VERTICAL;
    } else {
      return ShipOrientation.HORIZONTAL;
    }
  }

  /**
   * returns list of origin point coordinates where a ship with the given type
   * and orientation could fit using the coordinates that
   * have not already been occupied by other ships
   *
   * @param type type of ship (includes length)
   * @param orientation horizontal or vertical
   * @param remainingCoords the spots on the board that are left to place
   * @return list of origin coordinates that a ship of the given type and orientation could be at
   */
  private List<Coord> whereDoesShipFit(ShipType type, ShipOrientation orientation,
                                      List<Coord> remainingCoords) {
    // maxPoint represents point on ship furthest away from the origin
    // (bottommost point of ship on vertical, rightmost point of ship on horizontal)
    Set<Coord> remainingCoordsSet = new HashSet<>(remainingCoords);
    List<Coord> output = new ArrayList<>();
    List<Coord> potentialRange;

    // for every single point that could be an "origin point"
    for (Coord coord : remainingCoords) {
      potentialRange = new ArrayList<>();
      // add to the potentialRange all the coordinates that would be occupied by a ship
      // with the given type and orientation placed at origin point coord
      if (orientation.equals(ShipOrientation.VERTICAL)) {
        for (int i = 0; i < type.getSize(); i++) {
          potentialRange.add(new Coord(coord.getCoordX(), coord.getCoordY() + i));
        }
      } else {
        for (int i = 0; i < type.getSize(); i++) {
          potentialRange.add(new Coord(coord.getCoordX() + i, coord.getCoordY()));
        }
      }


      // if the board's open spaces contains all the required spaces to sufficiently
      // hold a ship of this type and orientation at origin point coord, then add that
      // origin point to the output array
      if (remainingCoordsSet.containsAll(potentialRange)) {
        output.add(coord);
      }
    }

    return output;
  }

  /**
   * converts the map of specifications into a list of ShipType, with the number of elements
   * of that ShipType being the value in the map
   *
   * @param specifications map to convert into list
   * @return List of ShipType that's equivalent to the specifications map
   */
  private List<ShipType> getSpecsAsList(Map<ShipType, Integer> specifications) {
    List<ShipType> specsAsList = new ArrayList<>();
    for (ShipType type : specifications.keySet()) {
      for (int i = 0; i < specifications.get(type); i++) {
        specsAsList.add(type);
      }
    }

    return specsAsList;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain
   *         all locations of shots that hit a ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    // coordShipMap contains coords that are CURRENTLY occupied by a given ship
    // (meaning non-occupied shouldn't appear)
    Map<Coord, Ship> coordShipMap = new LinkedHashMap<>();
    List<Coord> allPreviouslyHitSpaces = new ArrayList<>();
    for (Ship ship : this.shipsList) {
      for (Coord coord : ship.getCurrentlyOccupiedSpaces()) {
        coordShipMap.put(coord, ship);
      }
      allPreviouslyHitSpaces.addAll(ship.getSunkSpaces());
    }

    List<Coord> output = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      // if the occupied spaces contains the given coord, add it to the output
      // (since it hit a part of a ship), put it in the map as a hit, and then remove that space
      // as occupied from the ship that used to occupy that coord
      if (coordShipMap.containsKey(coord)) {
        output.add(coord);
        this.opponentShotsOnThisPlayer.put(coord, BoardMarking.HIT);
        coordShipMap.get(coord).removeOccupiedSpace(coord);
      } else if (!allPreviouslyHitSpaces.contains(coord)) {
        // otherwise, the shot missed, so mark it as such UNLESS the condition above is true
        // (the condition above being that it was not contained in the previously hit spaces
        // of the ships)
        this.opponentShotsOnThisPlayer.put(coord, BoardMarking.MISS);
      }
    }

    return output;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    List<Coord> missedShots = new ArrayList<>(this.lastTakenShots);
    missedShots.removeAll(shotsThatHitOpponentShips);

    for (Coord coord : shotsThatHitOpponentShips) {
      this.shotsOnOpponent.put(coord, BoardMarking.HIT);
    }

    for (Coord coord : missedShots) {
      if (!this.shotsOnOpponent.containsKey(coord)) {
        this.shotsOnOpponent.put(coord, BoardMarking.MISS);
      }
    }

  }



  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    return;
  }

  /**
   * creates ships using height, width, and given specs, and also assigns height and width
   *
   * @param height given board height
   * @param width given board width
   * @param specifications given specifications
   * @return list of ships with specified placements
   */
  protected List<Ship> createShips(int height, int width, Map<ShipType, Integer> specifications) {
    List<Ship> output = new ArrayList<>();
    List<Coord> remainingCoords = GameUtility.getFullCoordsList(height, width);
    List<ShipType> specsAsList = this.getSpecsAsList(specifications);

    // for every potential ship given by the user
    for (ShipType type : specsAsList) {
      ShipOrientation currentOrientation = this.getRandomOrientation();
      List<Coord> potentialOriginCoords =
          this.whereDoesShipFit(type, currentOrientation, remainingCoords);

      if (potentialOriginCoords.isEmpty()) {
        currentOrientation = currentOrientation.getOpposite();
        potentialOriginCoords = this.whereDoesShipFit(type, currentOrientation, remainingCoords);
      }

      int randomIndex = this.rand.nextInt(potentialOriginCoords.size());
      Ship ship = new Ship(type, currentOrientation, potentialOriginCoords.get(randomIndex));

      output.add(ship);

      remainingCoords.removeAll(ship.getCurrentlyOccupiedSpaces());
    }
    this.height = height;
    this.width = width;
    this.shipsList = output;
    return output;
  }


}
