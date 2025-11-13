import java.util.Scanner;

class Car {
    String model, color;
    double price;

    Car(String model, String color) {
        this.model = model;
        this.color = color;

        switch (model.toLowerCase()) {
            case "delta":
                price = 800000;
                break;
            case "beta":
                price = 1000000;
                break;
            case "alpha":
                price = 1200000;
                break;
            default:
                System.out.println("Invalid model! Defaulting to 0");
                price = 0;
        }
    }
}

class LoanCalculator {
    double principal;
    double rate;
    int years;

    LoanCalculator(double principal, double rate, int years) {
        this.principal = principal;
        this.rate = rate;
        this.years = years;
    }

    double totalAmountSimple() {
        double si = (principal * rate * years) / 100;
        return principal + si;
    }

    double totalAmountCompound() {
        double amount = principal * Math.pow((1 + rate / 100), years);
        return amount;
    }

    double emi(double totalAmount) {
        int months = years * 12;
        return totalAmount / months;
    }
}

public class BalenoLoan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter model (Delta/Beta/Alpha): ");
        String model = sc.next();

        System.out.print("Enter color: ");
        String color = sc.next();

        Car car = new Car(model, color);

        System.out.println("Car price: ₹" + car.price);

        System.out.print("Enter down payment: ₹");
        double downPayment = sc.nextDouble();

        double loan = car.price - downPayment;

        System.out.print("Enter interest rate (% per year): ");
        double rate = sc.nextDouble();

        System.out.print("Enter loan tenure (3 or 5 years): ");
        int years = sc.nextInt();

        System.out.print("Choose interest type (1: Simple, 2: Compound): ");
        int choice = sc.nextInt();

        LoanCalculator loanCalc = new LoanCalculator(loan, rate, years);

        double total = 0;

        if (choice == 1) {
            total = loanCalc.totalAmountSimple();
        } else if (choice == 2) {
            total = loanCalc.totalAmountCompound();
        } else {
            System.out.println("Invalid choice! Defaulting to Simple Interest.");
            total = loanCalc.totalAmountSimple();
        }

        double emi = loanCalc.emi(total);

        System.out.println("\n--- Loan Summary ---");
        System.out.println("Car Model: " + model);
        System.out.println("Color: " + color);
        System.out.println("Car Price: ₹" + car.price);
        System.out.println("Down Payment: ₹" + downPayment);
        System.out.println("Loan Amount: ₹" + loan);
        System.out.println("Interest Rate: " + rate + "%");
        System.out.println("Tenure: " + years + " years");
        System.out.println("Interest Type: " + (choice == 2 ? "Compound" : "Simple"));
        System.out.println("Total Amount Payable: ₹" + total);
        System.out.println("Monthly EMI: ₹" + Math.round(emi));

        sc.close();
    }
}

