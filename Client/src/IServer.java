import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

	public void dici(String nick,String msg)throws RemoteException;
	public void iscrivi(IClient ref, String nick)throws RemoteException;
	public String list() throws RemoteException;
	public void anonimo()throws RemoteException;
	public void fineAnonimo() throws RemoteException;
	public void scrivi(String listaNick,String msg)throws RemoteException;
}
