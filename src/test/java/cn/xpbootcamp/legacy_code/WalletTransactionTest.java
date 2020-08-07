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
    private static final String ORDER_ID = "orderId";
    private static final String PRE_ASSIGNED_ID = "preAssignedId";
    private static final String EXCEPTION_MESSAGE = "This is an invalid transaction";

    private WalletTransaction walletTransaction;

    @Test
    void should_set_pre_assigned_id_with_t_prefix_to_id_if_it_is_not_null_or_empty() {
        walletTransaction = new WalletTransaction(
                PRE_ASSIGNED_ID, BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        assertThat(walletTransaction.getId()).isEqualTo("t_" + PRE_ASSIGNED_ID);
    }

    @Test
    void should_set_pre_assigned_id_to_id_if_it_starts_with_t() {
        walletTransaction = new WalletTransaction(
                "t_" + PRE_ASSIGNED_ID, BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        assertThat(walletTransaction.getId()).isEqualTo("t_" + PRE_ASSIGNED_ID);
    }

    @Test
    void should_set_id_if_pre_assigned_id_is_null() {
        walletTransaction = new WalletTransaction(
                null, BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        assertThat(walletTransaction.getId()).isNotNull();
    }

    @Test
    void should_set_id_if_pre_assigned_id_is_empty() {
        walletTransaction = new WalletTransaction(
                "", BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        assertThat(walletTransaction.getId()).isNotBlank();
    }

    @Test
    void should_throw_exception_if_buyer_id_is_null() {
        walletTransaction = new WalletTransaction(
                PRE_ASSIGNED_ID, null, SELLER_ID, PRODUCT_ID, ORDER_ID);

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
    }

    @Test
    void should_throw_exception_if_seller_id_is_null() {
        walletTransaction = new WalletTransaction(
                PRE_ASSIGNED_ID, BUYER_ID, null, PRODUCT_ID, ORDER_ID);

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
    }

    @Test
    void should_throw_exception_if_amount_is_less_than_zero() {
        walletTransaction = new WalletTransaction(
                PRE_ASSIGNED_ID, BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        walletTransaction.setAmount(-1.0);

        InvalidTransactionException invalidTransactionException = assertThrows(InvalidTransactionException.class,
                () -> walletTransaction.execute());

        assertThat(invalidTransactionException.getMessage()).isEqualTo(EXCEPTION_MESSAGE);
    }

    @Test
    void should_return_true_when_status_is_executed() throws InvalidTransactionException {
        walletTransaction = new WalletTransaction(
                PRE_ASSIGNED_ID, BUYER_ID, SELLER_ID, PRODUCT_ID, ORDER_ID);

        walletTransaction.setAmount(0.0);
        walletTransaction.setStatus(STATUS.EXECUTED);

        assertTrue(walletTransaction.execute());
    }
}