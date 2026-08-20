import java.util.Scanner;

/**
 * A minimal chatbot that echoes commands until the user says goodbye.
 */
public class Computa {

    /** Separates the greeting and each command response in the console output. */
    private static final String SEPARATOR = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(SEPARATOR);
        System.out.println("                         COMPUTA");
        System.out.println("Konnichiwassup! °˖✧◝(⁰▿⁰)◜✧˖°");
        System.out.println("I'm your personal Computa ｡:ﾟ(｡ﹷ ‸ ﹷ ✿)");
        System.out.println("What can I do for you?");
        System.out.println(SEPARATOR);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("Noooo don't go!!! Hmph. Fine... Hope to see you again soon!");
                System.out.println(SEPARATOR);
                break;
            }

            System.out.println(command);
            System.out.println(SEPARATOR);
        }
    }
}
