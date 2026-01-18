package school.sorokin.springcore.service;

import org.springframework.stereotype.Component;
import school.sorokin.springcore.operation.OperationCommand;

import java.util.*;
import java.util.stream.Collectors;

public class OperationConsoleListener {

    private final Map<ConsoleOperationType, OperationCommand> commandMap;
    //private final List<ConsoleOperationType> consoList;
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
                getAllOperationType();
                while (true) {
                    String nextOperation = scanner.nextLine();
                    nextOperation(ConsoleOperationType.valueOf(nextOperation));
                    try {
                         ConsoleOperationType.valueOf(nextOperation);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Command not found");
                    }
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
