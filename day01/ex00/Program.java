
public class Program {

    public static void main(String[] args) {
        User user1 = new User(1, "John", -10000);
        User user2 = new User(2, "John", 10000);
        User user3 = new User(3, "Mike", 10000);

        System.out.printf("User1 id - %d, name - %s, balance - %d\n",
                user1.getIdentifier(), user1.getName(), user1.getBalance());
        System.out.printf("User2 id - %d, name - %s, balance - %d\n",
                user2.getIdentifier(), user2.getName(), user2.getBalance());
        System.out.printf("User3 id - %d, name - %s, balance - %d\n",
                user3.getIdentifier(), user3.getName(), user3.getBalance());

        Transaction transaction1 = new Transaction(user1, user2,
                Transaction.Category.CREDIT, 100);
        System.out.println(transaction1.getIdentifier());
        System.out.println(transaction1.getRecipient().getIdentifier());
        System.out.println(transaction1.getSender().getIdentifier());
        System.out.println(transaction1.getCategory());
        System.out.println(transaction1.getAmount());

        Transaction transaction2 = new Transaction(user1, user2,
                Transaction.Category.CREDIT, -100);
        System.out.println(transaction2.getIdentifier());
        System.out.println(transaction2.getRecipient().getIdentifier());
        System.out.println(transaction2.getSender().getIdentifier());
        System.out.println(transaction2.getCategory());
        System.out.println(transaction2.getAmount());

        Transaction transaction3 = new Transaction(user1, user2,
                Transaction.Category.DEBIT, 100);
        System.out.println(transaction3.getIdentifier());
        System.out.println(transaction3.getRecipient().getIdentifier());
        System.out.println(transaction3.getSender().getIdentifier());
        System.out.println(transaction3.getCategory());
        System.out.println(transaction3.getAmount());

        Transaction transaction4 = new Transaction(user1, user2,
                Transaction.Category.DEBIT, -100);
        System.out.println(transaction4.getIdentifier());
        System.out.println(transaction4.getRecipient().getIdentifier());
        System.out.println(transaction4.getSender().getIdentifier());
        System.out.println(transaction4.getCategory());
        System.out.println(transaction4.getAmount());
    }
}
