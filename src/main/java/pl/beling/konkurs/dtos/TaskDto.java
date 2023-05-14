package pl.beling.konkurs.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.StringJoiner;

/**
 * Task DTO - input
 */
public class TaskDto {
    @JsonProperty("region")
    private Integer region;

    /**
     * Type of request
     */
    public enum RequestTypeEnum {
        STANDARD("STANDARD"),

        PRIORITY("PRIORITY"),

        SIGNAL_LOW("SIGNAL_LOW"),

        FAILURE_RESTART("FAILURE_RESTART");

        private final String value;

        RequestTypeEnum(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static RequestTypeEnum fromValue(String value) {
            for (RequestTypeEnum b : RequestTypeEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @JsonProperty("requestType")
    private RequestTypeEnum requestType;

    @JsonProperty("atmId")
    private Integer atmId;

    public TaskDto region(Integer region) {
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

    public TaskDto requestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
        return this;
    }

    /**
     * Type of request
     *
     * @return requestType
     */
    public RequestTypeEnum getRequestType() {
        return requestType;
    }

    public TaskDto atmId(Integer atmId) {
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
        return new StringJoiner(", ", TaskDto.class.getSimpleName() + "[", "]")
                .add("region=" + region)
                .add("requestType=" + requestType)
                .add("atmId=" + atmId)
                .toString();
    }

}

