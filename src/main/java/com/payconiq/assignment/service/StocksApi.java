package com.payconiq.assignment.service;

import com.payconiq.assignment.controller.StocksApiController;
import com.payconiq.assignment.model.PriceRequest;
import com.payconiq.assignment.model.Stock;
import com.payconiq.assignment.model.StockRequest;

import java.util.List;

/**
 * A delegate to be called by the {@link StocksApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface StocksApi {

    /**
     * POST /stocks : Create a stock
     * create a stock and add to db
     *
     * @param stockRequestDTO stockKey to find stock (required)
     * @return Successful operation - new Stock Created (status code 200)
     * or Invalid input (status code 400)
     * or not found (status code 404)
     */
     Stock createStock(StockRequest stockRequestDTO);

    /**
     * DELETE /stocks/{stockKey} : Delete a stock
     * delete a stock
     *
     * @param stockKey stockKey to find stock(required)
     */
     void deleteStock(String stockKey);

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
    List<Stock> findAllStocks(String sortBy, Long pageNo, Long pageSize);

    /**
     * GET /stocks/{stockKey} : Find stock by stockKey
     * get stock based on stockKey
     *
     * @param stockKey stockKey to find stock (required)
     * @return successful operation - stock as response (status code 200)
     * or Invalid stockKey (status code 400)
     * or not found (status code 404)
     */
    Stock getStockByStockKey(String stockKey);

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
    Stock updateStock(String stockKey, PriceRequest priceRequest);

}