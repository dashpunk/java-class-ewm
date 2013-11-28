package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * Classe que cancela a adição de dias de prorrogação aos campos "Data prorrogada" e 
 * "Data de Prestação de Contas Prorrogada", ou seja, retira os dias adicionados às datas
 * 
 * @author Patrick
 */
public class AditivosEProrrogasCancelar extends MboValueAdapter {

    public AditivosEProrrogasCancelar(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("*** AditivosEProrrogasCancelar ***");
        super.validate();
        
        System.out.println("*** cancelar "+getMboValue().getMbo().getString("MECANCELAR"));
        
        if(getMboValue().getMbo().getString("MECANCELAR").equals("S")){
        	Date endDate = getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ENDDATE");
        	Date prestContas = getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ID2CDTPC");
        	
        	getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).setValue("ID2DATAPRO", endDate);
        	getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).setValue("MEPRESTAPRO", prestContas);
        }
    }
}
