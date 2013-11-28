package br.inf.id2.mapa.field;

import br.inf.id2.common.util.Uteis;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 * 
 */
public class MatbcircularId2checkcirc extends MboValueAdapter {

    public MatbcircularId2checkcirc(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        if (getMboValue("ID2CHECKCIRC").getBoolean()) {
            throw new MXApplicationException("matbcircular", "readonly");
        }
        if (getMboValue().getName().equalsIgnoreCase("ANO")) {
            String valor = Uteis.getApenasNumeros(getMboValue().getString());
            getMboValue().setValue(valor, MboConstants.NOVALIDATION_AND_NOACTION);
        }
        super.validate();

    }
}
