package school.sorokin.springcore;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school.sorokin.springcore.service.OperationConsoleListener;

public class App
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("school.sorokin.springcore");
        OperationConsoleListener operationConsoleListener = context.getBean(OperationConsoleListener.class);
        operationConsoleListener.engine();
    }
}
