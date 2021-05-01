package com.gvatreya.finmidbanking.service;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AccountService {

    AccountDto getAccountDetails(@NonNull long accountId);
    List<AccountDto> getAllAccounts();
    Long createAccount(@NonNull AccountDto accountDto);
    void updateBalance(@NonNull final AccountDto accountDto);
}
