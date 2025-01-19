package com.tradingplatform.ServiceImpementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradingplatform.entity.Coin;
import com.tradingplatform.repository.CoinRepo;
import com.tradingplatform.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {
    @Autowired
    private CoinRepo coinRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=" + page;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            List<Coin> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});
            return coinList;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    @Override
    public String getMarketChart(String coinId, int days) throws Exception {
        // Correct URL formatting
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;
        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    @Override
    public String getCoinDetails(String coinId) throws Exception {
        // Define the API URL
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Create headers and HTTP entity
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters", httpHeaders);

            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Parse JSON response
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            JsonNode marketData = jsonNode.get("market_data");

            // Map JSON to Coin object
            Coin coin = new Coin();
            coin.setId(jsonNode.path("id").asText());
            coin.setName(jsonNode.path("name").asText());
            coin.setSymbol(jsonNode.path("symbol").asText());
            coin.setImage(jsonNode.path("image").path("large").asText());
            coin.setCurrentPrice(marketData.path("current_price").path("usd").asDouble(0.0));
            coin.setMarketCap(marketData.path("market_cap").path("usd").asLong(0L));
            coin.setMarketCapRank(marketData.path("market_cap_rank").asInt(0));
            coin.setTotalVolume(marketData.path("total_volume").path("usd").asLong(0L));
            coin.setHigh24h(marketData.path("high_24h").path("usd").asDouble(0.0));
            coin.setLow24h(marketData.path("low_24h").path("usd").asDouble(0.0));
            coin.setPriceChange24h(marketData.path("price_change_24h").asDouble(0.0));
            coin.setPriceChangePercentage24h(marketData.path("price_change_percentage_24h").asDouble(0.0));
            coin.setMarketCapChange24h(marketData.path("market_cap_change_24h").path("usd").asLong(0L));
            coin.setMarketCapChangePercentage24h(marketData.path("market_cap_change_percentage_24h").path("usd").asDouble(0.0));
            coin.setTotalSupply(marketData.path("total_supply").asLong(0L));

            // Save coin to the repository
            coinRepo.save(coin);

            // Return the API response body
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle specific HTTP errors
            throw new Exception("HTTP error occurred: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            // Handle generic exceptions
            throw new Exception("An error occurred while fetching coin details: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Coin findById(String id) {
        Coin coin = coinRepo.findById(id).orElseThrow(() -> new RuntimeException("coin id is nto found"));
        return coin;
    }

    @Override
    public String searchCoin(String keyword) throws Exception {
        String url = "https://api.coingecko.com/api/v3/search?query="+keyword;
        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters",httpHeaders);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new Exception(ex.getMessage());
        }

    }

    @Override
    public String getTop50coinByMarketCapRank() throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=50&page=1";

        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters",httpHeaders);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new Exception(ex.getMessage());
        }
    }

    @Override
    public String getTreadingCoins() throws Exception {
        String url = "https://api.coingecko.com/api/v3/search/treading";
        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>("parameters",httpHeaders);
            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            throw new Exception(ex.getMessage());
        }
    }
}
