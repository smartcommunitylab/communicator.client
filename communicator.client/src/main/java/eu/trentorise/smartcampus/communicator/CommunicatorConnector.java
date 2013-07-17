package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.UserSignature;
import eu.trentorise.smartcampus.communicator.util.HTTPConnector;
import eu.trentorise.smartcampus.communicator.util.HttpMethod;

/**
 * Class used to connect with the communicator service.
 * 
 */
public class CommunicatorConnector {

	public static final String REGISTRATIONID_HEADER = "REGISTRATIONID";

	private static final Logger logger = Logger
			.getLogger(CommunicatorConnector.class);

	private static final String NOTIFICATION = "notification";
	private static final String BYAPP = "app/";
	private static final String BYUSER = "user/";

	private String communicatorURL;

	/**
	 * 
	 * @param serverURL
	 *            address of the server to connect to
	 * @param appName
	 *            name of app
	 */
	public CommunicatorConnector(String serverURL) {
		communicatorURL = serverURL;
		communicatorURL += (serverURL.endsWith("/")) ? "" : "/";
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
	public List<Notification> getNotifications(Long since, Integer position,
			Integer count, String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = HTTPConnector.doGet(url, map, "application/json",
					"application/json", token, "UTF-8");

			@SuppressWarnings("rawtypes")
			List list = mapper.readValue(resp, List.class);
			List<Notification> result = new ArrayList<Notification>();
			for (Object o : list) {
				Notification notification = mapper.convertValue(o,
						Notification.class);
				result.add(notification);
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
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
	public Notification getNotification(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + "/" + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");

			Notification result = mapper.readValue(resp, Notification.class);

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void updateNotification(Notification notification, String id,
			String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + "/" + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.PUT, url, null, not,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void deleteNotification(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + "/" + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HTTPConnector.doPost(HttpMethod.DELETE, url, null, null,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	

	// "/{capp}/notification")
	public List<Notification> getNotificationsByApp(Long since,
			Integer position, Integer count, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYAPP + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = HTTPConnector.doGet(url, map, "application/json",
					"application/json", token, "UTF-8");

			@SuppressWarnings("rawtypes")
			List list = mapper.readValue(resp, List.class);
			List<Notification> result = new ArrayList<Notification>();
			for (Object o : list) {
				Notification notification = mapper.convertValue(o,
						Notification.class);
				result.add(notification);
			}

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{capp}/notification/{id}
	public Notification getNotificationByApp(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYAPP + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");

			Notification result = mapper.readValue(resp, Notification.class);

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void updateByApp(Notification notification, String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYAPP + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.PUT, url, null, not,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void deleteByApp(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYAPP + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HTTPConnector.doPost(HttpMethod.DELETE, url, null, null,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /user/notification
	public List<Notification> getNotificationsByUser(Long since,
			Integer position, Integer count, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYUSER + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = HTTPConnector.doGet(url, map, "application/json",
					"application/json", token, "UTF-8");

			@SuppressWarnings("rawtypes")
			List list = mapper.readValue(resp, List.class);
			List<Notification> result = new ArrayList<Notification>();
			for (Object o : list) {
				Notification notification = mapper.convertValue(o,
						Notification.class);
				result.add(notification);
			}

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{user/notification/{id}
	public Notification getNotificationByUser(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYUSER + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");

			Notification result = mapper.readValue(resp, Notification.class);

			return result;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void updateByUser(Notification notification, String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYUSER + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.PUT, url, null, not,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
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
	public void deleteByUser(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + BYUSER + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HTTPConnector.doPost(HttpMethod.DELETE, url, null, null,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/register/app/{appid}")
	public boolean registerApp(AppSignature signature, String appid, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "register/"+ BYAPP + appid;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			//String ids = mapper.writeValueAsString(users);
			//Map<String, String> map = new TreeMap<String, String>();
		//	map.put("users", ids);

			String not = mapper.writeValueAsString(signature);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, null, not,
					"application/json", "application/json", token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}


	// "/register/user/{appid}/user")
	public boolean registerUserToPush(String appid, UserSignature signature, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "register/"+ BYUSER + appid;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			//String ids = mapper.writeValueAsString(users);
			//Map<String, String> map = new TreeMap<String, String>();
		//	map.put("users", ids);

			String not = mapper.writeValueAsString(signature);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, null, not,
					"application/json", "application/json", token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /unregister/user/{appId}/{userid}")
	public boolean unregisterUserToPush(String appid, String userid,String token)throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "unregister" + BYUSER + appid+"/"+userid;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");
			@SuppressWarnings("unchecked")
			Boolean result = mapper.readValue(resp, Boolean.class);
			
			return result;
			
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}
	// /unregister/app/{appId}")
	public boolean unregisterAppToPush(String appId,String token)throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "unregister" + BYAPP + appId;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");
			@SuppressWarnings("unchecked")
			Boolean result = mapper.readValue(resp, Boolean.class);
			
			return result;
			
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /send/app/{appId}")
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
	public void sendAppNotification(Notification notification, String appId,
			List<String> users, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "send/app/" + appId;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String ids = mapper.writeValueAsString(users);
			Map<String, String> map = new TreeMap<String, String>();
			map.put("users", ids);

			String not = mapper.writeValueAsString(notification);
			// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, map, not,
					"application/json", "application/json", token);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /send/user")
	public void sendUserNotification(List<String> users,
			Notification notification, String token) 
					throws CommunicatorConnectorException {
				try {
					String url = communicatorURL + "send/user";
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

					String ids = mapper.writeValueAsString(users);
					Map<String, String> map = new TreeMap<String, String>();
					map.put("users", ids);

					String not = mapper.writeValueAsString(notification);
					// HTTPConnector.doPostWithReturn(HttpMethod.PUT, url, null, not,
					// "application/json", "application/json", token, "UTF-8");
					HTTPConnector.doPost(HttpMethod.POST, url, map, not,
							"application/json", "application/json", token);
				} catch (Exception e) {
					e.printStackTrace();
					throw new CommunicatorConnectorException(e);
				}
			}

	// /configuration/app/{appid}")
	public Map<String, String> requestAppConfigurationToPush(String appid ,String token)throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "configuration/" + BYAPP + appid;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");
			@SuppressWarnings("unchecked")
			Map<String, String> result = mapper.readValue(resp, Map.class);
			
			return result;
			
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /configuration/user/{appid}")
	public Map<String, String> requestUserConfigurationToPush(String appid,String token)throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "configuration/" + BYUSER + appid;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String resp = HTTPConnector.doGet(url, null, "application/json",
					"application/json", token, "UTF-8");
			@SuppressWarnings("unchecked")
			Map<String, String> result = mapper.readValue(resp, Map.class);
			
			return result;
			
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

}
