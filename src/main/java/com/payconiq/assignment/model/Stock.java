package com.payconiq.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

/**
 * Stock
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @JsonProperty("stockKey")
    private String stockKey;

    @JsonProperty("name")
    private String name;

    @JsonProperty("currentPrice")
    private Double currentPrice;

    @JsonProperty("lastUpdate")
    private Date lastUpdate;

    /**
     * Get stockKey
     *
     * @return stockKey
     */
    @Schema(example = "Pay_123")
    public String getStockKey() {
        return stockKey;
    }

    public void setStockKey(String stockKey) {
        this.stockKey = stockKey;
    }

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


    /**
     * Get lastUpdate
     *
     * @return lastUpdate
     */
    @Schema(example = "")
    @Valid
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return Objects.equals(this.stockKey, stock.stockKey) &&
                Objects.equals(this.name, stock.name) &&
                Objects.equals(this.currentPrice, stock.currentPrice) &&
                Objects.equals(this.lastUpdate, stock.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockKey, name, currentPrice, lastUpdate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Stock {\n");
        sb.append("    stockKey: ").append(toIndentedString(stockKey)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    currentPrice: ").append(toIndentedString(currentPrice)).append("\n");
        sb.append("    lastUpdate: ").append(toIndentedString(lastUpdate)).append("\n");
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

