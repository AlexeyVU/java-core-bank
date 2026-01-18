package school.sorokin.springcore.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;

import java.util.*;

public class UserService {
    private final Map<Integer, User> userMap;
    private final Set<String> loginUser;
    private final AccountService accountService;
    private int idCount;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
        this.userMap = new HashMap<>();
        this.idCount = 0;
        this.loginUser = new HashSet<>();
    }
    //Создание пользователя
    public User createUser(String login) {
        if (loginUser.contains(login)) {
            throw new IllegalArgumentException("User is exist");
        }
        loginUser.add(login);
        idCount++;
        User newUser = new User(idCount, login, new ArrayList<>());
        Account newAccount = accountService.createAccount(newUser);
        newUser.getAccountList().add(newAccount);
        userMap.put(idCount, newUser);
        System.out.println();

        return newUser;
    }
    //поиск пользователя по ID
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(userMap.get(id));
    }
    //Получение списка всех пользователей
    public List<User> getAll() {
        return userMap.values().stream().toList();
    }
}
