
public class Program {

    public static void main(String[] args) {
        TransactionsService transactionsService = new TransactionsService();

        User user1 = new User("John", 1000);
        User user2 = new User("Mike", 1000);

        transactionsService.addUser(user1);
        transactionsService.addUser(user2);

        transactionsService.executeTransaction(1, 2, 100);

        System.out.println(user1.getBalance());
        System.out.println(user2.getBalance());

        System.out.println(user1.getTransactionsList().toArray()[0].getIdentifier());
        System.out.println(user2.getTransactionsList().toArray()[0].getIdentifier());

        transactionsService.executeTransaction(2, 1, 100);

        System.out.println(user1.getBalance());
        System.out.println(user2.getBalance());

        System.out.println(user1.getTransactionsList().toArray()[1].getIdentifier());
        System.out.println(user2.getTransactionsList().toArray()[1].getIdentifier());

        transactionsService.executeTransaction(2, 1, 100);

        System.out.println(user1.getBalance());
        System.out.println(user2.getBalance());

        System.out.println(user1.getTransactionsList().toArray()[2].getIdentifier());
        System.out.println(user2.getTransactionsList().toArray()[2].getIdentifier());

        for (Transaction transaction : transactionsService.checkTransactions()) {
            System.out.println(transaction.getIdentifier());
        }

        System.out.println();

        user1.getTransactionsList().addTransaction(new Transaction(user1, user2, Transaction.Category.DEBIT, 100));

        for (Transaction transaction : transactionsService.checkTransactions()) {
            System.out.println(transaction.getIdentifier());
        }

        user2.setBalance(-1000);

        System.out.println(transactionsService.getBalance(user2));

        transactionsService.executeTransaction(1, 2, 100);
    }
}
