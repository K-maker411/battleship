package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.ConfigurationModel;
import org.junit.jupiter.api.Test;

/**
 * rests ConfigurationModel
 */
public class ConfigurationModelTest {
  /**
   * tests invalid board dimensions
   */
  @Test
  public void testInvalidBoardDims() {
    assertThrows(IllegalArgumentException.class,
        () -> new ConfigurationModel(5, 5));
    assertThrows(IllegalArgumentException.class,
        () -> new ConfigurationModel(7, 5));
    assertThrows(IllegalArgumentException.class,
        () -> new ConfigurationModel(5, 7));
    assertThrows(IllegalArgumentException.class,
        () -> new ConfigurationModel(17, 15));
  }

  /**
   * tests invalid ship counts for validateShipCounts
   */
  @Test
  public void testInvalidShipCounts() {
    ConfigurationModel model = new ConfigurationModel(6, 6);
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(4, 0,
            1, 1));
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(7, 1,
            1, 2));
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(0, 1,
            1, 1));
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(1, 0,
            1, 1));
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(1, 1,
            0, 1));
    assertThrows(IllegalArgumentException.class,
        () -> model.validateShipCounts(1, 1,
            1, 0));
  }

  /**
   * tests validateShipCounts
   */
  @Test
  public void testValidateShipCounts() {
    ConfigurationModel model = new ConfigurationModel(6, 6);
    model.validateShipCounts(1, 1, 1, 1);
    assertEquals(1, model.getCarrierCount());
    assertEquals(1, model.getBattleShipCount());
    assertEquals(1, model.getDestroyerCount());
    assertEquals(1, model.getSubmarineCount());

    model.validateShipCounts(2, 2, 1, 1);
    assertEquals(2, model.getCarrierCount());
    assertEquals(2, model.getBattleShipCount());

    model = new ConfigurationModel(8, 8);
    model.validateShipCounts(2, 2, 2, 2);
    assertEquals(2, model.getCarrierCount());
    assertEquals(2, model.getBattleShipCount());
    assertEquals(2, model.getDestroyerCount());
    assertEquals(2, model.getSubmarineCount());
  }

  /**
   * tests getSmallerDimensionSize
   */
  @Test
  public void getSmallerDimensionSize() {
    ConfigurationModel model = new ConfigurationModel(6, 6);
    assertEquals(6, model.getSmallerDimensionSize());
    model = new ConfigurationModel(15, 8);
    assertEquals(8, model.getSmallerDimensionSize());
  }

  /**
   * tests getBoardWidth
   */
  @Test
  public void testGetBoardWidth() {
    ConfigurationModel model = new ConfigurationModel(6, 7);
    assertEquals(7, model.getBoardWidth());
  }

  /**
   * tests getBoardHeight
   */
  @Test
  public void testGetBoardHeight() {
    ConfigurationModel model = new ConfigurationModel(7, 6);
    assertEquals(7, model.getBoardHeight());
  }

  /**
   * tests getCarrierCount
   */
  @Test
  public void testGetCarrierCount() {
    ConfigurationModel model = new ConfigurationModel(6, 7);
    model.validateShipCounts(3, 1, 1, 1);
    assertEquals(3, model.getCarrierCount());
  }

  /**
   * tests getBattleShipCount
   */
  @Test
  public void testGetBattleShipCount() {
    ConfigurationModel model = new ConfigurationModel(6, 7);
    model.validateShipCounts(1, 2, 1, 1);
    assertEquals(2, model.getBattleShipCount());
  }

  /**
   * tests getDestroyerCount
   */
  @Test
  public void testGetDestroyerCount() {
    ConfigurationModel model = new ConfigurationModel(6, 7);
    model.validateShipCounts(1, 1, 3, 1);
    assertEquals(3, model.getDestroyerCount());
  }

  /**
   * tests getSubmarineCount
   */
  @Test
  public void testGetSubmarineCount() {
    ConfigurationModel model = new ConfigurationModel(6, 7);
    model.validateShipCounts(1, 1, 1, 3);
    assertEquals(3, model.getSubmarineCount());
  }
}
