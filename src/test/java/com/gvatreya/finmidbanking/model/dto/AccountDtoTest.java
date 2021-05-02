package com.gvatreya.finmidbanking.model.dto;

import com.gvatreya.finmidbanking.TestUtility;
import com.gvatreya.finmidbanking.model.Account;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static com.gvatreya.finmidbanking.TestUtility.*;

class AccountDtoTest {

    @Test
    void fromModel() {
        final Account account = TestUtility.getPreBuiltAccount();
        final AccountDto accountDto = AccountDto.fromModel(account);
        Assertions.assertNotNull(accountDto, "accountDto is null");
        Assertions.assertEquals(TestUtility.ACCOUNT_ID, accountDto.getAccountId(), "accountId mismatch");
        Assertions.assertEquals(TestUtility.BALANCE, accountDto.getBalance(), "balance mismatch");

    }

    @Test
    void validate() {
        AccountDto accountDto = AccountDto.builder()
                .accountId(1L)
                .balance(100D)
                .build();

        // No Error Case
        ValidationResponse response = accountDto.validate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertTrue(response.isValid(), "Expected true, got " + response.isValid());
        Assertions.assertTrue(response.getProblems().isEmpty(), "Expected problems to be empty list but got list of size " + response.getProblems().size());

        // Check for Negative Values
        accountDto.setAccountId(-1L);
        accountDto.setBalance(-1D);
        response = accountDto.validate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertFalse(response.isValid(), "Expected false, got " + response.isValid());
        Assertions.assertEquals(2, response.getProblems().size(), "Expected problems to be 2 but got list of size " + response.getProblems().size());
        Assertions.assertEquals("accountId is negative", response.getProblems().get(0), "Error mismatch ");
        Assertions.assertEquals("balance is negative", response.getProblems().get(1), "Error mismatch ");

        // Check for Nulls
        accountDto.setAccountId(null);
        accountDto.setBalance(null);
        response = accountDto.validate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertFalse(response.isValid(), "Expected false, got " + response.isValid());
        Assertions.assertEquals(2, response.getProblems().size(), "Expected problems to be 2 but got list of size " + response.getProblems().size());
        Assertions.assertEquals("accountId is null", response.getProblems().get(0), "Error mismatch ");
        Assertions.assertEquals("balance is null", response.getProblems().get(1), "Error mismatch ");

    }
}