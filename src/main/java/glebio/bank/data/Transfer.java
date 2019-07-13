package glebio.bank.data;


import java.time.Instant;
import java.util.UUID;

/**
 * @author Gleb Danichev
 */
public class Transfer {

    private final UUID uuid = UUID.randomUUID();

    private final Instant created = Instant.now();

    private final UUID fromAccount;

    private final UUID toAccount;

    private final long cents;

    public Transfer(UUID fromAccount, UUID toAccount, long cents) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.cents = cents;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getCreated() {
        return created;
    }

    public UUID getFromAccount() {
        return fromAccount;
    }

    public UUID getToAccount() {
        return toAccount;
    }

    public long getCents() {
        return cents;
    }
}
