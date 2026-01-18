package school.sorokin.springcore.service;

public class CoordinationService {
    private final UserService userService;
    private final AccountService accountService;

    public CoordinationService(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }
}
