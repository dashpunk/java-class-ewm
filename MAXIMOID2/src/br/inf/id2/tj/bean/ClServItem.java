package br.inf.id2.tj.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.util.MXException;
import br.inf.id2.common.util.Executa;

/**
 *
 * @author Ricardo S Gomes
 *
 * retirei de totalcost a classe psdi.app.contract.common.FldCommonContractTotalCost
 */
public class ClServItem extends psdi.webclient.beans.contpurch.ContPurchAppBean {

    public ClServItem() {
        super();
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        //System.out.println("---- inicico ClServItem");

//      Serviço - RL02CONTRACTLINE
        //System.out.println("*** FLBDISERV "+getMbo().getString("FLBDISERV"));
        String bdiServico = getMbo().getString("FLBDISERV");
        
        MboRemote mboServico;
        for (int i = 0; ((mboServico = getMbo().getMboSet("RL02CONTRACTLINE").getMbo(i)) != null); i++) {
//        	//System.out.println("*** BDI servico old "+mboServico.getString("BDI"));
        	mboServico.setValue("BDI", bdiServico);
        }

//      Material - RL01CONTRACTLINE
        //System.out.println("*** FLBDIMAT "+getMbo().getString("FLBDIMAT"));
        String bdiMaterial = getMbo().getString("FLBDIMAT");
        
        MboRemote mboMaterial;
        for (int i = 0; ((mboMaterial = getMbo().getMboSet("RL01CONTRACTLINE").getMbo(i)) != null); i++) {
//        	//System.out.println("*** BDI Material old "+mboMaterial.getString("BDI"));
        	mboMaterial.setValue("BDI", bdiMaterial);
        }
        

        double lineCost01 = Executa.somaValor("LINECOST", getMbo().getMboSet("RL01CONTRACTLINE"));
        double lineCost02 = Executa.somaValor("LINECOST", getMbo().getMboSet("RL02CONTRACTLINE"));

        //System.out.println("---- soma linecost01 " + lineCost01);
        //System.out.println("---- soma linecost02 " + lineCost02);



        double aValorTotal = lineCost01 + lineCost02;



        double valorTotal = getMbo().getDouble("TOTALCOST");

        //System.out.println("---- totalcost readonly true");

        //System.out.println("---- fim ClServItem");
        try {
            int retorno = super.SAVE();
        } catch (Exception e) {
        }

        for (int i = 0; i < 20; i++) {
            //System.out.println("--- i");
            valorTotal = getMbo().getDouble("TOTALCOST");
            //System.out.println("--- valorTotal  " + valorTotal);
            //System.out.println("--- aValorTotal " + aValorTotal);

            if (aValorTotal == valorTotal) {
                //System.out.println("--- break");
                break;
            }

            getMbo().setFieldFlag("TOTALCOST", psdi.mbo.MboConstants.READONLY, false);
            getMbo().setValue("TOTALCOST", aValorTotal);
            getMbo().setFieldFlag("TOTALCOST", psdi.mbo.MboConstants.READONLY, true);
            try {
                super.SAVE();
            } catch (Exception e) {
            }
        }

        //return retorno;
        return 1;

    }
}
