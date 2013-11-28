package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;

/**
 *
 * @author Willians Andrade
 *
 */
public class ProtocoloExploracaoPecuaria extends MboValueAdapter {

    private String codEstab;

    public ProtocoloExploracaoPecuaria(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        define();
    }

    private void define() throws MXException {
        try {

            if (!getMboValue().getMbo().getString("ID2CODPROP").equals("")) {
                codEstab = getMboValue().getMbo().getString("ID2CODPROP").substring(0, 2);
            } else {
                codEstab = "XX";
            }

            if (!getMboValue().getMbo().getString("LOCATION").startsWith("04")) {
                String valor = "04" + codEstab + Uteis.adicionaValorEsquerda(
                        getMboValue().getMbo().getString("LOCATION"),
                        "0", 9);

                getMboValue().getMbo().setValue("LOCATION", valor, NOACCESSCHECK);
            }
        } catch (RemoteException re) {
            System.out.println("######## Exceção ao definir o valor de LOCATION: " + re.getMessage());
        }
    }
}