package com.payconiq.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * StockRequestDTO
 */
public class StockRequest {
    @JsonProperty("name")
    private String name;

    @JsonProperty("currentPrice")
    private Double currentPrice;

    /**
     * Get name
     *
     * @return name
     */
    @Schema(example = "Payconiq stock")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get currentPrice
     *
     * @return currentPrice
     */
    @Schema(example = "145.334")
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockRequest stockRequestDTO = (StockRequest) o;
        return Objects.equals(this.name, stockRequestDTO.name) &&
                Objects.equals(this.currentPrice, stockRequestDTO.currentPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, currentPrice);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StockRequestDTO {\n");

        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    currentPrice: ").append(toIndentedString(currentPrice)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

