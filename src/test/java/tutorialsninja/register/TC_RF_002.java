package tutorialsninja.register;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;



public class TC_RF_002 {
	
	
	@Test	
	public static void main (String args[]) throws InterruptedException {
		
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://www.amazon.in/");
		
		driver.findElement(By.xpath("//span[text()='Hello, sign in']")).click();
		driver.findElement(By.xpath("//span[contains(text(), 'Need help')]")).click();
		driver.findElement(By.id("auth-fpp-link-bottom")).click();
		
		String email = "gwenstacy0007@gmail.com";
		String appPasscode = "god duq gojo bihar";
		String link = null;
		
		driver.findElement(By.id("ap_email")).sendKeys("gwenstacy0007@gmail.com");
		driver.findElement(By.id("continue")).click();
		
		try {
		Thread.sleep(10000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Gmail IMAP configuration
		String host = "imap.gmail.com";
		String port = "993";
		String username = email;
		String appPassword = "appPasscode";
		
		try {
			//Set mail properties
			Properties properties = new Properties();
			properties.put("mail.store.protocol","imaps");
			properties.put("mail.imap.host",host);
			properties.put("mail.imap.port",port);
			properties.put("mail.imap.ssl.enable","true");
			
			//Get the session object
			Session emailSession = Session.getDefaultInstance(properties);
			
			//Create the IMAP store object and connect with the server
			Store store = emailSession.getStore("imaps");
			store.connect(host,username,appPassword);
			
			//Open the inbox folder
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			
			//Retrieve messages from the inbox
			Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN),false));
			
			boolean found = false;
			
			for (int i = messages.length-1; i>=0 ; i--) {
				
				Message message = messages[i];
				
				if(message.getSubject().contains("amazon.in:Password recovery")) {
					found = true;
					System.out.println("Email Subject: " + message.getSubject());
					System.out.println("Email From: " + message.getFrom()[0].toString());
					System.out.println("Email Body: " + getTextFromMessage(message));
					break;
				}
			}
			
			if(!found) {
				System.out.println("NO confirmation email found.");
			}
			
			//Close the store and folder objects
			inbox.close(false);
			store.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}				
				
	//Method to get text from a multipart message (supporting plain text and HTML)
	private static String getTextFromMessage(Message message) throws Exception{
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		}else if(message.isMimeType("text/html")) {
			result = message.getContent().toString();
		}else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}
	
	//Recursively extract text from multipart
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception{
		StringBuilder result = new StringBuilder();
		int count = mimeMultipart.getCount();
		for (int i=0; i<count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result.append(bodyPart.getContent());
			}else if (bodyPart.isMimeType("text/html")){
				result.append(bodyPart.getContent());
			}else if(bodyPart.getContent() instanceof MimeMultipart) {
				result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
				
			}
		}
	return result.toString();
	}
}
