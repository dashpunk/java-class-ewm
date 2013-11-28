package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * Classe adiciona dias de prorrogação aos campos "Data prorrogada" e 
 * "Data de Prestação de Contas Prorrogada"
 * 
 * @author Patrick
 */
public class AditivosEProrrogasNumeroDias extends MboValueAdapter {

    public AditivosEProrrogasNumeroDias(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

    	System.out.println("*** DiasAditivosProrrogasAditivos ***");
        super.validate();
        
        System.out.println("########### Relacionamento MERLADI01: " + getMboValue().getMbo().getMboSet("MERLADI01"));
        System.out.println("########### Relacionamento MERLADI01.getMbo(0): " + getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0));
        System.out.println("########### Relacionamento MERLADI01.getMbo(0).getDate(ENDDATE): " + getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ENDDATE"));
        Date endDate = getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ENDDATE");
        Date prestContas = getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ID2CDTPC");
        int qtdDiasAdd = getMboValue().getInt();
        String cancelar = getMboValue().getMbo().getString("MECANCELAR");
        
        System.out.println("*** qtdDiasAdd "+qtdDiasAdd);
        System.out.println("*** cancelar "+cancelar);
        System.out.println("*** dataPro "+getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("ID2DATAPRO"));
        System.out.println("*** prestContas "+getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).getDate("MEPRESTAPRO"));
        
        if(qtdDiasAdd > 0 && cancelar != "S"){
        	System.out.println("*** IF ***");
        	getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).setValue("ID2DATAPRO", Data.getDataAcrescimo(endDate, qtdDiasAdd));
        	getMboValue().getMbo().getMboSet("MERLADI01").getMbo(0).setValue("MEPRESTAPRO", Data.getDataAcrescimo(prestContas, qtdDiasAdd));
        }
    }
}
