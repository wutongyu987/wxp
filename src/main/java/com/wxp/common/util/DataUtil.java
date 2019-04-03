package com.wxp.common.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * create by ff on 2018/7/25
 */
public class DataUtil {
    public DataUtil() {
    }

    public static long getLongValue(Object obj) {
        if (obj == null) {
            return 0L;
        } else if (obj instanceof Long) {
            return ((Long)obj).longValue();
        } else {
            return obj.toString().equals("") ? 0L : (new Long(obj.toString())).longValue();
        }
    }

    public static int getIntegerValue(Object obj) {
        if (obj == null) {
            return 0;
        } else if (obj instanceof Integer) {
            return ((Integer)obj).intValue();
        } else {
            return obj.toString().equals("") ? 0 : (new Integer(obj.toString())).intValue();
        }
    }

    public static Long getLong(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Long) {
            return (Long)obj;
        } else {
            return obj.toString().equals("") ? null : new Long(obj.toString());
        }
    }

    public static Float getFloat(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Float) {
            return (Float)obj;
        } else {
            return obj.toString().equals("") ? null : new Float(obj.toString());
        }
    }

    public static Double getDoubleValue(Object obj) {
        if (obj == null) {
            return 0.0D;
        } else if (obj instanceof Double) {
            return (Double)obj;
        } else {
            return obj.toString().equals("") ? 0.0D : new Double(obj.toString());
        }
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (!(obj instanceof Date) && !(obj instanceof Timestamp)) {
            return obj.toString();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(obj);
        }
    }

    public static Boolean getBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean)obj;
        } else {
            return obj == null ? null : new Boolean(obj.toString());
        }
    }

    public static BigDecimal getBigDecimal(Object obj) {
        return obj == null ? null : new BigDecimal(obj.toString());
    }

    public static java.util.Date getUtilDate(Object obj) {
        return obj == null ? null : (Timestamp)obj;
    }

    public static Float getFloatValue(Object obj) {
        if (obj == null) {
            return 0.0F;
        } else if (obj instanceof Float) {
            return (Float)obj;
        } else {
            return obj.toString().equals("") ? 0.0F : new Float(obj.toString());
        }
    }

    public static String array2String(String[] arr) {
        if (arr == null) {
            return "";
        } else {
            String str = "";

            for(int i = 0; i < arr.length; ++i) {
                if (i > 0) {
                    str = str + ",";
                }

                str = str + arr[i];
            }

            return str;
        }
    }
}
