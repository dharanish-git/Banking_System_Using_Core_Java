package menus;

import services.AccountService;
import java.util.Scanner;

public class MenuHandler {
    private Scanner sc;
    private AccountService accountService;

    public MenuHandler() {
        sc = new Scanner(System.in);
        accountService = new AccountService();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n===== Banking System =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> accountService.createAccount();
                case 2 -> accountService.deposit();
                case 3 -> accountService.withdraw();
                case 4 -> accountService.checkBalance();
                case 5 -> {
                    System.out.println("Thank you for Reaching us! 🙏");
                    return;
                }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }
}

