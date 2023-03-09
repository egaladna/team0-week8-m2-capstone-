package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTest extends BaseDaoTests {

    protected static final User USER_1 = new User(1001, "user1", "user1", "USER");
    protected static final User USER_2 = new User(1002, "user2", "user2", "USER");
    private static final User USER_3 = new User(1003, "user3", "user3", "USER");

    private JdbcTransferDao sut;
    private Transfer testTransfer;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        testTransfer = new Transfer(0,1,1,2001,2003,1000.00);
    }



    @Test
    public void get_transfer_amount_by_user_id() {
        Transfer createTransfer = sut.addTransferAmount(testTransfer);
        testTransfer.setId(createTransfer.getId());
        Transfer retrievedTransfer = sut.getTransfer(createTransfer.getId());



        Assert.assertEquals(testTransfer,retrievedTransfer);
    }

//    @Test
//    public void get_transfer_returns_correct_transfer_for_id(){
//    Transfer retrievedTransfer = sut.getTransfer(3001);
//
//    Assert.assertEquals(USER_1,retrievedTransfer);
//
//
//    }




}
