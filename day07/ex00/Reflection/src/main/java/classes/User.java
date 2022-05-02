package classes;

public class User {

    private String firstName;
    private String lastName;
    private int height;

    public User() {
        this.firstName = "Default first name";
        this.lastName = "Default last name";
        this.height = 0;
    }

    public User(String firstName, String lastName, int height) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
    }

    private void hah() {
        System.out.println("hah");
    }

    public int grow(int value) {
        this.height += value;
        return height;
    }

    public void printFullName(String firstName, String lastName) {
        System.out.println(firstName + " " +  lastName);
    }

    @Override
    public String toString() {
        return "User[" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", height=" + height +
                ']';
    }
}
