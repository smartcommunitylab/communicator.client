/**
 *    Copyright 2012-2013 Trento RISE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package eu.trentorise.smartcampus.communicator;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import eu.trentorise.smartcampus.communicator.model.AppSignature;
import eu.trentorise.smartcampus.communicator.model.Notification;
import eu.trentorise.smartcampus.communicator.model.Notifications;
import eu.trentorise.smartcampus.communicator.model.SyncData;
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
	 * @throws Exception
	 */
	public CommunicatorConnector(String serverURL)
			throws Exception {
		if (serverURL != null && serverURL.compareTo("") != 0) {
			this.communicatorURL = serverURL;
			if (!communicatorURL.endsWith("/"))
				communicatorURL += '/';
		} else {
			throw new Exception("Parameters not setted");
		}
	}	
	
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
	
	// "/public/notification/{capp}")
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
	 *            a user access token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public Notifications getPublicNotificationsByApp(Long since, Integer position,
			Integer count, String token) throws CommunicatorConnectorException {
		try {

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("since", since);
			map.put("position", position);
			map.put("count", count);

			String resp = RemoteConnector.getJSON(communicatorURL,
					Constants.BYAPP + "/" + Constants.PUBLIC + "/" + Constants.NOTIFICATION + "/" + appId,
					token, map);

			return JsonUtils.toObject(resp, Notifications.class);
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
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
	 *            a user access token
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
	 *            a user access token
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
	 * Synchronize notification data per app
	 * 
	 * @param syncData
	 * 			  data to synchronize: should contain set of notification IDs to delete in 'deleted' property
	 *            and a set of notifications to be updated.
	 * @param token
	 *            a user access token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public SyncData syncNotificationsByApp(SyncData syncData, String token) throws CommunicatorConnectorException {
		assert syncData != null;
		try {
			String body = convertSyncRequest(syncData);
			String resp = RemoteConnector.postJSON(communicatorURL,
					Constants.BYAPP + appId + "/" + Constants.NOTIFICATION
							+ "/sync", body, token, Collections.<String,Object>singletonMap("since", syncData.getVersion()));

			SyncData res = convertSyncResponse(resp);
			return res;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}
	/**
	 * Synchronize notification data per app
	 * 
	 * @param syncData
	 * 			  data to synchronize: should contain set of notification IDs to delete in 'deleted' property
	 *            and a set of notifications to be updated.
	 * @param token
	 *            a user access token
	 * @return
	 * @throws CommunicatorConnectorException
	 */
	public SyncData syncNotificationsByUser(SyncData syncData, String token) throws CommunicatorConnectorException {
		assert syncData != null;
		try {
			String body = convertSyncRequest(syncData);
			String resp = RemoteConnector.postJSON(communicatorURL,
					Constants.BYUSER + Constants.NOTIFICATION
							+ "/sync", body, token, Collections.<String,Object>singletonMap("since", syncData.getVersion()));

			SyncData res = convertSyncResponse(resp);
			return res;
		} catch (Exception e) {
			throw new CommunicatorConnectorException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SyncData convertSyncResponse(String resp) {
		Map<String, Object> map;
		map = JsonUtils.toObject(resp, Map.class);
		SyncData res = new SyncData();
		res.setVersion(Long.parseLong(map.get("version")+""));
		Map resMap = map.containsKey("deleted") ? (Map)map.get("deleted") : Collections.emptyMap();
		List<String> deletedList = (List<String>) resMap.get(Notification.class.getName());
		if (deletedList != null) {
			Set<String> set = new HashSet<String>();
			set.addAll(deletedList);
			res.setDeleted(set);
		}
		resMap = map.containsKey("updated") ? (Map)map.get("updated") : Collections.emptyMap();
		List<Map> updatedList = (List<Map>) resMap.get(Notification.class.getName());
		if (updatedList != null) {
			Set<Notification> set = new HashSet<Notification>();
			for (Map nm : updatedList) {
				set.add(JsonUtils.convert(nm, Notification.class));
			}
			res.setUpdated(set);
		}
		return res;
	}

	private String convertSyncRequest(SyncData syncData) {
		Map<String, Set<String>> deleted = new HashMap<String, Set<String>>();
		deleted.put(Notification.class.getName(), syncData.getDeleted());
		Map<String,Set<Notification>> updated = new HashMap<String, Set<Notification>>();
		updated.put(Notification.class.getName(), syncData.getUpdated());
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("version", syncData.getVersion());
		map.put("deleted", deleted);
		map.put("updated", updated);
		
		String body =  JsonUtils.toJSON(map);
		return body;
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
	public AppSignature requestAppConfigurationToPush(String appid,
			String token) throws CommunicatorConnectorException {
		try {

			String resp = RemoteConnector.getJSON(communicatorURL,
					"configuration/" + Constants.BYAPP + appid, token);

			return JsonUtils.toObject(resp,AppSignature.class);

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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
