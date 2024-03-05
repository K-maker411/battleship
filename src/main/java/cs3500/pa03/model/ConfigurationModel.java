package cs3500.pa03.model;

/**
 * used for verifying data from console
 */
public class ConfigurationModel {
  private final int boardHeight;
  private final int boardWidth;
  private int carrierCount;
  private int battleShipCount;
  private int destroyerCount;
  private int submarineCount;

  /**
   * constructor
   *
   * @param boardHeight height of the board
   * @param boardWidth width of the board
   */
  public ConfigurationModel(int boardHeight, int boardWidth) {
    this.boardHeight = this.validateDim(boardHeight);
    this.boardWidth = this.validateDim(boardWidth);
  }

  /**
   * checks if the given board dimension is valid (between 6 and 15, inclusive)
   *
   * @param boardDim an integer representing a board dimension
   * @return boardDim if the integer is a valid dimension, throws exception otherwise
   */
  private int validateDim(int boardDim) {
    if (boardDim >= 6 && boardDim <= 15) {
      return boardDim;
    } else {
      throw new IllegalArgumentException("Board dimension must be between 6 and 15, inclusive.");
    }
  }

  /**
   * checks if there is at least one of each ship type and that
   *
   * @param carrierCount specified number of carriers
   * @param battleShipCount specified number of battleships
   * @param destroyerCount specified number of destroyers
   * @param submarineCount specified number of submarines
   */
  public void validateShipCounts(int carrierCount, int battleShipCount,
                                  int destroyerCount, int submarineCount) {
    int maxShipCount = this.getSmallerDimensionSize();
    if ((carrierCount >= 1 && battleShipCount >= 1 && destroyerCount >= 1 && submarineCount >= 1)
        && carrierCount + battleShipCount + destroyerCount + submarineCount <= maxShipCount) {
      this.carrierCount = carrierCount;
      this.battleShipCount = battleShipCount;
      this.destroyerCount = destroyerCount;
      this.submarineCount = submarineCount;
    } else {
      throw new IllegalArgumentException("Must have at least 1 of each ship type and total number "
          + "of ships must be less than " + maxShipCount);
    }

  }

  /**
   * gets smaller dimension size (useful for max number of ships)
   *
   * @return size of smaller dimension
   */
  public int getSmallerDimensionSize() {
    return Math.min(this.boardHeight, this.boardWidth);
  }

  /**
   * returns verified board width
   *
   * @return board width
   */
  public int getBoardWidth() {
    return this.boardWidth;
  }

  /**
   * returns verified board height
   *
   * @return board height
   */
  public int getBoardHeight() {
    return this.boardHeight;
  }

  /**
   * returns inputted carrier count
   *
   * @return carrier count
   */
  public int getCarrierCount() {
    return carrierCount;
  }

  /**
   * returns inputted battleship count
   *
   * @return battleship count
   */
  public int getBattleShipCount() {
    return battleShipCount;
  }

  /**
   * returns inputted destroyer count
   *
   * @return carrier count
   */
  public int getDestroyerCount() {
    return destroyerCount;
  }

  /**
   * returns inputted submarine count
   *
   * @return carrier count
   */
  public int getSubmarineCount() {
    return submarineCount;
  }
}
