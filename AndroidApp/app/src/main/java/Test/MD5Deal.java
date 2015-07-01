package Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2015/6/30.
 */
public class MD5Deal {

    public static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    public static String convetToMD5(String str)
    {
        String result = "";
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = str.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();

            result = byteArrayToHex(resultByteArray);
        }
        catch (NoSuchAlgorithmException e)
        {

        }

        return result;
    }
}
