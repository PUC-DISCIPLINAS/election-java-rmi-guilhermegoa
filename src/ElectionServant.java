import java.rmi.RemoteException;
import java.util.TreeMap;

public class ElectionServant implements IElection {
    TreeMap mapSenator;
    TreeMap mapVotes;
    File file;

    ElectionServant() {
        super();
        this.file = new File();
        this.mapSenator = file.readFile();
        this.mapVotes = file.readVotes();

        if (mapVotes.size() > 0) {
            mapVotes.forEach((vHashName, vCandidateNumber) -> {
                ((Senator) mapSenator.get(vCandidateNumber)).vote();
            });
        }
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

        this.file.writeVotes(hashName, candidateNumber);

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
