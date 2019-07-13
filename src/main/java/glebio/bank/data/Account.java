package glebio.bank.data;

import java.util.UUID;

/**
 * @author Gleb Danichev
 */
public class Account {

    private final UUID uuid = UUID.randomUUID();

    private long cents;

    public UUID getUuid() {
        return uuid;
    }

    public long getCents() {
        return cents;
    }

    public void setCents(long cents) {
        this.cents = cents;
    }
}
