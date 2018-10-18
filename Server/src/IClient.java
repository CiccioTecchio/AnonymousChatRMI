import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {

	public void detto(String nick,String msg)throws RemoteException;
	public void scritto(String mittente,String destinatari,String msg)throws RemoteException;
	public void anonimato()throws RemoteException;
	public void fineAnonimato()throws RemoteException;
}
