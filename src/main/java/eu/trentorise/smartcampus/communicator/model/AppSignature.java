package eu.trentorise.smartcampus.communicator.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.trentorise.smartcampus.network.JsonUtils;

@XmlRootElement(name = "appsignature")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppSignature {

	private String appId;

	private String senderId;

	private String apiKey;

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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
	
	 */
	public static AppSignature valueOf(String json)  {
		try {
			JSONObject o = new JSONObject(json);
			AppSignature appSignature = new AppSignature();
			appSignature.setApiKey(o.getString("apiKey"));
			appSignature.setAppId(o.getString("appId"));
			appSignature.setSenderId(o.getString("senderId"));
			
			return appSignature;
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Configuration [senderId=" + senderId + ", appid=" + appId+ ", apiKey=" + apiKey + "]";
	}

	public Map<String,Object> toMap() {
		Map<String,Object> returnMap=new HashMap<String, Object>();
		returnMap.put("apiKey", getApiKey());
		returnMap.put("appId", getAppId());
		returnMap.put("senderId", getSenderId());
		return returnMap;
	}

}
