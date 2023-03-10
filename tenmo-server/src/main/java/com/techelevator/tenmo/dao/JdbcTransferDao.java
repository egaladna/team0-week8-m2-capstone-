package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcTransferDao implements TransferDao {



    private final JdbcTemplate jdbcTemplate;
    private User user;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }




    @Override
    public Transfer getTransferAmountByUserId(int id) {

        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer\n" +
                "    JOIN account ON transfer.account_from = account.account_id\n" +
                "    JOIN tenmo_user ON tenmo_user.user_id = account.user_id\n" +
                "    WHERE tenmo_user.user_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if(results.next()){
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }


    @Override
    public Transfer  addTransferAmount(Transfer transfer) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?,?,(SELECT account_id FROM account\n" +
                "WHERE user_id = ?),(SELECT account_id FROM account\n" +
                "WHERE user_id = ?), ?) RETURNING transfer_id";

        String subtractSql = "UPDATE  account\n" +
                " SET balance = balance - ?\n" +
                " WHERE user_id = ?; ";

        jdbcTemplate.update(subtractSql, transfer.getAmount(), transfer.getAccountFrom());

        String addSql = "UPDATE  account\n" +
                " SET balance = balance + ?\n" +
                " WHERE user_id = ?; ";

        jdbcTemplate.update(addSql, transfer.getAmount(), transfer.getAccountTo());

         Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(),
                transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount() );

        return getTransfer(newId);
    }

    @Override
    public List<Transfer> listOfTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
         String sql = "SELECT transfer.transfer_id, userFrom.user_id AS user_id_from, userFrom.username AS user_from, \n" +
                 "userTo.user_id AS user_id_to, userTo.username AS user_to, transfer.amount\n" +
                 "FROM transfer\n" +
                 "JOIN account AS acctFrom ON transfer.account_from = acctFrom.account_id\n" +
                 "JOIN tenmo_user AS userFrom ON acctFrom.user_id = userFrom.user_id\n" +
                 "JOIN account AS acctTo ON transfer.account_to = acctTo.account_id\n" +
                 "JOIN tenmo_user AS userTo ON acctTo.user_id = userTo.user_id\n" +
                 "WHERE userFrom.user_id = ? OR userTo.user_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId, userId);

        while(results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    public Transfer getTransfer(int transferId) {
        Transfer transfer= null;

        String sql = "SELECT transfer_id, account_from, account_to, amount \n" +
                "From transfer\n" +
                "where transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        transfer.setId(transferId);
        transfer.setAccountFrom(results.getInt("account_from"));
        transfer.setAccountTo(results.getInt("account_to"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }



    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("transfer_id"));
        transfer.setUserFromId(rs.getInt("user_id_from"));
        transfer.setUserToId(rs.getInt("user_id_to"));
        transfer.setUsernameFrom(rs.getString("user_from"));
        transfer.setUsernameTo(rs.getString("user_to"));
        transfer.setAmount(rs.getDouble("amount"));
        return transfer;
    }



}
