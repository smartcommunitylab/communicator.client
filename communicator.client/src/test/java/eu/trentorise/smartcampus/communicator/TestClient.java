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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class TestClient {

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
		List<Notification> results = communicatorConnector.getNotifications(0L,
				0, -1, AUTH_TOKEN);

		Notification notification = results.get(0);
		notification = communicatorConnector.getNotification(
				notification.getId(), AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());

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

}
