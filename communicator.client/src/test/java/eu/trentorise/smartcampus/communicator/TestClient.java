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
import eu.trentorise.smartcampus.communicator.model.UserSignature;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class TestClient {

	private static final String CLIENTNAME = "clientname";
	private static final String AUTH_TOKEN = "Bearer 7ce0597d-a270-4f26-9aa6-03faa1130e98";
	@Autowired
	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {

	}

	public static final String REGISTRATIONID_HEADER = "REGISTRATIONID";

	// @Test
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

	// /send/user")
	@Test
	public void sendUserNotification() throws CommunicatorConnectorException {
		List<String> users = new ArrayList<String>();
		users.add("39");
		users.add("40");

		Notification not = new Notification();
		not.setDescription("Sei Registrato alle notifiche push");
		not.setTitle("Sei Registrato alle notifiche push");
		not.setType(CLIENTNAME);
		not.setUser("21");
		not.setId(null);
		NotificationAuthor author = new NotificationAuthor();
		author.setUserId("21");
		not.setAuthor(author);

		communicatorConnector.sendUserNotification(users, not, AUTH_TOKEN);
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

	// "/register/user/{appid}")
	@Test
	public void registerUser() throws CommunicatorConnectorException {
		UserSignature signature = new UserSignature();
		signature.setRegistrationId("blabla");
		signature.setAppName(CLIENTNAME);

		Assert.assertTrue(communicatorConnector.registerUserToPush(CLIENTNAME,
				signature, AUTH_TOKEN));

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

	// /configuration/user/{appid}")
	@Test
	public void requestUserConfigurationToPush()
			throws CommunicatorConnectorException {
		Map<String, String> x = communicatorConnector
				.requestUserConfigurationToPush(CLIENTNAME, AUTH_TOKEN);

		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());
	}

	/**
	 * Get notifications of the user
	 * 
	 * @param since
	 *            since date, in milliseconds (use 0L for every time)
	 * @param position
	 *            position in the result set
	 * @param count
	 *            number of results (use -1L for all)
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	// "/notification"
	@Test
	public void getNotifications() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector.getNotifications(0L,
				0, -1, AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.size());

	}
	
	/**
	 * Update a notification (only starred and labelIds values are currently
	 * updated)
	 * 
	 * @param notification
	 *            a notification
	 * @param id
	 *            the id of the notification to update
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// /notification/{id}
	@Test
	public void updateNotification() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector.getNotifications(0L,
				0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification.setStarred(true);
		communicatorConnector.updateNotification(notification,
				notification.getId(), AUTH_TOKEN);

	}

	/**
	 * Get a notification by id
	 * 
	 * @param id
	 *            a notification id
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	// "/notification/{id}")
	@Test
	public void getNotification() throws CommunicatorConnectorException {

		// create two notifications
		Notification notification = new Notification();
		notification = communicatorConnector.getNotification("1", AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
		Assert.assertFalse(notification.isStarred());
	}



	/**
	 * Delete a notification
	 * 
	 * @param id
	 *            the id of the notification to delete
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// /notification/{id}
	@Test
	public void deleteNotification() throws CommunicatorConnectorException {
		List<Notification> results = communicatorConnector.getNotifications(0L,
				0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);

		communicatorConnector.deleteNotification(notification.getId(),
				AUTH_TOKEN);

	}

	/**
	 * Send a notification as an application
	 * 
	 * @param notification
	 *            a notification
	 * @param appId
	 *            an application id
	 * @param users
	 *            a list of user Ids
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */

	// "/{capp}/notification")
	public void getNotificationsByApp() throws CommunicatorConnectorException {
	}

	// /{capp}/notification/{id}
	public void getNotificationByApp() throws CommunicatorConnectorException {
	}

	/**
	 * Update a notification (only starred and labelIds values are currently
	 * updated)
	 * 
	 * @param notification
	 *            a notification
	 * @param id
	 *            the id of the notification to update
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// /{capp}/notification/{id}
	public void updateByApp() throws CommunicatorConnectorException {
	}

	/**
	 * Delete a notification
	 * 
	 * @param id
	 *            the id of the notification to delete
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// //{capp}/notification/{id}
	public void deleteByApp() throws CommunicatorConnectorException {
	}

	// /user/notification
	public void getNotificationsByUser() throws CommunicatorConnectorException {
	}

	// /{user/notification/{id}
	public void getNotificationByUser() throws CommunicatorConnectorException {
	}

	/**
	 * Update a notification (only starred and labelIds values are currently
	 * updated)
	 * 
	 * @param notification
	 *            a notification
	 * @param id
	 *            the id of the notification to update
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// /user/notification/{id}
	public void updateByUser() throws CommunicatorConnectorException {
	}

	/**
	 * Delete a notification
	 * 
	 * @param id
	 *            the id of the notification to delete
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	// //user/notification/{id}
	public void deleteByUser() throws CommunicatorConnectorException {
	}

	// /unregister/user/{appId}/{userid}")
	public void unregisterUserToPush(String appid, String userid, String token)
			throws CommunicatorConnectorException {

	}

	// /unregister/app/{appId}")
	public void unregisterAppToPush(String appId, String token)
			throws CommunicatorConnectorException {

	}

}
