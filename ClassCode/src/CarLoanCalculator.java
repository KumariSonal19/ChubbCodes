package project1_sample;
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
            case "alfa":
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

    double totalAmount() {
        // Simple Interest = (P * R * T) / 100
        double si = (principal * rate * years) / 100;
        return principal + si;
    }

    double emi() {
        double total = totalAmount();
        int months = years * 12;
        return total / months;
    }
}

public class BalenoLoan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter model (Delta/Beta/Alfa): ");
        String model = sc.next();

        System.out.print("Enter color: ");
        String color = sc.next();

        Car car = new Car(model, color);

        System.out.println("Car price: " + car.price);

        System.out.print("Enter loan amount: ");
        double loan = sc.nextDouble();

        System.out.print("Enter interest rate: ");
        double rate = sc.nextDouble();

        System.out.print("Enter loan tenure (3 or 5 years): ");
        int years = sc.nextInt();

        LoanCalculator loanCalc = new LoanCalculator(loan, rate, years);

        double total = loanCalc.totalAmount();
        double emi = loanCalc.emi();

        System.out.println("\n--- Loan Summary ---");
        System.out.println("Car Model: " + model);
        System.out.println("Color: " + color);
        System.out.println("Loan Amount: ₹" + loan);
        System.out.println("Interest Rate: " + rate + "%");
        System.out.println("Tenure: " + years + " years");
        System.out.println("Total Amount Payable: ₹" + total);
        System.out.println("Monthly EMI: ₹" + emi);

        sc.close();
    }
}