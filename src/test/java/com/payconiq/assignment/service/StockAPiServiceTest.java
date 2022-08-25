package com.payconiq.assignment.service;


import com.payconiq.assignment.component.StockUtil;
import com.payconiq.assignment.entity.StockEntity;
import com.payconiq.assignment.exception.StockEntityNotFoundException;
import com.payconiq.assignment.model.PriceRequest;
import com.payconiq.assignment.model.Stock;
import com.payconiq.assignment.model.StockRequest;
import com.payconiq.assignment.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockAPiServiceTest {

    @InjectMocks
    StockApiService stockAPiService;

    @Mock
    StockRepository repository;

    @Mock
    StockUtil stockUtil;

    StockEntity stockEntity = StockEntity.builder().stockKey("un0ngOtbhzKUyUZJIRbwlA==").name("Payconiq").id(123L).build();

    @Test
    public void shouldGetStockWhenFindByStockKeyTest() {
        when(stockUtil.DecryptStockKey(any())).thenReturn("Payconiq_123");
        when(stockUtil.EncryptStockKey(any())).thenReturn("un0ngOtbhzKUyUZJIRbwlA==");
        when(repository.findByStockKey(any())).thenReturn(Optional.of(stockEntity));
        Stock stock = stockAPiService.getStockByStockKey("un0ngOtbhzKUyUZJIRbwlA==");
        assertThat(stock).isNotNull();
        assertThat(stock.getName()).isEqualTo(stockEntity.getName());
    }

    @Test
    public void shouldGetExceptionWhenFindByStockKeyTest() {
        when(repository.findByStockKey(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(StockEntityNotFoundException.class, () ->
                stockAPiService.getStockByStockKey("OTUZAqWa5prGQ8/wnQqhNQ=="));
        assertThat(exception.getMessage()).isEqualTo("Invalid stockKey");
    }

    @Test
    public void shouldGetAllStockTest() {
        List<StockEntity> stockEntityList = new ArrayList<>();
        stockEntityList.add(stockEntity);
        Page<StockEntity> stockEntityPage = new PageImpl<>(stockEntityList);
        when(repository.findAll(Mockito.any(PageRequest.class))).thenReturn(stockEntityPage);
        List<Stock> stockList = stockAPiService.findAllStocks("id", 0L, 10L);
        assertThat(stockList.size()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteStockByStockKeyTest() {
        when(repository.findByStockKey(any())).thenReturn(Optional.of(stockEntity));
        stockAPiService.deleteStock("un0ngOtbhzKUyUZJIRbwlA==");
        verify(repository).deleteById(any());
    }

    @Test
    public void shouldCreateStockTest() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setCurrentPrice(123444.0);
        stockRequest.setName("test");
        Stock stock = stockAPiService.createStock(stockRequest);
        assertThat(stock).isNotNull();
        assertThat(stock.getName()).isEqualTo(stockRequest.getName());
    }

    @Test
    public void shouldUpdateStockByStockKeyTest() {
        when(stockUtil.DecryptStockKey(any())).thenReturn("Payconiq_123");
        when(stockUtil.EncryptStockKey(any())).thenReturn("un0ngOtbhzKUyUZJIRbwlA==");
        when(repository.findByStockKey(any())).thenReturn(Optional.of(stockEntity));
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setPrice(123444.0);
        Stock stock = stockAPiService.updateStock("un0ngOtbhzKUyUZJIRbwlA==", priceRequest);
        assertThat(stock).isNotNull();
        assertThat(stock.getName()).isEqualTo(stockEntity.getName());
    }
}
