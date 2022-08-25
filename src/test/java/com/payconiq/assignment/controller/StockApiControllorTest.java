package com.payconiq.assignment.controller;

import com.payconiq.assignment.model.Stock;
import com.payconiq.assignment.model.StockRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockApiControllorTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void testGetStock() {
        HashMap<String, String> urlVariables = new HashMap<>();
        urlVariables.put("sortBy", "id");
        urlVariables.put("pageNo", "0");
        urlVariables.put("pageSize", "11");
        ResponseEntity<Stock[]> response = restTemplate.getForEntity(
                createURLWithPort("/api/stocks"), Stock[].class, urlVariables);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testGetStockByStockKey() {
        HashMap<String, String> urlVariables = new HashMap<>();
        urlVariables.put("stockKey", "un0ngOtbhzKUyUZJIRbwlA==");

        ResponseEntity<Stock> response = restTemplate.getForEntity(
                createURLWithPort("/api/stocks/{stockKey}"), Stock.class, urlVariables);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testCreateStock() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setName("Payocniq");
        stockRequest.setCurrentPrice(123.33);

        HttpEntity<StockRequest> entity = new HttpEntity<>(stockRequest);

        ResponseEntity<Stock> response = restTemplate.postForEntity(
                createURLWithPort("/api/stocks"), entity, Stock.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testdeleteStockByStockKey() {
        HashMap<String, String> urlVariables = new HashMap<>();
        urlVariables.put("stockKey", "un0ngOtbhzKUyUZJIRbwlA==");
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/stocks/{stockKey}"), HttpMethod.DELETE, null, String.class, urlVariables);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Stock deleted successfully");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
