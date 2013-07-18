package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.NotificationAuthor;
import eu.trentorise.smartcampus.communicator.model.UserSignature;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class TestClientUser {

	private static final String CLIENTNAME = "clientname";
	private static final String AUTH_TOKEN = "Bearer dc8f4ef0-c810-4f56-a9b0-2627fe77b040";
	@Autowired
	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {

	}

	public static final String REGISTRATIONID_HEADER = "REGISTRATIONID";

	// /send/user")
	@Test
	public void sendUserNotification() throws CommunicatorConnectorException {
		List<String> users = new ArrayList<String>();
		users.add("39");
		users.add("40");

		Notification not = new Notification();
		not.setDescription("Check in update e delete");
		not.setTitle("Check in update e delete");
		not.setType(CLIENTNAME);
		not.setUser("21");
		not.setId(null);
		NotificationAuthor author = new NotificationAuthor();
		author.setUserId("21");
		not.setAuthor(author);

		communicatorConnector.sendUserNotification(users, not, AUTH_TOKEN);
	}

	// "/register/user/{appid}")
	@Test
	public void registerUser() throws CommunicatorConnectorException {
		UserSignature signature = new UserSignature();
		signature.setRegistrationId("blabla");
		signature.setAppName(CLIENTNAME);

		Assert.assertTrue(communicatorConnector.registerUserToPush(CLIENTNAME,
				signature, AUTH_TOKEN));

	}

	// /configuration/user/{appid}")
	@Test
	public void requestUserConfigurationToPush()
			throws CommunicatorConnectorException {
		Map<String, String> x = communicatorConnector
				.requestUserConfigurationToPush(CLIENTNAME, AUTH_TOKEN);

		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());
	}

	// /user/notification
	@Test
	public void getNotificationsByUser() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByUser(0L, 0, -1, AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.size());
	}

	// /{user/notification/{id}
	@Test
	public void getNotificationByUser() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByUser(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification = communicatorConnector.getNotificationByUser(
				notification.getId(), AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
	}

	// /user/notification/{id}
	@Test
	public void updateByUser() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByUser(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification.setStarred(true);
		communicatorConnector.updateByUser(notification, notification.getId(),
				AUTH_TOKEN);
	}

	// //user/notification/{id}
	@Test
	public void deleteByUser() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByUser(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);

		communicatorConnector.deleteByUser(notification.getId(), AUTH_TOKEN);
	}

	// /unregister/user/{appId}/{userid}")
	@Test
	public void unregisterUserToPush() throws CommunicatorConnectorException {
		communicatorConnector.unregisterUserToPush("21", AUTH_TOKEN);
	}

}
