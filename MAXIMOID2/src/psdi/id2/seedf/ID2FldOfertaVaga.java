package psdi.id2.seedf;

import psdi.id2.Validar;
import psdi.mbo.*;


import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2FldOfertaVaga extends MboValueAdapter {

    public ID2FldOfertaVaga(MboValue mbv)
            throws MXException {
        super(mbv);
    }

    public void validate()
            throws MXException, RemoteException {
        super.validate();
        int valorAtual = getMboValue().getInt();

        Double valorArea = getMboValue().getMbo().getMboSet("SEERL01AMB").getMbo(0).getDouble("SEEAREA");
        Double resultado = valorArea / 1.2;

        //String param[] = {String.valueOf(resultado.intValue())};
        //System.out.println("*************************************");
        //System.out.println("OfertaVaga************** valorAtual " + valorAtual);
        //System.out.println("OfertaVaga************** valorArea " + valorArea);
        //System.out.println("OfertaVaga************** resultado " + resultado);
        //System.out.println("*************************************");
        if (valorAtual <= resultado.intValue()) {
            return;
        } else {
            throw new MXApplicationException("company", "ValorOfertaVagaSuperior");
        }

    }
}
