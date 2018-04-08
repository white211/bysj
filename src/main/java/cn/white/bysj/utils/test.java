package cn.white.bysj.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.security.SignatureException;

/**
 * Create by @author white
 *
 * @date 2018-03-27 0:41
 */
public class test {



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
