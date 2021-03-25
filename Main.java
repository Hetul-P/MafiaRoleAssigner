
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;




public class Main {
	
	private static String[] people;
	private static List<String> roles = new ArrayList<String>();
	private static int numPlayers = 0;
	private static Scanner input = new Scanner(System.in);  // Create a Scanner object
	
	private static int mafTeam;
	
	public static void Assign(String[] people, List<String> roles) throws AddressException, MessagingException {
		
		
		List<String> players = new ArrayList<String>();		
		for(String s : people) {
			players.add(s);
		}
		
		
		Collections.shuffle(players);
		Collections.shuffle(roles);
		System.out.println("");
		
		for(int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i) + " is " + roles.get(i));
			
			System.out.println(players.get(i) + " is " + roles.get(i));
			final String fromEmail = "yourgmail@gmail.com"; //requires valid gmail id
			final String password = "yourpass"; // correct password for gmail id
			final String toEmail = players.get(i); // can be any email id 
			
			System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
			props.put("mail.smtp.port", "587"); //TLS Port
			props.put("mail.smtp.auth", "true"); //enable authentication
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
			
	                //create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromEmail, password);
				}
			};
			Session session = Session.getInstance(props, auth);
			
			EmailUtil.sendEmail(session, toEmail,"Role for Mafia", "Your role is: " + roles.get(i));
		}
		
	}
	
	public static void mafiaTeam() {
		
		mafTeam = 0;
		int mafia = 0;
		int madeMan = -1;
		
				
		while(madeMan * 2 + 1 >= numPlayers/2  || madeMan < 0) {
			System.out.println("How many Mademen would you like in this game?");
			String madeM = input.nextLine();
			madeMan = Integer.parseInt(madeM);			
		}
		mafTeam += madeMan;
		
		while(mafia + madeMan >= numPlayers/2 || mafia == 0 || mafia < 0) {
			System.out.println("How many regular Mafias would you like in this game?");
			String mafias = input.nextLine();
			mafia = Integer.parseInt(mafias);			
		}
		mafTeam += mafia;
		
		for(int i =0; i < madeMan; i++) {
			roles.add("Made Man");
		}
		for(int i = 0; i < mafia; i++) {
			roles.add("Mafia");
		}
		
	}
	
	public static void townTeam() {
		int vijalAunty = -1;
		int hunter = -1;
		int detective = -1;
		int nurse = -1;
		
		while(detective > numPlayers - mafTeam || detective < 0) {
			System.out.println("How many Detectives would you like in this game?");
			String detectives = input.nextLine();
			detective = Integer.parseInt(detectives);
		}
		
		while(nurse + detective > numPlayers - mafTeam || nurse <0) {
			System.out.println("How many Nurses would you like in this game?");
			String nurses = input.nextLine();
			nurse = Integer.parseInt(nurses);
		}
		
		while(vijalAunty + nurse + detective > numPlayers - mafTeam || vijalAunty < 0) {
			System.out.println("How many Vijal Auntys would you like in this game?  Vijal Aunty has the ability to only ONCE kill someone who they think is mafia");
			String answerV = input.nextLine();
			vijalAunty = Integer.parseInt(answerV);
		}
		
		while(hunter + nurse + detective + vijalAunty > numPlayers - mafTeam || hunter < 0) {
			System.out.println("How many Hunters would you like in this game?  Hunter has the ability to kill someone who they think is mafia once they die");
			String answerH = input.nextLine();
			hunter = Integer.parseInt(answerH);
		}
		
		
		for(int i = 0; i < detective; i++) {
			roles.add("Detective");
		}
		
		for(int i = 0; i < nurse; i++) {
			roles.add("Nurse");
		}
		
		for(int i =0; i < vijalAunty; i++) {
			roles.add("Vijal Aunty");
		}
		for(int i = 0; i < hunter; i++) {
			roles.add("Hunter");
		}
		
		while(roles.size() < people.length) {
			roles.add("Citizen");
		}
	}
	
	public static void newGame() throws AddressException, MessagingException {	
		
		roles.clear();
		
		System.out.println("Enter everyone's names seperated by 1 space. ");
		String participants = input.nextLine();
		people = participants.split(" ");
		numPlayers = people.length;
		
		mafiaTeam();
		townTeam();
		
		Assign(people, roles);
	}
	
	public static void main(String[] args) throws AddressException, MessagingException {
		// TODO Auto-generated method stub
		newGame();
		while(true){			
			
			while(true) {
				System.out.println("Would you like to reassign roles with same people and settings or start a brand new game? (reassign / new game)");
				String game = input.nextLine();
				if(game.equals("reassign")) {
					Assign(people, roles);
					break;
				}
				else if(game.equals("new game")) {
					newGame();
					break;
				}
			}
		}
	}
}

