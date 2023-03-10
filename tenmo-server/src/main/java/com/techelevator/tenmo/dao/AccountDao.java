package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

public interface AccountDao {

    Account getBalanceByUserId(int id);
    Account getAccountIdByUser(int id);
}
