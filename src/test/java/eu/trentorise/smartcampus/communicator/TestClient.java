package eu.trentorise.smartcampus.communicator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.Notifications;

public class TestClient {

	

	private CommunicatorConnector communicatorConnector;

	@Before
	public void setup() throws Exception {
		communicatorConnector = new CommunicatorConnector(
				Constants.COMMUNICATOR_SRV_URL, Constants.APPID);
	}

	


	@Test
	public void testAll() throws CommunicatorConnectorException {
	
		Notifications results = communicatorConnector.getNotifications(0L,
				0, -1, Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(results);
		Assert.assertNotSame(0, results.getNotifications().size());
		
		
		Notification notification = results.getNotifications().get(0);
		notification = communicatorConnector.getNotification(
				notification.getId(), Constants.USER_AUTH_TOKEN);
		Assert.assertNotNull(notification.getId());
		
		

		 notification = results.getNotifications().get(0);
		notification.setStarred(true);
		communicatorConnector.updateNotification(notification,
				notification.getId(), Constants.USER_AUTH_TOKEN);
		
		

	
				

		communicatorConnector.deleteNotification(notification.getId(),
				Constants.USER_AUTH_TOKEN);

	}

//	// /configuration/user/{appid}")
//	@Test
//	public void requestUserConfigurationToPush()
//			throws CommunicatorConnectorException {
//		Map<String, Object> x = communicatorConnector
//				.requestUserConfigurationToPush(Constants.APPID, Constants.USER_AUTH_TOKEN);
//
//		Assert.assertNotNull(x);
//		Assert.assertNotSame(0, x.size());
//	}
//
//	/**
//	 * Get notifications of the user
//	 * 
//	 * @param since
//	 *            since date, in milliseconds (use 0L for every time)
//	 * @param position
//	 *            position in the result set
//	 * @param count
//	 *            number of results (use -1L for all)
//	 * @param token
//	 *            an authorization token
//	 * @return
//	 * @throws CommunicatorConnectorException
//	 */
//	// "/notification"
//	@Test
//	public void getNotifications() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector.getNotifications(0L,
//				0, -1, Constants.USER_AUTH_TOKEN);
//		Assert.assertNotNull(results);
//		Assert.assertNotSame(0, results.getNotifications().size());
//
//	}
//
//	/**
//	 * Update a notification (only starred and labelIds values are currently
//	 * updated)
//	 * 
//	 * @param notification
//	 *            a notification
//	 * @param id
//	 *            the id of the notification to update
//	 * @param token
//	 *            an authorization token
//	 * @throws CommunicatorConnectorException
//	 */
//	// /notification/{id}
//	@Test
//	public void updateNotification() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector.getNotifications(0L,
//				0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification.setStarred(true);
//		communicatorConnector.updateNotification(notification,
//				notification.getId(), Constants.USER_AUTH_TOKEN);
//
//	}
//
//	/**
//	 * Get a notification by id
//	 * 
//	 * @param id
//	 *            a notification id
//	 * @param token
//	 *            an authorization token
//	 * @return
//	 * @throws CommunicatorConnectorException
//	 */
//	// "/notification/{id}")
//	@Test
//	public void getNotification() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector.getNotifications(0L,
//				0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//		notification = communicatorConnector.getNotification(
//				notification.getId(), Constants.USER_AUTH_TOKEN);
//		Assert.assertNotNull(notification.getId());
//
//	}
//
//	/**
//	 * Delete a notification
//	 * 
//	 * @param id
//	 *            the id of the notification to delete
//	 * @param token
//	 *            an authorization token
//	 * @throws CommunicatorConnectorException
//	 */
//	// /notification/{id}
//	@Test
//	public void deleteNotification() throws CommunicatorConnectorException {
//		Notifications results = communicatorConnector.getNotifications(0L,
//				0, -1, Constants.USER_AUTH_TOKEN);
//
//		Notification notification = results.getNotifications().get(0);
//
//		communicatorConnector.deleteNotification(notification.getId(),
//				Constants.USER_AUTH_TOKEN);
//
//	}
//
}
