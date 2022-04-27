
import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {

    private Transaction head;
    private Transaction last;
    private Integer size = 0;

    @Override
    public void addTransaction(Transaction transaction) {
        if (this.head == null) {
            this.head = transaction;
        } else {
            last.setNext(transaction);
        }

        last = transaction;
        size++;
    }

    @Override
    public void removeTransactionById(UUID uuid) {
        if (head != null) {
            Transaction temp = head.getNext();
            Transaction prev = head;

            if (prev.getIdentifier() == uuid) {
                head = temp;
                size--;
                return;
            }

            while (temp != null) {
                if (temp.getIdentifier() == uuid) {
                    prev.setNext(temp.getNext());
                    size--;
                    return;
                }
                temp = temp.getNext();
                prev = prev.getNext();
            }
        }
        throw new TransactionNotFoundException("Transaction not found!");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactions = new Transaction[size];

        Integer i = 0;

        Transaction temp = head;

        while (i < size) {
            transactions[i] = temp;
            temp = temp.getNext();
            i++;
        }
        return transactions;
    }

    public Integer getSize() {
        return size;
    }
}
