package iticbcn.xifratge;
public class XifradorRotX implements Xifrador {
    private static final char[] MINUSCULES = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz".toCharArray();
    
    private final char[] MAJUSCULES = transformacion(MINUSCULES);

    public char[] transformacion(char[] MINUSCULES){
        char[] resultat = new char[MINUSCULES.length];
        for (int i = 0; i < MINUSCULES.length; i++){
            char caracter = Character.toUpperCase(MINUSCULES[i]);
            resultat[i] = caracter;
        }
        return resultat;
    }

    public char desplaça(char c, int desplaçament) {
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
    public String xifratRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++){
            char caracter = cadena.charAt(i);
            resultat += desplaça(caracter, desplaçament);
        }
        return resultat;
    }

    public String desxifratRotX(String cadena, int desplaçament) {
        String resultat = "";
        for (int i = 0; i < cadena.length(); i++){
            char caracter = cadena.charAt(i);
            resultat += desplaça(caracter, -desplaçament);
        }
        return resultat;
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada{
        int desplaçament;
        try {
            desplaçament = Integer.parseInt(clau);
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplaçament < 0 || desplaçament > 40) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        String TextXifrat = xifratRotX(msg, desplaçament);
        return new TextXifrat(TextXifrat.getBytes());
    }
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada{
        int desplaçament;
        try {
            desplaçament = Integer.parseInt(clau);
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }

        if (desplaçament < 0 || desplaçament > 40){
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        }
        String msg = new String(xifrat.getBytes());

        return desxifratRotX(msg, desplaçament);
    }
}