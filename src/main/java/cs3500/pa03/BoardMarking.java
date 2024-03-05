package cs3500.pa03;

/**
 * represents a marking on the board
 */
public enum BoardMarking {
  HIT("H"),
  MISS("M"),
  NOT_YET_HIT("0");
  private final String marking;

  /**
   * constructor
   *
   * @param marking string marking
   */
  BoardMarking(String marking) {
    this.marking = marking;
  }

  /**
   * gets the string representation of the marking
   *
   * @return string representation of marking
   */
  public String getMarkingAsString() {
    return this.marking;
  }
}
