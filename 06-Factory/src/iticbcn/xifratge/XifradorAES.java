package iticbcn.xifratge;
import java.security.SecureRandom;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class XifradorAES implements Xifrador{
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    private final int MIDA_IV = 16;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {

            SecureRandom sr = new SecureRandom();
            byte[] iv = new byte[MIDA_IV];
            sr.nextBytes(iv);

            byte[] clauEnBytes = clau.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
            byte[] clauHash = sha.digest(clauEnBytes);

            SecretKeySpec secretKey = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] msgEnBytes = msg.getBytes("UTF-8");
            byte[] msgXifrat = cipher.doFinal(msgEnBytes);

            byte[] ivIMsgXifrat = new byte[iv.length + msgXifrat.length];
            System.arraycopy(iv, 0, ivIMsgXifrat, 0, iv.length);
            System.arraycopy(msgXifrat, 0, ivIMsgXifrat, iv.length, msgXifrat.length);

            return new TextXifrat(ivIMsgXifrat);

        } catch (Exception e) {
            System.err.println("Error en el xifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            byte[] bIvIMsgXifrat = xifrat.getBytes();

            byte[] iv = new byte[MIDA_IV];
            byte[] msgXifrat = new byte[bIvIMsgXifrat.length - MIDA_IV];
            System.arraycopy(bIvIMsgXifrat, 0, iv, 0, MIDA_IV);
            System.arraycopy(bIvIMsgXifrat, MIDA_IV, msgXifrat, 0, msgXifrat.length);

            byte[] clauEnBytes = clau.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance(ALGORISME_HASH);
            byte[] clauHash = sha.digest(clauEnBytes);

            SecretKeySpec secretKey = new SecretKeySpec(clauHash, ALGORISME_XIFRAT);

            Cipher cipher = Cipher.getInstance(FORMAT_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

            byte[] msgDesxifrat = cipher.doFinal(msgXifrat);

            return new String(msgDesxifrat, "UTF-8");

        } catch (Exception e) {
            System.err.println("Error en el desxifrat AES: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }
}