package com.gvatreya.finmidbanking.service;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface AccountService {

    AccountDto getAccountDetails(@NonNull long accountId);
    List<AccountDto> getAllAccounts();
    long createAccount(@NonNull @Validated AccountDto accountDto);
}
