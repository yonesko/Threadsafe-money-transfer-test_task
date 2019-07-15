package glebio.bank.core.account;

import java.util.UUID;

import glebio.bank.data.model.Account;

public interface AccountManager {

    void replenish(UUID accountId, long cents);

    /**
     * Creates and saves {@link Account}
     *
     * @return {@link Account#getId()}
     */
    Account addAccount();

    /**
     * @return {@link Account} or null if not found
     */
    Account getAccount(UUID accountId);
}
