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
public class DiasAditivos extends MboValueAdapter {

    public DiasAditivos(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        System.out.println("DiasAditivos.validade ...getDate(ENDDATE)" + getMboValue().getMbo().getDate("ENDDATE"));
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");
        System.out.println("DiasAditivos.validade ...getDate(ID2DATAPRO)" + getMboValue().getMbo().getInt("ID2DATAPRO")); 
        Date dataProc = getMboValue().getMbo().getDate("ID2DATAPRO");
        System.out.println("DiasAditivos.validade ...getDate(MEDIAADI)" + getMboValue().getMbo().getDate("MEDIAADI")); 
        int iDiasAdd = getMboValue().getMbo().getInt("MEDIAADI");
        
        endDate=dataProc ;
        
        if(iDiasAdd > 0){
        	System.out.println("DiasAditivos.validade.iDiasAdd = " + iDiasAdd) ;
        	getMboValue().getMbo().setValue("ID2DATAPRO", Data.getDataAcrescimo(dataProc, iDiasAdd));
        }else{
        	System.out.println("DiasAditivos.validade.endDate = " + dataProc) ;
        	getMboValue().getMbo().setValue("ID2DATAPRO",dataProc);
        }
    }
}
