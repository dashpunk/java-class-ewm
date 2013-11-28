package br.inf.id2.valec.bean;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import br.inf.id2.common.util.Executa;

/**
 * @author Patrick
 */
public class ValecPur02 extends AppBean/*ContMasterAppBean*//*ContPurchAppBean*/ {

    public ValecPur02() {
    }

    /**
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {

        System.out.println("*** ValecPur02 ***");
        if (getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
            int rel = getMbo().getMboSet("MTRL01MTTBTERADI").count();
            double novoValorGlobal = 0;
            double novoValorMensal = 0;
            double valorAtualGarantia = 0;
            Date dataAssinatura = null;
            Date vencimentoGarantia = null;
            Date vencimento = null;
            Date dataInicio = null;

//            System.out.println("*** rel " + rel);

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

//            System.out.println("*** tipo " + tipo);
//            System.out.println("*** status " + status);
//            System.out.println("*** garantia " + garantia);
            
//            System.out.println("*** vencimento " + vencimento);
//            System.out.println("*** dataInicio " + dataInicio);
            
            //tipo==1 - Prorrogação   status==01 - Aprovado
            if (tipo.equals("01") && status.equals("01")) {
//                System.out.println("*** if ***");
                getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOVALIDATION_AND_NOACTION);
                getMbo().setValue("MTATCONT", novoValorGlobal, MboConstants.NOACTION);
                getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
                getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
                getMbo().setValue("DTINIC", dataInicio, MboConstants.NOACTION);
                //garantia==01   Sim
                if (garantia.equals("01")) {
//                    System.out.println("*** if interno ***");
                    getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
                    getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
                }
            }else if(status.equals("01") && !tipo.equals("01")){
//            	System.out.println("*** else if ***");
                getMbo().setValue("MTATCONT", novoValorGlobal, MboConstants.NOACTION);
                getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
                getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
                if (garantia.equals("01")) {
//                    System.out.println("*** else if interno ***");
                    getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
                    getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
                }
            }
        } else {

//        	System.out.println("*** else");
        	Date dataAssinatura = getMbo().getDate("MTDATASS");
            Date vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            Date vencimento = getMbo().getDate("MTDATVEN");

            double valorAtualContratoTermoAditivo = getMbo().getDouble("MXCUSTOTOTAL");
            double novoValorMensal = getMbo().getDouble("MTVALMEN");
            double valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
            
//            System.out.println("*** dataAssinatura "+dataAssinatura);
//            System.out.println("*** vencimentoGarantia "+vencimentoGarantia);
//            System.out.println("*** vencimento "+vencimento);
//            System.out.println("*** valorAtualContrato "+valorAtualContrato);
//            System.out.println("*** novoValorMensal "+novoValorMensal);
//            System.out.println("*** valorAtualGarantia "+valorAtualGarantia);

            getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
            getMbo().setValue("MTATCONT", valorAtualContratoTermoAditivo, MboConstants.NOACTION);
            getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
            getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
            getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);
            getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
        }

        double totaisEmpenho = 0;
        double totaisPagamentos = 0;
        
        System.out.println("*** AMVLA "+getMbo().getDouble("AMVLA"));
        System.out.println("*** MXCUSTOTOTAL antes "+getMbo().getDouble("MXCUSTOTOTAL"));
        double valorAtualContrato = getMbo().getDouble("AMVLA");
        getMbo().setValue("MXCUSTOTOTAL", valorAtualContrato, MboConstants.NOACTION);
        System.out.println("*** MXCUSTOTOTAL depois "+getMbo().getDouble("MXCUSTOTOTAL"));

       //Atualizado por Leysson e solicitado pelo Ivan Kimura 17/11/2011 
       for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
    	   totaisEmpenho += Executa.somaValor("MEVALOR", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MXRL01EMP"));
//    	   System.out.println("Total de Empenho " + totaisEmpenho);
       }
       
//       System.out.println("*** totaisEmpenho"+totaisEmpenho);
       
       totaisPagamentos += Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));

//       System.out.println("*** totaisPagamentos "+totaisPagamentos);
       
       getMbo().setValue("AMSOMEMP", totaisEmpenho, MboConstants.NOVALIDATION_AND_NOACTION);
       getMbo().setValue("AMSOMPAG", totaisPagamentos, MboConstants.NOVALIDATION_AND_NOACTION);

       int save = super.SAVE();
       
//       System.out.println("*** save "+save);
       
       return save;
    }
}
