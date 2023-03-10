package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.awt.image.PackedColorModel;
import java.math.BigDecimal;

public class AccountService {
    private final String baseUrl;
    private AuthenticatedUser currentUser;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public AccountService(String baseUrl, AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
        this.authToken = currentUser.getToken();
        this.baseUrl = baseUrl;
    }


    public Account showBalance(){
        Account account = null;

        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl +"/account", HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return account;
    }
    public double getCurrentBalance(Account account){
      Double currentBalance =  account.getBalance();
      return currentBalance;
    }
    public int getAccountId(Account account){
        int currentId = account.getId();
        return  currentId;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
