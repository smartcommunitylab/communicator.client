package eu.trentorise.smartcampus.communicator.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import eu.trentorise.smartcampus.network.JsonUtils;

@XmlRootElement(name = "appsignature")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppSignature {

	private String appId;

	private Map<String, Object> privateKey;

	private Map<String, Object> publicKey;

	public Map<String, Object> getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(Map<String, Object> publicKey) {
		this.publicKey = publicKey;
	}

	public Map<String, Object> getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(Map<String, Object> privateKey) {
		this.privateKey = privateKey;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

//	/**
//	 * @param json
//	 * @return
//	 */
//	public static AppSignature valueOf(String json) {
//		try {
//			JSONObject o = new JSONObject(json);
//			AppSignature appSignature = new AppSignature();
//			appSignature.setPrivateKey(JsonUtils.toO(o
//					.getJSONObject("privateKey")));
//			appSignature.setAppId(o.getString("appId"));
//			appSignature.setPublicKey(JsonUtils.toMap(o
//					.getJSONObject("publicKey")));
//
//			return appSignature;
//		} catch (JSONException e) {
//			return null;
//		}
//	}

	@Override
	public String toString() {
		return "Configuration [privateKey=" + privateKey + ", appid=" + appId
				+ ", publicKey=" + publicKey + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("privateKey", getPrivateKey());
		returnMap.put("appId", getAppId());
		returnMap.put("publicKey", getPublicKey());
		return returnMap;
	}

}
