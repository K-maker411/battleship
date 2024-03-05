package cs3500.pa03;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * represents a type (and size) of ship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);
  private final int size;

  /**
   * constructor
   *
   * @param size number of spaces this ShipType occupies
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * returns the size of the ShipType
   *
   * @return integer size
   */
  public int getSize() {
    return this.size;
  }

  /**
   * returns a string representation of the given ship type (in terms of the letter representation
   * on the board)
   *
   * @return "C," "B," "D," or "S"
   */
  @Override
  public String toString() {
    return switch (this) {
      case CARRIER -> "C";
      case BATTLESHIP -> "B";
      case DESTROYER -> "D";
      case SUBMARINE -> "S";
    };
  }
}

