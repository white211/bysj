package cn.white.bysj.utils.shieldWord;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * <p>
 * Create by @author white
 *
 * @date 2018-04-19 17:02
 */
public class ShieldInit {

    //字符编码
    private String ENCODING = "GBK";

    @SuppressWarnings("rawtypes")
    public HashMap shieldMap;

    public ShieldInit() {
        super();
    }

    /**
     * 初始化敏感词 转化为map
     *
     * @param set
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Map initKeyWord(Set<String> set) {
        try {
            //将敏感词库加入到HashMap中
            addShieldToHashMap(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shieldMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     * isEnd = 0
     * 国 = {<br>
     * isEnd = 1
     * 人 = {isEnd = 0
     * 民 = {isEnd = 1}
     * }
     * 男  = {
     * isEnd = 0
     * 人 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     * 五 = {
     * isEnd = 0
     * 星 = {
     * isEnd = 0
     * 红 = {
     * isEnd = 0
     * 旗 = {
     * isEnd = 1
     * }
     * }
     * }
     * }
     *
     * @param keyWordSet
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addShieldToHashMap(Set<String> keyWordSet) {
        //初始化敏感词容器，减少扩容操作
        shieldMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            //关键字
            key = iterator.next();
            nowMap = shieldMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //获取
                Object wordMap = nowMap.get(keyChar);
                //如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }



}
