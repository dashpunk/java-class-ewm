package psdi.id2.mapa;

import psdi.mbo.MboValue;
import psdi.id2.Uteis;

/**
 *
 * Esta classe SERÁ alterada no futuro próximo para que esteja de acordo com o padrão
 *
 * @author Ricardo S Gomes
 *
 *
 */
public class MboStrategy {

    public static String[] converteUTM2DMS(MboValue aMboValue) throws Exception {

        String[] valores;

        String orientacao;
        valores = Uteis.converteUTM2DMS(aMboValue.getDouble());
        if (aMboValue.getAttributeName().contains("LAT")) {
            if (valores[0].substring(0, 1).equals("-")) {
                orientacao = "S";
            } else {
                orientacao = "N";
            }
        } else {
            if (valores[0].substring(0, 1).equals("-")) {
                orientacao = "O";
            } else {
                orientacao = "L";
            }
        }
        return new String[]{orientacao, valores[0].replace("-", "").replace(".", ","), valores[1].replace(".", ","), valores[2].replace(".", ",")};
    }
}
