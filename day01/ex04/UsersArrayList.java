
public class UsersArrayList implements UsersList {

    public static final String USER_NOT_FOUND = "User not found!";

    private Integer size = 10;
    private Integer index = 0;
    private User[] users = new User[size];

    @Override
    public void addUser(User user) {
        if (index == size - 1) {
            Integer newSize = size * 2;
            User[] newUsers = new User[newSize];
            copyArray(users, newUsers);
            users = newUsers;
            size = newSize;
        }

        users[index++] = user;
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < size; i++) {
            if (users[i] != null) {
                if (users[i].getIdentifier() == (int) id) {
                    return users[i];
                }
            }
        }
        throw new UserNotFoundException(USER_NOT_FOUND);
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (this.index >= index && index >= 0) {
            return users[index];
        }
        return null;
    }

    @Override
    public Integer getUsersCount() {
        return index;
    }

    private void copyArray(User[] from, User[] to) {
        for (int i = 0; i < size; i++) {
            to[i] = from[i];
        }
    }
}
