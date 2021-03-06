import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];

        int option = 0;

        do {
            menu();

            System.out.println("Type option number.");
            Scanner input = new Scanner(System.in);
            option = input.nextInt();

            String candidateNumber, name;

            switch (option) {
                case 0:
                    break;
                case 1:
                    System.out.printf("-> 1 vote by name. <-\nType your name: ");
                    name = input.next();
                    System.out.printf("Type candidate number: ");
                    candidateNumber = input.next();
                    rmiConnection("vote", host, generateTokenName(name), candidateNumber);
                    break;
                case 2:
                    System.out.printf("Type candidate number: ");
                    candidateNumber = input.next();
                    rmiConnection("result", host, "", candidateNumber);
                    break;
                default:
                    System.out.println("Option invalid");
            }
        } while (option != 0);


    }

    private static void menu() {
        System.out.println("---------------- Menu ----------------");
        System.out.println("0 - Type 0 to exit.");
        System.out.println("1 - Type 1 to vote. -> 1 vote by name. <-");
        System.out.println("2 - Type 2 to see the result of a candidate.");
    }

    public static void rmiConnection(String option, String host, String hashName, String candidateNumber) {
        int count = 0;

        Registry registry;
        IElection stub;
        String response;

        while (count < 3) {
            try {
                registry = LocateRegistry.getRegistry(host);
                stub = (IElection) registry.lookup("rmi://localhost/Election");

                switch (option) {
                    case "vote":
                        response = (String) stub.vote(hashName, candidateNumber);
                        System.out.println("--------- Response ----------");
                        System.out.println(response);
                        return;
                    case "result":
                        response = stub.result(candidateNumber);
                        System.out.println("--------- Response ----------");
                        System.out.println(response);
                        return;
                    default:
                        System.out.println("The option not exist");
                        return;
                }
            } catch (Exception e) {
                ++count;
                if (count < 3) {
                    System.err.println("problem connecting to the server.\nTrying to reconnect...");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                } else {
                    System.err.println("Unable to connect with server");
                }

            }
        }

    }

    public static String generateTokenName(String name) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(name.getBytes());
            byte[] md5 = md.digest();

            BigInteger numMd5 = new BigInteger(1, md5);
            String hashMd5 = String.format("%022x", numMd5);

            return hashMd5;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}
