package glebio.bank.data;


import java.time.Instant;
import java.util.UUID;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return cents == transfer.cents &&
               Objects.equal(uuid, transfer.uuid) &&
               Objects.equal(created, transfer.created) &&
               Objects.equal(fromAccount, transfer.fromAccount) &&
               Objects.equal(toAccount, transfer.toAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, created, fromAccount, toAccount, cents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("uuid", uuid)
            .add("created", created)
            .add("fromAccount", fromAccount)
            .add("toAccount", toAccount)
            .add("cents", cents)
            .toString();
    }
}
