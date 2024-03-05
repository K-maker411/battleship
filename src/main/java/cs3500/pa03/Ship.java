package cs3500.pa03;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a ship on the board
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Ship {
  @JsonIgnore
  private ShipType type;
  @JsonProperty("direction")
  private ShipOrientation orientation;
  @JsonProperty("coord")
  private final Coord originPoint;
  @JsonIgnore
  private List<Coord> currentlyOccupiedSpaces;
  @JsonIgnore
  private List<Coord> sunkSpaces;

  /**
   * constructor
   *
   * @param type type of ship
   * @param orientation orientation of ship (horizontal/vertical)
   * @param originPoint leftmost or topmost point of ship (respective to orientation)
   */
  @JsonCreator
  public Ship(ShipType type,
              ShipOrientation orientation,
              Coord originPoint) {
    this.type = type;
    this.orientation = orientation;
    this.originPoint = originPoint;
    this.currentlyOccupiedSpaces = this.getOccupiedSpaces();
    this.sunkSpaces = new ArrayList<>();
  }

  /**
   * returns spaces currently occupied by this ship
   *
   * @return currently occupied spaces by this ship
   */
  public List<Coord> getCurrentlyOccupiedSpaces() {
    return this.currentlyOccupiedSpaces;
  }

  /**
   * removes the given coord from the ship's occupied space, meaning the ship was hit
   * at that coordinate
   *
   * @param coord coord to remove
   */
  public void removeOccupiedSpace(Coord coord) {
    this.currentlyOccupiedSpaces.remove(coord);
    this.sunkSpaces.add(coord);
  }

  /**
   * gets coords that are occupied by this ship using its type (thus length) and orientation
   *
   * @return coordinates occupied by this ship
   */
  private List<Coord> getOccupiedSpaces() {
    List<Coord> output = new ArrayList<>();

    int currentPoint;

    if (this.orientation.equals(ShipOrientation.VERTICAL)) {
      currentPoint = this.originPoint.getCoordY();
      for (int i = currentPoint; i < currentPoint + this.type.getSize(); i++) {
        output.add(new Coord(this.originPoint.getCoordX(), i));
      }

    } else {
      currentPoint = this.originPoint.getCoordX();
      for (int i = currentPoint; i < currentPoint + this.type.getSize(); i++) {
        output.add(new Coord(i, this.originPoint.getCoordY()));
      }
    }

    return output;
  }

  /**
   * returns the type of this ship
   *
   * @return ship type
   */
  public ShipType getType() {
    return this.type;
  }

  /**
   * determines if a ship is sunk by checking if the number of hits taken
   * is equal to the size of the ship (meaning the object was hit with as many
   * "spaces" as it takes up on the board, it has been sunk)
   *
   * @return true if ship has been sunk
   */
  @JsonIgnore
  public boolean isSunk() {
    return this.currentlyOccupiedSpaces.size() == 0;
  }

  /**
   * gets spaces occupied by this ship that have been hit by the opponent's shots
   *
   * @return list of coords that have been hit
   */
  public List<Coord> getSunkSpaces() {
    return this.sunkSpaces;
  }

  /**
   * returns length of this ship (from its type)
   * (used for json purposes)
   *
   * @return ship length
   */
  @JsonProperty("length")
  public int getLength() {
    return this.type.getSize();
  }
}
