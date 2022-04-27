
import java.util.UUID;

public class Transaction {

    private final UUID identifier;
    private User recipient;
    private User sender;
    private Category category;
    private Integer amount;
    private Transaction next;

    enum Category {
        DEBIT, CREDIT
    }

    public Transaction(User recipient, User sender, Category category, Integer amount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.category = category;
        setAmount(amount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        if (category == Category.CREDIT) {
            if (amount > 0) {
                this.amount = 0;
            } else {
                this.amount = amount;
            }
        } else if (category == Category.DEBIT) {
            if (amount < 0) {
                this.amount = 0;
            } else {
                this.amount = amount;
            }
        }
    }

    public Transaction getNext() {
        return next;
    }

    public void setNext(Transaction next) {
        this.next = next;
    }
}
