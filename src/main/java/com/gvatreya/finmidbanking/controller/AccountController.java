package com.gvatreya.finmidbanking.controller;

import com.gvatreya.finmidbanking.model.dto.AccountDto;
import com.gvatreya.finmidbanking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
@Tag( name = "Accounts", description = "The Accounts API")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    @ResponseBody
    @Operation(summary = "Get all Accounts", description = "Get all accounts in the system")
    public ResponseEntity<Collection<AccountDto>> getAllAccounts() {
        final List<AccountDto> allAccounts = accountService.getAllAccounts();
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create a new Account", description = "Creates new account with given details. " +
            "A default balance maybe provided, if the environment variable is set")
    public ResponseEntity<AccountDto> createAccount(@RequestBody final AccountDto accountDto) {
        final AccountDto createdAccount = accountService.createAccount(accountDto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PatchMapping
    @ResponseBody
    @Operation(summary = "Update Balance", description = "Updates the balance for a given accountId. " +
            "This API does an absolute value updation. For Debit/Credit operations, refer Transaction API")
    public ResponseEntity<String> updateBalance(@RequestBody final AccountDto accountDto) {
        accountService.updateBalance(accountDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Get account details", description = "Get account details given an accountId")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id")final Long accountId) {
        final AccountDto accountDto = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
}
