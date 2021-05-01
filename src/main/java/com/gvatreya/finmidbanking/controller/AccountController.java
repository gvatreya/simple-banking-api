package com.gvatreya.finmidbanking.controller;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.service.AccountService;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Collection<AccountDto>> getAllAccounts() {
        final List<AccountDto> allAccounts = accountService.getAllAccounts();
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Long> createAccount(@RequestBody final AccountDto accountDto) {
        final Long createdAccountId = accountService.createAccount(accountDto);
        return new ResponseEntity<>(createdAccountId, HttpStatus.CREATED);
    }

    @PatchMapping
    @ResponseBody
    public ResponseEntity<String> updateBalance(@RequestBody final AccountDto accountDto) {
        accountService.updateBalance(accountDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id")final Long accountId) {
        final AccountDto accountDto = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
}
