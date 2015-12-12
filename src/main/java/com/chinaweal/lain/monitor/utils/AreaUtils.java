package com.chinaweal.lain.monitor.utils;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 */
public class AreaUtils {

    public static final ArrayList AREA_LIST = new ArrayList();

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


    public static int areaIndex(String area){
        int index = AREA_LIST.indexOf(area);
        return index;
    }
}
