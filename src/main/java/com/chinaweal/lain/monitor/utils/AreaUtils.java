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
        AREA_LIST.add("sj"); //ʡ��
        AREA_LIST.add("zh"); //�麣
        AREA_LIST.add("st"); //��ͷ
        AREA_LIST.add("fs"); //��ɽ
        AREA_LIST.add("sg"); //�ع�
        AREA_LIST.add("hy"); //��Դ
        AREA_LIST.add("mz"); //÷��
        AREA_LIST.add("hz"); //����
        AREA_LIST.add("sw"); //��β
        AREA_LIST.add("dg"); //��ݸ
        AREA_LIST.add("zs"); //��ɽ
        AREA_LIST.add("yj"); //����
        AREA_LIST.add("zj"); //տ��
        AREA_LIST.add("mm"); //ï��
        AREA_LIST.add("zq"); //����
        AREA_LIST.add("qy"); //��Զ
        AREA_LIST.add("cz"); //����
        AREA_LIST.add("jy"); //����
        AREA_LIST.add("yf"); //�Ƹ�
        AREA_LIST.add("sd"); //˳��
        AREA_LIST.add("hq"); //����

    }


    public static int areaIndex(String area){
        int index = AREA_LIST.indexOf(area);
        return index;
    }
}
