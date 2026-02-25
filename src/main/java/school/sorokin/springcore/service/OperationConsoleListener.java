package school.sorokin.springcore.service;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.operation.OperationCommand;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
@Component
public class OperationConsoleListener {

    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    private final Scanner scanner;
    public OperationConsoleListener
            (List<OperationCommand> consoleList, Scanner scanner) {
        System.out.println("DEBUG: Size of injected OperationCommand list: " + consoleList.size());

        this.commandMap = consoleList
                .stream()
                .collect(
                        Collectors.toMap(
                                OperationCommand::getConsoleOperationType,
                                processor -> processor
                        )
                );
        this.scanner = scanner;

    }
    public Map<ConsoleOperationType, OperationCommand> getCommandMap() {
        return commandMap;
    }

    public void engine() {
        System.out.println("Please enter one of operation type: ");

            try {
                getAllOperationType();
                while (true) {
                    String nextOperation = scanner.nextLine();
                    nextOperation(ConsoleOperationType.valueOf(nextOperation));
                 ConsoleOperationType.valueOf(nextOperation);
                 }

                } catch (IllegalArgumentException e) {
                System.out.println("Command not found");
        }
    }

    public void getAllOperationType() {
        commandMap.keySet().forEach(System.out::println);
    }
    public void nextOperation(ConsoleOperationType consoleOperationType) {
        try {
            OperationCommand processor = commandMap.get(consoleOperationType);
            processor.execute();
            getAllOperationType();
        } catch (IllegalArgumentException e) {
            System.out.println("Command execute with error ");
        }
    }
}
