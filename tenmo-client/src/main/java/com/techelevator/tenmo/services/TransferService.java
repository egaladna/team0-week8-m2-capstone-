package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String baseUrl;
    private AuthenticatedUser currentUser;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public TransferService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.authToken = currentUser.getToken();
        this.currentUser = currentUser;
    }

    public Transfer createTransfer(Transfer newTransfer){
     Transfer transfer = null;
     try{
         ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "/transfer",
                 HttpMethod.POST,makeTransferEntity(newTransfer), Transfer.class);
         transfer = response.getBody();
     } catch (RestClientResponseException e){
         BasicLogger.log(e.getMessage());
     }
     return transfer;
    }
    public User[] listUsers(){
        User[] users = null;
        try{
            ResponseEntity<User[]> response = restTemplate.exchange(baseUrl + "/transfer", HttpMethod.GET,
                    makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException e){
            BasicLogger.log(e.getMessage());
        }
        return users;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return  new HttpEntity<>(transfer,headers);
    }
    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
