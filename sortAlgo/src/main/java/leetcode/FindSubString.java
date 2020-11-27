package leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-26 15:46
 **/
public class FindSubString {

    public static void main(String[] args){
        String str = "goodgoogle";
        String subStr = "google";
        System.out.println(findSubStr(str, subStr));
    }

    private static boolean findSubStr(String str, String subStr) {

        int cur = 0;
        boolean contains = false;
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == subStr.charAt(cur)){
                cur++;
            }else {
                cur = 0;
            }
            if (cur == subStr.length() - 1) {
                contains = true;
                break;
            }
        }

        if (cur == subStr.length() - 1) {
            contains = true;
        }

        return contains;
    }
}
