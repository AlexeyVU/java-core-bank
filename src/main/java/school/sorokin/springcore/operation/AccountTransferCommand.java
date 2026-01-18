package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.util.Scanner;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public AccountTransferCommand(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter ID from account");
        int fromAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter ID to account");
        int toAccountId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter money count for transfer between account");
        int money = Integer.parseInt(scanner.nextLine());
        if (money <= 0) {
            throw new IllegalArgumentException("insufficient funds in the account");
        }
        accountService.transfer(fromAccountId, toAccountId, money);
    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }
}
