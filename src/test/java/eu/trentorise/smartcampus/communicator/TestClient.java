package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.trentorise.smartcampus.communicator.model.Notification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class TestClient {

	private static final String AUTH_TOKEN = "aee58a92-d42d-42e8-b55e-12e4289586fc";
	@Autowired
	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {
	}

	@Test
	public void testSend() throws Exception {
		List<String> users = new ArrayList<String>();
		users.add("39");
		users.add("40");
		
		Notification notification = new Notification();
		notification.setTitle("Test notification");
		notification.setUser("39");
		
		communicatorConnector.sendAppNotification(notification, "test_app", users, AUTH_TOKEN);
	}
	
	@Test
	public void testRegister() throws Exception {
		
		communicatorConnector.registerApp(AUTH_TOKEN, "apikeytest","testsender");
		
		communicatorConnector.registerUser(AUTH_TOKEN, "prova1");
	}
	
	@Test
	public void testCRUD() throws Exception {

			// create two notifications
			Notification notification = new Notification();
			notification.setTitle("Test notification");
			notification.setUser("39");
			notification.setTimestamp(System.currentTimeMillis());

			// get all notifications
			List<Notification> results = communicatorConnector.getNotifications(0L, 0, -1, AUTH_TOKEN);
			Assert.assertNotNull(results);
			Assert.assertNotSame(0, results.size());
			System.out.println(results);
			Notification notification2 = results.get(0);
			
			// get no future notifications
			results = communicatorConnector.getNotifications(System.currentTimeMillis(), 0, -1, AUTH_TOKEN);
			Assert.assertNotNull(results);
			Assert.assertSame(0, results.size());
			System.out.println(results);
			
			// get one notification
			results = communicatorConnector.getNotifications(0L, 1, 1, AUTH_TOKEN);
			Assert.assertNotNull(results);
			Assert.assertSame(1, results.size());
			System.out.println(results);			
		
			
			notification = communicatorConnector.getNotification(notification2.getId(), AUTH_TOKEN);
			Assert.assertNotNull(notification.getId());
			Assert.assertFalse(notification.isStarred());
			System.out.println(notification.getTitle() + " / " + notification.isStarred());
			
			notification.setStarred(true);
			communicatorConnector.updateNotification(notification, notification2.getId(), AUTH_TOKEN);
			
			notification = communicatorConnector.getNotification(notification2.getId(), AUTH_TOKEN);
			Assert.assertNotNull(notification);
			Assert.assertTrue(notification.isStarred());			
			System.out.println(notification.getTitle() + " / " + notification.isStarred());
			
			communicatorConnector.deleteNotification(notification.getId(), AUTH_TOKEN);
			
			notification = communicatorConnector.getNotification(notification.getId(), AUTH_TOKEN);
			System.out.println(notification);
			
	}

}
