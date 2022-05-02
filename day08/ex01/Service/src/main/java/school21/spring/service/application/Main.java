package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);

        User newUser = new User();

        newUser.setEmail("user@user.ru");

        System.out.println(usersRepository.findAll());

        for (User user : usersRepository.findAll()) {
            System.out.println(user.getEmail());
        }
        System.out.println("---");

        usersRepository.save(newUser);

        for (User user : usersRepository.findAll()) {
            System.out.println(user.getEmail());
        }
        System.out.println("---");

        usersRepository.delete(1L);

        for (User user : usersRepository.findAll()) {
            System.out.println(user.getEmail());
        }
        System.out.println("---");

        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        System.out.println(usersRepository.findAll());
        for (User user : usersRepository.findAll()) {
            System.out.println(user.getEmail());
        }
    }
}
