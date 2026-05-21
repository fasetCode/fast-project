package com.fastproject.utils.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class SvgCaptchaUtils {

    public static String generateSvgCaptcha(String code) {
        StringBuilder sb = new StringBuilder();
        int width = 130;
        int height = 50;

        // 生成随机浅色背景
        String bgColor = String.format("#%02x%02x%02x",
                200 + (int)(Math.random() * 55),
                200 + (int)(Math.random() * 55),
                200 + (int)(Math.random() * 55));

        sb.append(String.format("""
        <svg xmlns="http://www.w3.org/2000/svg" width="%d" height="%d">
            <style>
                text {
                    user-select: none;
                    -webkit-user-select: none;
                    -moz-user-select: none;
                    -ms-user-select: none;
                    pointer-events: none;
                }
            </style>
            <rect width="100%%" height="100%%" fill="%s"/>
    """, width, height, bgColor));

        // 1. 干扰点 (噪点) - 数量增加到 80 个
        for (int i = 0; i < 80; i++) {
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            int r = (int)(Math.random() * 2) + 1;
            String color = String.format("#%02x%02x%02x",
                    100 + (int)(Math.random() * 120),
                    100 + (int)(Math.random() * 120),
                    100 + (int)(Math.random() * 120));
            sb.append(String.format("        <circle cx=\"%d\" cy=\"%d\" r=\"%d\" fill=\"%s\" />\n", x, y, r, color));
        }

        // 2. 干扰曲线 (贝塞尔曲线) - 数量增加到 6 条，并且线更粗
        for (int i = 0; i < 6; i++) {
            int x1 = (int)(Math.random() * width);
            int y1 = (int)(Math.random() * height);
            int cx1 = (int)(Math.random() * width);
            int cy1 = (int)(Math.random() * height);
            int cx2 = (int)(Math.random() * width);
            int cy2 = (int)(Math.random() * height);
            int x2 = (int)(Math.random() * width);
            int y2 = (int)(Math.random() * height);
            String color = String.format("#%02x%02x%02x",
                    80 + (int)(Math.random() * 100),
                    80 + (int)(Math.random() * 100),
                    80 + (int)(Math.random() * 100));
            sb.append(String.format("        <path d=\"M%d %d C%d %d, %d %d, %d %d\" stroke=\"%s\" stroke-width=\"%d\" fill=\"none\" />\n",
                    x1, y1, cx1, cy1, cx2, cy2, x2, y2, color, (int)(Math.random() * 4) + 1));
        }

        // 3. 随机字符，通过混淆 DOM 和插入幽灵字符来防止机器提取
        List<String> textElements = new ArrayList<>();
        String[] fonts = {"Arial", "Verdana", "Georgia", "Comic Sans MS", "Impact", "Courier New"};

        // 真实的字符
        for (int i = 0; i < code.length(); i++) {
            String color = String.format("#%02x%02x%02x",
                    (int)(Math.random() * 80),
                    (int)(Math.random() * 80),
                    (int)(Math.random() * 80));

            String font = fonts[(int)(Math.random() * fonts.length)];
            // 字体大小变化更大
            int fontSize = 24 + (int)(Math.random() * 14);

            // X 轴加上左右随机偏移
            int x = 15 + i * (width / code.length() - 5) + (int)(Math.random() * 10 - 5);
            // Y 轴变化更大
            int y = 35 + (int)(Math.random() * 16) - 8;
            
            // 旋转角度加大，最大可达正负 45 度
            int rotate = (int)(Math.random() * 90 - 45);
            
            // 加入 X 轴和 Y 轴的随机缩放 (倾斜/变形)
            double scaleX = 0.8 + Math.random() * 0.4;
            double scaleY = 0.8 + Math.random() * 0.4;

            // 将字符转为 HTML 实体 (例如 '1' -> '&#49;') 进一步防止简单正则匹配
            String charEntity = String.format("&#%d;", (int)code.charAt(i));

            textElements.add(String.format(java.util.Locale.US, """
            <text x="0" y="0" font-family="%s" font-size="%d" font-weight="bold" fill="%s"
                  transform="translate(%d, %d) rotate(%d) scale(%.2f, %.2f)">%s</text>
        """, font, fontSize, color, x, y, rotate, scaleX, scaleY, charEntity));
        }

        // 幽灵字符 (看不见或在画布外，用于干扰机器提取) - 数量增加到 6 个
        for (int i = 0; i < 6; i++) {
            String fakeChar = String.valueOf((char)('0' + (int)(Math.random() * 10)));
            // 放在画布外
            textElements.add(String.format("""
            <text x="-100" y="-100" font-size="20">%s</text>
        """, fakeChar));
            // 透明字符放在正常位置，且加入旋转混淆
            textElements.add(String.format("""
            <text x="%d" y="%d" font-size="20" fill="transparent" transform="rotate(%d %d,%d)">%s</text>
        """, (int)(Math.random() * width), (int)(Math.random() * height), 
                (int)(Math.random() * 90 - 45), (int)(Math.random() * width), (int)(Math.random() * height), fakeChar));
        }

        // 打乱真实的字符和幽灵字符在 DOM 中的顺序
        Collections.shuffle(textElements);
        for (String el : textElements) {
            sb.append(el);
        }

        // 4. 顶层干扰线 (直线) - 数量增加到 5 条
        for (int i = 0; i < 5; i++) {
            int x1 = (int)(Math.random() * width);
            int y1 = (int)(Math.random() * height);
            int x2 = (int)(Math.random() * width);
            int y2 = (int)(Math.random() * height);
            String color = String.format("#%02x%02x%02x",
                    (int)(Math.random() * 150),
                    (int)(Math.random() * 150),
                    (int)(Math.random() * 150));
            sb.append(String.format("""
            <line x1="%d" y1="%d" x2="%d" y2="%d" stroke="%s" stroke-width="2"/>
        """, x1, y1, x2, y2, color));
        }

        sb.append("</svg>");

        return "data:image/svg+xml;base64," +
                Base64.getEncoder()
                        .encodeToString(sb.toString().getBytes(StandardCharsets.UTF_8));
    }
}
