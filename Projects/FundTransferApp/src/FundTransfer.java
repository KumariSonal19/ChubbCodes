import java.io.*;
import com.app.dto.Customer;
import com.app.dto.NEFTProcessFund;
import com.app.dto.AccountBalanceException;

public class FundTransfer {

    public int count; // heap variable

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("This is test");

        FundTransfer fdobj = new FundTransfer();

        Customer c1 = new Customer("James", "james@gmail.com", "43432432442", 4343);
        Customer c2 = new Customer("Robin", "robin@gmail.com", "43432432441", 50000);

        NEFTProcessFund neftobj = new NEFTProcessFund();

        System.out.println("Customer balance initiator: " + c1.getAmountbalance());
        System.out.println("Customer balance beneficiary: " + c2.getAmountbalance());

        boolean isValidCustomer = neftobj.validateCustomer(c2);
        if (isValidCustomer) {
            try {
                neftobj.processFund(c1, c2, 30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Not a valid customer");
        }
    }
}
