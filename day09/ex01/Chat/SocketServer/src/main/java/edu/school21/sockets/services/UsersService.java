package edu.school21.sockets.services;

public interface UsersService {

    String saveNewUser(String userName, String password);

    boolean validateUser(String userName, String password);
}
