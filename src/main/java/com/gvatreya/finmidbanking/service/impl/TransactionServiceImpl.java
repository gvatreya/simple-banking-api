package com.gvatreya.finmidbanking.service.impl;

import com.gvatreya.finmidbanking.exceptions.ApplicationException;
import com.gvatreya.finmidbanking.model.Transaction;
import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.model.dto.TransactionDto;
import com.gvatreya.finmidbanking.repository.TransactionRepository;
import com.gvatreya.finmidbanking.service.AccountService;
import com.gvatreya.finmidbanking.service.TransactionService;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private Environment environment;

    @Override
    public @Nullable TransactionDto getTransactionDetails(@NonNull final String transactionUuid) {
        if(StringUtils.hasText(transactionUuid)) {
            LOG.info(String.format("Fetching transactionDetails for %s", transactionUuid));
            final Optional<Transaction> transaction = transactionRepository.findTransactionsByTransactionUuid(transactionUuid);
            LOG.debug("Returning: " + transaction);
            return transaction.map(TransactionDto::fromModel).orElse(null);
        } else {
            throw new IllegalArgumentException("Invalid transactionUuid: " + transactionUuid);
        }
    }

    @Override
    public Collection<TransactionDto> getAllTransactionsForAccount(@NonNull final Long accountId) {
        if(accountId < 1) {
            throw new IllegalArgumentException("Invalid accountId: " + accountId);
        }
        LOG.info("Fetching all transactions for account: " + accountId);
        final Collection<Transaction> transactions = transactionRepository.findTransactionsByAccountId(accountId);
        LOG.debug("Transactions: " + StringUtils.collectionToCommaDelimitedString(transactions));
        final Collection<TransactionDto> transactionDtos = transactions.stream()
                .map(TransactionDto::fromModel).collect(Collectors.toList());
        LOG.debug("TransactionDTOs: " + StringUtils.collectionToCommaDelimitedString(transactionDtos));
        return transactionDtos;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public @Nullable TransactionDto createTransaction(@NonNull final TransactionDto transactionDto) {

        final ValidationResponse validationResponse = transactionDto.validateForCreate();

        if (!validationResponse.isValid()) {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }

        // Verify that the source and destination accounts exist
        if(!accountService.existsByAccountId(transactionDto.getSourceAccountId())) {
            throw new ApplicationException(String.format("Source Account with id:%s does not exist.", transactionDto.getSourceAccountId()));
        }
        if(!accountService.existsByAccountId(transactionDto.getDestAccountId())) {
            throw new ApplicationException(String.format("Destination Account with id:%s does not exist.", transactionDto.getDestAccountId()));
        }

        // Need to check if the sender's account has sufficient
        // balance to complete the transaction
        final boolean hasSufficientBalance = hasSufficientBalance(transactionDto.getSourceAccountId(), transactionDto.getValue());

        if(!hasSufficientBalance) {
            throw new ApplicationException("Sender does not have sufficient balance to proceed with this transaction");
        }

        // Debit sender's account
        LOG.info("Debiting amount from account");
        accountService.debitAccount(transactionDto.getSourceAccountId(), transactionDto.getValue());

        // Credit receiver's account
        LOG.info("Crediting amount to account");
        accountService.creditAccount(transactionDto.getDestAccountId(), transactionDto.getValue());

        // Create Transactions record to reflect the transaction
        final Transaction transactionToBeSaved = buildTransactionForSaving(transactionDto);
        final Transaction savedTransaction = transactionRepository.save(transactionToBeSaved);
        LOG.debug("SavedTransaction: " + savedTransaction);

        return TransactionDto.fromModel(savedTransaction);
    }

    @Override
    public boolean hasSufficientBalance(@NonNull final Long accountId, @NonNull final Double transactionAmount) {
        final Double MINIMUM_BALANCE = environment.getProperty("balance.minimum", Double.class);
        if(null == MINIMUM_BALANCE) {
            throw new ApplicationException("Could not fetch the minimum balance from environment");
        }
        LOG.info("MINIMUM_BALANCE: " + MINIMUM_BALANCE);

        final AccountDto accountDto = accountService.getAccountDetails(accountId);
        if(null == accountDto) {
            throw new ApplicationException("Failed to fetch details for accountId: " + accountId);
        }
        LOG.debug("AccountDto: " + accountDto);
        LOG.info(String.format("Current balance is %s in account %s", accountDto.getBalance(), accountDto.getAccountId()));
        LOG.info("Amount to be debited: " + transactionAmount);

        final double balanceRemainingAfterTxn = accountDto.getBalance() - transactionAmount;
        LOG.info("Balance remaining in account if transaction succeds: " + balanceRemainingAfterTxn);

        if(balanceRemainingAfterTxn < MINIMUM_BALANCE) {
            LOG.warn("Account will be left with less than the required Minimum Balance. Disallowing request");
            return false;
        } else {
            LOG.info("Allowing transaction request as account will have sufficient balance post debit");
            return true;
        }
    }

    /**
     * Helper method that generates a Transaction POJO object from it's transport
     * by generating the UUID and setting time.
     * @param transactionDto Transaction Transport object that is the source of the
     *                       transaction object.
     * @return Transaction
     */
    private Transaction buildTransactionForSaving(@NonNull final TransactionDto transactionDto) {
        return Transaction.builder()
                .transactionUuid(UUID.randomUUID().toString())
                .value(transactionDto.getValue())
                .sourceAccountId(transactionDto.getSourceAccountId())
                .destAccountId(transactionDto.getDestAccountId())
                .time(new Date())
                .build();
    }
}
