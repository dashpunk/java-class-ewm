package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ID2VwLoc05ID2Cep extends MboValueAdapter {

    public ID2VwLoc05ID2Cep(MboValue mbv) {
        super(mbv);
        System.out.println("*** ID2VwLoc05ID2Cep ***");
    }

    @Override
    public void action() throws MXException, RemoteException {
        super.action();
        String atributoAtual = getMboValue().getName();
//        System.out.println("--------- atributoAtual " + atributoAtual);
        MboRemote mbo;
        if (atributoAtual.endsWith("ID2CEPCODE2")) {
//        	System.out.println("*** IF");
            mbo = getMboValue().getMbo().getMboSet("ID2LOCADDRESS2").getMbo(0);
        } else {
//        	System.out.println("*** ELSE");
            mbo = getMboValue().getMbo().getMboSet("ID2LOCADDRESS").getMbo(0);
        }
        if (mbo != null) {
//            System.out.println("------------- cep is not null");
            String codMun = mbo.getString("ID2CODMUN");
            String addUf = mbo.getString("ID2ADDUF");
            String id2LocUf = mbo.getString("ID2LOCUF");
            String addressId = mbo.getString("ADDRESSID");
            String municipio = mbo.getString("ADDRESS4");

            getMboValue().getMbo().setValue("ID2ADDUF", addUf, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValue("ID2LOCUF", id2LocUf, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValue("ID2CODMUN", codMun, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValue("ADDRESSID", addressId, MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValue("ID2MUN", municipio, MboConstants.NOVALIDATION_AND_NOACTION);

        } else {
//            System.out.println("------------- cep null");
            getMboValue().getMbo().setValueNull("ID2CODMUN", MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValueNull("ID2LOCUF", MboConstants.NOVALIDATION_AND_NOACTION);
            getMboValue().getMbo().setValueNull("ID2ADDUF", MboConstants.NOVALIDATION_AND_NOACTION);

        }
    }
}
