package cn.white.bysj.commons;

/**
 * Create by @author white
 *
 * @date 2017-12-28 14:57
 */
public class Constant {

    //发送邮件的邮箱，要与application.properties中的一致
    public static final String MAIL_FROM = "up_white0211@163.com";

    //域名
    public static final String DOMAIN_NAME = "http://127.0.0.1:8082";

    //秒滴科技相关参数
    public static final String QUERY_PATH = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
    public static final String ACCOUNT_SID = "c24a16dfffdf46e293ce3212faad2cff";
    public static final String AUTH_TOKEN = "cd95fc6a054142ea8034d120ddc52baa";

    //session相关参数
    public static  final String CURRENT_USER = "user";
    public static final String CURRENT_USER_ACCOUNT ="account";
    public static final String CURRENT_USER_ID = "cn_user_id";

    //七牛云相关配置
    public static final String QINIU_ACCESS_KEY ="R2s07DxzycnzR7gNbgf9-6zSDdXqQ7VAE4e7C1tj";
    public static final String QINIU_SECRET_KEY ="BDMFX3QIr67ERsv7fsBbGvZxSgzE6tUyCx4H-na2";
    public static final String QINIU_BUCKET_NAME ="test";
    public static final String QINIU_DOMAIN_NAME = "http://oxdypxsie.bkt.clouddn.com/";

    //es上的索引与类型参数
    public static final String ES_INDEX_NAME = "bysj";
    public static final String ES_TYPE_NAME = "note";

}
