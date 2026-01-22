package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class AccountWithDrawCommand implements OperationCommand {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public AccountWithDrawCommand(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter account ID ");
        int accountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter count money for draw ");
        BigDecimal money = BigDecimal.valueOf(Integer.parseInt(scanner.nextLine()));
        accountService.withDrawAccount(accountId, money);
    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.ACCOUNT_WITHDRAW;
    }
}
