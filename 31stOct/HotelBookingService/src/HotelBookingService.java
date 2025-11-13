public class HotelBookingService {

    private static final Object roomLock = new Object();
    private static final Object paymentLock = new Object();

    public static void main(String[] args) {
        Thread user1 = new Thread(() -> bookRoom("User1"));
        Thread user2 = new Thread(() -> payForRoom("User2"));

        user1.start();
        user2.start();
    }

    static void bookRoom(String user) {
        synchronized (roomLock) {
            System.out.println(user + " locked room for booking.");
            sleep(100); // simulate time delay
            synchronized (paymentLock) {
                System.out.println(user + " locked payment for booking.");
            }
        }
    }

    static void payForRoom(String user) {
        synchronized (paymentLock) {
            System.out.println(user + " locked payment for booking.");
            sleep(100);
            synchronized (roomLock) {
                System.out.println(user + " locked room for booking.");
            }
        }
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
