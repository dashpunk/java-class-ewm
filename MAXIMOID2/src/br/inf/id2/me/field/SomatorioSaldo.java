package br.inf.id2.me.field;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class SomatorioSaldo extends MboValueAdapter {

    public SomatorioSaldo(MboValue mbv) {
    	super(mbv);
//    	System.out.println("*** SomatorioSaldo ***");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
//    	System.out.println("*** initValue");
    	super.initValue();

        double soma = 0;
        MboSetRemote mboRel = getMboValue().getMbo().getMboSet("MXRLSR01");
        
//        System.out.println("*** MXRLSR01).count() "+getMboValue().getMbo().getMboSet("MXRLSR01").count());
        if(mboRel.isEmpty()){
//        	System.out.println("*** if");
        	getMboValue().setValue(0);
        }else{
//        	System.out.println("*** else");
	    	for (int i=0; i < mboRel.count(); i++) {
	    		Mbo mboValor = (Mbo) mboRel.getMbo(i);
	    		soma += mboValor.getDouble("MXVALOR");
	    	}
	    	getMboValue().setValue(soma);
        }
    }

}
