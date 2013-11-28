package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Uteis;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class MTTbProComMTNumMod extends MboValueAdapter {

    public MTTbProComMTNumMod(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---MTTbProComMTNumMod");
    }

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        System.out.println("---MTTbProComMTNumMod.validete()");
        if (!getMboValue().getMbo().isNull("MTNUMMOD") && !getMboValue("MTNUMMOD").getString().equals("")) {
            System.out.println("---MTTbProComMTNumMod.validete() not null");
            if (Uteis.getApenasNumeros(getMboValue().getString()).equals("")) {
                getMboValue().setValue("000");
            } else {
                getMboValue().setValue(String.format("%03d", Integer.valueOf(Uteis.getApenasNumeros(getMboValue().getString()))));
            }
            System.out.println("---MTTbProComMTNumMod.validete() mask");
        }
        super.validate();
    }
}
