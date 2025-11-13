import java.util.Scanner;

class User {
    String name;
    String destination;

    User(String name, String destination) {
        this.name = name;
        this.destination = destination;
    }
}

class Hotel {
    int id;
    String name;
    String destination;
    int roomsAvailable = 200;

    Hotel(int id, String name, String destination) {
        this.id = id;
        this.name = name;
        this.destination = destination;
    }

    boolean bookRoom() {
        if (roomsAvailable > 0) {
            roomsAvailable--;
            return true;
        }
        return false;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter destination (Goa/Manali/Delhi): ");
        String destination = sc.nextLine();

        User user = new User(name, destination);

        // Array of hotels
        Hotel[] hotels = {
            new Hotel(1, "Sunrise Inn", "Goa"),
            new Hotel(2, "Sea Breeze Resort", "Goa"),
            new Hotel(3, "Palm Haven", "Goa"),
            new Hotel(4, "Mountain View", "Manali"),
            new Hotel(5, "Snow Crest Lodge", "Manali"),
            new Hotel(6, "Pine Retreat", "Manali"),
            new Hotel(7, "City Central", "Delhi"),
            new Hotel(8, "Heritage Palace", "Delhi"),
            new Hotel(9, "Riverside Hotel", "Delhi")
        };

        // Filter hotels by destination
        Hotel[] availableHotels = new Hotel[hotels.length];
        int count = 0;
        for (Hotel h : hotels) {
            if (h.destination.equalsIgnoreCase(user.destination)) {
                availableHotels[count++] = h;
            }
        }

        if (count == 0) {
            System.out.println("No hotels found for " + user.destination);
            return;
        }

        int[] bookedRooms = new int[count];
        boolean continueBooking = true;

        while (continueBooking) {
            System.out.println("\nAvailable Hotels in " + user.destination + ":");
            for (int i = 0; i < count; i++) {
                Hotel h = availableHotels[i];
                System.out.println((i + 1) + ". " + h.name + " (Rooms left: " + h.roomsAvailable + ")");
            }

            System.out.print("Select hotel number: ");
            int option = Integer.parseInt(sc.nextLine().trim());

            if (option < 1 || option > count) {
                System.out.println("Invalid selection!");
                continue;
            }

            Hotel selected = availableHotels[option - 1];

            if (selected.bookRoom()) {
                bookedRooms[option - 1]++;
                System.out.println("Room booked successfully!");
            } else {
                System.out.println("No rooms left in this hotel!");
            }

            System.out.print("Do you want to book another room? (yes/no): ");
            String ans = sc.nextLine();
            continueBooking = ans.equalsIgnoreCase("yes");
        }

        // Summary Output
        System.out.println("\n------ Booking Summary ------");
        System.out.println("Name: " + user.name);
        System.out.println("Destination: " + user.destination);

        int total = 0;
        for (int i = 0; i < count; i++) {
            if (bookedRooms[i] > 0) {
                System.out.println("- " + availableHotels[i].name + ": " + bookedRooms[i] + " room(s)");
                total += bookedRooms[i];
            }
        }

        if (total == 0) System.out.println("No rooms booked.");
        else System.out.println("Total rooms booked: " + total);

        System.out.println("------------------------------");
        sc.close();
    }
}

