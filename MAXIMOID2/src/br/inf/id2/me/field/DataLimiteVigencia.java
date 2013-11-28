package br.inf.id2.me.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class DataLimiteVigencia extends MboValueAdapter {

    public DataLimiteVigencia(MboValue mbv) {
        super(mbv);
        System.out.println("*** DataLimiteVigencia ***");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
//        System.out.println("*** DataLimiteVigencia - initValue");
    	super.initValue();
        realizar();
    }

    @Override
    public void validate() throws MXException, RemoteException {
//    	System.out.println("*** DataLimiteVigencia - validate");
        super.validate();
        realizar();
    }

    /**
     * Método centraliza os códigos, identicos, dos dois métodos anteriores.
     * Verifica a quantidade de dias entre hoje e a data passada e seta esse
     * valor no campo determinado.
     */
    public void realizar() throws MXException, RemoteException {

    	//System.out.println("############# Data de Vencimento = " + getMboValue().getMbo().isNull("MTATDATVEN"));
        if (!getMboValue().getMbo().isNull("MTATDATVEN")) {
            Date startDate = new Date();
            Date endDate = getMboValue().getMbo().getDate("MTATDATVEN");
            //System.out.println("############# Data inicial = " + startDate + "| Final = " + endDate);
            int dias = Data.recuperaDiasInteirosEntreDatas(startDate, endDate);
//            System.out.println("############## DIAS = " + dias);
            try {
            	getMboValue().getMbo().setFieldFlag("MEDIAAPOS", MboConstants.READONLY, false);
            	getMboValue().getMbo().setValue("MEDIAAPOS", dias + "", MboConstants.NOACCESSCHECK);
            	getMboValue().getMbo().setFieldFlag("MEDIAAPOS", MboConstants.READONLY, true);
            } catch (Exception e) {
//            	System.out.println("############ Erro = " + e.getMessage());
            }
            getMboValue().getMbo().getThisMboSet().save();
        } else {
        	getMboValue().getMbo().setFieldFlag("MEDIAAPOS", MboConstants.READONLY, false);
            getMboValue().getMbo().setValueNull("MEDIAAPOS", MboConstants.NOACCESSCHECK);
            getMboValue().getMbo().setFieldFlag("MEDIAAPOS", MboConstants.READONLY, true);
        }
//        System.out.println("############## Fim");
        //System.out.println("--- end datalimiteviagem");
    }
}
