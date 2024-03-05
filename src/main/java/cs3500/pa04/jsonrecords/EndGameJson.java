package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.GameResult;

/**
 * holds json for the provided endGame message
 *
 * @param result win, loss, or tie
 * @param reason reason for loss
 */
public record EndGameJson(@JsonProperty("result") GameResult result,
                          @JsonProperty("reason") String reason) {
}
