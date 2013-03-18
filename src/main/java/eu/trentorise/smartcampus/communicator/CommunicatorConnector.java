package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.util.HTTPConnector;
import eu.trentorise.smartcampus.communicator.util.HttpMethod;

/**
 * Class used to connect with the communicator service.
 * 
 */
public class CommunicatorConnector {

	private static final String NOTIFICATION = "eu.trentorise.smartcampus.communicator.model.Notification/";

	private String communicatorURL;

	private static final String COMMUNICATORSERVICE = "communicator/";


	/**
	 * 
	 * @param serverURL
	 *          address of the server to connect to
	 */
	public CommunicatorConnector(String serverURL) {
		this.communicatorURL = serverURL + COMMUNICATORSERVICE;
	}

	/**
	 * Get notifications of the user
	 * @param since since date, in milliseconds (use 0L for every time)
	 * @param position position in the result set
	 * @param count number of results (use -1L for all)
	 * @param token an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public List<Notification> getNotifications(Long since, Integer position, Integer count, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = HTTPConnector.doGet(url, map, "application/json", "application/json", token, "UTF-8");

			List list = mapper.readValue(resp, List.class);
			List<Notification> result = new ArrayList<Notification>();
			for (Object o : list) {
				Notification notification = mapper.convertValue(o, Notification.class);
				result.add(notification);
			}

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Get a notification by id
	 * @param id a notification id
	 * @param token an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public Notification getNotification(String id, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String resp = HTTPConnector.doGet(url, null, "application/json", "application/json", token, "UTF-8");

			Notification result = mapper.readValue(resp, Notification.class);

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}


	/**
	 * Create a notification
	 * @param notification a notification
	 * @param token an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void createNotification(Notification notification, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);
			
//			HTTPConnector.doPostWithReturn(HttpMethod.POST, url, null, not, "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, null, not, "application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Update a notification (only starred and labelIds values are currently updated)
	 * @param notification a notification
	 * @param id the id of the notification to update
	 * @param token an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void updateNotification(Notification notification, String id, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);
//			HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not, "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.PUT, url, null, not, "application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}	
	
	/**
	 * Delete a notification
	 * @param id the id of the notification to delete
	 * @param token an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void deleteNotification(String id, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HTTPConnector.doPost(HttpMethod.DELETE, url, null, null, "application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}	
	
	/**
	 * Send a notification as a user
	 * @param notification a notification
	 * @param users a list of user Ids
	 * @param token an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void sendUserNotification(Notification notification, List<String> users, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "send/user";
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			String ids = mapper.writeValueAsString(users);
			Map<String, String> map = new TreeMap<String, String>();
			map.put("users", ids);

			String not = mapper.writeValueAsString(notification);
//			HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not, "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, map, not, "application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}		
	
	/**
	 * Send a notification as an application
	 * @param notification a notification
	 * @param appId an application id
	 * @param users a list of user Ids
	 * @param token an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void sendAppNotification(Notification notification, String appId, List<String> users, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "send/app/" + appId;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			String ids = mapper.writeValueAsString(users);
			Map<String, String> map = new TreeMap<String, String>();
			map.put("users", ids);

			String not = mapper.writeValueAsString(notification);
//			HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not, "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, map, not, "application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}		
	
}
