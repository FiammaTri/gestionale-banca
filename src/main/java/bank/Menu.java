package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Benvenuto to Globe Bank International");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if(customer != null){
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Inserire l'username");
    String username = scanner.next();

    System.out.println("Inserire la password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("Credenziali non valide. " + e.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account){
    
    int selection = 0;


    while(selection != 4 && customer.isAuthenticated()){
      System.out.println("====================");
      System.out.println("Seleziona una delle seguenti opzioni \n " +
      "1: Deposito \n" +
      "2: Ritiro \n" +
      "3: Bilancio \n" +
      "4: Esci \n" +
      "====================");

      selection = scanner.nextInt();
      double amount = 0;

      switch(selection){
        case 1: 
          System.out.println("Quanto vuoi depositare?");
          amount = scanner.nextDouble();
          account.deposit(amount);
          break;

        case 2:
          System.out.println("Quanto vuoi ritirare?");
          amount = scanner.nextDouble();
          account.withdraw(amount);
          break;

        case 3:
          System.out.println("Bilancio attuale: " + account.getBalance());
          break;

        case 4: 
          Authenticator.logout(customer);
          System.out.println("Grazie e a presto!");
          break;

          default: 
           System.out.println("Opzione non valida. Seleziona un'opzione valida.");
      }
    } 
  }
}
