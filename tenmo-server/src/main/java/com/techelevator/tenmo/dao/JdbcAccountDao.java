package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{
    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalanceByUserId(int id) {

        String sql = "SELECT account_id, account.user_id, balance FROM account " +
                " JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                " WHERE tenmo_user.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if(results.next()){
            return mapRowToAccount(results);
        } else {
            return null;
        }
    }
    @Override
    public Account getAccountIdByUser(int id) {
        String sql = "SELECT user_id FROM account where account_id = ?; ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if(results.next()){
            return mapRowToAccount(results);
        } else {
            return null;
        }
    }



    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
