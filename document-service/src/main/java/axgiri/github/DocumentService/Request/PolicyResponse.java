package axgiri.github.DocumentService.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("insurancePackage")
    private String insurancePackage;

    @JsonProperty("insuranceType")
    private String insuranceType;

    @JsonProperty("price")
    private Double price;
}
