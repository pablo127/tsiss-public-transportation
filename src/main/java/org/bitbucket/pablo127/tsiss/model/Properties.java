package org.bitbucket.pablo127.tsiss.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Properties {

    @JsonProperty("stop_from")
    private String stopFrom;

    @JsonProperty("stop_to")
    private String stopTo;

    @JsonProperty("a3")
    private String lineNumber;

    @JsonProperty("a4")
    private String lineType;

    @JsonProperty("a5")
    private String lineDirection;

    @JsonProperty("a6")
    private String order;

    @JsonProperty("stop_name")
    private String stopName;

    @JsonProperty("zone")
    private String zone;
}
