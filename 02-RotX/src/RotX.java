public class RotX {
    private static final char[] MINUSCULES = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    
    private static final char[] MAJUSCULES = transformacion(MINUSCULES);

    public static char[] transformacion(char[] MINUSCULES){
        char[] resultat = new char[MINUSCULES.length];
        for (int i = 0; i < MINUSCULES.length; i++){
            char caracter = Character.toUpperCase(MINUSCULES[i]);
            resultat[i] = caracter;
        }
        return resultat;
    }

    public static char desplaça(char c, int desplaçament) {
        for (int i = 0; i < MINUSCULES.length; i++){
            if(MINUSCULES[i] == c){
                int novaPosicio = (i + desplaçament) % MINUSCULES.length;
                if (novaPosicio < 0) novaPosicio +=  MINUSCULES.length;
                return MINUSCULES[novaPosicio];
            }
        }
        for (int i = 0; i < MAJUSCULES.length; i++){
            if(MAJUSCULES[i] == c) {
                int novaPosicio = (i + desplaçament) % MAJUSCULES.length;
                if (novaPosicio < 0) novaPosicio +=  MAJUSCULES.length;
                return MAJUSCULES[novaPosicio];
            }
        }
        return c;
    }
    public static String xifratRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++){
            char caracter = cadena.charAt(i);
            resultat += desplaça(caracter, desplaçament);
        }
        return resultat;
    }

    public static String desxifratRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++){
            char caracter = cadena.charAt(i);
            resultat += desplaça(caracter, -desplaçament);
        }
        return resultat;
    }
    
    public static void main(String[] args){
        System.out.println("Xifrat");
        System.out.println("-----");
        System.out.println("(0)-ABC                   => " + xifratRotX("ABC", 0));
        System.out.println("(2)-XYZ                   => " + xifratRotX("XYZ", 2));
        System.out.println("(4)-Hola, Mr. calçot      => " + xifratRotX("Hola, Mr. calçot", 4));
        System.out.println("(6)-Perdó, per tu què és? => " + xifratRotX("Perdó, per tu què és?", 6));
        System.out.println("Desxifrat");
        System.out.println("-----");
        System.out.println("(0)-ABC                   => " + desxifratRotX("ABC", 0));
        System.out.println("(2)-ZAÁ                   => " + desxifratRotX("ZAÁ", 2));
        System.out.println("(4)-Ïqoc, Óú. écoèqü      => " + desxifratRotX("Ïqoc, Óú. écoèqü", 4));
        System.out.println("(6)-Úiüht, úiü wx ùxì ív? => " + desxifratRotX("Úiüht, úiü wx ùxì ív?", 6));
    }




}
