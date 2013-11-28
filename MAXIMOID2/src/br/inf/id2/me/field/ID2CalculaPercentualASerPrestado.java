package br.inf.id2.me.field;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class ID2CalculaPercentualASerPrestado extends MboValueAdapter {

    /**
     * Método construtor
     * @param mbv
     * @throws MXException
     */
    public ID2CalculaPercentualASerPrestado(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        System.out.println("*** ID2CalculaPercentualASerPrestado ***");
        super.validate();
        
        double novoValorGlobal = getMboValue().getMbo().getDouble("MTNOVVALGLO");
        double percentual = getMboValue().getDouble() / 100;
        
//        System.out.println("*** novoValorGlobal "+novoValorGlobal);
//        System.out.println("*** percentual "+percentual);

        double result = novoValorGlobal * percentual;
//        System.out.println("*** result "+result);
//        System.out.println("*** MTVALPER "+getMboValue("MTVALPER"));
        getMboValue().getMbo().setValue("MTVALPRES", result, MboConstants.NOACTION);

    }
}
