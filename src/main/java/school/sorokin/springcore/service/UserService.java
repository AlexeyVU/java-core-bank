package school.sorokin.springcore.service;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springcore.AccountProperties;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import java.util.Optional;
import java.util.*;
import java.util.function.Supplier;

@Service
public class UserService {
    private final AccountService accountService;
    private final SessionFactory sessionFactory;
    private final AccountProperties accountProperties;
    private final TransactionHelper transactionHelper;


    public UserService(AccountService accountService, SessionFactory sessionFactory,
                       AccountProperties accountProperties, TransactionHelper transactionHelper) {
        this.accountService = accountService;
        this.sessionFactory = sessionFactory;
        this.accountProperties = accountProperties;
        this.transactionHelper = transactionHelper;

    }
    //Создание пользователя
    public User createUser(String login) {
        return transactionHelper.executeInTransaction(() -> {
            Session session = sessionFactory.getCurrentSession();
            User user = new User(null, login, new ArrayList<>());
            session.persist(user);
            Account newAccount = new Account(null, user, accountProperties.getDefaultAmount());
            user.getAccountList().add(newAccount);
            return user;
        });
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