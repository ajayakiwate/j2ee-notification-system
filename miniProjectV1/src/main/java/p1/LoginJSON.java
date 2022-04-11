package p1;

public class LoginJSON {
	public boolean success = false;
	public Message message = new Message();
}

class Message{
	public boolean session = false;
	public boolean cookie = false;
	public boolean admin = false;
	public String emailid = "";
	public String log = "";
}