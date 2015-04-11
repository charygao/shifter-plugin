/*
 * Copyright 2011-2015 Kay Stenschke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kstenschke.shifter.models.shiftertypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ternary Expression
 */
public class TernaryExpression {

	/**
	 * Constructor
	 */
	public TernaryExpression() {

	}

	/**
	 * Check whether shifted string is a ternary expression
	 *
	 * @param   str
	 * @param   prefixChar     Character preceding the string
	 * @return  boolean.
	 */
	public static boolean isTernaryExpression(String str, String prefixChar) {
		str = str.trim();

		return ((str.startsWith("?") || prefixChar.equals("?") )
			 && (str.contains(":") && !str.endsWith(":") && !str.startsWith(":"))
			 && str.length() >= 3
		);
	}

	/**
	 * Shift: swap IF and ELSE parts
	 *
	 * @param   str				string to be shifted
	 * @param   isUp            Shifting up or down? irrelevant w/ this type
	 * @return  String			The shifted string
	 */
	public static String getShifted(String str, boolean isUp) {
	   	Integer offsetElse = str.indexOf(":");

		if( offsetElse > -1 ) {
			boolean isQuestionMarkInline = str.startsWith("?");
			if (isQuestionMarkInline) str = str.substring(1);

			String partThan = str.substring(0, offsetElse - 1);
			String partElse = str.substring(offsetElse);

			// Detect and maintain "glue" w/ (single) whitespace
			boolean wrapWithSpace = partThan.endsWith(" ") || partElse.startsWith(" ");
			boolean wrapWithTab   = partThan.endsWith("\t") || partElse.startsWith("\t");

			String glue = wrapWithSpace ? " " : (wrapWithTab ? "\t" : "");

			str = partElse.trim() + glue + ":" + glue + partThan.trim();

			if( isQuestionMarkInline ) str = "?" + glue + str;
		}

		return str;
	}

}