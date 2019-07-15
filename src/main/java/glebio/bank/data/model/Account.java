package glebio.bank.data.model;

import java.util.UUID;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author Gleb Danichev
 */
public class Account {

    private final UUID id = UUID.randomUUID();

    private long cents;

    public UUID getId() {
        return id;
    }

    public long getCents() {
        return cents;
    }

    public void addCents(long cents) {
        Preconditions.checkArgument(cents >= 0, "negative sum %s", cents);
        setCents(this.cents + cents);
    }

    public void subtractCents(long cents) {
        Preconditions.checkArgument(cents >= 0, "negative sum %s", cents);
        setCents(this.cents - cents);
    }

    private void setCents(long cents) {
        Preconditions.checkArgument(cents >= 0, "negative sum %s", cents);
        this.cents = cents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return cents == account.cents && Objects.equal(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, cents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("cents", cents)
            .toString();
    }
}
