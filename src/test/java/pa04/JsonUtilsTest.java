package pa04;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cs3500.pa04.JsonUtils;
import org.junit.jupiter.api.Test;

/**
 * "tests" JsonUtils
 */
public class JsonUtilsTest {
  JsonUtils utils;

  /**
   * only for test coverage purposes
   */
  @Test
  public void testInstantiation() {
    assertDoesNotThrow(() -> this.utils = new JsonUtils());
  }
}
