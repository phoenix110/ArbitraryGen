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

package cc.suitalk.arbitrarygen.demo.jsapi;

import org.json.JSONObject;

import cc.suitalk.arbitrarygen.demo.jsapi.annotation.JsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.annotation.Sync;
import cc.suitalk.arbitrarygen.demo.jsapi.base.BaseJsApiFunc;
import cc.suitalk.arbitrarygen.demo.jsapi.base.JsApiContext;

/**
 * Created by AlbieLiang on 16/11/16.
 */
@Sync
@JsApiFunc(id = 3, name = "D")
public class JsApiFunc_D extends BaseJsApiFunc {

    @Override
    public boolean invoke(JsApiContext context, JSONObject args, InvokedCallback callback) {
        return false;
    }
}
