package com.chinaweal.lain.monitor.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Lain
 * Date: 15-12-8
 *
 * index - en(����Ӣ����) - cn(����������) ����ӳ���ϵ
 */
public class AreaUtils {

    public static final ArrayList AREA_LIST = new ArrayList();
    public static final HashMap AREA_MAP = new HashMap();

    //index - en
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

    static {
        AREA_MAP.put("sj", "ʡ��");
        AREA_MAP.put("zh", "�麣");
        AREA_MAP.put("st", "��ͷ");
        AREA_MAP.put("fs", "��ɽ");
        AREA_MAP.put("sg", "�ع�");
        AREA_MAP.put("hy", "��Դ");
        AREA_MAP.put("mz", "÷��");
        AREA_MAP.put("hz", "����");
        AREA_MAP.put("sw", "��β");
        AREA_MAP.put("dg", "��ݸ");
        AREA_MAP.put("zs", "��ɽ");
        AREA_MAP.put("yj", "����");
        AREA_MAP.put("zj", "տ��");
        AREA_MAP.put("mm", "ï��");
        AREA_MAP.put("zq", "����");
        AREA_MAP.put("qy", "��Զ");
        AREA_MAP.put("cz", "����");
        AREA_MAP.put("jy", "����");
        AREA_MAP.put("yf", "�Ƹ�");
        AREA_MAP.put("sd", "˳��");
        AREA_MAP.put("hq", "����");
    }


    /**
     * ���������index
     *
     * @param area
     * @return
     */
    public static int areaIndex(String area) {
        int index = AREA_LIST.indexOf(area);
        return index;
    }

    /**
     * ����index��ȡ��������ı��
     *
     * @param index
     * @return
     */
    public static String cnName(int index) {
        return AREA_MAP.get(AREA_LIST.get(index)).toString();
    }
}
