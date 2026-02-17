package school.sorokin.springcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
