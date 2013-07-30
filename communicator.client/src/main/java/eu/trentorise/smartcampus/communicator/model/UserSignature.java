package eu.trentorise.smartcampus.communicator.model;

import java.io.IOException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

@XmlRootElement(name = "usersignature")
@XmlAccessorType(XmlAccessType.FIELD)
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
	 * @param json
	 * @return
	 * @throws IOException 
	 * @throws JSONException
	 */
	public static UserSignature valueOf(String json) throws IOException {
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
		return "UserSignature [appname=" + appName + ", registrationId=" + registrationId+  "]";
	}

}
