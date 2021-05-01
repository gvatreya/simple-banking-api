package com.gvatreya.finmidbanking.controller;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.service.AccountService;
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
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id")final Long accountId) {
        System.out.println("TTTTTT: " + accountId);
        final AccountDto accountDto = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<Collection<AccountDto>> getAllAccounts() {
        System.out.println("GET ALL");
        final List<AccountDto> allAccounts = accountService.getAllAccounts();
        System.out.println(StringUtils.collectionToCommaDelimitedString(allAccounts));
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Long> createAccount(@RequestBody @Validated final AccountDto accountDto) {
        System.out.println("Create Account: " + accountDto);
        final Long createdAccountId = accountService.createAccount(accountDto);
        System.out.println(createdAccountId);
        return new ResponseEntity<>(createdAccountId, HttpStatus.CREATED);
    }
}
