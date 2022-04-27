
import java.util.UUID;

public class Program {

    public static void main(String[] args) {
        TransactionsLinkedList transactionsLinkedList = new TransactionsLinkedList();

        User user1 = new User("A", 100);
        User user2 = new User("B", 100);

        Transaction t1 = new Transaction(user2, user1, Transaction.Category.DEBIT, 100);
        Transaction t2 = new Transaction(user1, user2, Transaction.Category.DEBIT, 100);
        Transaction t3 = new Transaction(user1, user2, Transaction.Category.DEBIT, 100);
        Transaction t4 = new Transaction(user1, user2, Transaction.Category.DEBIT, 100);

        transactionsLinkedList.addTransaction(t1);
        transactionsLinkedList.addTransaction(t2);
        transactionsLinkedList.addTransaction(t3);
        transactionsLinkedList.addTransaction(t4);

        System.out.println();

        System.out.println(transactionsLinkedList.getSize());

        Transaction[] transactions = transactionsLinkedList.toArray();

        for (int i = 0; i < transactionsLinkedList.getSize(); i++) {
            System.out.print(transactions[i].getIdentifier() + " ");
            System.out.println(transactions[i].getRecipient().getName());
        }

        System.out.println();

        transactionsLinkedList.removeTransactionById(t1.getIdentifier());

        Transaction[] transactions1 = transactionsLinkedList.toArray();

        System.out.println(transactionsLinkedList.getSize());

        for (int i = 0; i < transactionsLinkedList.getSize(); i++) {
            System.out.print(transactions1[i].getIdentifier() + " ");
            System.out.println(transactions1[i].getRecipient().getName());
        }

        System.out.println();

        transactionsLinkedList.removeTransactionById(t3.getIdentifier());

        Transaction[] transactions2 = transactionsLinkedList.toArray();

        System.out.println(transactionsLinkedList.getSize());

        for (int i = 0; i < transactionsLinkedList.getSize(); i++) {
            System.out.println(transactions2[i].getIdentifier());
        }

        System.out.println();

        transactionsLinkedList.removeTransactionById(t2.getIdentifier());

        Transaction[] transactions3 = transactionsLinkedList.toArray();

        System.out.println(transactionsLinkedList.getSize());

        for (int i = 0; i < transactionsLinkedList.getSize(); i++) {
            System.out.println(transactions3[i].getIdentifier());
        }

        transactionsLinkedList.removeTransactionById(UUID.randomUUID());
    }
}
