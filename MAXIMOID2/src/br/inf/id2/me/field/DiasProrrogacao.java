package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Davi Dias
 *  
 */
public class DiasProrrogacao extends MboValueAdapter {

    public DiasProrrogacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        System.out.println("DiasProrrogacao.validade ...getDate(ENDDATE)" + getMboValue().getMbo().getDate("ENDDATE"));
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");
        System.out.println("DiasProrrogacao.validade ...getDate(MEDIAPRO)" + getMboValue().getMbo().getInt("MEDIAPRO")); 
        int iDiasAdd = getMboValue().getMbo().getInt("MEDIAPRO");
        System.out.println("DiasProrrogacao.validade ...getDate(ID2DATAPRO)" + getMboValue().getMbo().getDate("ID2DATAPRO")); 
        Date dataProc = getMboValue().getMbo().getDate("ID2DATAPRO");
                
        endDate = dataProc;
        
        if(iDiasAdd > 0){        
        	System.out.println("DiasProrrogacao.validade.iDiasAdd = " + iDiasAdd) ;
        	getMboValue().getMbo().setValue("ID2DATAPRO", Data.getDataAcrescimo(dataProc, iDiasAdd));
        }
        else{
        	System.out.println("DiasProrrogacao.validade.endDate = " + dataProc) ;
        	getMboValue().getMbo().setValue("ID2DATAPRO",dataProc);
        }
    }
}
