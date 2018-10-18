
public class LocalClient {

	private String nick;
	private IClient ref;
	
	public LocalClient(String nick, IClient ref) {
		super();
		this.nick = nick;
		this.ref = ref;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public IClient getRef() {
		return ref;
	}

	public void setRef(IClient ref) {
		this.ref = ref;
	}
	
	
	
}
