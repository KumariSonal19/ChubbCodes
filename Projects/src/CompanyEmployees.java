import java.util.*;

public class CompanyEmployees {
    public static void main(String[] args) {

        Map<String, List<String>> companyMap = new HashMap<>();

        companyMap.put("TCS", Arrays.asList("Aman", "Sonal", "Rohit"));
        companyMap.put("Infosys", Arrays.asList("Priya", "Vikas", "Neha"));
        companyMap.put("Wipro", Arrays.asList("Raj", "Kunal", "Meena"));
        companyMap.put("IBM", Arrays.asList("Karan", "Swati", "Ajay"));
        

        System.out.println("Company and Employee Details:");
        for (Map.Entry<String, List<String>> entry : companyMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

       
        if (!companyMap.containsKey("Cognizant")) {
            System.out.println("\nCognizant not found! Adding Cognizant...");
            companyMap.put("Cognizant", new ArrayList<>(Arrays.asList("Aakash", "Ravi", "Anita")));
        }

        
        List<String> cognizantEmployees = companyMap.get("Cognizant");

        if (cognizantEmployees.contains("Ram")) {
            System.out.println("\nRam is working in Cognizant");
        } else {
            System.out.println("\nRam not found in Cognizant");
        }


        System.out.println("\nUpdated Company and Employee Details:");
        for (Map.Entry<String, List<String>> entry : companyMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
