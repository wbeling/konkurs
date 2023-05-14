package pl.beling.konkurs.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.StringJoiner;

/**
 * ATMs details
 */
public class ATMDto {
    @JsonProperty("region")
    private Integer region;

    @JsonProperty("atmId")
    private Integer atmId;

    public ATMDto region(Integer region) {
        this.region = region;
        return this;
    }

    /**
     * Get region
     * minimum: 1
     * maximum: 9999
     *
     * @return region
     */
    @Min(1)
    @Max(9999)
    public Integer getRegion() {
        return region;
    }

    public ATMDto atmId(Integer atmId) {
        this.atmId = atmId;
        return this;
    }

    /**
     * Get atmId
     * minimum: 1
     * maximum: 9999
     *
     * @return atmId
     */

    @Min(1)
    @Max(9999)
    public Integer getAtmId() {
        return atmId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ATMDto.class.getSimpleName() + "[", "]")
                .add("region=" + region)
                .add("atmId=" + atmId)
                .toString();
    }
}

