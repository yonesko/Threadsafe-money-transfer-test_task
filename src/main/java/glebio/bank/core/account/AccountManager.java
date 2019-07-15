package glebio.bank.core.account;

import java.util.UUID;

public interface AccountManager {

    void replenish(UUID accountId, long cents);
}
