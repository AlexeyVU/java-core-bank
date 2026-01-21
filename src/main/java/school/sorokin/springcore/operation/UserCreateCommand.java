package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.util.Scanner;
@Component
public class UserCreateCommand implements OperationCommand{
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public UserCreateCommand(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter login for new user");
        String login = scanner.nextLine();
        User user = userService.createUser(login);
        System.out.println("User created");
        System.out.println(userService.findUserById(user.getId()));
    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }
}
