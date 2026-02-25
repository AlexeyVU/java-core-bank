package school.sorokin.springcore;

import org.springframework.beans.factory.annotation.Value;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import school.sorokin.springcore.model.Account;
import school.sorokin.springcore.model.User;

@Configuration
public class HibernateConfiguration {
    private final String driverClass;
    private final String url;
    private final String username;
    private final String password;
    private final String dialect;
    private final String hbm2ddlAuto;
    private final String showSql;
    private final String formatSql;
    private final String currentSessionContextClass;
    public HibernateConfiguration(
    @Value("${db.driver}") String driverClass,
    @Value("${db.url}") String url,
    @Value("${db.username}") String username,
    @Value("${db.password}") String password,
    @Value("${db.dialect}") String dialect,
    @Value("${hibernate.hbm2ddl.auto}") String hbm2ddlAuto,
    @Value("${hibernate.show_sql}") String showSql,
    @Value("${hibernate.format_sql}") String formatSql,
    @Value("${hibernate.current_session_context_class:thread}") String currentSessionContextClass
    ) {
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
        this.dialect = dialect;
        this.hbm2ddlAuto = hbm2ddlAuto;
        this.showSql = showSql;
        this.formatSql = formatSql;
        this.currentSessionContextClass = currentSessionContextClass;
    }
    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Account.class)
                .addPackage("school.sorokin.springcore")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5531/postgres")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.connection.username", "pg-dev")
                .setProperty("hibernate.connection.password", "password")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl.auto", "update");

        return configuration.buildSessionFactory();
    }
}
