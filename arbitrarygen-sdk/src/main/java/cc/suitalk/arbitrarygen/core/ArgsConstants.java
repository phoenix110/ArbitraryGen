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

package cc.suitalk.arbitrarygen.core;

/**
 * 
 * @author AlbieLiang
 *
 */
public interface ArgsConstants {

	/**
	 * External args key set
	 */
	String EXTERNAL_ARGS_KEY_ENABLE = "enable";
	String EXTERNAL_ARGS_KEY_DEST_DIR = "destDir";
	String EXTERNAL_ARGS_KEY_SRC_DIR = "srcDir";
	String EXTERNAL_ARGS_KEY_TEMPLATE_DIR = "templateDir";
	String EXTERNAL_ARGS_KEY_FORMAT = "format";
	String EXTERNAL_ARGS_KEY_PARSER = "parser";
	String EXTERNAL_ARGS_KEY_EXTENSION = "extension";
	String EXTERNAL_ARGS_KEY_ENGINE = "engine";
	String EXTERNAL_ARGS_KEY_PROCESSOR = "processor";
	String EXTERNAL_ARGS_KEY_JAR = "jar";
	String EXTERNAL_ARGS_KEY_CLASS = "tClass";
	String EXTERNAL_ARGS_KEY_PROCESSOR_CLASS = "pClass";
	String EXTERNAL_ARGS_KEY_ARG_JSON = "argJson";
	String EXTERNAL_ARGS_KEY_ENV_ARG_JSON = "envArgJson";
	String EXTERNAL_ARGS_KEY_ARG_JSON_PATH = "argJsonPath";
	String EXTERNAL_ARGS_KEY_ENV_ARG_JSON_PATH = "envArgJsonPath";

	String EXTERNAL_ARGS_KEY_LOG_DEBUG = "debug";
	String EXTERNAL_ARGS_KEY_LOG_LEVEL = "logLevel";
	String EXTERNAL_ARGS_KEY_LOG_PRINT_TAG = "printTag";
	String EXTERNAL_ARGS_KEY_LOG_PRINT_LEVEL = "printLevel";
	String EXTERNAL_ARGS_KEY_TO_FILE = "toFile";
	String EXTERNAL_ARGS_KEY_PATH = "path";

	String EXTERNAL_ARGS_KEY_RULE_FILE = "ruleFile";
	String EXTERNAL_ARGS_KEY_RULE = "rule";

	interface EnvKey {
		String NAME = "name";

		String ROOT_PROJECT = "rootProject";
		String ROOT_DIR = "rootDir";
		String PROJECT = "project";
		String PROJECT_DIR = "projectDir";
		String BUILD_DIR = "buildDir";
	}
}
