import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        StudySessionDAO dao = new StudySessionDAO();

        while (true) {

            System.out.println("\nSMART STUDY TRACKER");
            System.out.println("1. Add Session");
            System.out.println("2. View Sessions");
            System.out.println("3. Delete Session");
            System.out.println("4. Exit");

            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Subject: ");
                    String subject = sc.nextLine();

                    System.out.print("Topic: ");
                    String topic = sc.nextLine();

                    System.out.print("Duration (hours): ");
                    int duration = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Date: ");
                    String date = sc.nextLine();

                    dao.addSession(subject, topic, duration, date);
                    break;

                case 2:
                    dao.viewSessions();
                    break;

                case 3:
                    System.out.print("Enter session id: ");
                    int id = sc.nextInt();
                    dao.deleteSession(id);
                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}