package com.payconiq.assignment.controller;

import com.payconiq.assignment.model.PriceRequest;
import com.payconiq.assignment.model.Stock;
import com.payconiq.assignment.model.StockRequest;
import com.payconiq.assignment.service.StocksApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
@Tag(name = "Stock Api", description = "Stock management web API")
@SecurityRequirement(name = "bearerAuth")
public class StocksApiController {

    private final StocksApi stocksApi;

    /**
     * POST /stocks : Create a stock
     * create a stock and add to db
     *
     * @param stockRequestDTO stock details (required)
     * @return Successful operation - new Stock Created (status code 200)
     * or Invalid input (status code 400)
     * or not found (status code 404)
     */
    @Operation(summary = "Create a stock", method = "createStock", description = "create a stock and add to db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - new Stock Created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "not found")})
    @PostMapping(
            value = "/stocks",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<?> createStock(@Parameter(description = "stock details", required = true) @Valid @RequestBody StockRequest stockRequestDTO) {
        log.info("Request received: /stocks to create new stock");
        Stock stock = stocksApi.createStock(stockRequestDTO);
        return ResponseEntity.ok().body(stock);
    }


    /**
     * DELETE /stocks/{stockKey} : Delete a stock
     * delete a stock
     *
     * @param stockKey stockKey to find stock (required)
     * @return deleted successful - stock deleted (status code 200)
     * or Invalid stockKey (status code 400)
     * or not found (status code 404)
     */
    @Operation(summary = "Delete a stock", operationId = "deleteStock", description = "delete a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted successful - stock deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid stockKey"),
            @ApiResponse(responseCode = "404", description = "not found")})
    @DeleteMapping(
            value = "/stocks/{stockKey}"
    )
    ResponseEntity<?> deleteStock(@Parameter(description = "stockKey to find stock", required = true) @PathVariable("stockKey") String stockKey) {
        log.info("Request received: /stocks/{} to delete new stock",stockKey);
        stocksApi.deleteStock(stockKey);
        return ResponseEntity.ok("Stock deleted successfully");
    }


    /**
     * GET /stocks : Get All Stocks
     * get a list of stocks
     *
     * @param sortBy  (optional, default to id)
     * @param pageNo   (optional, default to 0)
     * @param pageSize (optional, default to 10)
     * @return Successful operation - List of stock as response (status code 200)
     * or stock not found (status code 404)
     * or bad request (status code 400)
     */
    @Operation(summary = "Get All Stocks", operationId = "findAllStocks", description = "get a list of stocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation - List of stock as response"),
            @ApiResponse(responseCode = "404", description = "stock not found"),
            @ApiResponse(responseCode = "400", description = "bad request")})
    @GetMapping(
            value = "/stocks",
            produces = {"application/json"}
    )
    ResponseEntity<?> findAllStocks(@Parameter(description = "sortBy Id default") @Valid @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy, @Parameter(description = "default PageNo is 0") @Valid @RequestParam(value = "pageNo", required = false, defaultValue = "0") Long pageNo, @Parameter(description = "default pageSize is 10") @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize) {
        log.info("Request received: /stocks to fetch all stocks");
        List<Stock> stockList = stocksApi.findAllStocks(sortBy, pageNo, pageSize);
        return ResponseEntity.ok().body(stockList);
    }


    /**
     * GET /stocks/{stockKey} : Find stock by stockKey
     * get stock based on stockKey
     *
     * @param stockKey stockKey to find stock (required)
     * @return successful operation - stock as response (status code 200)
     * or Invalid stockKey (status code 400)
     * or not found (status code 404)
     */
    @Operation(summary = "Find stock by stockKey", operationId = "getStockBystockKey", description = "get stock based on stockKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation - stock as response"),
            @ApiResponse(responseCode = "400", description = "Invalid stockKey"),
            @ApiResponse(responseCode = "404", description = "not found")})
    @GetMapping(
            value = "/stocks/{stockKey}",
            produces = {"application/json"}
    )
    ResponseEntity<?> getStockByStockKey(@Parameter(description = "stockKey to find stock ", required = true) @PathVariable("stockKey") String stockKey) {
        log.info("Request received: /stocks/{} to find a based stockKey",stockKey);
        Stock stock =stocksApi.getStockByStockKey(stockKey);
        return ResponseEntity.ok().body(stock);
    }


    /**
     * PATCH /stocks/{stockKey} : Update a stock
     * Update a stock
     *
     * @param stockKey     stockKey to find stock (required)
     * @param priceRequest update stock in db (required)
     * @return successful operation - stock updated (status code 200)
     * or Invalid input (status code 400)
     * or not found (status code 404)
     */
    @Operation(summary = "Update a stock", operationId = "updateStock", description = "Update a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation - stock updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "not found")})
    @PatchMapping(
            value = "/stocks/{stockKey}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<?> updateStock(@Parameter(description = "stockKey to find stock", required = true) @PathVariable("stockKey") String stockKey, @Parameter(description = "price details", required = true) @Valid @RequestBody PriceRequest priceRequest) {
        log.info("Request received: /stocks/{} to update price details based stockKey",stockKey);
        Stock stock =stocksApi.updateStock(stockKey, priceRequest);
        return ResponseEntity.ok().body(stock);
    }
}