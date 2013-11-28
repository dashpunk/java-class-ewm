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
public class DiasAditivosProrrogacao extends MboValueAdapter {

    public DiasAditivosProrrogacao(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	
        super.validate();
        System.out.println("DiasAditivos.validade ...getDate(ENDDATE)" + getMboValue().getMbo().getDate("ENDDATE"));
        Date endDate = getMboValue().getMbo().getDate("ENDDATE");

        System.out.println("DiasAditivos.validade ...getInt(MEDIAADI)" + getMboValue().getMbo().getInt("MEDIAADI")); 
        int iMEDIAADIAdd = (getMboValue().getMbo().isNull("MEDIAADI") ? 0: getMboValue().getMbo().getInt("MEDIAADI") );
        
        System.out.println("DiasAditivos.validade ...getInt(MEDIAPRO)" + getMboValue().getMbo().getInt("MEDIAPRO")); 
        int iMEDIAPROAdd = (getMboValue().getMbo().isNull("MEDIAPRO") ? 0 : getMboValue().getMbo().getInt("MEDIAPRO"));

        boolean possuiAditivos = getMboValue().getMbo().getBoolean("ID2CADITIVO");
        System.out.println("DiasAditivos.validade possuiAditivos = " + possuiAditivos);
        
        boolean possuiOficio = getMboValue().getMbo().getBoolean("ID2COFICIO");
        System.out.println("DiasAditivos.validade possuiOficio = " + possuiOficio);
        
        Date dataProc = endDate;
        
        if(possuiAditivos & possuiOficio){
	        if((iMEDIAADIAdd + iMEDIAPROAdd ) > 0 ){
	        	System.out.println("DiasAditivos.validade.iDiasAdd (iMEDIAADIAdd + iMEDIAPROAdd ) = " + (iMEDIAADIAdd + iMEDIAPROAdd )) ;
	        	getMboValue().getMbo().setValue("ID2DATAPRO", Data.getDataAcrescimo(dataProc, (iMEDIAADIAdd + iMEDIAPROAdd )));
	        }else if ((iMEDIAADIAdd + iMEDIAPROAdd ) == 0){
	        	setEndDate(endDate);	        }
        }else  if(possuiAditivos && !possuiOficio){
        	
        	getMboValue().getMbo().setValue("ID2CQTDEOFICIO", "");
        	getMboValue().getMbo().setValue("MEDIAPRO", "");
        	
        	if(iMEDIAADIAdd > 0 ){
	        	System.out.println("DiasAditivos.validade.iDiasAdd iMEDIAADIAdd  = " + iMEDIAADIAdd ) ;
	        	getMboValue().getMbo().setValue("ID2DATAPRO", Data.getDataAcrescimo(dataProc, iMEDIAADIAdd ));
	        
        	}else if (iMEDIAADIAdd == 0){
        		setEndDate(endDate);
        	}
        }else if (!possuiAditivos && possuiOficio){
        	
        	getMboValue().getMbo().setValue("ID2CQTDEADITIVO", "");
        	getMboValue().getMbo().setValue("MEDIAADI", "");
        	
        	if(iMEDIAPROAdd > 0 ){
	        	System.out.println("DiasAditivos.validade.iDiasAdd iMEDIAPROAdd  = " + iMEDIAPROAdd) ;
	        	getMboValue().getMbo().setValue("ID2DATAPRO", Data.getDataAcrescimo(dataProc, iMEDIAPROAdd));
	        		        	
        	}else if (iMEDIAPROAdd == 0){
        		setEndDate(endDate);
        	}        	
        }else{
        	setEndDate(endDate);
        	getMboValue().getMbo().setValue("ID2CQTDEADITIVO", "");
        	getMboValue().getMbo().setValue("MEDIAADI", "");
        	getMboValue().getMbo().setValue("ID2CQTDEOFICIO", "");
        	getMboValue().getMbo().setValue("MEDIAPRO", "");
        }
    }
    
    private void setEndDate(Date endDate)throws MXException, RemoteException {
    	System.out.println("DiasAditivos.validade.endDate = " + endDate) ;
    	getMboValue().getMbo().setValue("ID2DATAPRO",endDate);
    }
    
}
