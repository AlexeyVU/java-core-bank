package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.util.List;
import java.util.Scanner;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final Scanner scanner;
    private final AccountService accountService;
    private final UserService userService;

    public AccountCloseCommand(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("Enter accountId to close");
        int accountId = Integer.parseInt(scanner.nextLine());
        Account account = accountService.findAccountById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        accountService.deleteAccount(accountId);
        User user = userService.findUserById(account.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

        System.out.println("Account close");
    }


    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
