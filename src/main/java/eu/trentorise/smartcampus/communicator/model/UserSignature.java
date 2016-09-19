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
package eu.trentorise.smartcampus.communicator.model;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSignature {

	private String appName;

	private String registrationId;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	/**
	 * @param json string representation of the object
	 * @return {@link NotificationAuthor} structure
	 */
	public static UserSignature valueOf(String json) {
		try {
			JSONObject o = new JSONObject(json);
			UserSignature userSignature = new UserSignature();
			userSignature.setAppName(o.getString("appName"));
			userSignature.setRegistrationId(o.getString("registrationId"));

			return userSignature;
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "UserSignature [appname=" + appName + ", registrationId="
				+ registrationId + "]";
	}

	public Map<Object, Object> toMap() {
		Map<Object, Object> returnMap = new TreeMap<Object, Object>();
		returnMap.put("appName", getAppName());
		returnMap.put("registrationId", getRegistrationId());

		return returnMap;
	}

}
