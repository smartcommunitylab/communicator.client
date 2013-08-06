package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.NotificationAuthor;
import eu.trentorise.smartcampus.communicator.model.Notifications;


public class TestClientApp {

	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {
		communicatorConnector = new CommunicatorConnector(
				ConstantsTest.COMMUNICATOR_SRV_URL, ConstantsTest.APPID);
	}


	@Test
	public void testAll() throws CommunicatorConnectorException{
		List<String> users = new ArrayList<String>();
		users.add("2");
		users.add("1");

		Notification notification = new Notification();
		notification.setTitle("Test notification");
		notification.setUser("1");

		NotificationAuthor author = new NotificationAuthor();
		author.setUserId("1");
		notification.setAuthor(author);

		communicatorConnector.sendAppNotification(notification, ConstantsTest.APPID,
				users, ConstantsTest.CLIENT_AUTH_TOKEN);
		
		AppSignature signature = new AppSignature();
		signature.setApiKey("AIzaSyBA0dQYoF2YQKwm6h5dH4q7h5DTt7LmJrw");
		signature.setAppId(ConstantsTest.APPID);
		signature.setSenderId("557126495282");
		Assert.assertTrue(communicatorConnector.registerApp(signature,
				ConstantsTest.APPID, ConstantsTest.CLIENT_AUTH_TOKEN));
		
		Map<String, Object> x = communicatorConnector
				.requestAppConfigurationToPush(ConstantsTest.APPID, ConstantsTest.CLIENT_AUTH_TOKEN);
		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());
		
		Notifications results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.getNotifications().size());
		
		notification = results.getNotifications().get(0);
		
		notification = communicatorConnector.getNotificationByApp(
				notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
		
		
		
		
		notification.setStarred(true);
		communicatorConnector.updateByApp(notification, notification.getId(),
				ConstantsTest.USER_AUTH_TOKEN);
		
		communicatorConnector.deleteByApp(notification.getId(), ConstantsTest.USER_AUTH_TOKEN);

	

		communicatorConnector.unregisterAppToPush(ConstantsTest.CLIENT_AUTH_TOKEN);

	


	}

//	@Test
//	public void sendAppNotification() throws CommunicatorConnectorException {
//
//		List<String> users = new ArrayList<String>();
//		users.add("2");
//		users.add("1");
//
//		Notification notification = new Notification();
//		notification.setTitle("Test notification");
//		notification.setUser("1");
//
//		NotificationAuthor author = new NotificationAuthor();
//		author.setUserId("1");
//		notification.setAuthor(author);
//
//		communicatorConnector.sendAppNotification(notification, ConstantsTest.APPID,
//				users, ConstantsTest.USER_AUTH_TOKEN);
//
//	}
//
//	// "/register/app/{appid}")
//	@Test
//	public void registerApp() throws CommunicatorConnectorException {
//		AppSignature signature = new AppSignature();
//		signature.setApiKey("AIzaSyBA0dQYoF2YQKwm6h5dH4q7h5DTt7LmJrw");
//		signature.setAppId(ConstantsTest.APPID);
//		signature.setSenderId("557126495282");
//		Assert.assertTrue(communicatorConnector.registerApp(signature,
//				ConstantsTest.APPID, ConstantsTest.USER_AUTH_TOKEN));
//
//	}
//
//	// /configuration/app/{appid}")
//	@Test
//	public void requestAppConfigurationToPush()
//			throws CommunicatorConnectorException {
//		Map<String, Object> x = communicatorConnector
//				.requestAppConfigurationToPush(ConstantsTest.APPID, ConstantsTest.USER_AUTH_TOKEN);
//		Assert.assertNotNull(x);
//		Assert.assertNotSame(0, x.size());
//
//	}
//
//	// "/{capp}/notification")
//	@Test
//	public void getNotificationsByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
//		Assert.assertNotNull(results);
//		Assert.assertNotSame(0, results.getNotifications().size());
//	}
//
//	// /{capp}/notification/{id}
//	@Test
//	public void getNotificationByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification = communicatorConnector.getNotificationByApp(
//				notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
//		Assert.assertNotNull(notification.getId());
//	}
//
//	// /{capp}/notification/{id}
//	//@Test
//	public void updateByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification.setStarred(true);
//		communicatorConnector.updateByApp(notification, notification.getId(),
//				ConstantsTest.USER_AUTH_TOKEN);
//	}
//
//	// //{capp}/notification/{id}
//	//@Test
//	public void deleteByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, ConstantsTest.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//
//		communicatorConnector.deleteByApp(notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
//
//	}
//
//	// /unregister/app/{appId}")
//	//@Test
//	public void unregisterAppToPush() throws CommunicatorConnectorException {
//		communicatorConnector.unregisterAppToPush(ConstantsTest.USER_AUTH_TOKEN);
//
//	}

}
