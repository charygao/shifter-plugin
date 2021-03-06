package com.kstenschke.shifter.models.shiftable_types;

import org.junit.Test;

import static org.junit.Assert.*;

public class CssTest {

    @Test
    public void getShifted() {
        assertEquals(
                "color: #fff;font-size: 10px;",
                Css.getShifted(
                        "color: #fff;\n" +
                              "font-size: 10px;")
        );

        assertEquals(
                "color: #fff;font-size: 10px;",
                Css.getShifted(
                              "font-size: 10px;\n" +
                                      "color: #fff;")
        );

        assertEquals(
                "clear: both;content: \"\";display: table;",
                Css.getShifted(
                              "content: \"\";\n" +
                                     "display: table;\n" +
                                     "clear: both;")
        );
    }
}