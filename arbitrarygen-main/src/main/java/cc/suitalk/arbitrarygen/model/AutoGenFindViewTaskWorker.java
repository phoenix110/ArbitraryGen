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

package cc.suitalk.arbitrarygen.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.suitalk.arbitrarygen.core.ConfigInfo;
import cc.suitalk.arbitrarygen.extension.model.Command;
import cc.suitalk.arbitrarygen.base.BaseDefineCodeBlock;
import cc.suitalk.arbitrarygen.base.BaseStatement;
import cc.suitalk.arbitrarygen.base.JavaFileObject;
import cc.suitalk.arbitrarygen.block.FieldCodeBlock;
import cc.suitalk.arbitrarygen.block.TypeDefineCodeBlock;
import cc.suitalk.arbitrarygen.expression.ReferenceExpression;
import cc.suitalk.arbitrarygen.extension.BaseAGTaskWorker;
import cc.suitalk.arbitrarygen.model.AutoGenFindViewHelper.FindViewIdsTask;
import cc.suitalk.arbitrarygen.statement.AnnotationStatement;
import cc.suitalk.arbitrarygen.statement.ImportStatement;
import cc.suitalk.arbitrarygen.statement.NormalStatement;
import cc.suitalk.arbitrarygen.utils.Log;
import cc.suitalk.arbitrarygen.utils.SignatureCreator;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class AutoGenFindViewTaskWorker extends BaseAGTaskWorker {

	private static final String TAG = "AG.AutoGenFindViewTaskWorker";

	public AutoGenFindViewTaskWorker() {
		super(Command.Type.COMMAND_EXTRACT_VIEW_ID);
	}
	
	@Override
	public boolean doTask(ConfigInfo configInfo, ArbitraryGenTaskInfo task, JavaFileObject fileObject, Map<String, ArbitraryGenTaskInfo> srcGenTasks, Map<String, ArbitraryGenTaskInfo> targetTasks) {

		AnnotationStatement stm = task.getMatchAnnotations().get(Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION);
		if (stm == null) {
			return false;
		}
		String key = SignatureCreator.create(SignatureCreator.TYPE_ANNOTATION, Constants.NEED_TO_HANDLE_SOURCE_LOCATION_ANNOTATION, stm.getArg("command").toString());
		ArbitraryGenTaskInfo targetTask = targetTasks.get(key);
		if (targetTask == null) {
			return false;
		}
		// obtain view id
		String fileName = AutoGenFindViewHelper.getLayoutFileName(task.getCodeBlock());
		Log.d(TAG, "layout file name : " + fileName);

		Set<String> needImportSet = new HashSet<String>();
		
		List<FindViewIdsTask> fvidTasks = AutoGenFindViewHelper.getIdsFromLayoutFile(fileName);
		if (fvidTasks != null && fvidTasks.size() != 0) {
			BaseDefineCodeBlock cb = targetTask.getCodeBlock();
			BaseStatement outerStm = cb.getOuterStatement();
			TypeDefineCodeBlock outerCodeBlock = null;
			if (outerStm instanceof TypeDefineCodeBlock) {
				outerCodeBlock = (TypeDefineCodeBlock) outerStm;
			}
			for (FindViewIdsTask fvidTask : fvidTasks) {
				// TODO
				Log.d(TAG, "extracted view : " + fvidTask);
				if (outerCodeBlock != null) {
					FieldCodeBlock field = new FieldCodeBlock();
					field.setModifier("private");
					field.setType(Util.createSimpleTypeName(fvidTask.getType()));
					field.setName(Util.createSimpleTypeName(fvidTask.getId()));
					outerCodeBlock.addField(field);
					// Replace the default EnvironmentArgs
					field.attachEnvironmentArgs(Util.obtainEnvArgs(field));
				}
				String Import = AutoFindFindViewConstants.getMappingImport(fvidTask.getType());
				if (!Util.isNullOrNil(Import)) {
					needImportSet.add(Import);
				} else {
					// TODO
					needImportSet.add(fvidTask.getType()); 
				}
				NormalStatement nstm = new NormalStatement(fvidTask.getId() + " = (" + fvidTask.getType() + ") findViewById(R.id." + fvidTask.getId() + ")");
				cb.addStatement(nstm);
				nstm.attachEnvironmentArgs(Util.obtainEnvArgs(nstm));
			}
			for (String _import : needImportSet) {
				// Import android.view
				ImportStatement importStm = new ImportStatement(new ReferenceExpression(_import));
				fileObject.addImport(importStm);
				importStm.attachEnvironmentArgs(Util.obtainEnvArgs(importStm));
			}
			return true;
		}
		return false;
	}
}
