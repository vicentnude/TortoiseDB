import java.io.IOException;

public class Server {
    private static int PORT = 1212;

    public static void main(String[] args) {
        if (!Server.checkArgs(args)) {
            System.exit(1);
        }

        try {
            MainThread mainthread = new MainThread(PORT);
            mainthread.startServer();
        } catch (IOException e) {
            System.err.println("Error: can't start main thread: " + e.getMessage());
        }
    }

    private static boolean checkArgs(String[] args) {
        boolean isArgumentsValid = true;

        if (args.length < 1) {
            isArgumentsValid = false;
            System.err.println("2 arguments needed to start application -u amd -p");
        } else if (args.length == 1) {
            isArgumentsValid = false;
            checkOneArgument(args[0]);
        }

        return isArgumentsValid;
    }

    private static void checkOneArgument(String argument) {
        if (argument.equals("p")) {
            System.err.println("User not inserted");
        } else if (argument.equals("u")) {
            System.err.println("Password not inserted");
        } else {
            System.err.println("Invalid argument");
        }
    }
}
