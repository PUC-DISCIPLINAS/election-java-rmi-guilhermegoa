import java.rmi.Remote;

public interface IElection extends Remote {
    public Object vote(String hashName, String candidateNumber) throws java.rmi.RemoteException;

    public String result(String candidateNumber) throws java.rmi.RemoteException;

}
