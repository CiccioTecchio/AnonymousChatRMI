import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ClientImpl extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID=1L;
	public static Logger logger=Logger.getLogger("global");
	private static String nick,cmd;
	@Override
	public void detto(String nick, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(""+nick+": "+msg);
	}

	@Override
	public void scritto(String mittente, String destinatari, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Da "+mittente+" a "+destinatari+": "+msg);
	}

	@Override
	public void anonimato() throws RemoteException {
		// TODO Auto-generated method stub
		logger.info("La chat è stata resa anonima");
	}

	@Override
	public void fineAnonimato() throws RemoteException {
		// TODO Auto-generated method stub
		logger.info("La chat NON è più anonima");
	}



	public ClientImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setSecurityManager(new RMISecurityManager());
		BufferedReader in= new BufferedReader(new InputStreamReader(System.in));


		try{	
			if(args.length<0){
				logger.info("Inserire nick del Server da riga di comando");
				System.exit(0);
			}
			else{
				nick=args[0];
				ClientImpl myself=new ClientImpl();
				logger.info("Ciao "+nick+" aspetta che mi registro sul server e poi ti iscivo ...");
				IServer server=(IServer)Naming.lookup("rmi://192.168.1.13/Server");
				logger.info("Iscrizione in corso ...");
				server.iscrivi(myself, nick);
				logger.info("Iscrizione completata "+nick+" ora puoi chattare");
				
				while(true){
				cmd=in.readLine();
							if(cmd.equals("!list"))System.out.println(server.list());
					   else if(cmd.startsWith("!scrivi")){
						   								  String app[]=cmd.split(" ");
						   								  String msg="";
						   								  int i=2;
						   								  while(i<app.length){
						   								  msg=msg+""+app[i]+" ";// corretta concatenazione
						   								  i++;
						   								  					 }
						   								  server.scrivi(app[1], msg);
					}else server.dici(nick, cmd);
				}
			}

		}catch(RemoteException e){
								  logger.severe("Problema con oggetti remoti... "+e.getMessage());
		  						  e.printStackTrace();
		  						  System.exit(0);
		  						  }
		 catch(Exception e){
							logger.severe("Problema sconosciuto... "+e.getMessage());
							e.printStackTrace();
							System.exit(0);
							}
	}


}
