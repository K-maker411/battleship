package cs3500.pa03;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a set of x-y coordinates
 */
public class Coord {
  private final int coordX;
  private final int coordY;

  /**
   * constructor
   *
   * @param coordX x-coordinate
   * @param coordY y-coordinate
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int coordX,
               @JsonProperty("y") int coordY) {
    this.coordX = this.validateCoordPart(coordX);
    this.coordY = this.validateCoordPart(coordY);
  }

  /**
   * checks if the given object is a Coord object,
   * then checks whether the x and y values are the same
   *
   * @param obj any object
   * @return whether this coord is the same as another object
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coord)) {
      return false;
    } else {
      Coord coord = (Coord) obj;
      return this.coordX == coord.coordX && this.coordY == coord.coordY;
    }
  }

  /**
   * generates hashcode for the given Coord object
   *
   * @return integer hashcode
   */
  @Override
  public int hashCode() {
    return (37 * this.coordX) + (29 * this.coordY);
  }

  /**
   * gets x coord
   *
   * @return x coord
   */
  @JsonProperty("x")
  public int getCoordX() {
    return this.coordX;
  }

  /**
   * gets y coord
   *
   * @return y coord
   */
  @JsonProperty("y")
  public int getCoordY() {
    return this.coordY;
  }

  /**
   * checks if coord part (either x or y) is greater than or equal to 0
   *
   * @param coordPart an x or y coord
   * @return coordPart or an exception
   */
  private int validateCoordPart(int coordPart) {
    if (coordPart >= 0) {
      return coordPart;
    } else {
      throw new IllegalArgumentException("The provided x or y coordinate must be an"
          + " integer greater than or equal to 0.");
    }
  }
}

