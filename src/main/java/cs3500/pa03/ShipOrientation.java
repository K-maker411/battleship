package cs3500.pa03;

/**
 * represents whether a ship is vertical or horizontal on the board
 */
public enum ShipOrientation {
  VERTICAL,
  HORIZONTAL;

  /**
   * gets the opposite of the current ShipOrientation
   *
   * @return opposite of current orientation
   */
  public ShipOrientation getOpposite() {
    if (this.equals(VERTICAL)) {
      return HORIZONTAL;
    } else {
      return VERTICAL;
    }
  }
}
