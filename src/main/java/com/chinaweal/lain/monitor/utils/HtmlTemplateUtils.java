package com.chinaweal.lain.monitor.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-9
 */
public class HtmlTemplateUtils {
    private static String head = "<html><body><table border='1'>";
    private static String end = "</table></body></html>";
    private static String line = "<tr><td>@item</td><td>@content</td></tr>";

    public static String getHead() {
        return head;
    }

    public static String getEnd() {
        return end;
    }

    public static String getLine() {
        return line;
    }
}
