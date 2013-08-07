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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.trentorise.smartcampus.network.JsonUtils;

/**
 * Class representing a notification
 */
public class Notification {

	private String id; // id
	private long updateTime = -1L;
	private long version;

	private String title; // title
	private String description; // description
	private String type; // source of the notification
	private String user; // id of the user "owning" the notification
	private Map<String, Object> content; // content
	private long timestamp; // time, in milliseconds
	private boolean starred; // starred flag
	private List<String> labelIds; // label ids
	private List<String> channelIds; // channel ids
	private List<EntityObject> entities; // list of entities

	private NotificationAuthor author; // notification author

	private boolean readed; // readed flag

	public Notification() {
		super();
		labelIds = new ArrayList<String>();
		channelIds = new ArrayList<String>();
		entities = new ArrayList<EntityObject>();
		author = new NotificationAuthor();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isStarred() {
		return starred;
	}

	public void setStarred(boolean starred) {
		this.starred = starred;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(List<String> channelIds) {
		this.channelIds = channelIds;
	}

	public void addChannelId(String channelId) {
		if (channelIds == null)
			channelIds = new ArrayList<String>();
		channelIds.add(channelId);
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	public List<String> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<String> labelIds) {
		this.labelIds = labelIds;
	}

	public List<EntityObject> getEntities() {
		return entities;
	}

	public void setEntities(List<EntityObject> entities) {
		this.entities = entities;
	}

	public NotificationAuthor getAuthor() {
		return author;
	}

	public void setAuthor(NotificationAuthor author) {
		this.author = author;
	}
	
	/**
	 * @param json
	 * @return
	 * @throws JSONException 
	 */
	@SuppressWarnings("unchecked")
	public static Notification valueOf(String json)  {
		try {
			JSONObject o = new JSONObject(json);
			Notification notification = new Notification();
			notification.setAuthor(NotificationAuthor.valueOf(o.getString("author")));
			
			notification.setDescription(o.getString("description"));
			
			notification.setId(o.getString("id"));
			
			notification.setReaded(o.getBoolean("readed"));
			notification.setStarred(o.getBoolean("starred"));
			notification.setTimestamp(o.getLong("timestamp"));
			notification.setTitle(o.getString("title"));
			notification.setType(o.getString("type"));
			notification.setUpdateTime(o.getLong("updateTime"));
			notification.setUser(o.getString("user"));
			notification.setVersion(o.getLong("version"));
			if(!o.isNull("channelIds"))
				notification.setChannelIds(JsonUtils.toList(o.getJSONArray("channelIds")));
			if(!o.isNull("content"))
				notification.setContent(JsonUtils.toMap(o.getJSONObject("content")));
			if(!o.isNull("entities"))
				notification.setEntities(JsonUtils.toList(o.getJSONArray("entities")));
			if(!o.isNull("labelIds"))
				notification.setLabelIds(JsonUtils.toList(o.getJSONArray("labelIds")));
			
			
			return notification;
		} catch (JSONException e) {
			return null;
		}
	}


	@Override
	public String toString() {
		return "Notification [author="+ author + ",channelIds=" +channelIds+",content=" +content+
				",description="+description +",entities="+entities +",id="+id +
				",labelIds="+labelIds +",readed="+readed +",starred="+starred +",timestamp="+timestamp +
				",title="+title +",type=" +type+",updateTime="+updateTime +",user="+user+",version="+version +"]";
	}

	
	public Map<String,Object> toMap() {
		// TODO Auto-generated method stub
		
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", getId());
		map.put("content", getContent());
		map.put("channelIds", getChannelIds());
		map.put("entities", getEntities());
		map.put("labelIds", getLabelIds());
		map.put("version", getVersion());
		map.put("user", getUser());
		map.put("updateTime", getUpdateTime());
		map.put("type", getType());
		map.put("title", getTitle());
		map.put("timestamp", getTimestamp());
		map.put("starred", isStarred());
		map.put("description", getDescription());
		map.put("readed", isReaded());
		map.put("author", getAuthor().toMap());
		
		return map;
	}
	

}
