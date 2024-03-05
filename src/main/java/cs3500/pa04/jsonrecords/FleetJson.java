package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.Ship;
import java.util.List;

/**
 * holds json for a fleet of ships
 *
 * @param fleet list of ships
 */
public record FleetJson(
    @JsonProperty("fleet") List<Ship> fleet) {
}
