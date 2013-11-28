package br.inf.id2.mintur.field;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Ricardo S Gomes
 */
public class Mtvw01fisconMtamtotvallib extends MboValueAdapter {

    public Mtvw01fisconMtamtotvallib(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("------ Mtvw01fisconMtamtotvallib()");
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        System.out.println("------ Mtvw01fisconMtamtotvallib initValue()");
        super.initValue();
        System.out.println("---before desbloqueado");
        double valorOB = Executa.somaValor("AMVAL", getMboValue().getMbo().getMboSet("MXRL01TBOBCF"));
        System.out.println("---after desbloqueado " + valorOB);
        getMboValue().setValue(valorOB, MboConstants.NOACCESSCHECK);
        System.out.println("---after setValue ");
    }
}
