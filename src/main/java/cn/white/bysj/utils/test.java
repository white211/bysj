package cn.white.bysj.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SignatureException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by @author white
 *
 * @date 2018-03-27 0:41
 */
public class test {

    public static void main(String[] args) {
        System.out.println(getV4IP());
    }

    public static String getV4IP(){
        String ip = "172.17.70.1";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            while((read=in.readLine())!=null){
                inputLine.append(read+"\r\n");
            }
            //System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if(m.find()){
            String ipstr = m.group(1);
            ip = ipstr;
            //System.out.println(ipstr);
        }
        return ip;
    }

//    public static void main(String[] args) {
//        String pattern = "^/admin.*$";
//        String url = "/admin/user/login.do";
//        System.out.println(url.matches(pattern));
//    }

          //测试redis链接
//    public static void main(String[] args) {
//        Jedis jedis = new Jedis("139.199.154.75",6379);
//        jedis.auth("admin");
//        jedis.set("name","white");
//        System.out.println(jedis.get("name"));
//        jedis.close();
//    }

     //测试心知天气接口
//    public static void main(String[] args) throws IOException, SignatureException {
//       String url = WeatherUtil.generateGetDiaryWeatherURL(  "广州",
//               "zh-Hans",
//               "c",
//               "1",
//               "1");
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet(url);
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        String responseBody = httpClient.execute(httpGet, responseHandler);
//        System.out.println(responseBody);
//        JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(responseBody);
//        System.out.println(jsonObject);
//
//    }

}
