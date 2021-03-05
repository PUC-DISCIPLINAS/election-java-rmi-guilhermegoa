import java.rmi.RemoteException;
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
                case 1:
                    name = input.next();
                    candidateNumber = input.next();

                    System.out.println("Response: " + send("vote", host, hashMD5(name), candidateNumber));
                    break;
                case 2:
                    candidateNumber = input.next();
                    System.out.println("response: " + send("result", host, "", candidateNumber));
                    break;
                default:
                    System.out.println("Option invalid");
            }
        } while (option != 0);


    }

    private static void menu() {
        System.out.println("---------------- Menu ----------------");
        System.out.println("0 - Type 0 to exit.");
        System.out.println("1 - Type 1 to vote.");
        System.out.println("2 - Type 2 to see the result of a candidate.");
    }

    public static String send(String option, String host, String hashName, String candidateNumber) {
        int cont = 0;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            IElection stub = (IElection) registry.lookup("Election");

            switch (option) {
                case "vote":
                    return vote(stub, hashName, candidateNumber, cont);
                case "result":
                    return result(stub, candidateNumber, cont);
                default:
                    return "The option not exist";
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        } finally {
            cont = 0;
        }

        return "blabla";
    }

    private static String vote(IElection stub, String hashName, String candidateNumber, int cont) throws InterruptedException {
        try {
            return (String) stub.vote(hashName, candidateNumber);
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            System.out.println("Was not possible to vote\n. Trying again...");
            if (cont < 3) {
                Thread.sleep(10000);
                return vote(stub, hashName, candidateNumber, ++cont);
            }
        }
        return "Unexpected error\n";
    }

    private static String result(IElection stub, String candidateNumber, int cont) throws InterruptedException {
        try {
            return (String) stub.result(candidateNumber);
        } catch (RemoteException e) {
            System.err.println("Client exception: " + e.toString());
            System.out.println("Was not possible to vote\n. Trying again...");
            if (cont < 3) {
                Thread.sleep(10000);
                return result(stub, candidateNumber, ++cont);
            }
        }
        return "Unexpected error\n";
    }

    public static String hashMD5(String name) {

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
