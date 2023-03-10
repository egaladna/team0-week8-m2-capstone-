package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private final TransferDao transferDao;
    private  final UserDao userDao;

    public TransferController(TransferDao transferDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping( path = "/transfer", method = RequestMethod.GET)
    public List<User> listAllUsers(){
        return userDao.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer, Principal principal){
        int loggedInUserId = userDao.findIdByUsername(principal.getName());
        if (loggedInUserId == transfer.getId()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot transfer to non-user");
        }

        return  transferDao.addTransferAmount(transfer);
    }

    @RequestMapping(path = "/transfer/history", method = RequestMethod.GET)
    public List<Transfer> listOfTransfers(Principal principal){
        int loggedInUserId = userDao.findIdByUsername(principal.getName());
        return transferDao.listOfTransfers(loggedInUserId);
    }
}
