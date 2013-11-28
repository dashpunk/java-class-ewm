package br.inf.id2.valec.mbo;

import br.inf.id2.mintur.mbo.*;
import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 */
public class Contract extends psdi.app.contract.Contract
        implements ContractRemote {

    public Contract(MboSet mboset) throws MXException, RemoteException {
        super(mboset);
    }

    @Override
    protected void save() throws MXException, RemoteException {
        super.save();
        atualizaMasterContract();
    }

    private void atualizaMasterContract() throws RemoteException, MXException {
        System.out.println("--- atualizaMasterContract");
        //TODO validar com o jesse se u atributo masternum pode ser alterado, caso porsitivo, precisaremos recuperar o valor anterior de masternum
        if (!isNull("MASTERNUM")) {
            System.out.println("--- atualizaMasterContract masternum is not null");

            MboSet masterContractSet;
            masterContractSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("CONTRACTMASTER", getUserInfo());

            masterContractSet.setWhere("CONTRACTNUM = '" + getString("MASTERNUM") + "' and REVISIONNUM = "+getInt("REVISIONNUM"));
            masterContractSet.reset();

            MboSet contractSet;
            contractSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("CONTRACT", getUserInfo());

            contractSet.setWhere("MASTERNUM = '" + getString("MASTERNUM") + "' and REVISIONNUM = "+getInt("REVISIONNUM"));
            contractSet.reset();

            MboRemote mbo;
            MboRemote mboc;
            System.out.println("------ count em mastercontract "+masterContractSet.count());
            System.out.println("------ count em contract "+contractSet.count());
            for (int i = 0; ((mbo = masterContractSet.getMbo(i)) != null); i++) {
                System.out.println("--- i "+i);
                double valorTotal = 0D;
                for (int j = 0; ((mboc = contractSet.getMbo(j)) != null); j++) {
                    System.out.println("--- j "+j);
                    valorTotal += mboc.getDouble("TOTALCOST");
                    System.out.println("--- valorTotal = "+valorTotal);
                }
                System.out.println("--- antes do setvalue");
                mbo.setValue("TOTALCOST", valorTotal, MboConstants.NOACCESSCHECK);
                System.out.println("--- após o setvalue");

            }
            System.out.println("--- antes do save em masterContractSet");
            masterContractSet.save();
            System.out.println("--- após do save em masterContractSet");
        } else {
            System.out.println("--- atualizaMasterContract masternum is null");
        }

    }
}
