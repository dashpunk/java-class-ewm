package br.inf.id2.valec.field;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;

import psdi.mbo.MboConstants;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class ContractMasterTotalCost extends MboValueAdapter {

    public ContractMasterTotalCost(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void initValue() throws MXException, RemoteException {
        super.initValue();
        System.out.println("------ ContractMasterTotalCost inicio");
        double valorTotal = Executa.somaValor("TOTALCOST", getMboValue().getMbo().getMboSet("CHILDCONTRACTS"));
        System.out.println("--- valorTotal "+valorTotal);
        getMboValue().setValue(valorTotal, MboConstants.NOACCESSCHECK);
        System.out.println("------ ContractMasterTotalCost FIM");
    }
}
