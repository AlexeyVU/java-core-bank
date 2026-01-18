package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.util.List;
import java.util.Scanner;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final Scanner scanner;
    private final UserService userService;
    private final AccountService accountService;

    public AccountDepositCommand(Scanner scanner, UserService userService, AccountService accountService) {
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {

        System.out.println("Enter ID account");
        int accountToId = Integer.parseInt(scanner.nextLine());
        Account accountFrom = accountService.findAccountById(accountToId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        System.out.println("create money count");
        int money = Integer.parseInt(scanner.nextLine());
        int moneyAmount = accountFrom.getMoneyAmount() + money;
        accountService.accountRepl(accountToId, money);

    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
