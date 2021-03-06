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

package cc.suitalk.event.rx;

/**
 * Created by albieliang on 16/3/15.
 */
public class RxEvent {

    protected String action;
    protected Callback callback;

    public RxEvent() {

    }

    public RxEvent(String action) {
        this.action = action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Callback for the RxEvent by RxEventBus after the event was published.
     */
    public interface Callback {
        void onCallback(RxEvent event);
    }
}