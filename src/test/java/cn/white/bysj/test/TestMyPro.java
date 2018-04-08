package cn.white.bysj.test;

import cn.white.bysj.BysjApplication;
import cn.white.bysj.admin.vo.NoteVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Create by @author white
 *
 * @date 2018-04-07 21:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BysjApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class TestMyPro {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void get() throws Exception {
        Object result = testRestTemplate.getForObject("/note/page.do",NoteVo.class);
//        Assert.assertEquals(result,0);
        System.out.println(result);
    }

}
