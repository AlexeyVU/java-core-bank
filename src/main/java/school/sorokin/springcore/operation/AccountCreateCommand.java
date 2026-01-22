package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.util.Optional;
import java.util.Scanner;
@Component
public class AccountCreateCommand implements OperationCommand {
    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public AccountCreateCommand(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("Enter user ID");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userService.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Account account = accountService.createAccount(user);
        user.getAccountList().add(account);
        System.out.println("Account created");
    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return  ConsoleOperationType.ACCOUNT_CREATE;
    }
}
