package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * represents a method being sent to and from the server
 *
 * @param methodName name of the method being sent to/from the server
 * @param arguments method arguments
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments) {
}
