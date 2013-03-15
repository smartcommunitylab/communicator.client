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