package eu.trentorise.smartcampus.communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import eu.trentorise.smartcampus.communicator.model.AppAccount;
import eu.trentorise.smartcampus.communicator.model.ListAppAccount;
import eu.trentorise.smartcampus.communicator.model.ListUserAccount;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.UserAccount;
import eu.trentorise.smartcampus.communicator.util.HTTPConnector;
import eu.trentorise.smartcampus.communicator.util.HttpMethod;


/**
 * Class used to connect with the communicator service.
 * 
 */
public class CommunicatorConnector {
	
	public static final String REGISTRATIONID_HEADER = "REGISTRATIONID";
	
	private static final Logger logger = Logger.getLogger(CommunicatorConnector.class);

	private static final String NOTIFICATION = "eu.trentorise.smartcampus.communicator.model.Notification/";

	private String communicatorURL;

	private static final String COMMUNICATORSERVICE = "communicator/";
	

	private String appName;


	
	/**
	 * 
	 * @param serverURL
	 *            address of the server to connect to
	 * @param appName
	 *            name of app
	 */
	public CommunicatorConnector(String serverURL, String appName) {
		this.appName = appName;
		communicatorURL = serverURL;
		communicatorURL += (serverURL.endsWith("/")) ? "" : "/";
		communicatorURL += COMMUNICATORSERVICE;
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
	public Notification getNotification(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
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
	 * Create a notification
	 * 
	 * @param notification
	 *            a notification
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void createNotification(Notification notification, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String not = mapper.writeValueAsString(notification);

			// HTTPConnector.doPostWithReturn(HttpMethod.POST, url, null, not,
			// "application/json", "application/json", token, "UTF-8");
			HTTPConnector.doPost(HttpMethod.POST, url, null, not,
					"application/json", "application/json", token);
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
	public void updateNotification(Notification notification, String id,
			String token) throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
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
	public void deleteNotification(String id, String token)
			throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + NOTIFICATION + id;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			HTTPConnector.doPost(HttpMethod.DELETE, url, null, null,
					"application/json", "application/json", token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Send a notification as a user
	 * 
	 * @param notification
	 *            a notification
	 * @param users
	 *            a list of user Ids
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public void sendUserNotification(Notification notification,
			List<String> users, String token)
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
			throw new CommunicatorConnectorException(e);
		}
	}

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
			throw new CommunicatorConnectorException(e);
		}
	}
	
	/**
 	 * retrieves all the user communicator accounts binded to the application name and to the authentication token
	 * @param authToken the authentication token
	 * @return list of {@link UserAccount)
	 * @throws CommunicatorConnectorException
	 */
	public List<UserAccount> getUserAccounts(String authToken)
			throws CommunicatorConnectorException {
		
		try {
			
			String url = communicatorURL + "/useraccount/"  + appName;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			
			String resp=HTTPConnector.doGet(url, null, null, "application/json", authToken, "UTF-8");
			List<UserAccount> result = mapper.readValue(resp, ListUserAccount.class).getUserAccounts();
			return result;
			
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}

	}

	/**
	 * retrieves all the application communicator account binded to the application
	 * name
	 * 
	 * @return the list of {@link AppAccount}
	 * @throws CommunicatorConnectorException
	 */
	public List<AppAccount> getAppAccounts() throws CommunicatorConnectorException {
		try {
			String url = communicatorURL + "/appaccount/"  + appName;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			
			String resp=HTTPConnector.doGet(url, null, null, "application/json", null, "UTF-8");
			List<AppAccount> result = mapper.readValue(resp, ListAppAccount.class).getAppAccounts();
			return result;
			
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}
	}
	
	
	
	
	/**
	 * stores an user communicator account
	 * 
	 * @param authToken
	 *            authentication token
	 * @param userAccount
	 *            userAccount to store
	 * @return the {@link UserAccount} stored
	 * @throws CommunicatorConnectorException 
	 * @throws CommunicatorConnectorException
	 */
	public UserAccount storeUserAccount(String authToken,
			UserAccount userAccount) throws CommunicatorConnectorException  {
		
		try {
			String url = communicatorURL + "/useraccount/"  + appName;
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			String ids = mapper.writeValueAsString(userAccount);
			Map<String, String> map = new TreeMap<String, String>();
			map.put("users", ids);
			
			String userAccountAsString = mapper.writeValueAsString(userAccount);
			
			String resp=HTTPConnector.doPostWithReturn(HttpMethod.POST, url, map, userAccountAsString,
					"application/json", "application/json", authToken,"UTF-8");
			
			UserAccount result = mapper.readValue(resp, UserAccount.class);
			return result;
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}

	}
	
	/**
	 * regiter an user pushservice
	 * 
	 * @param authToken
	 *            authentication token
	 * @param registrationId
	 *            user googleid to pushservice
	 * @throws CommunicatorConnectorException 
	 * @throws CommunicatorConnectorException
	 */
	public String registerUser(String authToken,String registrationId) throws CommunicatorConnectorException  {
		
		try {
			String url = communicatorURL + "register/user/"  + appName ;
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//	String ids = mapper.writeValueAsString(userAccount);
		Map<String, String> map = new TreeMap<String, String>();
		map.put(REGISTRATIONID_HEADER, registrationId);
			
			
			//String userAccountAsString = mapper.writeValueAsString(userAccount);
			
			String resp=HTTPConnector.doPostWithReturn(HttpMethod.POST, url, map, null,
					"application/json", "application/json", authToken,"UTF-8");
		
			
			return resp;
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}
		
	}
	
	/**
	 * stores an user pushservice
	 * 
	 * @param authToken
	 *            authentication token
	 * @param registrationId
	 *            user googleid to store
	 * @throws CommunicatorConnectorException 
	 * @throws CommunicatorConnectorException
	 */
	public String unregisterUser(String authToken) throws CommunicatorConnectorException  {
		
		try {
			String url = communicatorURL + "unregister/user/"  + appName ;
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//	String ids = mapper.writeValueAsString(userAccount);
		Map<String, String> map = new TreeMap<String, String>();
		//map.put(REGISTRATIONID_HEADER, registrationId);
			
			
			//String userAccountAsString = mapper.writeValueAsString(userAccount);
			
			String resp=HTTPConnector.doPostWithReturn(HttpMethod.POST, url, map, null,
					"application/json", "application/json", authToken,"UTF-8");
		
			
			return resp;
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}
		
	}
	
	/**
	 * stores an app communicator account
	 * 
	 * @param authToken
	 *            authentication token
	 * 
	 * @throws CommunicatorConnectorException 
	 * @throws CommunicatorConnectorException
	 */
	public String registerApp(String authToken,String apikey) throws CommunicatorConnectorException  {
		
		try {
			String url = communicatorURL + "register/app/"  + appName + "/"+apikey;
		
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		//	String ids = mapper.writeValueAsString(userAccount);
		Map<String, String> map = new TreeMap<String, String>();
			//map.put("users", ids);
			
			//String userAccountAsString = mapper.writeValueAsString(userAccount);
			
			String resp=HTTPConnector.doPostWithReturn(HttpMethod.POST, url, map, null,
					"application/json", "application/json", authToken,"UTF-8");
		
			
			return resp;
			
		} catch (Exception e) {
			logger.error("Exception getting user accounts", e);
			throw new CommunicatorConnectorException(e);
		}
		
	}

}
