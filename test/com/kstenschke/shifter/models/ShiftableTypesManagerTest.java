package com.kstenschke.shifter.models;

import com.kstenschke.shifter.models.entities.AbstractShiftable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShiftableTypesManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getShiftableDetectAccessTypeAtCaret() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n\n/class Foo {\npublic function a(){}\nprotected function b(){}\nprivate function b(){}\n");
        actionContainer.setStringAtCaret("public");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar(" ");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());

        actionContainer.setStringAtCaret("private");
        shiftableTypesManager = new ShiftableTypesManager(actionContainer);
        shiftables = shiftableTypesManager.getShiftables();
        shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());

        actionContainer.setStringAtCaret("protected");
        shiftableTypesManager = new ShiftableTypesManager(actionContainer);
        shiftables = shiftableTypesManager.getShiftables();
        shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());
    }

    @Test
    public void getShiftableDetectAccessTypeSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n\n/class Foo {\npublic function a(){}\nprotected function b(){}\nprivate function b(){}\n");
        actionContainer.setSelectedText("public");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar(" ");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());

        actionContainer.setSelectedText("private");
        shiftableTypesManager = new ShiftableTypesManager(actionContainer);
        shiftables = shiftableTypesManager.getShiftables();
        shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());

        actionContainer.setSelectedText("protected");
        shiftableTypesManager = new ShiftableTypesManager(actionContainer);
        shiftables = shiftableTypesManager.getShiftables();
        shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.ACCESS_TYPE, shiftable.getType());
    }

    @Test
    public void getShiftableDetectCamelCaseStringAtCaret() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n\necho 'fooBarBaz';\n");
        actionContainer.setStringAtCaret("fooBarBaz");
        actionContainer.setPrefixChar("'");
        actionContainer.setPostfixChar("'");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.CAMEL_CASE_STRING, shiftable.getType());
    }

    @Test
    public void getShiftableDetectCamelCaseStringSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.txt");
        actionContainer.setDocumentText("fooBarBaz");
        actionContainer.setSelectedText("fooBarBaz");
        actionContainer.setPrefixChar("");
        actionContainer.setPostfixChar("");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.CAMEL_CASE_STRING, shiftable.getType());
    }

    @Test
    public void getShiftableDetectCommentSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.txt");
        actionContainer.setDocumentText("//fooBarBaz");
        actionContainer.setSelectedText("//fooBarBaz");
        actionContainer.setPrefixChar("");
        actionContainer.setPostfixChar("");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.COMMENT, shiftable.getType());
    }

    @Test
    public void getShiftableDetectConcatenationJsSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.js");
        actionContainer.setDocumentText("var txt= 'foo' + 'bar' + 'baz';\n");
        actionContainer.setSelectedText("'foo' + 'bar' + 'baz'");
        actionContainer.setPrefixChar("'");
        actionContainer.setPostfixChar("'");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.CONCATENATION_JS, shiftable.getType());
    }

    @Test
    public void getShiftableDetectConcatenationTsSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.ts");
        actionContainer.setDocumentText("let foo = 1;\nlet bar = foo + 'bar' + 10 + 'baz';\n");
        actionContainer.setSelectedText("foo + 'bar' + 10 + 'baz'");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar(";");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.CONCATENATION_JS_IN_TS, shiftable.getType());
    }

    @Test
    public void getShiftableDetectConcatenationPhpSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?\n$foo = 'bar' . $baz;\n");
        actionContainer.setSelectedText("'bar' . $baz");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar(";");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.CONCATENATION_PHP, shiftable.getType());
    }

    @Test
    public void getShiftableDetectDictionaryWordSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.txt");
        actionContainer.setDocumentText(" foo\n north\n monday\n dog\n");
        actionContainer.setSelectedText("north");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.DICTIONARY_WORD, shiftable.getType());
    }

    @Test
    public void getShiftableDetectDocCommentTagCaretLine() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n\n/**\n * @version 2.0\n**/\n");
        actionContainer.setCaretLine(" * @version 2.0");
        actionContainer.setPrefixChar("@");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.DOC_COMMENT_TAG, shiftable.getType());
    }

    @Test
    public void getShiftableDetectJqueryObserverSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.js");
        actionContainer.setDocumentText("$('#myId').click(function(){alert('click!');});");
        actionContainer.setSelectedText("click(");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.JQUERY_OBSERVER, shiftable.getType());
    }

    @Test
    public void getShiftableDetectJsVariableDeclarationsSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.js");
        actionContainer.setDocumentText("var foo=0;\nvar bar=1;\n");
        actionContainer.setSelectedText("var foo=0;\nvar bar=1;");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.JS_VARIABLE_DECLARATIONS, shiftable.getType());
    }

    @Test
    public void getShiftableDetectParenthesisSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n$foo=(10+9*8)/2;\n$bar=2;\n");
        actionContainer.setSelectedText("(10+9*8)");
        actionContainer.setPostfixChar(" ");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.PARENTHESIS, shiftable.getType());
    }

    @Test
    public void getShiftableDetectPhpDocContainingDataTypeSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setSelectedText("* @param int $x");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.PHP_DOC_PARAM_CONTAINING_DATA_TYPE, shiftable.getType());
    }

    @Test
    public void getShiftableDetectPhpVariableSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.php");
        actionContainer.setDocumentText("<?php\n$foo=1;\n$bar=2;\n");
        actionContainer.setSelectedText("$bar");
        actionContainer.setPostfixChar(" ");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.PHP_VARIABLE, shiftable.getType());
    }

    @Test
    public void getShiftableDetectSizzleSelectorSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.js");
        actionContainer.setDocumentText("$('div').hide();\n");
        actionContainer.setSelectedText("$('div')");
        actionContainer.setPostfixChar(".");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.SIZZLE_SELECTOR, shiftable.getType());
    }

    @Test
    public void getShiftableDetectTrailingCommentSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setSelectedText("echo 'x'; // output x");
        actionContainer.setIsLastLineInDocument(false);
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.TRAILING_COMMENT, shiftable.getType());
    }

    @Test
    public void getShiftableDetectHtmlEncodableSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.html");
        actionContainer.setDocumentText("fö bår baß");
        actionContainer.setSelectedText("fö bår baß");
        actionContainer.setIsLastLineInDocument(true);
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.HTML_ENCODABLE, shiftable.getType());
    }

    @Test
    public void getShiftableDetectNumericSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.txt");
        actionContainer.setDocumentText("1");
        actionContainer.setSelectedText("1");
        actionContainer.setIsLastLineInDocument(true);
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.NUMERIC_VALUE, shiftable.getType());
    }

    /*@Test
    public void getShiftableDetectTupelAndHtmlEncodableSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.html");
        actionContainer.setDocumentText("fö! bår, baß");
        actionContainer.setSelectedText("fö! bår, baß");
        actionContainer.setIsLastLineInDocument(true);
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        List<AbstractShiftable> shiftables = shiftableTypesManager.getShiftables();
        AbstractShiftable shiftable = ShiftableTypesManager.getShiftable(shiftables);

        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.HTML_ENCODABLE, shiftable.getType());
    }*/

    /*@Test
    public void getShiftableDetectDictionaryWordOfSpecificFileTypeSelected() {
        ActionContainer actionContainer = new ActionContainer(null, true, false);
        actionContainer.setFilename("foo.txt");
        actionContainer.setDocumentText(" foo\n north\n monday\n dog\n");
        actionContainer.setSelectedText("north");
        actionContainer.setPrefixChar(" ");
        actionContainer.setPostfixChar("\n");
        ShiftableTypesManager shiftableTypesManager = new ShiftableTypesManager(actionContainer);

        AbstractShiftable shiftable = shiftableTypesManager.getShiftables();
        assertNotNull(shiftable);
        assertEquals(ShiftableTypes.Type.DICTIONARY_WORD, shiftable.getType());
    }*/
}
