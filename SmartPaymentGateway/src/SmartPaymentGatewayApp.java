
import java.util.*;

import com.smartpayment.user.User;
import com.smartpayment.payments.*;
import com.smartpayment.exceptions.*;

public class SmartPaymentGatewayApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        User payer = new User("SystemUser");
        List<Payment> transactions = new ArrayList<>();
        boolean continueTxn = true;

        System.out.println("--- SmartPayment Gateway ---");

        while(continueTxn) {
            System.out.println("\n--- New Transaction ---");

            System.out.print("Enter beneficiary name: ");
            String bene = sc.nextLine();

            System.out.print("Enter amount: ");
            double amount = 0;
            try { amount = Double.parseDouble(sc.nextLine()); }
            catch(NumberFormatException e) { System.out.println("Invalid amount!"); continue; }

            System.out.println("Choose payment type: 1-UPI 2-CreditCard 3-Wallet 4-NetBanking");
            int choice = 0;
            try { choice = Integer.parseInt(sc.nextLine()); }
            catch(NumberFormatException e) { System.out.println("Invalid choice!"); continue; }

            Payment payment = null;

            try {
                switch(choice) {
                    case 1:
                        System.out.print("Enter UPI ID: ");
                        String upiId = sc.nextLine();
                        System.out.print("Enter UPI PIN: ");
                        String pin = sc.nextLine();
                        payment = new UPIPayment(payer, bene, amount, upiId, pin);
                        break;
                    case 2:
                        payment = new CreditCardPayment(payer, bene, amount);
                        break;
                    case 3:
                        payment = new WalletPayment(payer, bene, amount);
                        break;
                    case 4:
                        payment = new NetBankingPayment(payer, bene, amount);
                        break;
                    default:
                        System.out.println("Invalid payment type!"); continue;
                }

                payment.processPayment();
                transactions.add(payment);

            } catch(TransactionFailedException e) {
                System.out.println("Transaction failed: " + e.getMessage());
                if(e.getCause() != null)
                    System.out.println("Reason: " + e.getCause().getMessage());
            }

            System.out.print("\nDo you want to perform another transaction? (yes/no): ");
            String ans = sc.nextLine();
            if(ans.equalsIgnoreCase("no")) continueTxn = false;
        }

        System.out.println("\n--- All Transactions Processed ---");
        int count = 1;
        for(Payment p : transactions) {
            System.out.println(count + ") " + p.getClass().getSimpleName() + ": " +
                    p.getAmount() + " to " + p.getBene() + " - SUCCESS");
            count++;
        }

        System.out.println("\nThank you for using SmartPayment Gateway!");
        sc.close();
    }
}
