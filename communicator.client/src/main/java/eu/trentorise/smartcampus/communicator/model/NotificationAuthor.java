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

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationAuthor implements Serializable {
	private static final long serialVersionUID = -1045073082737340872L;

	private String appId;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	public static NotificationAuthor valueOf(String json)  {
		try {
			JSONObject o = new JSONObject(json);
			NotificationAuthor notificationAuthor = new NotificationAuthor();
			notificationAuthor.setAppId(o.getString("appId"));
			notificationAuthor.setUserId(o.getString("userId"));
			return notificationAuthor;
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "NotificationAuthor [ appId=" + appId + ", userId=" + userId + "]";
	}
	

}
