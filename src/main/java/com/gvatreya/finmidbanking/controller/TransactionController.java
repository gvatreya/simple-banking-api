package com.gvatreya.finmidbanking.controller;

import com.gvatreya.finmidbanking.model.dto.TransactionDto;
import com.gvatreya.finmidbanking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/transactions")
@Validated
@Tag( name = "Transaction", description = "The Transactions API")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/account/{id}")
    @ResponseBody
    @Operation(summary = "Find transactions by accountId", description = "Get all transactions where the accountId is a participant")
    public ResponseEntity<Collection<TransactionDto>> getTransactionsForAccount(@PathVariable("id")final Long accountId) {
        final Collection<TransactionDto> allTransactionsForAccount = transactionService.getAllTransactionsForAccount(accountId);
        return new ResponseEntity<>(allTransactionsForAccount, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    @Operation(summary = "Find transactions by transaction uuid", description = "Get a transaction by its UUID")
    public ResponseEntity<TransactionDto> getTransactionsForUuid(@PathVariable("uuid")final String transactionUuid) {
        final TransactionDto transactionDetails = transactionService.getTransactionDetails(transactionUuid);
        return new ResponseEntity<>(transactionDetails, HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create new Transaction", description = "Make a new transaction between accounts")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody final TransactionDto transactionDto) {
        final TransactionDto transaction = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }


}
