package school.sorokin.springcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sorokin.springcore.operation.OperationCommand;
import school.sorokin.springcore.service.AccountService;
import school.sorokin.springcore.service.ConsoleOperationType;
import school.sorokin.springcore.service.OperationConsoleListener;
import school.sorokin.springcore.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class ApplicationConfig {
    @Bean
    public UserService userService(AccountService accountService) {
        return new UserService(accountService);
    }
    @Bean
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    public OperationConsoleListener operationConsoleListener(List<OperationCommand> consolList, Scanner scanner) {
        return new OperationConsoleListener(consolList, scanner);
    }
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
