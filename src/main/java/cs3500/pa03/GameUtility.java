package cs3500.pa03;

import java.util.ArrayList;
import java.util.List;

/**
 * provides some utility methods for the game
 */
public class GameUtility {
  /**
   * returns full list of coordinates for a board of given dimensions
   *
   * @param height board height
   * @param width board width
   * @return full list of coordinates in that board
   */
  public static List<Coord> getFullCoordsList(int height, int width) {
    List<Coord> output = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        output.add(new Coord(j, i));
      }
    }

    return output;
  }
}
