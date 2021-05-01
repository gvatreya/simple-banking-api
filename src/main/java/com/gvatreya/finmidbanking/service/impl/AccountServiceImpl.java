package com.gvatreya.finmidbanking.service.impl;

import com.gvatreya.finmidbanking.model.Account;
import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.repository.AccountRepository;
import com.gvatreya.finmidbanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto getAccountDetails(long accountId) {
        final Optional<Account> byId = accountRepository.findById(1L);
        System.out.println(accountRepository);
        System.out.println(byId);
        final AccountDto accountDto = byId.map(AccountDto::fromModel).orElse(null);
        System.out.println(accountDto);
        return accountDto;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        final List<Account> accounts = accountRepository.findAll();
        System.out.println("ACCOUNTS:" + StringUtils.collectionToCommaDelimitedString(accounts));
        final List<AccountDto> accountDtos = accounts.stream().map(AccountDto::fromModel).toList();
        System.out.println("ACCOUNTDTO:" + StringUtils.collectionToCommaDelimitedString(accountDtos));
        return accountDtos;
    }

    @Override
    public long createAccount(AccountDto accountDto) {
        final Date currentTime = new Date();
        final Account account = Account.builder()
                .accountId(accountDto.getAccountId())
                .balance(accountDto.getBalance())
                .created(currentTime)
                .updated(currentTime)
                .build();
        final Account savedAccount = accountRepository.save(account);
        System.out.println(savedAccount);
        return savedAccount.getAccountId();
    }
}
