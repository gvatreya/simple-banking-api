package com.gvatreya.finmidbanking.service.impl;

import com.gvatreya.finmidbanking.exceptions.ApplicationException;
import com.gvatreya.finmidbanking.model.Account;
import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.repository.AccountRepository;
import com.gvatreya.finmidbanking.service.AccountService;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto getAccountDetails(@NonNull long accountId) {
        if(accountId < 1) {
            final String errorMessage = String.format("AccountId(%s) cannot be less than 1", accountId);
            LOG.info(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOG.info(String.format("Fetching accountDetails for %s", accountId));
        final Optional<Account> byId = accountRepository.findById(accountId);
        final AccountDto accountDto = byId.map(AccountDto::fromModel).orElse(null);
        LOG.debug("Returning: " + accountDto);
        return accountDto;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        LOG.info("Fetching all accountDetails");
        final List<Account> accounts = accountRepository.findAll();
        LOG.debug("All Accounts: " + StringUtils.collectionToCommaDelimitedString(accounts));
        final List<AccountDto> accountDtos = accounts.stream().map(AccountDto::fromModel).toList();
        LOG.debug("Returning AccountDtos: " + StringUtils.collectionToCommaDelimitedString(accountDtos));
        return accountDtos;
    }

    @Override
    public Long createAccount(@NonNull AccountDto accountDto) {

        final ValidationResponse validationResponse = accountDto.validate();

        if(validationResponse.isValid()) {
            LOG.info("Creating new account details for: " + accountDto);
            final Account savedAccount = accountRepository.save(buildAccountForSaving(accountDto));
            LOG.debug("Created account details: "+ savedAccount);
            return savedAccount.getAccountId();
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void updateBalance(@NonNull final AccountDto accountDto) {

        final ValidationResponse validationResponse = accountDto.validate();

        if(validationResponse.isValid()) {
            LOG.info("Updating account's balance: " + accountDto);
            final int rowsAffected = accountRepository.updateBalance(accountDto.getAccountId(), accountDto.getBalance());
            LOG.debug("rowsAffected: "+ rowsAffected);
            if(rowsAffected != 1) {
                throw new ApplicationException("Updated row count mismatch, expected 1 got " + rowsAffected);
            }
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }

    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void debitAccount(@NonNull final Long accountId, @NonNull final Double amountToBeDebited) {

        if(amountToBeDebited < 1 || accountId < 1) {
            throw new ApplicationException(String.format("AccountId %s and or AmountToBeDebited %s is invalid", accountId, amountToBeDebited));
        }

        LOG.info(String.format("Debiting %s from account %s ", amountToBeDebited, accountId));
        final int rowsAffected = accountRepository.debitAccountBalance(accountId, amountToBeDebited);
        LOG.debug("rowsAffected: "+ rowsAffected);
        if(rowsAffected != 1) {
            throw new ApplicationException("Updated row count mismatch, expected 1 got " + rowsAffected);
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void creditAccount(@NonNull final Long accountId, @NonNull final Double amountToBeCredited) {

        if(amountToBeCredited < 1 || accountId < 1) {
            throw new ApplicationException(String.format("AccountId %s and or AmountToBeCredited %s is invalid", accountId, amountToBeCredited));
        }

        LOG.info(String.format("Crediting %s to account %s ", amountToBeCredited, accountId));
        final int rowsAffected = accountRepository.creditAccountBalance(accountId, amountToBeCredited);
        LOG.debug("rowsAffected: "+ rowsAffected);
        if(rowsAffected != 1) {
            throw new ApplicationException("Updated row count mismatch, expected 1 got " + rowsAffected);
        }
    }

    /**
     * Helper method that builds an {@link Account} from {@link AccountDto}.
     * The book keeping attributes are set here, and if the environment property
     * {@code balance.use.default} is set, then the passed in balance is ignored.
     * @param accountDto Incoming accountDto object
     * @return {@link Account}
     */
    private Account buildAccountForSaving(@NonNull final AccountDto accountDto){

        final Boolean useDefaultBalance = env.getProperty("balance.use.default", Boolean.class);
        final Double defaultBalance = env.getProperty("balance.default", Double.class);

        Double balance = accountDto.getBalance();

        // If the env variable to set balance is set, use the default balance

        if(null != useDefaultBalance && null != defaultBalance) {
            LOG.debug("balance.set.default: " + useDefaultBalance);
            LOG.debug("balance.default: " + defaultBalance);
            balance = defaultBalance;
        }

        final Date currentTime = new Date();
        return Account.builder()
                .accountId(accountDto.getAccountId())
                .balance(balance)
                .created(currentTime)
                .updated(currentTime)
                .build();

    }

}
