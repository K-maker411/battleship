package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.ShipType;
import java.util.Map;

/**
 * holds json regarding setup
 * @param width board width
 * @param height board height
 * @param specs ship specifications
 */
public record SetupJson(@JsonProperty("width") int width,
                        @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> specs) {
}
