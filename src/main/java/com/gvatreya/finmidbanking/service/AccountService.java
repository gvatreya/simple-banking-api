package com.gvatreya.finmidbanking.service;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AccountService {

    AccountDto getAccountDetails(@NonNull long accountId);

    List<AccountDto> getAllAccounts();

    AccountDto createAccount(@NonNull AccountDto accountDto);

    /**
     * This method will update the balance of the account with the
     * value passed in accountDto.balance.
     * @param accountDto Object containing detail of the Update
     */
    void updateBalance(@NonNull final AccountDto accountDto);

    /**
     * This method will debit the account with the amountToBeDebited, meaning,
     * the balance will become current balance - amountToBeDebited
     * @param accountId Id of account affected
     * @param amountToBeDebited value of the amount to be debited
     */
    void debitAccount(@NonNull final Long accountId, @NonNull final Double amountToBeDebited);

    /**
     * This method will credit the account with the amountToBeCredited, meaning,
     * the balance will become current balance + amountToBeCredited
     * @param accountId Id of account affected
     * @param amountToBeCredited value of the amount to be credited
     */
    void creditAccount(@NonNull final Long accountId, @NonNull final Double amountToBeCredited);

    boolean existsById(@NonNull final Long accountId);
}
