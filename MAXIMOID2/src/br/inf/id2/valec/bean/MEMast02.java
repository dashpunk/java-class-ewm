package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.beans.contmaster.ContMasterAppBean;
import br.inf.id2.common.util.Executa;

/**
 * @author Dyogo
 */
public class MEMast02 extends ContMasterAppBean {

    public MEMast02() {
    }

    /**
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {

        System.out.println("*** MEMast02 ***");
        System.out.println("########## MTRL01MTTBTERADI" + getMbo().getMboSet("MTRL01MTTBTERADI").count());
        if (getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
            int rel = getMbo().getMboSet("MTRL01MTTBTERADI").count();
            double novoValorGlobal = 0;
            double novoValorMensal = 0;
            double valorAtualGarantia = 0;
            Date dataAssinatura = null;
            Date vencimentoGarantia = null;
            Date vencimento = null;
            Date dataInicio = null;

            System.out.println("*** rel " + rel);

            novoValorGlobal = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDouble("MTNOVVALGLO");
            novoValorMensal = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDouble("MTNOVVALMEN");
            dataAssinatura = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDate("MTDATASS");
            valorAtualGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDouble("MTVALAPR");
            vencimentoGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDate("MTDATVENFIA");
            vencimento = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDate("MTDATVEN");
            dataInicio = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getDate("MEDATINI");

            String tipo = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getString("MTCONTTIPO");
            String status = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getString("STATUS");
            String garantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(rel - 1).getString("MTGAR");

            System.out.println("*** tipo " + tipo);
            System.out.println("*** status " + status);
            System.out.println("*** garantia " + garantia);
            //tipo==1 - Prorrogação   status==01 - Aprovado
            if (tipo.equals("1") && status.equals("01")) {
                System.out.println("*** if ***");
                getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);
                getMbo().setValue("MTATCONT", novoValorGlobal, MboConstants.NOACTION);
                getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
                getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
                getMbo().setValue("DTINIC", dataInicio, MboConstants.NOACTION);
                //garantia==01   Sim
                if (garantia.equals("01")) {
                    System.out.println("*** if interno ***");
                    getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
                    getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
                }
            }
        } else {
            Date dataAssinatura = getMbo().getDate("MTDATASS");
            Date vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            Date vencimento = getMbo().getDate("MTDATVEN");

            double valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
            double novoValorMensal = getMbo().getDouble("MTVALMEN");
            double valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");

            System.out.println("############## Datas:" + dataAssinatura + "|" + vencimentoGarantia + "|" + 
            					vencimento + "|" + valorAtualContrato + "|" + valorAtualGarantia + "|" + novoValorMensal);
            
            getMbo().setFieldFlag("MTVALATGA", MboConstants.READONLY, false);
            getMbo().setFieldFlag("MTATCONT", MboConstants.READONLY, false);
            getMbo().setFieldFlag("MTVALMESAT", MboConstants.READONLY, false);
            getMbo().setFieldFlag("MTVENGA", MboConstants.READONLY, false);
            getMbo().setFieldFlag("MTATDATVEN", MboConstants.READONLY, false);
            getMbo().setFieldFlag("MTATDATASS", MboConstants.READONLY, false);
            
            
            getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOVALIDATION_AND_NOACTION);
            getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOVALIDATION_AND_NOACTION);
            
        }


        double totaisEmpenho = 0;
        try {
        	System.out.println("################# MTRL01CONEMP=" + getMbo().getMboSet("MTRL01CONEMP").count());
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
                totaisEmpenho += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL02PO"));
            }
        } catch (Exception e) {
        }

        getMbo().setValue("AMSOMEMP", totaisEmpenho, MboConstants.NOVALIDATION_AND_NOACTION);

        return super.SAVE();

    }
}
