package com.chinaweal.lain.monitor.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 *
 * index - en(地域英文名) - cn(地域中文名) 三者映射关系
 */
public class AreaUtils {

    public static final ArrayList AREA_LIST = new ArrayList();
    public static final HashMap AREA_MAP = new HashMap();

    //index - en
    static {
        AREA_LIST.add("sj"); //省局
        AREA_LIST.add("zh"); //珠海
        AREA_LIST.add("st"); //汕头
        AREA_LIST.add("fs"); //佛山
        AREA_LIST.add("sg"); //韶关
        AREA_LIST.add("hy"); //河源
        AREA_LIST.add("mz"); //梅州
        AREA_LIST.add("hz"); //惠州
        AREA_LIST.add("sw"); //汕尾
        AREA_LIST.add("dg"); //东莞
        AREA_LIST.add("zs"); //中山
        AREA_LIST.add("yj"); //阳江
        AREA_LIST.add("zj"); //湛江
        AREA_LIST.add("mm"); //茂名
        AREA_LIST.add("zq"); //肇庆
        AREA_LIST.add("qy"); //清远
        AREA_LIST.add("cz"); //潮州
        AREA_LIST.add("jy"); //揭阳
        AREA_LIST.add("yf"); //云浮
        AREA_LIST.add("sd"); //顺德
        AREA_LIST.add("hq"); //横琴

    }

    static {
        AREA_MAP.put("sj", "省局");
        AREA_MAP.put("zh", "珠海");
        AREA_MAP.put("st", "汕头");
        AREA_MAP.put("fs", "佛山");
        AREA_MAP.put("sg", "韶关");
        AREA_MAP.put("hy", "河源");
        AREA_MAP.put("mz", "梅州");
        AREA_MAP.put("hz", "惠州");
        AREA_MAP.put("sw", "汕尾");
        AREA_MAP.put("dg", "东莞");
        AREA_MAP.put("zs", "中山");
        AREA_MAP.put("yj", "阳江");
        AREA_MAP.put("zj", "湛江");
        AREA_MAP.put("mm", "茂名");
        AREA_MAP.put("zq", "肇庆");
        AREA_MAP.put("qy", "清远");
        AREA_MAP.put("cz", "潮州");
        AREA_MAP.put("jy", "揭阳");
        AREA_MAP.put("yf", "云浮");
        AREA_MAP.put("sd", "顺德");
        AREA_MAP.put("hq", "横琴");
    }


    /**
     * 跟地域查找index
     *
     * @param area
     * @return
     */
    public static int areaIndex(String area) {
        int index = AREA_LIST.indexOf(area);
        return index;
    }

    /**
     * 根据index获取地域的中文表达
     *
     * @param index
     * @return
     */
    public static String cnName(int index) {
        return AREA_MAP.get(AREA_LIST.get(index)).toString();
    }
}
