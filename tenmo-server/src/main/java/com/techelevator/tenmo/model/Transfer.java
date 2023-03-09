package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private int id;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer() {

    }

    public Transfer(int id ,int transferTypeId, int transferStatusId, int accountFrom, int accountTo, double amount) {
        this.id = id;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = BigDecimal.valueOf(amount);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id && transferTypeId == transfer.transferTypeId && transferStatusId == transfer.transferStatusId && accountFrom == transfer.accountFrom && accountTo == transfer.accountTo && Objects.equals(amount, transfer.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
    }
}
