package tech.soulcoder;


import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yunfeng.lu on 2017/10/3
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNil(Object src) {
        return src == null || "".equals(src.toString().trim());
    }

    public static boolean isNil(Collection<Object> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNil(Map<Object, Object> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNilWithoutTrim(Object src) {
        return src == null || "".equals(src.toString());
    }

    public static String nullToString(Object src) {
        return src == null ? "" : src.toString().trim();
    }

    public static String nullToStringWithoutTrim(Object src) {
        return src == null ? "" : src.toString();
    }

    public static Integer objToInt(Object src) {
        return isNil(src) ? null : (Double.valueOf(nullToString(src))).intValue();
    }

    public static Integer objToInt(Object src, int defaultInt) {
        return isNil(src) ? defaultInt : (Double.valueOf(nullToString(src))).intValue();
    }

    public static Double objToNum(Object src) {
        return isNil(src) ? null : (Double.valueOf(nullToString(src)));
    }

    public static Double objToNum(Object src, Double defaultNum) {
        return isNil(src) ? defaultNum : (Double.valueOf(nullToString(src)));
    }

    public static Double sumDouble(String... d) {
        BigDecimal bd = new BigDecimal("0");
        for (int i = 0; i < d.length; i++) {
            bd = bd.add((new BigDecimal(d[i])));
        }
        return bd.doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static String nullToZero(Object amt) {
        if (amt == null)
            return "0";
        return (String) amt;
    }

    public static String strYuanToFen(Object yuan) {
        String fen = isNil(yuan) ? "0" : yuan.toString().trim();
        double moneyI = Double.parseDouble(fen);
        return String
                .valueOf(Integer.parseInt(new java.text.DecimalFormat("0").format(moneyI * 100)));
    }

    public static String strFenToYuan(Object fen) {
        String yuan = isNil(fen) ? "0" : fen.toString().trim();
        double moneyI = Double.parseDouble(yuan);
        return String.valueOf(moneyI / 100);
    }

    /**
     * @param text        报文信息 填充位置 0在前 1在后
     * @param msgLenRange
     * @param msgLenSize  报文长度的长度
     */
    public static int getLen(String text, int msgLenRange, int msgLenSize) {
        if (text != null) {
            int len;
            try {
                len = text.getBytes("utf-8").length;
                if (msgLenRange > 0)
                    len += msgLenSize;
                return len;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 把16进制字符串转换成字节数组
     *
     * @return
     */
    public static byte[] hex2byte(byte[] b) {
        if (b == null)
            return null;
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static final String bytesToHexString(byte[] bArray) {
        if (bArray == null)
            return null;
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] writeInt32(int i) {
        byte[] dataBuffer = new byte[4];
        dataBuffer[0] = (byte) (i & 255);
        dataBuffer[1] = (byte) (i >>> 8 & 255);
        dataBuffer[2] = (byte) (i >>> 16 & 255);
        dataBuffer[3] = (byte) (i >>> 24 & 255);
        return dataBuffer;
    }

    public static int readInt32(byte[] dataBuffer) {
        int pos = 0;
        return (dataBuffer[pos] & 255) + ((dataBuffer[pos + 1] & 255) << 8)
                + ((dataBuffer[pos + 2] & 255) << 16) + ((dataBuffer[pos + 3] & 255) << 24);
    }

    /**
     * 按长度补足字符数据项
     *
     * @param needlen   报文长度规定的字符数
     * @param pack      填充字符
     * @param direction 填充位置 0在前 1在后
     * @return
     */
    public static String replenishString(Object obj, Integer needlen, char pack, int direction) {
        String text = nullToString(obj);
        StringBuffer sb = new StringBuffer(text);
        if (needlen == null || needlen <= 0) {
            return text;
        }
        if (text.length() > needlen) {
            sb.delete(needlen, sb.length());
        } else {
            if (direction > 0) {
                while (sb.length() < needlen) {
                    sb.append(pack);
                }
            } else {
                while (sb.length() < needlen) {
                    sb.insert(0, pack);
                }
            }
        }
        return sb.toString();
    }

    public static Object trimItem(Object obj, char pack, int direction) {
        if (obj instanceof String || obj instanceof StringBuffer || obj instanceof StringBuilder) {
            int index = 0;
            String text = nullToString(obj);
            char[] charArray = text.toCharArray();
            StringBuffer sbf = new StringBuffer(text);
            if (sbf.length() < 1)
                return "";
            if (direction > 0) { // 001100
                for (int i = charArray.length - 1; i >= 0; i--) {
                    if (charArray[i] != pack) {
                        index = i;
                        break;
                    }
                }
                sbf.delete(++index, sbf.length());
            } else {
                for (int i = 0; i < charArray.length; i++) {
                    if (charArray[i] != pack) {
                        index = i;
                        break;
                    }
                }

                sbf.delete(0, index);
            }
            return sbf.toString();
        } else {
            return obj;
        }
    }

    public static String replenishNum(int text, int needlen) {
        StringBuffer sb = new StringBuffer(text);
        while (sb.length() < needlen) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static boolean findStr(String[] subStr, String str) {
        for (int i = 0; i < subStr.length; i++) {
            if (subStr[i].equals(str))
                return true;
        }
        return false;
    }

    public static int findNumInStr(String source, String regexNew) {
        String regex = "[a-zA-Z]+";
        if (regexNew != null && !regexNew.equals("")) {
            regex = regexNew;
        }
        Pattern expression = Pattern.compile(regex);
        Matcher matcher = expression.matcher(source);
        TreeMap<Object, Integer> myTreeMap = new TreeMap<Object, Integer>();
        int n = 0;
        Object word = null;
        Object num = null;
        while (matcher.find()) {
            word = matcher.group();
            n++;
            if (myTreeMap.containsKey(word)) {
                num = myTreeMap.get(word);
                Integer count = (Integer) num;
                myTreeMap.put(word, new Integer(count.intValue() + 1));
            } else {
                myTreeMap.put(word, new Integer(1));
            }
        }
        return n;
    }

    public static String replaceStr(String source, Map<String, Object> param) throws Exception {
        // return replaceStr("\\$\\{(.*?)\\}",source,param);
        return replaceStr("${mapValue}", source, param);
    }

    public static String replaceStr(String regex, String source,
                                    Map<String, Object> param) throws Exception {
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String aaa = regex.replace("mapValue", entry.getKey());
            source = source.replace(aaa, nullToString(entry.getValue()));
        }
        return source;
    }

    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static String str2Unicode(String str) {
        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {

            // 取出每一个字符
            char c = str.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    public static String unicode2str(String s) {
        List<String> list = new ArrayList<String>();
        String zz = "\\\\u[0-9,a-z,A-Z]{4}";

        // 正则表达式用法参考API
        Pattern pattern = Pattern.compile(zz);
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            list.add(m.group());
        }
        for (int i = 0, j = 2; i < list.size(); i++) {
            String st = list.get(i).substring(j, j + 4);

            // 将得到的数值按照16进制解析为十进制整数，再強转为字符
            char ch = (char) Integer.parseInt(st, 16);
            // 用得到的字符替换编码表达式
            s = s.replace(list.get(i), String.valueOf(ch));
        }
        return s;
    }

    /**
     * 转换下划线命名方式为驼峰方式
     *
     * @param key
     * @return
     */
    public static String toUnderScore(String key) {
        if (isNil(key)) {
            return key;
        }
        if (key.equals(key.toUpperCase())) {
            return key.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(key);
        for (int i = 1; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c += 32;// 转换成小写
                sb.deleteCharAt(i);
                sb.insert(i, c);
                sb.insert(i, "_");
                i++;
            }
        }
        return sb.toString();
    }

    public static void mapToCamel(Map<String, Object> map) {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            Object value = map.get(key);
            if (value == null) {
                map.remove(key);
                continue;// 移除null值
            }
            if (value instanceof Map) {
                mapToCamel((Map) value);
            } else if (value instanceof List) {
                List eleList = (List) value;
                for (int j = 0; j < eleList.size(); j++) {
                    if (eleList.get(j) instanceof Map) {
                        mapToCamel((Map) eleList.get(j));
                    }
                }
            }
            String camelKey = toCamel(key);
            map.remove(key);
            map.put(camelKey, value);
        }
    }

    public static void mapToUnderscore(Map<String, Object> map) {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            Object value = map.get(key);
            if (value == null) {
                map.remove(key);
                continue;// 移除null值
            }
            if (value instanceof Map) {
                mapToUnderscore((Map) value);
            } else if (value instanceof List) {
                List eleList = (List) value;
                for (int j = 0; j < eleList.size(); j++) {
                    if (eleList.get(j) instanceof Map) {
                        mapToUnderscore((Map) eleList.get(j));
                    }
                }
            }
            String underscoreKey = toUnderScore(key);
            map.remove(key);
            map.put(underscoreKey, value);
        }
        keyList.clear();
        keyList = null;
    }

    public static void mapKeyToUpCase(Map<String, Object> map) {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            Object value = map.get(key);
            if (value == null) {
                map.remove(key);
                continue;// 移除null值
            }
            if (value instanceof Map) {
                mapKeyToUpCase((Map) value);
            } else if (value instanceof List) {
                List eleList = (List) value;
                for (int j = 0; j < eleList.size(); j++) {
                    if (eleList.get(j) instanceof Map) {
                        mapKeyToUpCase((Map) eleList.get(j));
                    }
                }
            }
            map.remove(key);
            map.put(key.toUpperCase(), value);
        }
    }

    /**
     * 转换下划线命名方式为驼峰方式
     *
     * @param key
     * @return
     */
    public static String toCamel(String key) {
        if (isNil(key)) {
            return key;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(key);
        for (int i = 1; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '_') {
                c = sb.charAt(i + 1);
                sb.delete(i, i + 2);
                if (c >= 'a' && c <= 'z') {
                    c -= 32;// 转换成大写
                }
                sb.insert(i, c);
            }
        }
        return sb.toString();
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * 邮箱工具 获取邮箱做对应的服务商
     */
    public static String email2Url(String email) {
        if (email != null && email.contains("@")) {
            if (email.contains("qq.com")) {
                return "https://mail.qq.com";
            } else if (email.contains("163.com")) {
                return "https://mail.163.com";
            } else if (email.contains("gmail.com")) {
                return "https://mail.google.com";
            } else {
                return null;
            }

        } else {
            return null;
        }
    }


    public static boolean isAllDigit(String strNum) {
        if (strNum != null) {
            // 去除收尾空格
            strNum=strNum.trim();
        }
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String str="";
        System.out.println(isAllDigit("45  45  "));


    }

}