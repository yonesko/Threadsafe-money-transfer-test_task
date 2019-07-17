package glebio.bank.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import glebio.bank.data.model.Account;
import glebio.bank.data.model.Transfer;

/**
 * @author Gleb Danichev
 */
public final class Db {

    private final static Db ourInstance = new Db();

    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    private final List<Transfer> transfers = Collections.synchronizedList(new LinkedList<>());

    public static Db getInstance() {
        return ourInstance;
    }

    private Db() {
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public Map<UUID, Account> getAccounts() {
        return accounts;
    }
}
