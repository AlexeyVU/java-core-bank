package school.sorokin.springcore.service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.postgresql.core.Query;
import org.hibernate.Transaction;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springcore.AccountProperties;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import java.math.BigDecimal;
import java.util.*;




@Service
public class AccountService {

    private final AccountProperties accountProperties;
    private final UserService userService;
    private final SessionFactory sessionFactory;


    public AccountService(AccountProperties accountProperties, @Lazy UserService userService, SessionFactory sessionFactory) {

        this.accountProperties = accountProperties;
        this.userService = userService;
        this.sessionFactory = sessionFactory;
    }


    //Создание счета
    public Account createAccount(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = new Account(null, user, accountProperties.getDefaultAmount());
        session.persist(account);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    //поиск счета по ID
    public Optional<Account> findAccountById(long accountId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Account account = session.get(Account.class, accountId);
        return Optional.ofNullable(account);
    }

    //пополнение счета
    public void accountRepl(Long accountId, BigDecimal money) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        BigDecimal moneyAmount = account.getMoneyAmount().add(money);
        account.setMoneyAmount(moneyAmount);
        update(account, moneyAmount);
        System.out.println("money has been added successfully");

    }

    //снятие средств
    public void withDrawAccount(Long accountId, BigDecimal money) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        BigDecimal moneyAmount = account.getMoneyAmount().subtract(money);
        if (moneyAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("low of funds");
        }
        account.setMoneyAmount(moneyAmount);
        update(account, moneyAmount);
    }

    //перевод средств между счетами
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal money) {
        Account fromAccount = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + fromAccountId + " not found"));
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        BigDecimal fromMoneyAmount =
                (fromAccount.getMoneyAmount().subtract(money)
                        .subtract(BigDecimal.valueOf(accountProperties.getTransferCommission())));
        if (fromMoneyAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("low of funds");
        }
        Account toAccount = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + fromAccountId + " not found"));
        BigDecimal toMoneyAmount = toAccount.getMoneyAmount().add(money);
        fromAccount.setMoneyAmount(fromMoneyAmount);
        update(fromAccount, fromMoneyAmount);
        toAccount.setMoneyAmount(toMoneyAmount);
        update(toAccount, toMoneyAmount);
    }

    //зкрытие счета
    public Account deleteAccount(Long accountId) {
        Account deleteAccount = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<Account> accountList = getAllUsersAccount(deleteAccount.getUser().getId());
        if (accountList.size() <= 1) {
            throw new IllegalArgumentException("The single account cannot be deleted");
        }
        Account firstElement = accountList.get(0);
        BigDecimal countAccountFirstElement = deleteAccount.getMoneyAmount().add(firstElement.getMoneyAmount());
        BigDecimal countAccountSecondElement = deleteAccount.getMoneyAmount().add(firstElement.getMoneyAmount());
        Account secondElement = accountList.get(1);
        if (firstElement.getId().equals(accountId)) {
            secondElement.setMoneyAmount(countAccountFirstElement);
            update(secondElement, countAccountFirstElement);
            User user = userService.findUserById(secondElement.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Session session = sessionFactory.openSession();
            user.getAccountList().remove(firstElement);
            session.remove(firstElement);
            session.beginTransaction();
            session.getTransaction().commit();
            session.close();
        } else {
            firstElement.setMoneyAmount(countAccountSecondElement);
            update(firstElement, countAccountSecondElement);
            User user = userService.findUserById(firstElement.getUser().getId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            Session session = sessionFactory.openSession();
            user.getAccountList().remove(deleteAccount);
            session.remove(deleteAccount);
            session.beginTransaction();
            session.getTransaction().commit();
            session.close();
        }
        return deleteAccount;
    }


    public List<Account> getAllUsersAccount(Long userId) {
        Session session = sessionFactory.openSession();
        System.out.println("метка для сверки");
        List<Account> accountList = session.createQuery("SELECT a From Account a where a.user.id = :userId", Account.class)
                        .setParameter("userId", userId)
                        .getResultList();

        session.beginTransaction();
        session.getTransaction().commit();
        session.close();
        return accountList;
    }
    //Обновление сущности Account
    public void update(Account accountToUpdate, BigDecimal newAmount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Account managedAccount = session.merge(accountToUpdate);
            managedAccount.setMoneyAmount(newAmount);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

