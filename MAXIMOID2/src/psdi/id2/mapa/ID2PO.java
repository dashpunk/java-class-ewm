package psdi.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboSet;
import psdi.util.MXException;

/*
 * ID2PO.java
 *
 * Created on 22 de Dezembro de 2010, 19:51
 *
 * @author Ricardo S Gomes
 *
 */
public class ID2PO extends psdi.tloam.app.po.PO
        implements ID2PORemote {

    public ID2PO(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    @Override
    public void save() throws MXException, RemoteException {
        //System.out.println("*************************** save em PO ---------------");
        Date aDataEmissao = getDate("POSTATUS.CHANGEDATE");

        if (aDataEmissao != null && getString("STATUS").equals("EM_TRANSITO")) {

            setValue("ID2CODBARRA", gerarCodigoBarras(aDataEmissao));

        }
        //System.out.println("*************************** save em PO FIM ---------------");
        super.save();
        //System.out.println("*************************** save em PO após save ---------------");

    }

    public String gerarCodigoBarras(Date iDataEmissao) {
        int onde = 0;
        ////Uteis.espera("************************** gerar c\363d barras - entrou");
        String codigoBarras = "";
        try {
            ////Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String NUMGTA = "000000";
            String UF = getString("ID2LOCUF");
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String DATAEMISSAO = (new StringBuilder()).append("00".substring(0, "00".length() - (new StringBuilder()).append(iDataEmissao.getDate()).append("").toString().length())).append(iDataEmissao.getDate()).append("00".substring(0, "00".length() - (new StringBuilder()).append(iDataEmissao.getMonth()).append("").toString().length())).append(iDataEmissao.getMonth()).append(iDataEmissao.getYear() + 1900).append("").toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            int iQTD = (new Double(getMboSet("POLINE").sum("ORDERQTY"))).intValue();
            String QTDANIMAL = (new StringBuilder()).append("0000000".substring(0, "0000000".length() - (new StringBuilder()).append(iQTD).append("").toString().length())).append(iQTD).toString();
            String ESPECIE = getString("COMMODITY");
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            //String PROPRIEDADE = getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").substring(getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").length() - 10, getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").length());
            //String MUNICIPIO = getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").substring(getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").length() - 6, getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").length());
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            char cSerie = getString("ID2SERIE").charAt(0);
            int iSerie = (cSerie - 65) + 1;
            String SERIE;
            if (iSerie < 10) {
                SERIE = (new StringBuilder()).append("0").append(iSerie).toString();
            } else {
                SERIE = (new StringBuilder()).append("").append(iSerie).toString();
            }
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            NUMGTA = (new StringBuilder()).append(NUMGTA.substring(0, NUMGTA.length() - getString("ID2NUMGTA").length())).append(getString("ID2NUMGTA")).toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String verificador = (new StringBuilder()).append(UF).append(SERIE).append(NUMGTA).toString();
            int j = 2;
            int res = 0;
            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }

            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            String DIGITO1 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            verificador = (new StringBuilder()).append(UF).append(SERIE).append(NUMGTA).append(DIGITO1).append(DATAEMISSAO).append(ESPECIE).append(QTDANIMAL).toString();
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            j = 2;
            res = 0;
            verificador = verificador.replace(".", "");
            //Uteis.espera("************************** gerar c\363d barras. Verificador ... " + verificador);
            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());
            String DIGITO2 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            //verificador = (new StringBuilder()).append(UF).append(SERIE).append(NUMGTA).append(DIGITO1).append(DATAEMISSAO).append(ESPECIE).append(QTDANIMAL).append(DIGITO2).append(PROPRIEDADE).append(MUNICIPIO).toString();
            verificador = (new StringBuilder()).append(UF).append(SERIE).append(NUMGTA).append(DIGITO1).append(DATAEMISSAO).append(ESPECIE).append(QTDANIMAL).append(DIGITO2).toString();
            j = 2;
            res = 0;
            verificador = verificador.replace(".", "");
            //Uteis.espera((new StringBuilder()).append("************************** gerar c\363d barras === ").append(++onde).toString());

            for (int i = verificador.length() - 1; i >= 0; i--) {
                Integer digito = new Integer(verificador.substring(i, i + 1));
                res += digito.intValue() * j;
                if (++j > 9) {
                    j = 2;
                }
            }

            /*
            String DIGITO3 = (new StringBuilder()).append(11 - res % 11 <= 9 ? 11 - res % 11 : 0).append("").toString();
            codigoBarras = (new StringBuilder()).append(verificador).append(DIGITO3).toString();

             */
            codigoBarras = (new StringBuilder()).append(verificador).toString();

        } catch (MXException mxe) {
            //Uteis.espera("************************** gerar c\363d barras exception 1... " + mxe.getMessage());
        } catch (RemoteException re) {
            //Uteis.espera("************************** gerar c\363d barras exception 1... " + re.getMessage());
        }
        //Uteis.espera("************************** gerar c\363d barras fim... " + codigoBarras);
        return codigoBarras;
    }
}
