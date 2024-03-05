package cs3500.pa04;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * contains helpful methods regarding json
 */
public class JsonUtils {
  /**
   * Converts a given record object to a JsonNode.
   *
   * @param record the record to convert
   * @return the JsonNode representation of the given record
   * @throws IllegalArgumentException if the record could not be converted correctly
   */
  public static JsonNode serializeRecord(Record record) throws IllegalArgumentException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.convertValue(record, JsonNode.class);
  }
}
