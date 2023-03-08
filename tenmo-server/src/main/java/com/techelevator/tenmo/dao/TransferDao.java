package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    Transfer getTransferAmountByUserId(int id);

    Transfer  addTransferAmount(Transfer transfer);
}
