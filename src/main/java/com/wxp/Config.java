package com.wxp;

/**
 * author: ff
 * date: 2017/1/10
 * description:
 */
public class Config {

    /**
     * 服务器地址
     */
    public final static String SERVER = "https://www.xingyuanji.com/";
    /**
     * 上传的图片的存放地址
     */
    public final static String UPLOAD_DIR = "E:/uuid/";
//    public final static String UPLOAD_DIR = "/opt/apache-tomcat-9.0.2/webapps/upload/";
//    public final static String UPLOAD_DIR = "C:/Users/Administrator/Desktop/apache-tomcat-8.5.31/webapps/upload/";

    public final static String[] djList = new String[]{ "Tt8caFAC3Y4WS4V35xv-HJy3PVmdMti-rp4B13COJuQ"};
//            "Tt8caFAC3Y4WS4V35xv-HFJvTJGq13dFHcOGaE4hiqY"
//            ,"Tt8caFAC3Y4WS4V35xv-HDMewl-bSxUDs80Ag1HnH5E"
//            ,"Tt8caFAC3Y4WS4V35xv-HLBQysCaA5IyWF_RI7sOFpw"
//            ,"Tt8caFAC3Y4WS4V35xv-HPikjVXJNJBpzpxItdtQ30k"};
    /**
     * 图片的访问地址,软连接地址
     */
    public final static String RESOURCE_DIR = "upload/";
    //sony黑卡M3
    public final static Integer SONY_M3=0;
    public final static double SONY_M3_PROBABILITY=0.000011;
    //GOPRO
    public final static Integer GOPRO=1;
    public final static double GOPRO_PROBABILITY=0.000018;
    //奖品：SWITCH游戏机
    public final static Integer SWITCH_GAMEBOX=2;
    public final static double SWITCH_GAMEBOX_PROBABILITY=0.000033;
    //奖品：BOSE蓝牙耳机
    public final static Integer BOSE_EARPHONE=3;
    public final static double BOSE_EARPHONE_PROBABILITY=0.00004;
    //奖品：NARS口红礼盒
    public final static Integer NARS_LIPSTICK_BOX=4;
    public final static double NARS_LIPSTICK_BOX_PROBABILITY=0.000062;
    //奖品：APM星球耳环
    public final static Integer APM_EARRINGS=5;
    public final static double APM_EARRINGS_PROBABILITY=0.000111;
    //奖品：黛珂牛油果套装
    public final static Integer DAIKE_AVOCADO=6;
    public final static double DAIKE_AVOCADO_PROBABILITY=0.000071;
    //奖品：蒂芙尼钻石香氛
    public final static Integer TIFFANY_DIAMOND_FRAGRANCE=7;
    public final static double TIFFANY_DIAMOND_FRAGRANCE_PROBABILITY=0.0001;
    //奖品：SK2洗面奶
    public final static Integer SK2_FACECLEANER=8;
    public final static double SK2_FACECLEANER_PROBABILITY=0.0002;
    //奖品：TF口红
    public final static Integer TF_LIPSTICK=9;
    public final static double TF_LIPSTICK_PROBABILITY=0.000122;
    //奖品：CPB口红唇釉
    public final static Integer CPB_LIPSTICK=10;
    public final static double CPB_LIPSTICK_PROBABILITY=0.000133;
    //奖品：JBL蓝牙音箱
    public final static Integer JBL_BLUESPEEKER=11;
    public final static double JBL_BLUESPEEKER_PROBABILITY=0.000333;
    //奖品：膳魔师保温杯
    public final static Integer SHANMOSHI_VACUUMCUP=12;
    public final static double SHANMOSHI_VACUUMCUP_PROBABILITY=0.000189;
    //奖品：星巴克礼品卡
    public final static Integer STARBARKS_GIFTCARD=13;
    public final static double STARBARKS_GIFTCARD_PROBABILITY=0.000220;
    //奖品：HOLIKA眼影
    public final static Integer HOLIKA_EYESSHADOW=14;
    public final static double HOLIKA_EYESSHADOW_PROBABILITY=0.000667;
    //奖品：LINE_FRIENDS桌面垃圾桶
    public final static Integer LINEFRIENDS_TRASH=15;
    public final static double LINEFRIENDS_TRASH_PROBABILITY=0.0009;
    //奖品：网红零食抱枕
    public final static Integer WANGHONG_BOLSTER=16;
    public final static double WANGHONG_BOLSTER_PROBABILITY=0.001233;
    //奖品：马克图布仙人掌加湿器
    public final static Integer MARKTUBU_HUMIDIFIER=17;
    public final static double MARKTUBU_HUMIDIFIER_PROBABILITY=0.000889;
    //奖品：资生堂尿素护手霜
    public final static Integer ZISHENGTANG_HANDRCREAM=18;
    public final static double ZISHENGTANG_HANDRCREAM_PROBABILITY=0.001333;
    //奖品：网红玻尿酸鸭
    public final static Integer WANGHONG_DUCK=19;
    public final static double WANGHONG_DUCK_PROBABILITY=0.001778;
    //奖品：红包88元
    public final static Integer REDPACKET_88=20;
    public final static double REDPACKET_88_PROBABILITY=0.001333;
    //奖品：红包66
    public final static Integer REDPACKET_36_6=21;
    public final static double REDPACKET_36_6_PROBABILITY=0.001333;
    //奖品：红包18_8
    public final static Integer REDPACKET_18_8=22;
    public final static double REDPACKET_18_8_PROBABILITY=0.002222;
    //奖品：红包16_6
    public final static Integer REDPACKET_16_6=23;
    public final static double REDPACKET_16_6_PROBABILITY=0.002667;
    //奖品：红包8_8
    public final static Integer REDPACKET_8_8=24;
    public final static double REDPACKET_8_8_PROBABILITY=0.004444;
    //奖品：红包6_6
    public final static Integer REDPACKET_6_6=25;
    public final static double REDPACKET_6_6_PROBABILITY=0.005556;
    //奖品：红包1_88
    public final static Integer REDPACKET_1_88=26;
    public final static double REDPACKET_1_88_PROBABILITY=0.006667;
    //奖品：红包1_66
    public final static Integer REDPACKET_1_66=27;
    public final static double REDPACKET_1_66_PROBABILITY=0.008889;
    //奖品：红包0_88
    public final static Integer REDPACKET_0_88=28;
    public final static double REDPACKET_0_88_PROBABILITY=0.355556;
    //奖品：红包0_66
    public final static Integer REDPACKET_0_66=29;
    public final static double REDPACKET_0_66_PROBABILITY=0.602889;


    public final static String JIN_YONG = "0";
    //公众号secret  ed3a5dbc1aafc037f9ecc1eccfe0a7e7

//    个人
//    public final static String APPID="wx4bdabda27edf13ef";
//    public final static String SECRET="d90700dd7f7662ada79df172cf0bb4c5";
//    心愿机
    public final static String APPID="wx67cadbc58a45b2cd";
    public final static String SECRET="30e40b8d6cb6e62e6c16554e31d50148";

//个人图灵机器人接口
    public final static String TLUSERINFO= "{\n" +
        "        \"apiKey\": \"a45e59ab26fe4b86a2b71b4812417e65\",\n" +
        "        \"userId\": \"5a0a67ab21a89344\"\n" +
        "    }";



}
