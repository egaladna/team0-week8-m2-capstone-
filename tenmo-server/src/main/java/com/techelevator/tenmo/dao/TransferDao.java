package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.ArrayList;
import java.util.List;

public interface TransferDao {

    Transfer getTransferAmountByUserId(int id);

    Transfer  addTransferAmount(Transfer transfer);

    List<Transfer> listOfTransfers(int userId);
}
