package school.sorokin.springcore.service;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springcore.AccountProperties;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import java.math.BigDecimal;
import java.util.*;




@Service
public class AccountService {
    private final Map<Integer, Account> accountMap;
    private int idAccount;
    private final AccountProperties accountProperties;

    private final UserService userService;



    public AccountService(AccountProperties accountProperties, @Lazy UserService userService) {
        this.accountMap = new HashMap<>();
        this.idAccount = 0;
        this.accountProperties = accountProperties;
        this.userService = userService;
    }



    //Создание счета
    public Account createAccount(User user) {
        idAccount++;
        Account newAccount = new Account(idAccount, user.getId(), accountProperties.getDefaultAmount());
        accountMap.put(idAccount, newAccount);
        return newAccount;
    }
    //поиск счета по ID
    public Optional<Account> findAccountById(int accountId) {
       return Optional.ofNullable(accountMap.get(accountId));
    }
    //пополнение счета
    public void accountRepl(int accountId, BigDecimal money) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account " + accountId + " not found"));
        if (money.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Hav`not money");
        }
        BigDecimal moneyAmount = account.getMoneyAmount().add(money);
        account.setMoneyAmount(moneyAmount);
        System.out.println("money has been added successfully");

    }
    //снятие средств
    public void withDrawAccount(int accountId, BigDecimal money) {
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
    }
    //перевод средств между счетами
    public void transfer(int fromAccountId, int toAccountId, BigDecimal money) {
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
        toAccount.setMoneyAmount(toMoneyAmount);
    }

    //зкрытие счета
    public Account deleteAccount(int accountId) {
        Account deleteAccount = findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        List<Account> accountList = getAllUsersAccount(deleteAccount.getUserId());
        if (accountList.size() <= 1) {
            throw new IllegalArgumentException("The single account cannot be deleted");
        }
        accountMap.remove(accountId);
        Account firstElement = accountList.get(0);
        BigDecimal countAccountFirstElement = deleteAccount.getMoneyAmount().add(firstElement.getMoneyAmount());
        BigDecimal countAccountSecondElement = deleteAccount.getMoneyAmount().add(firstElement.getMoneyAmount());
        Account secondElement = accountList.get(1);
        if (firstElement.getId() == accountId) {
            secondElement.setMoneyAmount(countAccountFirstElement);
            User user = userService.findUserById(secondElement.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.getAccountList().remove(firstElement);
        } else {
            firstElement.setMoneyAmount(countAccountSecondElement);
            User user = userService.findUserById(firstElement.getUserId())
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.getAccountList().remove(deleteAccount);
        }
        return deleteAccount;
    }


    public List<Account> getAllUsersAccount(int userId) {
        return accountMap.values().stream().toList();
        }
    }

