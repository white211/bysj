package cn.white.bysj.utils.shieldWord;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**敏感词过滤
 * Create by @author white
 *
 * @date 2018-04-19 16:55
 */
public class ShieldFilter {

    @SuppressWarnings("rawtypes")
    private Map sensitiveWordMap = null;

    //最小匹配规则
    public static int minMatchTYpe = 1;
    //最大匹配规则
    public static int maxMatchType = 2;

    /**
     * 构造函数，初始化敏感词库
     */
    public ShieldFilter(Set<String> set){
        sensitiveWordMap = new ShieldInit().initKeyWord(set);
    }

    /**
     * 判断文字中是否包含敏感词
     * @param txt
     * @param matchType
     * @return
     */
    public boolean isContaintSensitiveWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            //判断是否包含敏感字符
            int matchFlag = this.CheckShield(txt, i, matchType);
            //大于0存在，返回true
            if(matchFlag > 0){
                flag = true;
            }
        }
        return flag;
    }

    /**
     *获取文字中的敏感词
     * @param txt
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * 比如说我是中国人，铭感词库里面有“中国”、“中国人”，最小规则就变成了：我是**人、最大规则就是：我是***。
     * 所以最小规则就是：找到敏感词就结束，最大规则就是：找到最底层的那个敏感词。两个深度不一样！
     * @return
     */
    public Set<String> getShield(String txt , int matchType){
        Set<String> shieldList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            //判断是否包含敏感字符
            int length = CheckShield(txt, i, matchType);
            //存在,加入list中
            if(length > 0){
                shieldList.add(txt.substring(i, i+length));
                //减1的原因，是因为for会自增
                i = i + length - 1;
            }
        }

        return shieldList;
    }

    /**
     * 替换敏感词
     * @param txt
     * @param matchType
     * @param replaceChar
     * @return 默认为*
     */
    public String replaceShield(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        //获取所有的敏感词
        Set<String> set = getShield(txt, matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     * @param replaceChar
     * @param length
     * @return
     */
    private String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 判断文字中是否包括敏感词汇
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
     @SuppressWarnings({ "rawtypes"})
    public int CheckShield(String txt,int beginIndex,int matchType){
        //敏感词结束标识位：用于敏感词只有1位的情况
        boolean  flag = false;
        //匹配标识数默认为0
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            //获取指定key
            nowMap = (Map) nowMap.get(word);
            //存在，则判断是否为最后一个
            if(nowMap != null){
                //找到相应key，匹配标识+1
                matchFlag++;
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if("1".equals(nowMap.get("isEnd"))){
                    //结束标志位为true
                    flag = true;
                    //最小规则，直接返回,最大规则还需继续查找
                    if(ShieldFilter.minMatchTYpe == matchType){
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        //长度必须大于等于1，为词
        if(matchFlag < 2 || !flag){
            matchFlag = 0;
        }
        return matchFlag;
    }



}
