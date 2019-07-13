package glebio.bank.data;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gleb Danichev
 */
public class Db {

    private static Db ourInstance = new Db();

    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();

    public static Db getInstance() {
        return ourInstance;
    }

    private Db() {
    }
}
