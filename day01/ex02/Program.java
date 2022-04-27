
public class Program {

    public static void main(String[] args) {
        UsersArrayList usersArrayList = new UsersArrayList();

        System.out.println("Array size " + usersArrayList.getUsersCount());

        usersArrayList.addUser(new User("A", 100));
        usersArrayList.addUser(new User("B", 100));
        usersArrayList.addUser(new User("C", 100));
        usersArrayList.addUser(new User("D", 100));
        usersArrayList.addUser(new User("E", 100));

        System.out.println("Array size " + usersArrayList.getUsersCount());

        usersArrayList.addUser(new User("F", 100));
        usersArrayList.addUser(new User("F", 100));
        usersArrayList.addUser(new User("H", 100));
        usersArrayList.addUser(new User("F", 100));
        usersArrayList.addUser(new User("J", 100));
        usersArrayList.addUser(new User("D", 100));
        usersArrayList.addUser(new User("E", 100));
        usersArrayList.addUser(new User("W", 100));

        System.out.println("Array size " + usersArrayList.getUsersCount());

        usersArrayList.addUser(new User("P", 100));
        usersArrayList.addUser(new User("K", 100));
        usersArrayList.addUser(new User("L", 100));
        usersArrayList.addUser(new User("H", 100));
        usersArrayList.addUser(new User("M", 100));
        usersArrayList.addUser(new User("H", 100));
        usersArrayList.addUser(new User("T", 100));
        usersArrayList.addUser(new User("V", 100));
        usersArrayList.addUser(new User("N", 100));
        usersArrayList.addUser(new User("M", 100));
        usersArrayList.addUser(new User("S", 100));
        usersArrayList.addUser(new User("L", 100));
        usersArrayList.addUser(new User("U", 100));

        System.out.println("Array size " + usersArrayList.getUsersCount());

        for (int i = 0; i < usersArrayList.getUsersCount(); i++) {
            System.out.println(usersArrayList.getUserById(i + 1).getName());
            System.out.println(usersArrayList.getUserByIndex(i));
        }

        System.out.println(usersArrayList.getUserById(777));
    }
}
