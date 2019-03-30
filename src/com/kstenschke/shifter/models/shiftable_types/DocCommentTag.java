/*
 * Copyright 2011-2019 Kay Stenschke
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
package com.kstenschke.shifter.models.shiftable_types;

import com.kstenschke.shifter.models.ActionContainer;
import com.kstenschke.shifter.models.ShiftableTypeAbstract;
import com.kstenschke.shifter.utils.UtilsArray;
import com.kstenschke.shifter.utils.UtilsFile;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DocCommentType class
 */
public class DocCommentTag extends ShiftableTypeAbstract {

    private ActionContainer actionContainer;

    private final String[] tagsJavaScript;

    private final String[] tagsJava;

    private final String[] tagsPHP;

    /**
     * Constructor
     */
    public DocCommentTag(@Nullable ActionContainer actionContainer) {
        super(actionContainer);

        tagsJavaScript = new String[]{"author", "class", "constructor", "deprecated", "exception", "method", "module", "namespace", "param", "private", "property", "returns", "see", "this", "throws", "type", "version"};
        tagsJava = new String[]{"author", "version", "param", "return", "exception", "throws", "see", "since", "serial", "deprecated"};
        tagsPHP = new String[]{"abstract", "access", "author", "constant", "deprecated", "final", "global", "magic", "module", "param", "package", "return", "see", "static", "subpackage", "throws", "todo", "var", "version"};
    }


    /**
     * @return boolean    Does the given String represent a data type (number / integer / string /...) from a DOC comment (param / return /...)?
     */
    public boolean isApplicable() {
        String line = actionContainer.caretLine;

        return "@".equals(actionContainer.prefixChar) &&
                isDocCommentLineContext(line);
    }

    /**
     * @return Array    String array w/ all recognized doc comment tags
     */
    private String[] getAllTags() {
        return UtilsArray.mergeArrays(tagsJavaScript, tagsJava, tagsPHP);
    }

    /**
     * @return String   Pipe-separated list (as string) w/ all recognized doc comment tags
     */
    String getAllTagsPiped() {
        String[] allTags = getAllTags();

        return UtilsArray.implode(allTags, "|");
    }

    /**
     * Check whether given String looks like a doc comment line
     *
     * @param line Line the caret is at
     * @return boolean
     */
    private boolean isDocCommentLineContext(String line) {
        String allTagsPiped = getAllTagsPiped();
        String regExPatternLine = "\\s*\\*\\s+@(" + allTagsPiped + ")";

        Matcher m = Pattern.compile(regExPatternLine).matcher(line.toLowerCase());

        return m.find();
    }

    public static boolean isDocCommentLine(String line) {
        DocCommentTag docCommentTag = new DocCommentTag(null);
        return docCommentTag.isDocCommentLineContext(line);
    }

    /**
     * @return Shifting result
     */
    public String getShifted(
            String word,
            ActionContainer actionContainer,
            Integer moreCount,
            String leadingWhiteSpace
    ) {
        String[] commentTags = getTagsByFilename(actionContainer.filename);
        int amountTags = commentTags.length;
        if (amountTags > 0) {
            String wordLower = word.toLowerCase();
            List<String> commentTagsList = Arrays.asList(commentTags);
            int curIndex = commentTagsList.indexOf(wordLower);
            if (curIndex > -1) {
                curIndex = NumericValue.moduloShiftInteger(curIndex, amountTags, actionContainer.isShiftUp);
                String shiftedWord = commentTagsList.get(curIndex);
                if ("method".equals(shiftedWord)) {
                    shiftedWord = shiftedWord + parseNextMethod(actionContainer.textAfterCaret);
                }

                return shiftedWord;
            }
        }

        return word;
    }

    /**
     * Find first JavaScript function's name out of given code
     *
     * @param jsCode JavaScript source code to be analyzed
     * @return String JavaScript method name
     */
    private String parseNextMethod(String jsCode) {
        List<String> allMatches = new ArrayList<>();

        String regExPattern = "[a-zA-Z_$][0-9a-zA-Z_$]*\\s*:\\s*function";
        Matcher m = Pattern.compile(regExPattern).matcher(jsCode);
        while (m.find()) {
            if (!allMatches.contains(m.group())) {
                allMatches.add(m.group());
            }
        }

        return allMatches.isEmpty()
                ? ""
                : "\t" + (allMatches.get(0).replace("function", "").replace(":", "").trim());
    }

    /**
     * Return array of data shiftable_types of detected language of edited file
     *
     * @param filename Filename of edited file
     * @return String[]
     */
    private String[] getTagsByFilename(String filename) {
        if (null != filename) {
            String filenameLower = filename.toLowerCase();

            if (UtilsFile.isJavaScriptFile(filenameLower, true)) {
                // JavaScript comment shiftable_types
                return tagsJavaScript;
            }
            if (filenameLower.endsWith(".java")) {
                // Java comment tags in the recommended order
                return tagsJava;
            }
        }

        return tagsPHP;
    }
}
