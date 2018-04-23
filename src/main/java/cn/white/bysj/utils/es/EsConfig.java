package cn.white.bysj.utils.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Create by @author white
 *
 * @date 2018-04-17 0:29
 */
@Configuration
public class EsConfig {

    @Bean
    public static TransportClient client() throws UnknownHostException{
        //连接localhost的9300端口，即Elasticsearch的master
        InetSocketTransportAddress node1 = new InetSocketTransportAddress(InetAddress.getByName("localhost"),
                9300
        );

        Settings settings = Settings.builder().
                put("cluster.name","es-6.0")
                .put("client.transport.sniff",true)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(node1);
        return client;
    }


}

