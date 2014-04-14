package psdi.id2.mapa;

/**
 *
 * @author ID2
 */
public class Uteis {


    public enum TipoCordenada {

        UTM
    }

    /**
     * Converte um valor DMS (Degrees Minute Seconds) para UTM (Universal Transversal de Mercator)
     * <p>
     *
     * @param valor DMS (Degrees Minute Seconds). Excemplo: Para o valor 47º 49' 56.27S, informar -47.495627, ou seja, caso seja O ou S informar o valor em negativo. Sendo que os graus em inteiro e os minutos e segundos concatenas em decimais.
     * @return
     */
    public static double converteDMS2UTM(double valor) {
        int deg = 0;
        double dms = valor;
        double frac_part = 0, min_sec = 0, min = 0, sec = 0, min_dd = 0, sec_dd;

        deg = (int) dms;   /* 36 */
        frac_part = dms - deg;   /* 0.5212 */
        min_sec = frac_part * 100;   /* 52.12 */
        min = (int) min_sec;   /* 52 */
        sec = (min_sec - min) * 100;   /* 12 */
        min_dd = min / 60;   /* 0.8667 */
        sec_dd = sec / 3600;   /* 0.0033 */

        //System.out.format("%.8f%n", deg + min_dd + sec_dd);

        return deg + min_dd + sec_dd;

    }

    public static String[] converteUTM2DMS(double valor) throws Exception {

        double valorAbsoluto = 0D;
        int signValor = 1;
        if (valor < 0) {
            signValor = -1;
        }

        valorAbsoluto = Math.abs(Math.round(valor * 1000000.));

        if (valorAbsoluto > (90 * 1000000)) {

            valorAbsoluto = 0D;
            throw new Exception("Valor Inválido");

        }

        String[] retorno = {
            "" + (Math.floor(valorAbsoluto / 1000000) * signValor),
            "" + Math.floor(((valorAbsoluto / 1000000) - Math.floor(valorAbsoluto / 1000000)) * 60),
            "" + (Math.floor(((((valorAbsoluto / 1000000) - Math.floor(valorAbsoluto / 1000000)) * 60) - Math.floor(((valorAbsoluto / 1000000) - Math.floor(valorAbsoluto / 1000000)) * 60)) * 100000) * 60 / 100000)
        };
        return retorno;


    }

    public static double calculaDistancia(double long1, double lat1, double long2, double lat2) {
        double a = 180000000D;
        long1 = (long1 * Math.PI) / a;
        long2 = (long2 * Math.PI) / a;
        lat1 = (lat1 * Math.PI) / a;
        lat2 = (lat2 * Math.PI) / a;
        double m = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(long2 - long1));
        return m * (double) 6367;
    }

    public static String utm(int deg, int min, int sec) {
        double utm = 0;
        utm = deg + (min / 60) + (sec / 3600); //round down to 1/100000000
        utm = Math.floor(utm * 100000000) / 100000000;
        return String.valueOf(utm);
    }

    public static void espera(String msg) {
        //System.out.println(msg);

        try {

            System.out.println(msg);
            
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            ;
        }
    }

    public static void espera() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            ;
        }
    }

    public static void espera(int ms) {
        try {
            //Thread.sleep(ms);
            Thread.sleep(0);
        } catch (InterruptedException ex) {
            ;
        }
    }

    /*
    public String getNumeros(String str) {
    try {
    Regex reg = new Regex("[^0-9]");
    str = reg.Replace(str, "");
    return str;
    } catch (Exception ex) {
    return "";
    }

    }*/
    public static String getApenasNumeros(String value) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < value.length(); i++) {
            if (Character.isDigit(value.charAt(i))) {
                result.append(value.charAt(i));
            }
        }

        return result.toString();
    }
    
    public static boolean isApenasNumeros(String value) {
    	for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String getValorMascarado(String pMask, String pValue,
            boolean pReturnValueEmpty) {

        /*
         * Verifica se se foi configurado para nao retornar a
         * mascara se a string for nulo ou vazia se nao
         * retorna somente a mascara.
         */
        if (pReturnValueEmpty == true
                && (pValue == null || pValue.trim().equals(""))) {
            return "";
        }

        /*
         * Substituir as mascaras passadas como  9, X  por # para efetuar a formatcao
         */
        pMask = pMask.replaceAll("9", "#");
        pMask = pMask.toUpperCase().replaceAll("X", "#");
         
        /*
         * Formata valor com a mascara passada
         */
        for (int i = 0; i < pValue.length(); i++) {
            pMask = pMask.replaceFirst("#", pValue.substring(i, i + 1));
        }

        /*
         * Subistitui por string vazia os digitos restantes da mascara
         * quando o valor passado é menor que a mascara
         */
        return pMask.replaceAll("#", "");
    }
}
