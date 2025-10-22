import java.security.SecureRandom;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "ITICBCNdam2025";

    public static byte[] xifraAES(String msg, String password) throws Exception {
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        byte[] clauEnBytes = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] clauHash = sha.digest(clauEnBytes);
        SecretKeySpec clau = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

        byte[] msgEnBytes = msg.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, clau, ivParameterSpec);
        byte[] msgXifrat = cipher.doFinal(msgEnBytes);

        byte[] ivIMsgXifrat = new byte[iv.length + msgXifrat.length];
        System.arraycopy(iv, 0, ivIMsgXifrat, 0 , iv.length);
        System.arraycopy(msgXifrat, 0, ivIMsgXifrat, iv.length, msgXifrat.length);

        return ivIMsgXifrat;
    }

    public static String desxifraAES(byte[] bIvIMsgXifrat, String password) throws Exception {
        byte[] iv = new byte[MIDA_IV];
        byte[] msgXifrat = new byte[bIvIMsgXifrat.length - MIDA_IV];
        System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
        System.arraycopy(bIvIMsgXifrat, MIDA_IV, msgXifrat, 0, msgXifrat.length);
        byte[] clauEnBytes = password.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] clauHash = sha.digest(clauEnBytes);
        SecretKeySpec clau = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, clau, ivSpec);
        byte[] msgDesxifrat = cipher.doFinal(msgXifrat);
        return new String(msgDesxifrat, "UTF-8");
    }

    public static void main(String[] args) {
        String msgs[] = {
            "Lorem ipsum dicet",
            "Hola Andrés cómo está tu cuñado",
            "Àgora illa Òtto"
        };

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];
            byte[] bXifrats = null;
            String desxifrat = "";

            try {

                bXifrats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifrats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: " + e.getLocalizedMessage());
            }

            System.out.println("----------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + Base64.getEncoder().encodeToString(bXifrats));
            System.out.println("Dec: " + desxifrat);
        }
    }
}