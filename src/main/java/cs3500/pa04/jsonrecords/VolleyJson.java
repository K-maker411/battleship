package cs3500.pa04.jsonrecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.Coord;
import java.util.List;

public record VolleyJson(
    @JsonProperty("coordinates") List<Coord> volley) {
}
