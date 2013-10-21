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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SyncData {

	private long version;
	private Set<String> deleted = new HashSet<String>();
	private Set<Notification> updated = new HashSet<Notification>();
	
	private Map<String, Object> exclude;
	private Map<String, Object> include;
	
	public SyncData() {
		super();
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Set<String> getDeleted() {
		return deleted;
	}

	public void setDeleted(Set<String> deleted) {
		this.deleted = deleted;
	}

	public Set<Notification> getUpdated() {
		return updated;
	}

	public void setUpdated(Set<Notification> updated) {
		this.updated = updated;
	}

	public Map<String, Object> getExclude() {
		return exclude;
	}

	public void setExclude(Map<String, Object> exclude) {
		this.exclude = exclude;
	}

	public Map<String, Object> getInclude() {
		return include;
	}

	public void setInclude(Map<String, Object> include) {
		this.include = include;
	}

}
