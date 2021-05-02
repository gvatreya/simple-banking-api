package com.gvatreya.finmidbanking.repository;

import com.gvatreya.finmidbanking.TestUtility;
import com.gvatreya.finmidbanking.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAllWhenEmpty() {
        final Collection<Account> all = accountRepository.findAll();
        assertTrue(all.isEmpty(), "Expected empty list, found: " + all.size());
    }

    @Test
    void findByIdWhenEmpty() {
        final Optional<Account> byId = accountRepository.findById(1L);
        assertTrue(byId.isEmpty(), "Expected empty");
    }

    @Test
    void existsByIdWhenEmpty() {
        boolean exists = accountRepository.existsById(1L);
        assertFalse(exists, "Expected false got " + exists);
    }

    @Test
    void existsAccountByAccountIdWhenEmpty() {
        boolean exists = accountRepository.existsAccountByAccountId(1L);
        assertFalse(exists, "Expected false got " + exists);
    }

    @Test
    void findAll() {
        final int countOfAccounts = 5;
        final Collection<Account> accountsToSave = TestUtility.getAccountsWithoutIds(countOfAccounts);
        accountRepository.saveAll(accountsToSave);
        final Collection<Account> accounts = accountRepository.findAll();
        assertNotNull(accounts, "accounts is null");
        assertEquals(countOfAccounts, accounts.size(), String.format("Expected %s accounts got %s", countOfAccounts, accounts.size()));
    }

    @Test
    void findById() {
        final Account save = accountRepository.save(TestUtility.getPreBuiltAccount());
        final Optional<Account> byId = accountRepository.findById(save.getId());
        assertFalse(byId.isEmpty(), "Should not be empty");
        final Account account = byId.get();
        assertNotNull(account, "account is null");
        assertEquals(TestUtility.ACCOUNT_ID, account.getAccountId(), "accountId mismatch");
        assertNotNull(TestUtility.ID, "id is null");
        assertEquals(TestUtility.BALANCE, account.getBalance(), "balance mismatch");
        assertEquals(TestUtility.CURRENT_TIME, account.getCreated(), "created mismatch");
        assertEquals(TestUtility.CURRENT_TIME, account.getUpdated(), "updated mismatch");
        assertNull(account.getDeleted(), "expected deleted to be null");
    }

    @Test
    void save() {
        final Account account = accountRepository.save(TestUtility.getPreBuiltAccount());
        assertNotNull(account, "account is null");
        assertEquals(TestUtility.ACCOUNT_ID, account.getAccountId(), "accountId mismatch");
        assertNotNull(TestUtility.ID, "id mismatch");
        assertEquals(TestUtility.BALANCE, account.getBalance(), "balance mismatch");
        assertEquals(TestUtility.CURRENT_TIME, account.getCreated(), "created mismatch");
        assertEquals(TestUtility.CURRENT_TIME, account.getUpdated(), "updated mismatch");
        assertNull(account.getDeleted(), "expected deleted to be null");
    }

    @Test
    void existsById() {
        final Account account = accountRepository.save(TestUtility.getPreBuiltAccount());
        final boolean exists = accountRepository.existsById(account.getId());
        assertTrue(exists, "Expected true got " + exists);
    }

    @Test
    void existsByIdexistsAccountByAccountId() {
        final Account account = accountRepository.save(TestUtility.getPreBuiltAccount());
        final boolean exists = accountRepository.existsAccountByAccountId(account.getAccountId());
        assertTrue(exists, "Expected true got " + exists);
    }

    @Test
    void updateBalance() {

        final Double newBalance = 4421.324D;

        // Save default account
        final Account savedAccount = accountRepository.save(TestUtility.getPreBuiltAccount());
        final int i = accountRepository.updateBalance(savedAccount.getAccountId(), newBalance);
        assertEquals(1, i, "No of RowsUpdated mismatch");

        // Fetch the account and test for new balance
        final Optional<Account> byId = accountRepository.findAccountByAccountId(savedAccount.getAccountId());
        assertFalse(byId.isEmpty(), "Should not be empty");
        final Account account = byId.get();
        assertNotNull(account, "account is null");
        assertEquals(TestUtility.ACCOUNT_ID, account.getAccountId(), "accountId mismatch");
        assertEquals(newBalance, account.getBalance(), "balance mismatch");

    }

    @Test
    void debitAccountBalance() {

        final Double amountToBeDebited = 100D;
        final Double balanceAfterDebit = TestUtility.BALANCE - amountToBeDebited;

        // Save default account
        final Account savedAccount = accountRepository.save(TestUtility.getPreBuiltAccount());

        final int i = accountRepository.debitAccountBalance(savedAccount.getAccountId(), amountToBeDebited);
        assertEquals(1, i, "No of RowsUpdated mismatch");

        // Fetch the account and test for new balance
        final Optional<Account> byId = accountRepository.findAccountByAccountId(savedAccount.getAccountId());
        assertFalse(byId.isEmpty(), "Should not be empty");
        final Account account = byId.get();
        assertNotNull(account, "account is null");
        assertEquals(TestUtility.ACCOUNT_ID, account.getAccountId(), "accountId mismatch");
        assertEquals(balanceAfterDebit, account.getBalance(), "balance mismatch");
    }

    @Test
    void creditAccountBalance() {

        final Double amountToBeDebited = 100D;
        final Double balanceAfterDebit = TestUtility.BALANCE + amountToBeDebited;

        // Save default account
        final Account savedAccount = accountRepository.save(TestUtility.getPreBuiltAccount());

        final int i = accountRepository.creditAccountBalance(savedAccount.getAccountId(), amountToBeDebited);
        assertEquals(1, i, "No of RowsUpdated mismatch");

        // Fetch the account and test for new balance
        final Optional<Account> byId = accountRepository.findAccountByAccountId(savedAccount.getAccountId());
        assertFalse(byId.isEmpty(), "Should not be empty");
        final Account account = byId.get();
        assertNotNull(account, "account is null");
        assertEquals(TestUtility.ACCOUNT_ID, account.getAccountId(), "accountId mismatch");
        assertEquals(balanceAfterDebit, account.getBalance(), "balance mismatch");
    }

}