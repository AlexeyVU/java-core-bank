package school.sorokin.springcore.operation;

import school.sorokin.springcore.service.ConsoleOperationType;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getConsoleOperationType();
}
