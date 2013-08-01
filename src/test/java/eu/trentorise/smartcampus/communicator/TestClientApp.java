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
				Constants.COMMUNICATOR_SRV_URL, Constants.APPID);
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

		communicatorConnector.sendAppNotification(notification, Constants.APPID,
				users, Constants.CLIENT_AUTH_TOKEN);
		
		AppSignature signature = new AppSignature();
		signature.setApiKey("AIzaSyBA0dQYoF2YQKwm6h5dH4q7h5DTt7LmJrw");
		signature.setAppId(Constants.APPID);
		signature.setSenderId("557126495282");
		Assert.assertTrue(communicatorConnector.registerApp(signature,
				Constants.APPID, Constants.CLIENT_AUTH_TOKEN));
		
		Map<String, Object> x = communicatorConnector
				.requestAppConfigurationToPush(Constants.APPID, Constants.CLIENT_AUTH_TOKEN);
		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());
		
		Notifications results = communicatorConnector
				.getNotificationsByApp(0L, 0, -1, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.getNotifications().size());
		
		notification = results.getNotifications().get(0);
		
		notification = communicatorConnector.getNotificationByApp(
				notification.getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
		
		
		
		
		notification.setStarred(true);
		communicatorConnector.updateByApp(notification, notification.getId(),
				Constants.USER_AUTH_TOKEN);
		
		communicatorConnector.deleteByApp(notification.getId(), Constants.USER_AUTH_TOKEN);

	

		communicatorConnector.unregisterAppToPush(Constants.CLIENT_AUTH_TOKEN);

	


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
//		communicatorConnector.sendAppNotification(notification, Constants.APPID,
//				users, Constants.USER_AUTH_TOKEN);
//
//	}
//
//	// "/register/app/{appid}")
//	@Test
//	public void registerApp() throws CommunicatorConnectorException {
//		AppSignature signature = new AppSignature();
//		signature.setApiKey("AIzaSyBA0dQYoF2YQKwm6h5dH4q7h5DTt7LmJrw");
//		signature.setAppId(Constants.APPID);
//		signature.setSenderId("557126495282");
//		Assert.assertTrue(communicatorConnector.registerApp(signature,
//				Constants.APPID, Constants.USER_AUTH_TOKEN));
//
//	}
//
//	// /configuration/app/{appid}")
//	@Test
//	public void requestAppConfigurationToPush()
//			throws CommunicatorConnectorException {
//		Map<String, Object> x = communicatorConnector
//				.requestAppConfigurationToPush(Constants.APPID, Constants.USER_AUTH_TOKEN);
//		Assert.assertNotNull(x);
//		Assert.assertNotSame(0, x.size());
//
//	}
//
//	// "/{capp}/notification")
//	@Test
//	public void getNotificationsByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, Constants.USER_AUTH_TOKEN);
//		Assert.assertNotNull(results);
//		Assert.assertNotSame(0, results.getNotifications().size());
//	}
//
//	// /{capp}/notification/{id}
//	@Test
//	public void getNotificationByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification = communicatorConnector.getNotificationByApp(
//				notification.getId(), Constants.USER_AUTH_TOKEN);
//		Assert.assertNotNull(notification.getId());
//	}
//
//	// /{capp}/notification/{id}
//	//@Test
//	public void updateByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification.setStarred(true);
//		communicatorConnector.updateByApp(notification, notification.getId(),
//				Constants.USER_AUTH_TOKEN);
//	}
//
//	// //{capp}/notification/{id}
//	//@Test
//	public void deleteByApp() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector
//				.getNotificationsByApp(0L, 0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//
//		communicatorConnector.deleteByApp(notification.getId(), Constants.USER_AUTH_TOKEN);
//
//	}
//
//	// /unregister/app/{appId}")
//	//@Test
//	public void unregisterAppToPush() throws CommunicatorConnectorException {
//		communicatorConnector.unregisterAppToPush(Constants.USER_AUTH_TOKEN);
//
//	}

}
