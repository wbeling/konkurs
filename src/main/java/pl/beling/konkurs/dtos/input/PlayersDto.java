package pl.beling.konkurs.dtos.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import pl.beling.konkurs.dtos.output.ClanDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Players
 */
public class PlayersDto {
    @JsonProperty("groupCount")
    private Integer groupCount;

    @JsonProperty("clans")
    @Valid
    private List<ClanDto> clans = null;

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
        return clans;
    }

    public void setClans(List<ClanDto> clans) {
        this.clans = clans;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PlayersDto.class.getSimpleName() + "[", "]")
                .add("groupCount=" + groupCount)
                .add("clans=" + clans)
                .toString();
    }
}

