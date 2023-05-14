package pl.beling.konkurs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Players DTO - input
 */
public class PlayersDto {
    @JsonProperty("groupCount")
    private Integer groupCount;

    @JsonProperty("clans")
    @Valid
    private List<ClanDto> clans;

    /**
     * Number of players in a single group
     * minimum: 1
     * maximum: 1000
     *
     * @return groupCount
     */
    @Min(1)
    @Max(1000)
    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    /**
     * Get clans
     *
     * @return clans
     */
    @Valid
    @Size(max = 20000)
    public List<ClanDto> getClans() {
        return Collections.unmodifiableList(clans);
    }

    public void setClans(List<ClanDto> clans) {
        this.clans = Collections.unmodifiableList(clans);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlayersDto.class.getSimpleName() + "[", "]")
                .add("groupCount=" + groupCount)
                .add("clans=" + clans)
                .toString();
    }
}

