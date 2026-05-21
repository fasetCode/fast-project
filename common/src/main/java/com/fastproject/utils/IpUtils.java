package com.fastproject.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * IP工具类
 * 提供获取客户端真实IP地址等方法
 */
public class IpUtils {

    private IpUtils() {
        // 私有构造方法，防止实例化
    }

    /**
     * 获取客户端真实 IP 地址
     *
     * @param request HTTP请求
     * @return 客户端IP地址
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (isEmptyIp(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (isEmptyIp(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (isEmptyIp(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (isEmptyIp(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (isEmptyIp(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个代理情况，取第一个 IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip != null ? ip : "";
    }

    /**
     * 校验IP是否匹配CIDR网段
     *
     * @param ip      待校验IP
     * @param cidr    CIDR格式网段 (如: 192.168.1.0/24)
     * @return true-匹配
     */
    public static boolean isInRange(String ip, String cidr) {
        if (!cidr.contains("/")) {
            return ip.equals(cidr);
        }

        // 仅处理 IPv4
        if (!ip.contains(".") || !cidr.contains(".")) {
            return ip.equals(cidr.split("/")[0]);
        }

        try {
            String[] parts = cidr.split("/");
            String cidrIp = parts[0];
            int prefixLength = Integer.parseInt(parts[1]);

            long ipLong = ipToLong(ip);
            long cidrIpLong = ipToLong(cidrIp);

            long mask = -1L << (32 - prefixLength);
            return (ipLong & mask) == (cidrIpLong & mask);
        } catch (Exception e) {
            return false;
        }
    }

    private static long ipToLong(String ip) {
        String[] octets = ip.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (Long.parseLong(octets[i]) << (24 - (8 * i)));
        }
        return result & 0xFFFFFFFFL;
    }

    /**
     * 判断IP是否为空或未知
     *
     * @param ip IP地址
     * @return true-为空或未知
     */
    private static boolean isEmptyIp(String ip) {
        return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
    }
}
