package cn.white.bysj.utils;


import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;

/**
 * Create by @author white
 *
 * @date 2018-03-27 15:16
 */

public class GetCityUtil {

    /**
     * TODO: 获取客户端访问ip
     * @author white
     * @date 2018-03-30 19:21
       @param request
     * @return
     * @throws
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    /**
     * TODO: 通过ip地址 调用百度地体获取ip所在城市
     * @author white
     * @date 2018-03-30 19:22

     * @return
     * @throws
     */
    public static String getCity(HttpServletRequest request) throws IOException {
        String ip = getIpAddr(request);
        System.out.println(ip);
        String url = "http://api.map.baidu.com/location/ip?ak=3HU6oASoHn1Bv85BY83oBrhVTvr99ekt&ip=" + ip;
        JSONObject json = readJsonFromUrl(url);
        System.out.println(json.toString());
        String city = ((JSONObject) json.get("content")).get("address").toString();
        return city;
    }

    /**
     * TODO:
     * @author white
     * @date 2018-03-30 19:22

     * @return
     * @throws
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


    public static void main(String[] args) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=3HU6oASoHn1Bv85BY83oBrhVTvr99ekt&ip=118.196.5.73");
        System.out.println(json.toString());
        System.out.println(((JSONObject) json.get("content")).get("address"));
    }


}
