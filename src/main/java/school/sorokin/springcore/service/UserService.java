package school.sorokin.springcore.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springcore.AccountProperties;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import java.util.Optional;
import java.util.*;

@Service
public class UserService {


    private final AccountService accountService;
    private final SessionFactory sessionFactory;
    private final AccountProperties accountProperties;


    public UserService(AccountService accountService, SessionFactory sessionFactory, AccountProperties accountProperties) {
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
        this.accountProperties = accountProperties;

    }
    //Создание пользователя
    public User createUser(String login) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = new User(null, login, new ArrayList<>());
        session.persist(user);
        Account newAccount = new Account(null, user, accountProperties.getDefaultAmount());
        user.getAccountList().add(newAccount);
        session.getTransaction().commit();
        session.close();
//        List<Account> accountList = new ArrayList<>();
//        accountList.add(accountService.createAccount(user));
//        session.persist(user);
//        session.getTransaction().commit();
//        session.close();

        return user;

    }
    //поиск пользователя по ID
    public Optional<User> findUserById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        return Optional.ofNullable(user);
    }


    //Получение списка всех пользователей
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        return session.createQuery("SELECT e FROM User e ", User.class).list();

    }
}
