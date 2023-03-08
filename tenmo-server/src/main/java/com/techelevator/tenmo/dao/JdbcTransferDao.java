package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao {


    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private final JdbcTemplate jdbcTemplate;

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
    public Transfer addTransferAmount(Transfer transfer) {
        String sql
    }
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setId(rs.getInt("id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("account_to"));
        return transfer;
    }



}
