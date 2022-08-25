package com.payconiq.assignment.service;


import com.payconiq.assignment.component.StockUtil;
import com.payconiq.assignment.entity.StockEntity;
import com.payconiq.assignment.exception.StockEntityNotFoundException;
import com.payconiq.assignment.model.PriceRequest;
import com.payconiq.assignment.model.Stock;
import com.payconiq.assignment.model.StockRequest;
import com.payconiq.assignment.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StockApiService implements StocksApi {

    private StockRepository stockRepository;

    private StockUtil stockUtil;
    
    public List<Stock> findAllStocks(String sortBy, Long pageNo, Long pageSize) {
        log.info("Processing the request to find all stocks");
        Pageable sortedById = PageRequest.of(pageNo.intValue(), pageSize.intValue(), Sort.by(sortBy));
        List<Stock> stockList = new ArrayList<>();
        log.debug("stockRepository.findAll to retrieve all recipes from DB");
        stockRepository.findAll(sortedById).forEach(stockEntity ->
            stockList.add(buildStock(stockEntity))
        );
        log.info("Number of stocks retrieved from DB: "+stockList.size());
        return stockList;
    }

    public Stock createStock(StockRequest stockRequest) {
        log.info("Processing the request to create stock");
        StockEntity stockEntity = StockEntity.builder().currentPrice(stockRequest.getCurrentPrice()).name(stockRequest.getName()).stockKey(stockUtil.StockKeyGenerator(stockRequest.getName())).build();
        log.debug("call stockRepository.save to create stock in DB");
        stockRepository.save(stockEntity);
        log.info("new stock got created with stockKey:"+stockEntity.getStockKey());
        return buildStock(stockEntity);
    }


    public Stock getStockByStockKey(String encryptedStockKey) {
        log.info("Processing the request to find stock based on stockKey:{}",encryptedStockKey);
        return stockRepository.findByStockKey(stockUtil.DecryptStockKey(encryptedStockKey)).map(this::buildStock).orElseThrow(() -> {
            log.error("No stock found for stockKey:{} throw StockEntityNotFoundException:",encryptedStockKey);
            throw new StockEntityNotFoundException("Invalid stockKey");
        });
    }

    public void deleteStock(String encryptedStockKey) {
        log.info("Processing the request to delete stock based on stockKey:{}",encryptedStockKey);
        stockRepository.findByStockKey(stockUtil.DecryptStockKey(encryptedStockKey)).ifPresentOrElse(stock -> {
            log.debug("call stockRepository.deleteById to delete the stock in DB");
            stockRepository.deleteById(stock.getId());
        }, () -> {
            log.error("No stock found for stockKey:{} throw StockEntityNotFoundException",encryptedStockKey);
            throw new StockEntityNotFoundException("Invalid stockKey");
        });
    }

    public Stock updateStock(String encryptedStockKey, PriceRequest priceRequest) {
        log.info("Processing the request to update stock based on stockKey:{}",encryptedStockKey);
        return stockRepository.findByStockKey(stockUtil.DecryptStockKey(encryptedStockKey)).map(stockEntity -> {
            stockEntity.setCurrentPrice(priceRequest.getPrice());
            log.debug("call stockRepository.save to update the stock in DB");
            stockRepository.save(stockEntity);
            log.info("updated the stock in DB for stockKey:"+encryptedStockKey);
            return buildStock(stockEntity);
        }).orElseThrow(() -> {
            log.error("No stock found for stockKey:{} throw StockEntityNotFoundException",encryptedStockKey);
            throw new StockEntityNotFoundException("Invalid stockKey");
        });
    }

    private Stock buildStock(StockEntity stockEntity) {
        log.debug("inside BuildStock: to build api response for stockKey :{}",stockEntity.getStockKey());
       return Stock.builder().stockKey(stockUtil.EncryptStockKey(stockEntity.getStockKey())).currentPrice(stockEntity.getCurrentPrice()).name(stockEntity.getName()).lastUpdate(stockEntity.getLastUpdate()).build();
    }

}
