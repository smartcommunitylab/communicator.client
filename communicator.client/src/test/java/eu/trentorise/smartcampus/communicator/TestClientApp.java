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

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.NotificationAuthor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class TestClientApp {

	private static final String CLIENTNAME = "clientname";
	private static final String AUTH_TOKEN = "Bearer fd7129f7-7a44-49d0-8fbf-3d906ae6004c";
	@Autowired
	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {

	}

	public static final String REGISTRATIONID_HEADER = "REGISTRATIONID";

	@Test
	public void sendAppNotification() throws CommunicatorConnectorException {

		List<String> users = new ArrayList<String>();
		users.add("39");
		users.add("40");

		Notification notification = new Notification();
		notification.setTitle("Test notification");
		notification.setUser("21");

		NotificationAuthor author = new NotificationAuthor();
		author.setUserId("21");
		notification.setAuthor(author);

		communicatorConnector.sendAppNotification(notification, CLIENTNAME,
				users, AUTH_TOKEN);

	}

	// "/register/app/{appid}")
	@Test
	public void registerApp() throws CommunicatorConnectorException {
		AppSignature signature = new AppSignature();
		signature.setApiKey("prova");
		signature.setAppId(CLIENTNAME);
		signature.setSenderId("vaivai");
		Assert.assertTrue(communicatorConnector.registerApp(signature,
				CLIENTNAME, AUTH_TOKEN));

	}

	// /configuration/app/{appid}")
	@Test
	public void requestAppConfigurationToPush()
			throws CommunicatorConnectorException {
		Map<String, String> x = communicatorConnector
				.requestAppConfigurationToPush(CLIENTNAME, AUTH_TOKEN);
		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());

	}

	// "/{capp}/notification")
	@Test
	public void getNotificationsByApp() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.size());
	}

	// /{capp}/notification/{id}
	@Test
	public void getNotificationByApp() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification = communicatorConnector.getNotificationByApp(
				notification.getId(), AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
	}

	// /{capp}/notification/{id}
	@Test
	public void updateByApp() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification.setStarred(true);
		communicatorConnector.updateByApp(notification, notification.getId(),
				AUTH_TOKEN);
	}

	// //{capp}/notification/{id}
	@Test
	public void deleteByApp() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);

		communicatorConnector.deleteByApp(notification.getId(), AUTH_TOKEN);

	}

	// /unregister/app/{appId}")
	@Test
	public void unregisterAppToPush() throws CommunicatorConnectorException {
		communicatorConnector.unregisterAppToPush(AUTH_TOKEN);

	}

}
