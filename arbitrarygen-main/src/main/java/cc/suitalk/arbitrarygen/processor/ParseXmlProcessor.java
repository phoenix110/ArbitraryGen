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

package cc.suitalk.arbitrarygen.processor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.util.Map;

import cc.suitalk.arbitrarygen.extension.AGContext;
import cc.suitalk.arbitrarygen.extension.ArbitraryGenProcessor;
import cc.suitalk.arbitrarygen.tools.RuntimeContextHelper;
import cc.suitalk.arbitrarygen.utils.FileOperation;
import cc.suitalk.arbitrarygen.utils.JSONArgsUtils;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * Created by AlbieLiang on 2017/3/19.
 */
public class ParseXmlProcessor implements ArbitraryGenProcessor {

    private static final String TAG = "AG.ParseXmlProcessor";

    @Override
    public String getName() {
        return "parse-xml";
    }

    @Override
    public void initialize(AGContext context, JSONObject args) {
    }

    @Override
    public String[] getDependencies() {
        return new String[] { "parse-rule" };
    }

    @Override
    public JSONObject exec(AGContext context, Map<String, ArbitraryGenProcessor> processors, JSONObject args) {
        String name = args.optString("_name");
        if (Util.isNullOrNil(name)) {
            Log.i(TAG, "exec failed, name is null.");
            return null;
        }
        JSONObject result = context.execProcess(processors, "parse-rule", args);
        if (result == null) {
            Log.i(TAG, "parse rule result is null.");
            return null;
        }
        JSONArray fileArray = result.optJSONArray("fileArray");
        if (fileArray == null) {
            Log.i(TAG, "file array is null.");
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < fileArray.size(); i++) {
            String path = fileArray.optString(i);
            XMLSerializer ss = new XMLSerializer();
            ss.setTypeHintsEnabled(false);
            ss.setTypeHintsCompatibility(false);
            Log.i(TAG, "process parse xml file : %s", path);
            JSONObject jsonObject = (JSONObject) ss.read(RuntimeContextHelper.replace(FileOperation.read(path)));
            if (jsonObject != null) {
                jsonObject = JSONArgsUtils.validKey(jsonObject);
                jsonArray.add(jsonObject);
            }
        }
        JSONObject r = new JSONObject();
        r.put(name, jsonArray);
        return r;
    }

    @Override
    public void onError(AGContext context, int errorCode, String message) {
        Log.e(TAG, "execute engine error, code is '%d', message is '%s'", errorCode, message);
    }
}
