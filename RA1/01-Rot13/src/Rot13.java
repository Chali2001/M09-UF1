public class Rot13 {

    private static final char[] MINUSCULES = "aàáäbcçdeèéëfghiìíïjklmnñoòóöpqrstuùúüvwxyz".toCharArray();


    private static final char[] MAJUSCULES = "AÀÁÄBCÇDEÈÉËFGHIÌÍÏJKLMNÑOÒÓÖPQRSTUÙÚÜVWXYZ".toCharArray();

    public static char desplaça(char c, int desplaçament) {
    
        for (int i = 0; i < MINUSCULES.length; i++) {
            if (MINUSCULES[i] == c) {
                int novaPosicio = (i + desplaçament) % MINUSCULES.length;
                if (novaPosicio < 0) novaPosicio += MINUSCULES.length;
                return MINUSCULES[novaPosicio];
            }
        }

        for (int i = 0; i < MAJUSCULES.length; i++) {
            if (MAJUSCULES[i] == c) {
                int novaPosicio = (i + desplaçament) % MAJUSCULES.length;
                if (novaPosicio < 0) novaPosicio +=  MAJUSCULES.length;
                return MAJUSCULES[novaPosicio];

            }
        }
        return c;
}

        public static String xifratRot13(String cadena) {
            String resultat = "";

            for (int i = 0; i < cadena.length(); i++) {
                char caracter = cadena.charAt(i);
                resultat += desplaça(caracter, 13);
            }

            return resultat;
        }
        
        public static String descifratRot13(String cadena) {
            String resultat = "";
            for (int i = 0; i < cadena.length(); i++) {
                char caracter = cadena.charAt(i);
                resultat += desplaça(caracter, -13);
            }
            return resultat;
        }
        public static void main(String[] args) {
            System.out.println("Xifrat");
            System.out.println("---------");
            System.out.println("ABC => " + xifratRot13("ABC"));
            System.out.println("abc => " + xifratRot13("abc"));
            System.out.println("Hola, Mr. calçot => " + xifratRot13("Hola, Mr. calçot"));
            System.out.println("Perdó, per tu què és? => " + xifratRot13("Perdó, per tu què és?"));
            System.out.println();
            System.out.println("Desxifrat");
            System.out.println("---------");
            System.out.println("GÍÏ=> " + descifratRot13("GÍÏ"));
            System.out.println("gíï => " + descifratRot13("gíï"));
            System.out.println("Óvug, Ùà. ïgujvä => " + descifratRot13("Óvug, Ùà. ïgujvä"));
            System.out.println("Zlàkx, zlà äb abm ná? => " + descifratRot13("Zlàkx, zlà äb abm ná?"));
        }
    }