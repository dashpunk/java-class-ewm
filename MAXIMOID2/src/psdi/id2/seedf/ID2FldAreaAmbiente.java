package psdi.id2.seedf;

import psdi.mbo.*;


import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2FldAreaAmbiente extends MboValueAdapter {

    public ID2FldAreaAmbiente(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        Double valorArea = getMboValue().getMbo().getMboSet("SEERL01AMB").getMbo(0).getDouble("SEEAREA");
        Double resultado = valorArea / 1.2;
        //System.out.println("*************************************");
        //System.out.println("ID2FldAreaAmbiente************** valorArea " + valorArea);
        //System.out.println("ID2FldAreaAmbiente************** resultado " + resultado);
        //System.out.println("ID2FldAreaAmbiente************** resultado a aplicar " + resultado.intValue());
        //System.out.println("*************************************");
        getMboValue().getMbo().setValue("SEEOFEVAG", resultado.intValue(), MboConstants.NOVALIDATION_AND_NOACTION);
        //System.out.println("ID2FldAreaAmbiente************** resultadoAplicado " + resultado.intValue());
    }
}
