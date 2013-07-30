package eu.trentorise.smartcampus.communicator;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.Notifications;
import eu.trentorise.smartcampus.communicator.model.UserSignature;
import eu.trentorise.smartcampus.network.JsonHelper;
import eu.trentorise.smartcampus.network.RemoteConnector;

/**
 * Class used to connect with the communicator service.
 * 
 */
public class CommunicatorConnector {


	private String communicatorURL;
	private String appId;

	/**
	 * 
	 * @param serverURL
	 *            address of the server to connect to
	 * @param appName
	 *            name of app
	 */
	public CommunicatorConnector(String serverURL, String appId) {
		this.communicatorURL = serverURL;
		if (!communicatorURL.endsWith("/")) communicatorURL += '/';
		this.setAppId(appId);
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
	public Notifications getNotifications(Long since, Integer position,
			Integer count, String token) throws CommunicatorConnectorException {
		try {
		
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = RemoteConnector.getJSON(communicatorURL, Constants.NOTIFICATION, token,map);

			
			return Notifications.valueOf(resp);
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
			
			String resp = RemoteConnector.getJSON(communicatorURL, Constants.NOTIFICATION + "/" + id, token);
			

			return Notification.valueOf(resp);
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
		
			RemoteConnector.putJSON(communicatorURL, Constants.NOTIFICATION + "/" + id,new JSONObject(notification).toString(), token);
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

			RemoteConnector.deleteJSON(communicatorURL, Constants.NOTIFICATION + "/" + id, token);
	
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/{capp}/notification")
	public Notifications getNotificationsByApp(Long since,
			Integer position, Integer count, String token)
			throws CommunicatorConnectorException {
		try {
		
			

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

	String resp = RemoteConnector.getJSON(communicatorURL, Constants.BYAPP + appId + "/" + Constants.NOTIFICATION, token,map);

			
			return Notifications.valueOf(resp);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{capp}/notification/{id}
	public Notification getNotificationByApp(String id, String token)
			throws CommunicatorConnectorException {
		try {
			
	String resp = RemoteConnector.getJSON(communicatorURL, Constants.BYAPP + appId + "/" + Constants.NOTIFICATION
			+ "/" + id, token);
			

			return Notification.valueOf(resp);
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
		
			
			RemoteConnector.putJSON(communicatorURL, Constants.BYAPP + appId + "/" + Constants.NOTIFICATION
					+ "/" + id,new JSONObject(notification).toString(), token);
	
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
		
				
			RemoteConnector.deleteJSON(communicatorURL, Constants.BYAPP + appId + "/" + Constants.NOTIFICATION
					+ "/" + id, token);
			
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /user/notification
	public Notifications getNotificationsByUser(Long since,
			Integer position, Integer count, String token)
			throws CommunicatorConnectorException {
		try {
		

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

	String resp = RemoteConnector.getJSON(communicatorURL, Constants.BYUSER + Constants.NOTIFICATION, token,map);

			
			return Notifications.valueOf(resp);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{user/notification/{id}
	public Notification getNotificationByUser(String id, String token)
			throws CommunicatorConnectorException {
		try {
			
			String resp = RemoteConnector.getJSON(communicatorURL, Constants.BYUSER + Constants.NOTIFICATION + "/" + id, token);
					

					return Notification.valueOf(resp);
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

			RemoteConnector.putJSON(communicatorURL, Constants.BYUSER + Constants.NOTIFICATION + "/" + id,new JSONObject(notification).toString(), token);
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
		
			RemoteConnector.deleteJSON(communicatorURL, Constants.BYUSER + Constants.NOTIFICATION + "/" + id, token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/register/app/{appid}")
	public boolean registerApp(AppSignature signature, String appid,
			String token) throws CommunicatorConnectorException {
		try {

			RemoteConnector.postJSON(communicatorURL,"register/" + Constants.BYAPP + appid,new JSONObject(signature).toString(), token);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/register/user/{appid}/user")
	public boolean registerUserToPush(String appid, UserSignature signature,
			String token) throws CommunicatorConnectorException {
		try {
			
			RemoteConnector.postJSON(communicatorURL,"register/" + Constants.BYUSER + appid,new JSONObject(signature).toString(), token);
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /unregister/user/{appId}/{userid}")
	public void unregisterUserToPush(String userid, String token)
			throws CommunicatorConnectorException {
		try {
			
			RemoteConnector.deleteJSON(communicatorURL,"unregister/" + Constants.BYUSER + appId+"/"+userid, token);
			

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /unregister/app/{appId}")
	public void unregisterAppToPush(String token)
			throws CommunicatorConnectorException {
		try {
		
			RemoteConnector.deleteJSON(communicatorURL,"unregister/" + Constants.BYAPP + appId, token);
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
			

		
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("users", new JSONArray(users));

			RemoteConnector.postJSON(communicatorURL,"send/app/" + appId,new JSONObject(notification).toString(), token,map);
			
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
			
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("users", new JSONArray(users));

			RemoteConnector.postJSON(communicatorURL, "send/user",new JSONObject(notification).toString(), token,map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /configuration/app/{appid}")
	public Map<String, Object> requestAppConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {
				
			String resp = RemoteConnector.getJSON(communicatorURL, "configuration/" + Constants.BYAPP + appid, token);
		

			return JsonHelper.toMap(new JSONObject(resp));

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /configuration/user/{appid}")
	public Map<String, Object> requestUserConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {
			
			String resp = RemoteConnector.getJSON(communicatorURL,"configuration/" + Constants.BYUSER + appid, token);
			

			return JsonHelper.toMap(new JSONObject(resp));

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
