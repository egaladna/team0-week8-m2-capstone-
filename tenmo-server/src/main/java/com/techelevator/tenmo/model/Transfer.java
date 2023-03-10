package com.techelevator.tenmo.model;

import java.util.Objects;

public class Transfer {

    private int id;
    private final int transferTypeId = 2;
    private final int transferStatusId= 2;
    private int accountFrom;
    private int accountTo;
    private double amount;
    private int userFromId;
    private int userToId;
    private String usernameFrom;
    private String usernameTo;

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public void setUsernameFrom(String usernameFrom) {
        this.usernameFrom = usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }

    public void setUsernameTo(String usernameTo) {
        this.usernameTo = usernameTo;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public Transfer() {

    }

    public Transfer(int id , int accountFrom, int accountTo, double amount) {
        this.id = id;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
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


    public int getTransferStatusId() {
        return transferStatusId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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
