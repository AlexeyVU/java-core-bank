package school.sorokin.springcore.service;

import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class AccountService {
    private final Map<Integer, Account> accountMap;
    private int idAccount;



    public AccountService() {
        this.accountMap = new HashMap<>();
        this.idAccount = 0;


    }

    //Создание счета
    public Account createAccount(User user) {
        idAccount++;
        Account newAccount = new Account(idAccount, user.getId(), 100);
        accountMap.put(idAccount, newAccount);
        return newAccount;
    }
    //поиск счета по ID
    public Optional<Account> findAccountById(int accountId) {
       return Optional.ofNullable(accountMap.get(accountId));
    }
    //пополнение счета
    public void accountRepl(int accountId, int money) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        if (money <= 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        int moneyAmount = account.getMoneyAmount() + money;
        account.setMoneyAmount(moneyAmount);
        System.out.println("money has been added successfully");

    }
    //снятие средств
    public void withDrawAccount(int accountId, int money) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        if (money <= 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        int moneyAmount = account.getMoneyAmount() - money;
        if (moneyAmount < 0) {
            throw new IllegalArgumentException("low of funds");
        }
        account.setMoneyAmount(moneyAmount);
    }
    //перевод средств между счетами
    public void transfer(int fromAccountId, int toAccountId, int money) {
        Account fromAccount = findAccountById(fromAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + fromAccountId + " not found"));
        if (money <= 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        int fromMoneyAmount = fromAccount.getMoneyAmount() - money;
        if (fromMoneyAmount < 0) {
            throw new IllegalArgumentException("low of funds");
        }
        Account toAccount = findAccountById(toAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + fromAccountId + " not found"));
        int toMoneyAmount = toAccount.getMoneyAmount() + money;
        fromAccount.setMoneyAmount(fromMoneyAmount);
        toAccount.setMoneyAmount(toMoneyAmount);
    }

    //зкрытие счета
    public Account deleteAccount(int accountId) {
        Account fromAccount = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        List<Account> accountList = getAllUsersAccount(fromAccount.getUserId());
        if (accountList.size() <= 1) {
            throw new IllegalArgumentException("The single account cannot be deleted");
        }
        accountList.forEach(System.out::println);
        System.out.println("size" + accountList.size());
        accountMap.remove(accountId);
        return fromAccount;

    }
    public List<Account> getAllUsersAccount(int userId) {
        return accountMap.values().stream().toList();
        }
    }

