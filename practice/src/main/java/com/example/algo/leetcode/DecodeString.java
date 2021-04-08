package com.example.algo.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-03 10:22
 **/
public class DecodeString {

    /**
     * @param s 3[a2[c]]
     * @return
     */
    public static String decodeString(String s) {
        if (s == null || s.length() == 0) return "";

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            String strVal = String.valueOf(c);


            if (c >= '1' && c <= '9'){
                StringBuilder numBuilder = new StringBuilder();
                numBuilder.append(c);

                i++;
                while (s.charAt(i) >= '0' && s.charAt(i) <= '9'){
                    numBuilder.append(s.charAt(i));
                    i++;
                }

                int num = Integer.valueOf(numBuilder.toString());

                if (s.charAt(i) != '[') {
                    throw new IllegalArgumentException("illegal input string : " + s);
                }
                i++;

                int startIdx = i;
                // expect meet symbol ']' count
                int expectCount = 1;
                for (; expectCount > 0; i++){
                    char e = s.charAt(i);
                    if (e == '['){
                        expectCount++;
                    }else if (e == ']'){
                        expectCount--;
                    }
                }

                builder.append(repeatStr(num, s.substring(startIdx, i - 1)));
                i--;
            }else {
                builder.append(strVal);
            }
        }

        return builder.toString();
    }

    private static String repeatStr(int num, String substring) {

        String decodeStr = decodeString(substring);
        StringBuilder builder = new StringBuilder();
        while (num > 0){
            builder.append(decodeStr);
            num--;
        }
        return builder.toString();
    }


    public static void main(String[] args){
        System.out.println(decodeString("3[a2[c]]"));
    }
}
