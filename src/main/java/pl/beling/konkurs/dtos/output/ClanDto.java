package pl.beling.konkurs.dtos.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.StringJoiner;

/**
 * Clan
 */
public class ClanDto {
    @JsonProperty("numberOfPlayers")
    private Integer numberOfPlayers;

    @JsonProperty("points")
    private Integer points;

    public ClanDto numberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        return this;
    }

    /**
     * Get numberOfPlayers
     * minimum: 1
     * maximum: 1000
     *
     * @return numberOfPlayers
     */
    @Min(1)
    @Max(1000)
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ClanDto points(Integer points) {
        this.points = points;
        return this;
    }

    /**
     * Get points
     * minimum: 1
     * maximum: 1000000
     *
     * @return points
     */
    @Min(1)
    @Max(1000000)
    public Integer getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClanDto.class.getSimpleName() + "[", "]")
                .add("numberOfPlayers=" + numberOfPlayers)
                .add("points=" + points)
                .toString();
    }

}

