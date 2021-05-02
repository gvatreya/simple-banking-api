package com.gvatreya.finmidbanking.model.dto;

import com.gvatreya.finmidbanking.TestUtility;
import com.gvatreya.finmidbanking.model.Transaction;
import com.gvatreya.finmidbanking.utils.ValidationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

class TransactionDtoTest {

    @Test
    void fromModel() {
        final Transaction transaction = TestUtility.getPreBuiltTransaction();
        final TransactionDto transactionDto = TransactionDto.fromModel(transaction);
        Assertions.assertNotNull(transactionDto, "transactionDto is null");
        Assertions.assertEquals(TestUtility.UUID_STRING, transactionDto.getTransactionUuid(), "transactionUuid mismatch");
        Assertions.assertEquals(TestUtility.SRC_ACC_ID, transactionDto.getSourceAccountId(), "sourceAccountId mismatch");
        Assertions.assertEquals(TestUtility.DEST_ACC_ID, transactionDto.getDestAccountId(), "destinationAccountId mismatch");
        Assertions.assertEquals(TestUtility.VALUE, transactionDto.getValue(), "destinationAccountId mismatch");
        Assertions.assertEquals(TestUtility.CURRENT_TIME, transactionDto.getTime(), "time mismatch");
    }

    @Test
    void validateForCreate() {

        TransactionDto transactionDto = TransactionDto.builder()
                .transactionUuid(TestUtility.UUID_STRING)
                .sourceAccountId(TestUtility.SRC_ACC_ID)
                .destAccountId(TestUtility.DEST_ACC_ID)
                .value(TestUtility.VALUE)
                .time(TestUtility.CURRENT_TIME)
                .build();

        // No Error case
        ValidationResponse response = transactionDto.validateForCreate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertTrue(response.isValid(), "Expected true, got " + response.isValid());
        Assertions.assertTrue(response.getProblems().isEmpty(), "Expected problems to be empty list but got list of size " + response.getProblems().size());

        // Check for Nulls
        transactionDto.setSourceAccountId(null);
        transactionDto.setDestAccountId(null);
        transactionDto.setValue(null);
        response = transactionDto.validateForCreate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertFalse(response.isValid(), "Expected false, got " + response.isValid());
        Assertions.assertEquals(3, response.getProblems().size(), "Expected problems to be 2 but got list of size " + response.getProblems().size());
        Assertions.assertEquals("sourceAccountId is null", response.getProblems().get(0), "Error mismatch ");
        Assertions.assertEquals("destAccountId is null", response.getProblems().get(1), "Error mismatch ");
        Assertions.assertEquals("value is null", response.getProblems().get(2), "Error mismatch");

        // Check for Negatives
        transactionDto.setSourceAccountId(-1L);
        transactionDto.setDestAccountId(-2L);
        transactionDto.setValue(-200D);
        response = transactionDto.validateForCreate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertFalse(response.isValid(), "Expected false, got " + response.isValid());
        Assertions.assertEquals(3, response.getProblems().size(), "Expected problems to be 2 but got list of size " + response.getProblems().size());
        Assertions.assertEquals("sourceAccountId is negative", response.getProblems().get(0), "Error mismatch");
        Assertions.assertEquals("destAccountId is negative", response.getProblems().get(1), "Error mismatch");
        Assertions.assertEquals("value is negative", response.getProblems().get(2), "Error mismatch");

        // Check that the source and destination accounts are not same
        transactionDto.setSourceAccountId(1L);
        transactionDto.setDestAccountId(1L);
        transactionDto.setValue(200D);
        response = transactionDto.validateForCreate();
        Assertions.assertNotNull(response, "response is null");
        Assertions.assertFalse(response.isValid(), "Expected false, got " + response.isValid());
        Assertions.assertEquals(1, response.getProblems().size(), "Expected problems to be 2 but got list of size " + response.getProblems().size());
        Assertions.assertEquals("Source and destination account ids are same and must be different", response.getProblems().get(0), "Error mismatch");

    }
}