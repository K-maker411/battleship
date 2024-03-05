package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * holds json for message to server after a join request
 *
 * @param name GitHub username, in this case
 * @param gameType single or multi
 */
public record JoinJson(@JsonProperty("name") String name,
                       @JsonProperty("game-type") String gameType) {
}
