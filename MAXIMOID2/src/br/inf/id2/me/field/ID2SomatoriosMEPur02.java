package br.inf.id2.me.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick L. Silva
 */
public class ID2SomatoriosMEPur02 extends MboValueAdapter {

    public ID2SomatoriosMEPur02(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("*** ID2SomatoriosMTPur02 ***");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
    	System.out.println("*** validate");
    	
    	double valorFinanceiro  = getMboValue().getMbo().getDouble("ID2PCPVAL");
    	double valorBensServicos = getMboValue().getMbo().getDouble("ID2CPCPVALBS");
    	double valorCapital = getMboValue().getMbo().getDouble("ID2CREPVAL");
    	double valorConsumo = getMboValue().getMbo().getDouble("MEVALCONS");
    	
    	
//        getMboValue().getMbo().setFieldFlag("ID2CTPVAL", MboConstants.READONLY, false);
        getMboValue().getMbo().setValue("ID2CTPVAL", valorFinanceiro + valorBensServicos);
//        getMboValue().getMbo().setFieldFlag("ID2CTPVAL", MboConstants.READONLY, true);

//        getMboValue().getMbo().setFieldFlag("ID2CPTOTALVAL", MboConstants.READONLY, false);
        getMboValue().getMbo().setValue("ID2CPTOTALVAL", valorCapital + valorConsumo);
//        getMboValue().getMbo().setFieldFlag("ID2CPTOTALVAL", MboConstants.READONLY, true);
        
        super.validate();
    }
}
