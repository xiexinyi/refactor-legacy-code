package cn.xpbootcamp.legacy_code.service;

import cn.xpbootcamp.legacy_code.entity.User;
import cn.xpbootcamp.legacy_code.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WalletServiceImplTest {

    private WalletServiceImpl walletServiceImpl;
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void should_id_when_user_balance_is_equal_with_amount() {
        userRepositoryImpl = mock(UserRepositoryImpl.class);
        walletServiceImpl = new WalletServiceImpl(userRepositoryImpl);

        User buyer = new User();
        User seller = new User();

        buyer.setBalance(1);
        seller.setBalance(0);

        long buyerId = 1L;
        when(userRepositoryImpl.find(buyerId)).thenReturn(buyer);
        long sellerId = 2L;
        when(userRepositoryImpl.find(sellerId)).thenReturn(seller);

        String transactionId = walletServiceImpl.moveMoney("id", buyerId, sellerId, 1);
        assertNotNull(transactionId);
        assertThat(seller.getBalance()).isEqualTo(1);
        assertThat(buyer.getBalance()).isZero();
    }

    @Test
    void should_id_when_user_balance_is_greater_than_amount() {
        userRepositoryImpl = mock(UserRepositoryImpl.class);
        walletServiceImpl = new WalletServiceImpl(userRepositoryImpl);

        User buyer = new User();
        User seller = new User();

        buyer.setBalance(2);
        seller.setBalance(0);

        long buyerId = 1L;
        when(userRepositoryImpl.find(buyerId)).thenReturn(buyer);
        long sellerId = 2L;
        when(userRepositoryImpl.find(sellerId)).thenReturn(seller);

        String transactionId = walletServiceImpl.moveMoney("id", buyerId, sellerId, 1);
        assertNotNull(transactionId);
        assertThat(seller.getBalance()).isEqualTo(1);
        assertThat(buyer.getBalance()).isEqualTo(1);
    }

    @Test
    void should_id_when_user_balance_is_less_than_amount() {
        userRepositoryImpl = mock(UserRepositoryImpl.class);
        walletServiceImpl = new WalletServiceImpl(userRepositoryImpl);

        User buyer = new User();
        User seller = new User();

        buyer.setBalance(0);
        seller.setBalance(0);

        long buyerId = 1L;
        when(userRepositoryImpl.find(buyerId)).thenReturn(buyer);
        long sellerId = 2L;
        when(userRepositoryImpl.find(sellerId)).thenReturn(seller);

        String transactionId = walletServiceImpl.moveMoney("id", buyerId, sellerId, 1);
        assertNull(transactionId);
        assertThat(seller.getBalance()).isZero();
        assertThat(buyer.getBalance()).isZero();
    }
}