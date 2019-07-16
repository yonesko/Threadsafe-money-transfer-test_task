package glebio.bank.data.model;


import java.time.Instant;
import java.util.UUID;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author Gleb Danichev
 */
public class Transfer {

    private final UUID uuid = UUID.randomUUID();

    private final Instant created = Instant.now();

    private final UUID fromAccountId;

    private final UUID toAccountId;

    private final long cents;

    public Transfer(UUID fromAccountId, UUID toAccountId, long cents) {
        check(fromAccountId, toAccountId, cents);
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.cents = cents;
    }

    private void check(UUID fromAccountId, UUID toAccountId, long cents) {
        Preconditions.checkArgument(cents >= 0, "negative sum %s", cents);
        Preconditions.checkArgument(!fromAccountId.equals(toAccountId), "same from and to %s", fromAccountId);
        Preconditions.checkNotNull(fromAccountId, "fromAccountId");
        Preconditions.checkNotNull(toAccountId, "toA");
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getCreated() {
        return created;
    }

    public UUID getFromAccountId() {
        return fromAccountId;
    }

    public UUID getToAccountId() {
        return toAccountId;
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
               Objects.equal(fromAccountId, transfer.fromAccountId) &&
               Objects.equal(toAccountId, transfer.toAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid, created, fromAccountId, toAccountId, cents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("uuid", uuid)
            .add("created", created)
            .add("fromAccountId", fromAccountId)
            .add("toAccountId", toAccountId)
            .add("cents", cents)
            .toString();
    }
}
