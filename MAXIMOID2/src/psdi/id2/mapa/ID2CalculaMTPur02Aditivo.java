package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class ID2CalculaMTPur02Aditivo extends MboValueAdapter {

    /**
     * Método construtor
     * @param mbv
     * @throws MXException
     */
    public ID2CalculaMTPur02Aditivo(MboValue mbv) throws MXException {

        super(mbv);

    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        String atual = getMboValue().getAttributeName();
        double valorContrato = getMboValue().getMbo().getDouble("MTNOVVALGLO");
        double aliquota = getMboValue().getMbo().getDouble("MTVALPER") / 100;

        getMboValue().getMbo().getMboValue("MTVALPRES").setValue(valorContrato * aliquota, MboConstants.NOACTION);

        super.validate();
    }
}
