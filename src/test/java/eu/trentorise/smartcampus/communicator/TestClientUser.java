package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.NotificationAuthor;
import eu.trentorise.smartcampus.communicator.model.Notifications;
import eu.trentorise.smartcampus.communicator.model.UserSignature;

public class TestClientUser {

	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {
		communicatorConnector = new CommunicatorConnector(
				ConstantsTest.COMMUNICATOR_SRV_URL, ConstantsTest.APPID);
	}

	@Test
	public void allTest() throws CommunicatorConnectorException {

		UserSignature signature = new UserSignature();
		signature.setRegistrationId("blabla");
		signature.setAppName(ConstantsTest.APPID);

		Assert.assertTrue(communicatorConnector.registerUserToPush(signature,
				ConstantsTest.APPID, ConstantsTest.USER_AUTH_TOKEN));

		Map<String, Object> x = communicatorConnector
				.requestUserConfigurationToPush(ConstantsTest.APPID,
						ConstantsTest.USER_AUTH_TOKEN);

		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());

		List<String> users = new ArrayList<String>();
		users.add("39");
		users.add("40");

		Notification not = new Notification();
		not.setDescription("Check in update e delete");
		not.setTitle("Check in update e delete");
		not.setType(ConstantsTest.APPID);
		not.setUser("1");
		not.setId(null);
		NotificationAuthor author = new NotificationAuthor();
		author.setUserId("1");
		not.setAuthor(author);

		// communicatorConnector.sendUserNotification(users, not,
		// ConstantsTest.USER_AUTH_TOKEN);

		Notifications results = communicatorConnector.getNotificationsByUser(
				0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);

		Notification notification = results.getNotifications().get(0);
		notification = communicatorConnector.getNotificationByUser(
				notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());

		notification.setStarred(true);
		communicatorConnector.updateByUser(notification, notification.getId(),
				ConstantsTest.USER_AUTH_TOKEN);

		communicatorConnector.deleteByUser(notification.getId(),
				ConstantsTest.USER_AUTH_TOKEN);

		communicatorConnector.unregisterUserToPush(ConstantsTest.APPID,
				ConstantsTest.USER_AUTH_TOKEN);
	}

	//
	// // /send/user")
	// @Test
	// public void sendUserNotification() throws CommunicatorConnectorException
	// {
	// List<String> users = new ArrayList<String>();
	// users.add("39");
	// users.add("40");
	//
	// Notification not = new Notification();
	// not.setDescription("Check in update e delete");
	// not.setTitle("Check in update e delete");
	// not.setType(ConstantsTest.APPID);
	// not.setUser("1");
	// not.setId(null);
	// NotificationAuthor author = new NotificationAuthor();
	// author.setUserId("1");
	// not.setAuthor(author);
	//
	// communicatorConnector.sendUserNotification(users, not,
	// ConstantsTest.USER_AUTH_TOKEN);
	// }
	//
	// // "/register/user/{appid}")
	// @Test
	// public void registerUser() throws CommunicatorConnectorException {
	// UserSignature signature = new UserSignature();
	// signature.setRegistrationId("blabla");
	// signature.setAppName(ConstantsTest.APPID);
	//
	// Assert.assertTrue(communicatorConnector.registerUserToPush(ConstantsTest.APPID,
	// signature, ConstantsTest.USER_AUTH_TOKEN));
	//
	// }
	//
	// // /configuration/user/{appid}")
	// @Test
	// public void requestUserConfigurationToPush()
	// throws CommunicatorConnectorException {
	// Map<String, Object> x = communicatorConnector
	// .requestUserConfigurationToPush(ConstantsTest.APPID,
	// ConstantsTest.USER_AUTH_TOKEN);
	//
	// Assert.assertNotNull(x);
	// Assert.assertNotSame(0, x.size());
	// }
	//
	// // /user/notification
	// @Test
	// public void getNotificationsByUser() throws
	// CommunicatorConnectorException {
	// Notifications results = communicatorConnector
	// .getNotificationsByUser(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
	// Assert.assertNotNull(results);
	// Assert.assertNotSame(0, results.getNotifications().size());
	// }
	//
	// // /{user/notification/{id}
	// @Test
	// public void getNotificationByUser() throws CommunicatorConnectorException
	// {
	// Notifications results = communicatorConnector
	// .getNotificationsByUser(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
	//
	// Notification notification = results.getNotifications().get(0);
	// notification = communicatorConnector.getNotificationByUser(
	// notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
	// Assert.assertNotNull(notification.getId());
	// }
	//
	// // /user/notification/{id}
	// @Test
	// public void updateByUser() throws CommunicatorConnectorException {
	// Notifications results = communicatorConnector
	// .getNotificationsByUser(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
	//
	// Notification notification = results.getNotifications().get(0);
	// notification.setStarred(true);
	// communicatorConnector.updateByUser(notification, notification.getId(),
	// ConstantsTest.USER_AUTH_TOKEN);
	// }
	//
	// // //user/notification/{id}
	// @Test
	// public void deleteByUser() throws CommunicatorConnectorException {
	// Notifications results = communicatorConnector
	// .getNotificationsByUser(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
	//
	// Notification notification = results.getNotifications().get(0);
	//
	// communicatorConnector.deleteByUser(notification.getId(),
	// ConstantsTest.USER_AUTH_TOKEN);
	// }
	//
	// // /unregister/user/{appId}/{userid}")
	// @Test
	// public void unregisterUserToPush() throws CommunicatorConnectorException
	// {
	// communicatorConnector.unregisterUserToPush("1",
	// ConstantsTest.USER_AUTH_TOKEN);
	// }

}
