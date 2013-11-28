package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  br.inf.id2.mapa.field.AssetMaeEstratificacao
 */
public class FldMatRecTransReceiptQuantity extends MboValueAdapter {

    public FldMatRecTransReceiptQuantity(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("Valida FldMatRecTransReceiptQuantity");
        double valora = getMboValue("RECEIPTQUANTITY").getDouble();
        double valorb = getMboValue("ORDERQTY").getDouble();
        System.out.println("---vals");
        System.out.println(valora);
        System.out.println(valorb);
        boolean zerar = false;
        if (valora > valorb) {
            zerar = true;
            String[] params = new String[4];
            params[0] = String.valueOf(valora);
            params[1] = String.valueOf(getMboValue().getMbo().getThisMboSet().getCurrentPosition());
            params[2] = String.valueOf(valorb);
            params[3] = String.valueOf(valora - valorb);
            System.out.println(params[0]);
            System.out.println(params[1]);
            System.out.println(params[2]);
            System.out.println(params[3]);
            System.out.println("--- vals");
            //getMboValue().getMbo().setValueNull("RECEIPTQUANTITY", MboConstants.NOVALIDATION);
            System.out.println("--- antes da exceção");
            getMboValue().getMbo().setValueNull("RECEIPTQUANTITY", MboConstants.NOVALIDATION_AND_NOACTION);
            
            System.out.println("--- val atual = " + getMboValue().getDouble());
            throw new MXApplicationException("inventory", "OrderQuantityExceeded", params);

        }

        //inventory
        //OrderQuantityExceeded
        //Uma quantidade de {0} está sendo recebida na Linha OC {1}, que excede a quantidade solicitada de {2} por {3}.
        System.out.println("Valida FldMatRecTransReceiptQuantity antes de super");
        super.validate();
        System.out.println("Valida FldMatRecTransReceiptQuantity FIM");

    }


    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("initValue...");
        //super.initValue();
        getMboValue().setValueNull(MboConstants.NOVALIDATION_AND_NOACTION);
    }

    @Override
    public void action() throws MXException, RemoteException {
        System.out.println("action");
        super.action();
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("equals");
        return super.equals(o);
    }


}
