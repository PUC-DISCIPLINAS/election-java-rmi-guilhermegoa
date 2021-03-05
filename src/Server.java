import java.io.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.TreeMap;

public class Server implements IElection {
    TreeMap mapSenator;
    TreeMap mapVotes;

    Server() {
        this.mapSenator = readFile();
        this.mapVotes = new TreeMap<String, String>();
    }

    public static void main(String args[]) {
        try {
            Server obj = new Server();
            IElection stub = (IElection) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Election", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public TreeMap readFile() {
        final int NUMBER = 0;
        final int NAME = 1;
        final int POLITICAL_PARTY = 2;

        TreeMap map = new TreeMap<String, Senator>();

        try {
            InputStream is = getClass().getResourceAsStream("file/senadores.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader csvReader = new BufferedReader(isr);

            String row;

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                Senator senator = new Senator(data[NUMBER], data[NAME], data[POLITICAL_PARTY]);
                map.put(data[NUMBER], senator);
            }

            csvReader.close();

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public String vote(String hashName, String candidateNumber) throws RemoteException {
        boolean alreadyVote = mapVotes.containsKey(hashName);
        if (alreadyVote) {
            return "You already voted.";
        }

        boolean isCandidate = mapSenator.containsKey(candidateNumber);
        if (!isCandidate) {
            return "Candidate not exist.";
        }

        ((Senator) mapSenator.get(candidateNumber)).vote();
        mapVotes.put(hashName, candidateNumber);

        return "VOTED";
    }

    @Override
    public String result(String candidateNumber) throws RemoteException {
        if (mapSenator.containsKey(candidateNumber)) {
            return ((Senator) mapSenator.get(candidateNumber)).getVoted();
        }
        
        return "Candidate not exist.";
    }


}