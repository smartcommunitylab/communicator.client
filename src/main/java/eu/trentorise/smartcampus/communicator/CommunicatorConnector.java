package eu.trentorise.smartcampus.communicator;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.Notifications;
import eu.trentorise.smartcampus.communicator.model.UserSignature;
import eu.trentorise.smartcampus.communicator.util.Constants;
import eu.trentorise.smartcampus.network.JsonUtils;
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
	 * @param appId
	 *            name of app registered on smartcampus portal
	 * @throws Exception
	 */
	public CommunicatorConnector(String serverURL, String appId)
			throws Exception {
		if (serverURL != null && serverURL.compareTo("") != 0) {
			this.communicatorURL = serverURL;
			if (!communicatorURL.endsWith("/"))
				communicatorURL += '/';
			this.setAppId(appId);
		} else {
			throw new Exception("Parameters not setted");
		}
	}

	// "/{capp}/notification")
	/**
	 * Get notifications per user per app
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
	public Notifications getNotificationsByApp(Long since, Integer position,
			Integer count, String token) throws CommunicatorConnectorException {
		try {

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = RemoteConnector.getJSON(communicatorURL,
					Constants.BYAPP + appId + "/" + Constants.NOTIFICATION,
					token, map);

			return JsonUtils.toObject(resp, Notifications.class);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{capp}/notification/{id}
	/**
	 * Get a notification by id per user per app
	 * 
	 * @param id
	 *            a notification id
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public Notification getNotificationByApp(String id, String token)
			throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector.getJSON(communicatorURL,
					Constants.BYAPP + appId + "/" + Constants.NOTIFICATION
							+ "/" + id, token);

			return JsonUtils.toObject(resp, Notification.class);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Update a notification (only starred and labelIds values are currently
	 * updated) per user per app
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

			RemoteConnector.putJSON(communicatorURL, Constants.BYAPP + appId
					+ "/" + Constants.NOTIFICATION + "/" + id, JsonUtils.toJSON(notification), token);

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Delete a notification per user per app
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

			RemoteConnector.deleteJSON(communicatorURL, Constants.BYAPP + appId
					+ "/" + Constants.NOTIFICATION + "/" + id, token);

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Get notifications per user
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
	// /user/notification
	public Notifications getNotificationsByUser(Long since, Integer position,
			Integer count, String token) throws CommunicatorConnectorException {
		try {

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = RemoteConnector.getJSON(communicatorURL,
					Constants.BYUSER + Constants.NOTIFICATION, token, map);

			return JsonUtils.toObject(resp, Notifications.class);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /{user/notification/{id}
	/**
	 * Get a notification by id per user
	 * 
	 * @param id
	 *            a notification id
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public Notification getNotificationByUser(String id, String token)
			throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector
					.getJSON(communicatorURL, Constants.BYUSER
							+ Constants.NOTIFICATION + "/" + id, token);

			return JsonUtils.toObject(resp, Notification.class);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Update a notification (only starred and labelIds values are currently
	 * updated) per user
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

			RemoteConnector.putJSON(communicatorURL, Constants.BYUSER
					+ Constants.NOTIFICATION + "/" + id, JsonUtils.toJSON(notification), token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	/**
	 * Delete a notification per user
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

			RemoteConnector.deleteJSON(communicatorURL, Constants.BYUSER
					+ Constants.NOTIFICATION + "/" + id, token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/register/app/{appid}")
	/**
	 * The method allows registering the app at Communicator Service,the
	 * registration allows to save parameters for sending push notifications.
	 * The parameters is divide in public and private,depends if the client can
	 * read or not the value *
	 * 
	 * @param signature
	 *            Object with list of key/value parameters for
	 *            configuration,public or private
	 * @param appid
	 *            name of app registered on smartcampus portal
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public boolean registerApp(AppSignature signature, String appid,
			String token) throws CommunicatorConnectorException {
		try {

			RemoteConnector.postJSON(communicatorURL, "register/"
					+ Constants.BYAPP + appid,
					JsonUtils.toJSON(signature), token);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// "/register/user/{appid}/user")
	/**
	 * The method permit the user to receive the push notifications.After
	 * calling ,in android app, le GCM library to receive the RegistrationId,
	 * the user must send it to the Communicator Service *
	 * 
	 * @param signature
	 *            Object with list of key/value parameters for configuration
	 * @param appid
	 *            name of app registered on smartcampus portal
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public boolean registerUserToPush(UserSignature signature, String appid,
			String token) throws CommunicatorConnectorException {
		try {

//			RemoteConnector.postJSON(communicatorURL, "register/"
//					+ Constants.BYUSER + appid,
//					new JSONObject(signature.toMap()).toString(), token);
			RemoteConnector.postJSON(communicatorURL, "register/"
					+ Constants.BYUSER + appid,
					JsonUtils.toJSON(signature), token);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /unregister/user/{appId}/{userid}")
	/**
	 * The method allows the cancellation of the registration to the
	 * Communicator Service,the user must specify the app setted at the
	 * registration
	 * 
	 * 
	 * @param appid
	 *            name of app registered on smartcampus portal
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public void unregisterUserToPush(String appId, String token)
			throws CommunicatorConnectorException {
		try {

			RemoteConnector.deleteJSON(communicatorURL, "unregister/"
					+ Constants.BYUSER + appId, token);

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /unregister/app/{appId}")
	/**
	 * The method allows the cancellation of the app account to the Communicator
	 * Service.
	 * 
	 * @param token
	 *            an authorization token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public void unregisterAppToPush(String token)
			throws CommunicatorConnectorException {
		try {

			RemoteConnector.deleteJSON(communicatorURL, "unregister/"
					+ Constants.BYAPP + appId, token);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /send/app/{appId}")
	/**
	 * The method allows sending the app notifications .The author of the
	 * notification is the app and the notification can be sended to a list of
	 * users
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
			map.put("users", users);

//			RemoteConnector
//					.postJSON(communicatorURL, "send/app/" + appId,
//							new JSONObject(notification.toMap()).toString(),
//							token, map);
			
			RemoteConnector
			.postJSON(communicatorURL, "send/app/" + appId,JsonUtils.toJSON(notification),
					token, map);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommunicatorConnectorException(e);
		}
	}

	// /send/user")
	// public void sendUserNotification(List<String> users,
	// Notification notification, String token)
	// throws CommunicatorConnectorException {
	// try {
	//
	// Map<String, Object> map = new TreeMap<String, Object>();
	// map.put("users", users);
	//
	// RemoteConnector.postJSON(communicatorURL, "send/user",new
	// JSONObject(notification.toMap()).toString(), token,map);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new CommunicatorConnectorException(e);
	// }
	// }

	// /configuration/app/{appid}")
	/**
	 * The method allows reading the configuration of the app saved communicator
	 * service. The returned key/value list was setted at registration step
	 * 
	 * @param appId
	 *            an application id
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public Map<String, Object> requestAppConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector.getJSON(communicatorURL,
					"configuration/" + Constants.BYAPP + appid, token);

			return JsonUtils.toObject(resp,Map.class);

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /configuration/user/{appid}")
	/**
	 * The method allows receiving the configuration of the user in communicator
	 * service.
	 * 
	 * @param appId
	 *            an application id
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public Map<String, Object> requestUserConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector.getJSON(communicatorURL,
					"configuration/" + Constants.BYUSER + appid, token);

			return JsonUtils.toObject(resp,Map.class);

		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	// /configuration/{appid}")
	/**
	 * The method allows reading the public configuration of the app in
	 * communicator service. The return map are the public parameters setted in
	 * the registration step of the app.
	 * 
	 * @param appId
	 *            an application id
	 * @param token
	 *            an authorization token
	 * @throws CommunicatorConnectorException
	 */
	public Map<String, Object> requestPublicConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector.getJSON(communicatorURL,
					"configuration/public/" + appid, token);

			return JsonUtils.toObject(resp,Map.class);

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
