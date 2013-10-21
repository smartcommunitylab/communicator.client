package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.NotificationAuthor;
import eu.trentorise.smartcampus.communicator.model.Notifications;
import eu.trentorise.smartcampus.communicator.model.UserSignature;
import eu.trentorise.smartcampus.communicator.util.Constants;

public class TestClientApp {

	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {
		communicatorConnector = new CommunicatorConnector(
				ConstantsTest.COMMUNICATOR_SRV_URL, ConstantsTest.APPID);
	}
	
	
	
	
	@Test
	public void testReg() throws CommunicatorConnectorException{
		UserSignature usignature = new UserSignature();
		usignature
				.setRegistrationId("APA91bE3MENtNWVTtohuz4PLaQ6Z0gyyzM7ExGWW4bfjtaPyxbbcf91oUoE1B_pwCNVIgyO1R0uxkLEV-GZw7Lklul4zhMeoaqZEzFpEe9tVHdVM6Pv33Vfk0n4l33ayeEeBA1ViKoN3l9km3OsiP1pr0bRbbzb3kqknCASu1McKFw6GXfSG9GqkEDEm_HhVLWQCSpTO6ZG3");
		usignature.setAppName(ConstantsTest.APPID);

		// Assert.assertTrue(communicatorConnector.registerUserToPush(ConstantsTest.APPID,
		// usignature, ConstantsTest.USER_AUTH_TOKEN));

	

		AppSignature signature = new AppSignature();

		signature.setAppId(ConstantsTest.APPID);

		Map<String, Object> publiclist = new HashMap<String, Object>();
		publiclist.put(Constants.GCM_SENDER_ID, "557126495282");
		Map<String, Object> privatelist = new HashMap<String, Object>();
		privatelist.put(Constants.GCM_SENDER_API_KEY,
				"AIzaSyBA0dQYoF2YQKwm6h5dH4q7h5DTt7LmJrw");
		signature.setPrivateKey(privatelist);
		signature.setPublicKey(publiclist);

		
		Assert.assertTrue(communicatorConnector.registerApp(signature,
				ConstantsTest.APPID, ConstantsTest.CLIENT_AUTH_TOKEN));
		
	}
	
	@Test
	public void testRequestConfigutation() throws CommunicatorConnectorException {
		
		

		Map<String, Object> x = communicatorConnector
				.requestPublicConfigurationToPush(ConstantsTest.APPID,
						ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(x);
		Assert.assertNotSame(0, x.size());
	}
	

	@Test
	public void testSendNot() throws CommunicatorConnectorException {
		

		
		
		List<String> users = new ArrayList<String>();
		users.add("1");

		Notification notification = new Notification();
		notification.setTitle("Test notification");
		
		communicatorConnector.sendAppNotification(notification,
				ConstantsTest.APPID, users, ConstantsTest.CLIENT_AUTH_TOKEN);
	}
	@Test
	public void testGetAllUpdateDelete() throws CommunicatorConnectorException {
		

	

		Notifications results = communicatorConnector.getNotificationsByApp(0L,
				0, -1, ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.getNotifications().size());
	
	

		
		Notification notification = results.getNotifications().get(0);

		notification = communicatorConnector.getNotificationByApp(
				notification.getId(), ConstantsTest.USER_AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());

		notification.setStarred(true);
		communicatorConnector.updateByApp(notification, notification.getId(),
				ConstantsTest.USER_AUTH_TOKEN);

		communicatorConnector.deleteByApp(notification.getId(),
				ConstantsTest.USER_AUTH_TOKEN);

		// communicatorConnector.unregisterAppToPush(ConstantsTest.CLIENT_AUTH_TOKEN);

	}

}
