package cc.suitalk.player.autogen.table;

import cc.suitalk.sdk.db.base.IDatabaseEngine;
import cc.suitalk.sdk.db.base.IDatabaseInfoDelegate;

/*@@@#SCRIPT-BEGIN#
<%var _tables = _hybrid_tables;
if (_tables && _tables.length > 0) {
	for (var i = 0; i < _tables.length; i++) {%>
import <%=_package%>.<%=_tables[i]._name%>;<%
    }
}%>
#SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#

import cc.suitalk.player.autogen.table.DBItem_1;
import cc.suitalk.player.autogen.table.DBItem_2;
import cc.suitalk.player.autogen.table.DBItem_3;
import cc.suitalk.player.autogen.table.DBItem_4;
import cc.suitalk.player.autogen.table.DBItem_5;

//@@@#AUTO-GEN-END#

/**
 * Generated by ScriptCodeGenEngine.
 * <p/>
 * Auto generate add {@link cc.suitalk.sdk.db.VigorDBInfo} into {@link IDatabaseEngine} here.
 *
 * @author AlbieLiang
 */
public class VDBInfoDelegate implements IDatabaseInfoDelegate {

    private static final String TAG = "AG.VDBInfoDelegate";

    @Override
    public void delegate(IDatabaseEngine engine) {
        // Auto generate code here

        /*@@@#SCRIPT-BEGIN#
        <%if (_tables && _tables.length > 0) {
           for (var i = 0; i < _tables.length; i++) {%>
        engine.addDatabaseInfo(<%=_tables[i]._name%>.getVDBInfo());<%
           }
        }%>
        #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#
        
        engine.addDatabaseInfo(DBItem_1.getVDBInfo());
        engine.addDatabaseInfo(DBItem_2.getVDBInfo());
        engine.addDatabaseInfo(DBItem_3.getVDBInfo());
        engine.addDatabaseInfo(DBItem_4.getVDBInfo());
        engine.addDatabaseInfo(DBItem_5.getVDBInfo());
        
        //@@@#AUTO-GEN-END#
    }
}





