package cn.white.bysj.utils.HttpUtil;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Create by @author white
 *
 * @date 2018-03-25 13:17
 */
@Service
public class HttpClient {

    public String client(String url, HttpMethod method){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Content-Type","application/json; charset=utf-8");
        headers.add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        headers.add("Accept-Encoding","gzip, deflate, br");
        headers.add("Accept-Language","zh-CN,zh;q=0.9");
        headers.add("Cache-Control","no-cache");
        headers.add("Connection","keep-alive");
        headers.add("Cookie","_ga=GA1.2.1399139926.1521941546; _gid=GA1.2.1507142550.1521941546; intercom-id-q1mzxgqr=2b1c8893-5d48-4997-ada3-08b5b0cabd02; intercom-lou-q1mzxgqr=1; Hm_lvt_f28395955ba9f89d64b7c162becd43be=1521941546,1521941618,1521941877,1521957509; Hm_lpvt_f28395955ba9f89d64b7c162becd43be=1521957574; intercom-session-q1mzxgqr=c0dCaUp1dkFoSzYzbmRkaGhHTGJvRDRjUnhKMkxzNkQ1TWdTNkp1Mlc5N3RidWRvNkpEVzlHZDVKWk5Ua2xQRy0tc2tid3FlOFY1ejZPUm9mK2d4WG9ZUT09--4fee0bcce76bb0eb1d9efd6db58322b15e5df3f7");
        headers.add("Host","api.seniverse.com");
        headers.add("Pragma","no-cache");
        headers.add("Upgrade-Insecure-Requests","1");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(null, headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, HttpMethod.GET, requestEntity, String.class);
        return response.getBody();
    }

}
