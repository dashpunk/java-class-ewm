package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import psdi.mbo.MboValue;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class WorkOrderID2WoNum extends psdi.app.workorder.FldWOWONum {

    public WorkOrderID2WoNum(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        define();
    }

    private void define() throws MXException {
        try {
            //System.out.println("------------ wonum inicio");
            if (!getMboValue().getMbo().getString("ID2WONUM").startsWith("ORS")) {
                String valor = "ORS." + Uteis.adicionaValorEsquerda(
                        getMboValue().getMbo().getString("WONUM"),
                        "0", 9)
                        + "."
                        + new GregorianCalendar().get(GregorianCalendar.YEAR);

                //System.out.println("------------ wonum valor " + valor);
                getMboValue().getMbo().setValue("ID2WONUM", valor);
            }
        } catch (RemoteException re) {
            System.out.println("######## Excecao ao definir o valor de ID2WONUM: " + re.getMessage());
        }
        //System.out.println("------------ wonum fim ");
    }
}
