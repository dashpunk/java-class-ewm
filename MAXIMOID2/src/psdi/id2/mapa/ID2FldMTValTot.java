package psdi.id2.mapa;

import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2FldMTValTot extends MboValueAdapter {

    /**
     * Método construtor
     * @param mbv
     * @throws MXException
     */
    public ID2FldMTValTot(MboValue mbv) throws MXException {

        super(mbv);

    }

    /**
     *
     * <p>
     * "Valor Atual do Contrato" (ID2MTATCONT) = "Valor do Contrato" (ID2CPTOTALVAL) + soma(MTTERADI.MTVALTOT)
     *
     *
     * @throws MXException
     * @throws java.rmi.RemoteException
     */
    @Override
    public void validate() throws MXException, java.rmi.RemoteException {

        double valorContrato = getMboValue().getMbo().getDouble("MTRL01MTTBTERADI.ID2CPTOTALVAL");

        double valorAtualContrato;
        if (getMboValue().getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
            valorAtualContrato = getMboValue().getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALGLO");
        } else {
            valorAtualContrato = getMboValue().getMbo().getDouble("ID2CPTOTALVAL");
        }
        getMboValue().getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);

        //Uteis.espera("--------------------- valor total: " + getMboValue().getMbo().getDouble("MTRL01MTTBTERADI.MTATCONT"));

        super.validate();
    }
}
