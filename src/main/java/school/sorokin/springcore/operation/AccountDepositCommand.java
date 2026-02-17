package school.sorokin.springcore.operation;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.UserService;

import java.math.BigDecimal;
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
        long accountToId = Long.parseLong(scanner.nextLine());
        Account accountFrom = accountService.findAccountById(accountToId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        System.out.println("create money count");
        BigDecimal money = BigDecimal.valueOf(Integer.parseInt(scanner.nextLine()));
        BigDecimal moneyAmount = accountFrom.getMoneyAmount().add(money);
        accountService.accountRepl(accountToId, money);

    }

    @Override
    public ConsoleOperationType getConsoleOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }
}
