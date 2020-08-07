package cn.xpbootcamp.legacy_code;

import cn.xpbootcamp.legacy_code.enums.STATUS;
import org.junit.jupiter.api.Test;

import javax.transaction.InvalidTransactionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WalletTransactionTest {

    private static final Long BUYER_ID = 1L;
    private static final Long SELLER_ID = 2L;
    private static final Long PRODUCT_ID = 3L;

    private WalletTransaction walletTransaction;

    @Test
    void should_throw_exception_if_buyer_id_is_null() {
        walletTransaction = new WalletTransaction(
                "preAssignedId", null, SELLER_ID, PRODUCT_ID, "orderId");

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo("This is an invalid transaction");
    }

    @Test
    void should_throw_exception_if_seller_id_is_null() {
        walletTransaction = new WalletTransaction(
                "preAssignedId", BUYER_ID, null, PRODUCT_ID, "orderId");

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo("This is an invalid transaction");
    }

    @Test
    void should_throw_exception_if_amount_is_less_than_zero() {
        walletTransaction = new WalletTransaction(
                "preAssignedId", BUYER_ID, SELLER_ID, PRODUCT_ID, "orderId");

        walletTransaction.setAmount(-1.0);

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo("This is an invalid transaction");
    }

    @Test
    void should_return_true_when_status_is_executed() throws InvalidTransactionException {
        walletTransaction = new WalletTransaction(
                "preAssignedId", BUYER_ID, SELLER_ID, PRODUCT_ID, "orderId");

        walletTransaction.setAmount(0.0);
        walletTransaction.setStatus(STATUS.EXECUTED);

        assertTrue(walletTransaction.execute());
    }
}