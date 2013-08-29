/*******************************************************************************
 * Copyright 2012-2013 Trento RISE
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
 ******************************************************************************/
package eu.trentorise.smartcampus.communicator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class representing a notification
 */
public class Notifications {

	/**
	 * A list of basic profiles
	 */
	private List<Notification> notifications;

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	/**
	 * @param json
	 * @return
	 */
	public static Notifications valueOf(String json) {
		try {
			JSONObject o = new JSONObject(json);
			Notifications profile = new Notifications();
			profile.setNotifications(new ArrayList<Notification>());
			JSONArray arr = o.getJSONArray("notifications");
			if (arr != null)
				for (int i = 0; i < arr.length(); i++) {
					Notification index = Notification.valueOf(arr
							.getJSONObject(i).toString());
					profile.getNotifications().add(index);
				}
			return profile;
		} catch (JSONException e) {
			return null;
		}
	}

}
