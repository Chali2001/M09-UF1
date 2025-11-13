import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {

    public int npass = 0;

    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        if (pw == null) pw = "";
        if (salt == null) salt = "";
        String input = pw + salt;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
        HexFormat hf = HexFormat.of();
        return hf.formatHex(digest);
    }

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        if (pw == null) pw = "";
        if (salt == null) salt = "";
        int iterations = 10000;
        int keyLength = 512;
        char[] passwordChars = pw.toCharArray();
        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        HexFormat hf = HexFormat.of();
        return hf.formatHex(hash);
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {

        String charset = "abcdefABCDEF1234567890!";
        npass = 0;

        int len = 6;  
        char[] pw = new char[len];

        if (search(0, len, pw, charset, alg, hash, salt)) {
            return new String(pw);
        }

        return null;
    }


    private boolean search(int pos, int len, char[] pw, String charset, String alg, String hash, String salt) throws Exception {

        if (pos == len) {
            npass++;
            String generated;
            if (alg.equals("SHA-512"))
                generated = getSHA512AmbSalt(new String(pw), salt);
            else
                generated = getPBKDF2AmbSalt(new String(pw), salt);

            return generated.equals(hash);
        }

        for (int i = 0; i < charset.length(); i++) {
            pw[pos] = charset.charAt(i);

            if (search(pos + 1, len, pw, charset, alg, hash, salt)) {
                return true;
            }
        }

        return false;
    }


    public String getInterval(long t1, long t2) {
        long ms = t2 - t1;
        long segons = ms / 1000;
        long milisegonds = ms % 1000;
        long minuts = segons / 60;
        segons = segons % 60;
        long hores = minuts / 60;
        minuts = minuts % 60;
        long dies = hores / 24;
        hores = hores % 24;

        return dies + " dies / " + hores + " hores / " + minuts + " minuts / " + segons + " segons / " + milisegonds + " milisegonds";
    }

    public static void main(String[] args) throws Exception {

        String salt = "qpoweirualskldfjz";
        String pw = "aaabF!";

        Hashes h = new Hashes();
        String[] aHashes = {
                h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt)
        };

        String pwTrobat = null;
        String[] algoritmes = {"SHA-512", "PBKDF2"};

        for (int i = 0; i < aHashes.length; i++) {

            System.out.printf("===========================\n");
            System.out.printf("Algorisme: %s\n", algoritmes[i]);
            System.out.printf("Hash: %s\n", aHashes[i]);
            System.out.printf("---------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n\n");

            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algoritmes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();

            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("\n");
        }
    }
}
