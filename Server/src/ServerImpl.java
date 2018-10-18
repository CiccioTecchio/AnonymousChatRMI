import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerImpl extends UnicastRemoteObject implements IServer {

	private static String nick;//nickname del server passato da linea di comando
	private static ServerImpl server; //nel compito la variabile non è dichiarata static
									  // e il tipo di dato è Server al posto di ServerImpl
	private static final long serialVersionUID=1L;
	public static Logger logger=Logger.getLogger("global");
	private HashMap<String,LocalClient> map=new HashMap<String,LocalClient>();
	private boolean isAnonimo;
	private final String ANONIMO="XXXX";
	
	public ServerImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
		this.isAnonimo=false;
	}

	
	@Override
	public void dici(String nick, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		Iterable<LocalClient> it=map.values();
		if(isAnonimo){
			synchronized (map) {
				
								for(LocalClient aclient:it){
									aclient.getRef().detto(ANONIMO, msg);
								}
								}//FINE SYNCHRO
		}else {
			synchronized (map) {
								for(LocalClient aclient:it){
									aclient.getRef().detto(nick, msg);
								}
								}
		}
	}

	@Override
	public synchronized void iscrivi(IClient ref, String nick) throws RemoteException {
		// TODO Auto-generated method stub
		map.put(nick, new LocalClient(nick, ref)); // Nel compito i parametri della put sono sbagliati
	}

	@Override
	public String list() {
		// TODO Auto-generated method stub
		String app="";
		int i=1;
		Iterable<LocalClient> it=map.values(); // Nel compito non sono specificati i parametri dell Iterable
		for(LocalClient aClient:it){
			app=app+i+")"+aClient.getNick()+"\n"; //
			i++;
		}
		
		return app;
	}

	@Override
	public void anonimo() throws RemoteException {
		// TODO Auto-generated method stub
		Iterable<LocalClient>it= map.values();
							 isAnonimo=true;
							 synchronized (map) {
								for(LocalClient aClient:it){
									aClient.getRef().anonimato();
								}
							}
	}

	@Override
	public void fineAnonimo() throws RemoteException {
		// TODO Auto-generated method stub
		Iterable<LocalClient>it= map.values();
		 isAnonimo=false;
		 synchronized (map) {
			for(LocalClient aClient:it){
				aClient.getRef().fineAnonimato();
			}
		}
	}
	
	
	
	@Override
	//CORRETTO
	public void scrivi(String listaNick, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		LocalClient aClient;
		String [] app=listaNick.split(":");
		//String [] an= new String[app.length];
		String s="";
		int i=0;
		if(isAnonimo){
						while(i<app.length){ // creo una stringa contenente tanti ANONIMO per quanti sono i destinatari
							s=s+":"+ANONIMO;
							i++;
						}
						i=0;
				synchronized (map) {
					while(i<app.length){
						aClient=map.get(app[i]);
						aClient.getRef().scritto(ANONIMO, s, msg);
						i++;
					}
				}//FINE SYNCHRO
				i=0;
		}else {
				synchronized (map) {
					while(i<app.length){
						aClient=map.get(app[i]);
						aClient.getRef().scritto(nick, listaNick, msg);
						i++;
					}
				}
		}
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
				nick="SERVER-"+args[0];
				logger.info("Benvenuto "+nick);
				ServerImpl server=new ServerImpl();
				logger.info("Effettuo il rebind ...");
				Naming.rebind("Server", server);
				logger.info("Rebind effettuato con successo!");
				String cmd;
				logger.info("Usa puoi usare la chat...");
				int numero;
				while(true){
					cmd=in.readLine();
					if(cmd.equals("!list")){
											System.out.println(server.list());
											}//FINE LIST
					else if(cmd.equals("!anonimo")){
													server.anonimo();
													System.out.println("La chat è stata resa anonima");
													}
					else if(cmd.equals("!nonanonimo")){
														server.fineAnonimo();
														System.out.println("La chat NON è più anonima");
														}
					else if(cmd.startsWith("!scrivi")){
														String app[]=cmd.split(" ");
														String msg="";
														int i=2;
														while(i<app.length){
																			msg=msg+app[i]+" ";// aggiunta concatenzione della Stringa
																			i++;
																			}
														server.scrivi(app[1], msg);
														}
					else server.dici(nick, cmd);
				}
			}
		}catch(RemoteException e){logger.severe("Problema con oggetti remoti... "+e.getMessage());
								  e.printStackTrace();
								  System.exit(0);}
		catch(Exception e){
							logger.severe("Problema sconosciuto... "+e.getMessage());
							e.printStackTrace();
							System.exit(0);
							}
	}


	

}
