/*
 *  Copyright (C) 2016-present Albie Liang. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package cc.suitalk.arbitrarygen.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class TemplateManager {

	private static TemplateManager sTemplateManager;
	
	private Map<String, TemplateWrapper> mTemplates;
	
	public synchronized static TemplateManager getImpl() {
		if (sTemplateManager == null) {
			sTemplateManager = new TemplateManager();
		}
		return sTemplateManager;
	}
	
	public TemplateManager() {
		mTemplates = new HashMap<String, TemplateWrapper>();
	}
	
	public void put(String name, String template) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			tw = new TemplateWrapper();
			mTemplates.put(name, tw);
		}
		tw.template = template;
	}
	
	public void put(String name, DelayGetTask task) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			tw = new TemplateWrapper();
			mTemplates.put(name, tw);
		}
		tw.template = null;
		tw.delayGetTask = task;
	}
	
	/**
	 * Get template from the cache by the given name.
	 *
	 * @param name name of the template
	 * @return string of the template
	 */
	public String get(String name) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			return null;
		}
		if (Util.isNullOrNil(tw.template) && tw.delayGetTask != null) {
			tw.template = tw.delayGetTask.doGet();
		}
		return tw.template;
	}

	/**
	 * Get template by the given 'name', if the value do not exist
	 * then it with invoke {@link DelayGetTask#doGet()} to get value
	 * and update the template immediately.
	 * 
	 * @param name name of the template
	 * @param task delay-get task
	 * @return template string
	 */
	public String get(String name, DelayGetTask task) {
		TemplateWrapper tw = mTemplates.get(name);
		if (tw == null) {
			if (task != null) {
				put(name, task);
				return get(name);
			}
			return null;
		}
		if (Util.isNullOrNil(tw.template) && task != null) {
			tw.template = task.doGet();
		}
		return tw.template;
	}
	
	public String remove(String name) {
		TemplateWrapper tw = mTemplates.remove(name);
		if (tw != null) {
			if (Util.isNullOrNil(tw.template) && tw.delayGetTask != null) {
				tw.template = tw.delayGetTask.doGet();
			}
			return tw.template;
		}
		return null;
	}
	
	public void putAll(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return;
		}
		for (Entry<String, String> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	public interface DelayGetTask {
		String doGet();
	}
	
	/**
	 * 
	 * @author AlbieLiang
	 *
	 */
	private static class TemplateWrapper {
		String template;
		DelayGetTask delayGetTask;
	}
	
}
