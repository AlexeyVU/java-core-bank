package school.sorokin.springcore.service;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getConsoleOperationType();
}
