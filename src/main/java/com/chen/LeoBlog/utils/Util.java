package com.chen.LeoBlog.utils;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    static void initSnow(){
        // 创建 IdGeneratorOptions 对象，请在构造函数中输入 WorkerId：
        short workerId = 1;
        IdGeneratorOptions options = new IdGeneratorOptions(workerId);
        // options.WorkerIdBitLength = 10; // WorkerIdBitLength 默认值6，支持的 WorkerId 最大值为2^6-1，若 WorkerId 超过64，可设置更大的 WorkerIdBitLength
        // ...... 其它参数设置参考 IdGeneratorOptions 定义，一般来说，只要再设置 WorkerIdBitLength （决定 WorkerId 的最大值）。

        // 保存参数（必须的操作，否则以上设置都不能生效）：
        YitIdHelper.setIdGenerator(options);
        // 以上初始化过程只需全局一次，且必须在第2步之前设置。
    }
    /**
     * 获取id，从时间戳截取得到的id
     *根据雪花漂移算法，得出longId
     * @return
     */
    public static int getId() {
        // 初始化以后，即可在任何需要生成ID的地方，调用以下方法：
        long newId = YitIdHelper.nextId();
//        System.out.println("生成的ID为"+newId);
//        System.out.println((int) newId);
        return (int) newId;

//        Date date = new Date();
//        long time = date.getTime();
//        return Math.toIntExact((time / 1000) % 1000000000);
    }

    /**
     * 根据雪花漂移算法得出longID，截取前六个，转为整数。
     * @return
     */
    public static  int getConfirmCode(){
        long newId = YitIdHelper.nextId();
        String codeStr = String.valueOf(newId).substring(0, 6);
        return Integer.parseInt(codeStr);
    }

    /**
     * 格式化时间，返回字符串。
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @return
     */
    public static String timeFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
