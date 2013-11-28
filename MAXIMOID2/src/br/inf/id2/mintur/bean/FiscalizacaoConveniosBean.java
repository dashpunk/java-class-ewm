package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;
import br.inf.id2.common.util.Executa;
import psdi.mbo.MboRemote;

/**
 * @author Patrick
 */
public class FiscalizacaoConveniosBean extends ContPurchAppBean {

    public FiscalizacaoConveniosBean() {
    }

    /**
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("*** FiscalizacaoConveniosBean *** HHH");


        //MXRL01VW01FISVISr
        MboRemote mbo;

        System.out.println("--- total de itens relacinamento C " + getMbo().getMboSet("MXRL01VW01FISVIS").count());
        for (int i = 0; ((mbo = getMbo().getMboSet("MXRL01VW01FISVIS").getMbo(i)) != null); i++) {
            if (mbo.getMboSet("MTVW01EVOSER").count() > 0) {
                System.out.println("---i + " + i);
                System.out.println("---personid = " + mbo.getString("PERSONID"));
                System.out.println("---count 1o relacionamento " + mbo.getMboSet("MTVW01EVOSER").count());
                //Item 01 ---Inicio---
                //MTTBFISCON.MTTOTITENS: a classe desse campo deve apresentar a somatória das colunas MTVW01EVOSER.MTVLRTOTITEM
                double soma = Executa.somaValor("MTVLRTOTITEM", mbo.getMboSet("MTVW01EVOSER"));
                System.out.println("*** soma " + soma);
                mbo.setValue("MTTOTITENS", soma, MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("*** MTTBFISCON.MTTOTITENS " + mbo.getDouble("MTTOTITENS"));

                double somaMedia = Executa.somaValor("MTPEREXEC", mbo.getMboSet("MTVW01EVOSER"));
                System.out.println("*** somaMedia " + somaMedia);
                int qtd = mbo.getMboSet("MTVW01EVOSER").count();
                System.out.println("*** qtd " + qtd);
                mbo.setValue("MTTOTPEREXE", somaMedia / qtd, MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("*** somaMedia/qtd " + somaMedia / qtd);

                double soma2 = Executa.somaValor("MTVLREXEC", mbo.getMboSet("MTVW01EVOSER"));
                System.out.println("*** soma2 " + soma2);
                mbo.setValue("MTTOTVLREXE", soma2, MboConstants.NOVALIDATION_AND_NOACTION);
                System.out.println("*** MTTBFISCON.MTTOTVLREXE " + mbo.getDouble("MTTOTVLREXE"));

//                if (soma > 0 && soma2 > 0) {
//                    mbo.setValue("MTTOTPEREXE", (soma2 / soma) * 100, MboConstants.NOVALIDATION_AND_NOACTION);
//                } else {
//                    mbo.setValue("MTTOTPEREXE", 0, MboConstants.NOVALIDATION_AND_NOACTION);
//                }
            }
        }
        return super.SAVE();
    }
//    @Override
//    public synchronized void listenerChangedEvent(DataBean speaker) {
//        try {
//        	//MTVW01EVOSER.MTVLRTOTITEM * (MTVW01EVOSER.MTPEREXEC/100)
//        	if (speaker.getUniqueIdName().equals("")) {
//                System.out.println("*** ENTREI NO LISTENER()");
//                
//                double valor1 = getMbo().getMboSet("MTVW01EVOSER").getMbo(0).getDouble("MTVLRTOTITEM");
//    			double valor2 = getMbo().getMboSet("MTVW01EVOSER").getMbo(0).getDouble("MTPEREXEC");
//                //recebe o result da formula - MTVW01EVOSER.MTVLREXEC
//                getMbo().getMboSet("MTVW01EVOSER").getMbo(0).setValue("MTVLREXEC",valor1 * (valor2/100));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.listenerChangedEvent(speaker);
//    }
}
