package cn.white.bysj.utils;

import cn.white.bysj.commons.Constant;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * Create by @author white
 *
 *  2017-12-28 16:11
 */

//短信验证信息
public class SmsUtil {
    public static HashMap<String,String>  getCode(String telephone,String random) {
//        String random = getSms();
        String timeStamp = getTimestamp();
        String sig = getMD5(Constant.ACCOUNT_SID, Constant.AUTH_TOKEN, timeStamp);
//        String tamp = "【不黑科技】注册验证码："+random+"，如非本人操作，请忽略此短信。";
        String tamp = "【white科技】尊敬的用户，您的验证码为" + random;
        OutputStreamWriter out = null;
        BufferedReader br = null;
        StringBuilder sb = null;

        try {
            URL url = new URL(Constant.QUERY_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            String args = getQueryArgs(Constant.ACCOUNT_SID, tamp, telephone, timeStamp, sig, "JSON");
            out.write(args);
            out.flush();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String temp = "";
            sb = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        String respCode = jsonObject.getString("respCode");
        String respDesc = jsonObject.getString("respDesc");
        String successRespCode = "00000";
        HashMap<String,String> map = new HashMap<>();

        if (respCode.equals(successRespCode)) {
              map.put("respCode",respCode);
              map.put("respDesc",respDesc);
              map.put("code",random);
              return map;
        } else {
            map.put("respCode",respCode);
            map.put("respDesc",respDesc);
            return map;
        }
    }
    /*
    请求参数拼接
     */
    public static String getQueryArgs(String accountSid, String smsContent, String to, String timestamp, String sig, String respDataType) {
        return "accountSid=" + accountSid + "&smsContent=" + smsContent +
                " &to=" + to + "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + respDataType;
    }

    /*
    获取时间戳
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /*
    获取MD5
     */
    public static String getMD5(String sid, String token, String timestamp) {
        StringBuilder result = new StringBuilder();
        String source = sid + token + timestamp;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(source.getBytes());
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    result.append("0" + hex);
                } else {
                    result.append(hex);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
    获取六位随机数 使用递归
     */
    public static String getSms() {
        String random = new Random().nextInt(1000000) + "";
        if (random.length() != 6) {
            return getSms();
        } else {
            return random;
        }
    }
    //测试
    public static void main(String[] args) {
//        HashMap<String,String> result = getCode("");
//        System.out.println(result);
    }
}
