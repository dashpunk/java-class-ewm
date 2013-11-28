package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Data;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author ricardo
 */
public class Rhtbquesav01Rhnuvlrpontos extends MboValueAdapter {

    public Rhtbquesav01Rhnuvlrpontos(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("---- Rhtbquesav01Rhnuvlrpontos");
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println("---- Rhtbquesav01Rhnuvlrpontos validate()");
        super.validate();
        MboRemote mbo;

        System.out.println("--- count() " + getMboValue().getMbo().getMboSet("RHRLQUES01").count());

        if ((mbo = getMboValue().getMbo().getMboSet("RHRLQUES01").getMbo(0)) != null) {

            System.out.println("--- vlMax " + mbo.getInt("RHNUVLRMAXIMO"));
            System.out.println("--- vlAtual " + getMboValue().getInt());

            if ((mbo.getInt("RHNUVLRMAXIMO") > 0) && (getMboValue().getInt() > mbo.getInt("RHNUVLRMAXIMO"))) {
                String[] parametros = new String[3];
                parametros[0] = String.valueOf(getMboValue().getString());
                System.out.println(parametros[0]);
                parametros[1] = getMboValue().getMbo().getMboSet("RHRLQUES01").getMbo(0).getString("RHSTNOMITAV");
                System.out.println(parametros[1]);
                parametros[2] = String.valueOf(mbo.getInt("RHNUVLRMAXIMO"));
                System.out.println(parametros[2]);
                throw new MXApplicationException("rhrlques01", "rhnuvlrmaximoSuperior", parametros);
            }

        }
    }
}
