package glebio.bank.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import glebio.bank.data.model.Account;
import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
public class Db {

    private static Db ourInstance = new Db();

    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    private final List<Transfer> transfers = Collections.synchronizedList(new LinkedList<>());

    public static Db getInstance() {
        return ourInstance;
    }

    private Db() {
    }

    public void addAccount(Account account) {
        accounts.putIfAbsent(account.getId(), account);
    }

    public Optional<Account> getAccount(UUID accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
    }

    public List<Transfer> getTransfers() {
        return List.copyOf(transfers);
    }
}
