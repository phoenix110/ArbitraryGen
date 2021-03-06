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

package cc.suitalk.arbitrarygen.statement.parser;

import java.io.IOException;

import cc.suitalk.arbitrarygen.analyzer.IReader;
import cc.suitalk.arbitrarygen.base.BaseStatementParser;
import cc.suitalk.arbitrarygen.base.Expression;
import cc.suitalk.arbitrarygen.core.Word;
import cc.suitalk.arbitrarygen.extension.Lexer;
import cc.suitalk.arbitrarygen.statement.SyncStatement;
import cc.suitalk.arbitrarygen.utils.Util;

/**
 * 
 * @author AlbieLiang
 *
 */
public class SyncStatementParser extends BaseStatementParser {

	public SyncStatementParser() {
		super("synchronized");
	}

	@Override
	public SyncStatement parse(IReader reader, Lexer lexer, Word curWord) {
		try {
			super.parse(reader, lexer, curWord);
			curWord = getLastWord();
			if (curWord != null && "synchronized".equals(curWord.value)) {
				SyncStatement syncStm = new SyncStatement();
				syncStm.setPrefixWord(curWord);
				Word word = nextWord(reader, lexer);
//				syncStm.setCommendBlock(getCommendStr());
				Expression condition = Util.extractExpressionFromBracket(reader, lexer, word, this);
				if (condition == null) {
					throw new RuntimeException("extract expression from bracket failed.");
				}
				syncStm.setConditionExpression(condition);
				word = getLastWord();
				Util.getAndAttachCodeBlock(reader, lexer, word, syncStm, this);
				word = getLastWord();
				return syncStm;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
