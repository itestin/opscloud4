package com.baiyi.opscloud.datasource.util;

import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author baiyi
 * @Date 2021/6/18 5:07 下午
 * @Version 1.0
 */
public class AssetUtil {

    public static boolean equals(String var1, String var2) {
        if (StringUtils.isEmpty(var1))
            return StringUtils.isEmpty(var2);
        return var1.equals(var2);
    }

    public static boolean equals(Date date1, Date data2) {
        if (date1 == null)
            return data2 == null;
        return date1.equals(data2);
    }
}