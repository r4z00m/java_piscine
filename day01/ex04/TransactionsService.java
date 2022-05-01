
import java.util.UUID;

public class TransactionsService {

    public static final String ILLEGAL_TRANSACTION = "Illegal transaction!";

    private final UsersList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.addUser(user);
    }

    public Integer getBalance(User user) {
        return usersList.getUserById(user.getIdentifier()).getBalance();
    }

    public void executeTransaction(Integer recipientId, Integer senderId, Integer amount) {
        User recipient = usersList.getUserById(recipientId);
        User sender = usersList.getUserById(senderId);

        if (amount < 0 || sender.getBalance() < amount || recipientId == senderId) {
            throw new IllegalTransactionException(ILLEGAL_TRANSACTION);
        }

        Transaction t1 = new Transaction(recipient, sender, Transaction.Category.DEBIT, amount);
        Transaction t2 = new Transaction(sender, recipient, Transaction.Category.CREDIT, -amount);
        t2.setIdentifier(t1.getIdentifier());

        recipient.getTransactionsList().addTransaction(t1);
        sender.getTransactionsList().addTransaction(t2);
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] getUserTransactions(User user) {
        return user.getTransactionsList().toArray();
    }

    public void removeUserTransactionById(UUID transactionId, Integer userId) {
        usersList
                .getUserById(userId)
                .getTransactionsList()
                .removeTransactionById(transactionId);
    }

    public Transaction[] checkTransactions() {
        TransactionsLinkedList list = new TransactionsLinkedList();

        for (int i = 0; i < usersList.getUsersCount(); i++) {
            User user = usersList.getUserByIndex(i);
            if (user != null) {
                int size = user.getTransactionsList().getSize();
                for (int j = 0; j < size; j++) {
                    list.addTransaction(user.getTransactionsList().toArray()[j]);
                }
            }
        }

        TransactionsLinkedList result = new TransactionsLinkedList();

        for (int i = 0; i < list.getSize(); i++) {
            int count = 0;
            for (int j = 0; j < list.getSize(); j++) {
                if (list.toArray()[i].getIdentifier() == list.toArray()[j].getIdentifier()) {
                    count++;
                }
            }
            if (count != 2) {
                result.addTransaction(list.toArray()[i]);
            }
        }
        return result.toArray();
    }
}
