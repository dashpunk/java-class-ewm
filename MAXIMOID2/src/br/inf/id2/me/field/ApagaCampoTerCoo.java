package br.inf.id2.me.field;

import psdi.id2.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class ApagaCampoTerCoo extends MboValueAdapter {

    public ApagaCampoTerCoo(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        super.initValue();

        String valor = getMboValue().getString();
        //Uteis.espera("*** valor " + valor);
        try {

            if (valor != null) {
                if (!valor.equals("0")) {
                    getMboValue().getMbo().setValueNull("METERCOO");
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
