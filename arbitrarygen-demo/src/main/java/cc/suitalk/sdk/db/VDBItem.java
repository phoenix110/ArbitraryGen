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

package cc.suitalk.sdk.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by albieliang on 16/7/17.
 */
public abstract class VDBItem {
    public static final String COL_ROWID = "rowId";
    protected static final int rowId_HASHCODE = "rowId".hashCode();

    protected long rowId = -1;
    protected boolean hasRowId;

    public void convertFrom(Cursor fromType) {
        // TODO Auto-generated method stub
    }

    public ContentValues convertTo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void reset() {
        // TODO Auto-generated method stub
    }

    public void clear() {
        // TODO Auto-generated method stub
    }

    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }
}
