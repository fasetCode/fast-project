package com.fastproject.utils;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class XssUtil {

    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowElements(
                    "p", "div", "span", "b", "i", "u", "br",
                    "ul", "ol", "li",
                    "a", "img",
                    "table", "thead", "tbody", "tr", "th", "td",
                    "colgroup", "col"
            )

            // 链接
            .allowAttributes("href").onElements("a")

            // 图片
            .allowAttributes("src", "alt", "data-img-id").onElements("img")

            // 表格结构
            .allowAttributes("colspan", "rowspan").onElements("td", "th")

            // col/colgroup
            .allowAttributes("span").onElements("col")
            .allowAttributes("width").onElements("col", "table")

            // ⭐关键：安全样式支持
            .allowStyling()

            // URL 限制
            .allowUrlProtocols("http", "https")

            .requireRelNofollowOnLinks()

            .toFactory();

    public static String clean(String html) {
        return POLICY.sanitize(html);
    }

    static void main() {
        String str =
                "<img data-img-id=\"315745504677990400\"><table style=\"min-width: 75px;\"><colgroup><col style=\"min-width: 25px;\"><col style=\"min-width: 25px;\"><col style=\"min-width: 25px;\"></colgroup><tbody><tr><th colspan=\"2\" rowspan=\"1\"><p></p></th><th colspan=\"1\" rowspan=\"1\"><p></p></th></tr><tr><td colspan=\"1\" rowspan=\"1\"><p></p></td><td colspan=\"1\" rowspan=\"1\"><p></p></td><td colspan=\"1\" rowspan=\"1\"><p></p></td></tr><tr><td colspan=\"1\" rowspan=\"1\"><p></p></td><td colspan=\"1\" rowspan=\"1\"><p></p></td><td colspan=\"1\" rowspan=\"1\"><p></p></td></tr></tbody></table>";

        System.out.println(XssUtil.clean(str));
    }
}