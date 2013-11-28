package br.inf.id2.tj.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboConstants;
import psdi.util.*;
import psdi.webclient.beans.contwarrty.ContWarrtyAppBean;

/**
 *
 * @author Ricardo S Gomes
 */
public class ID2CL01WAR extends ContWarrtyAppBean {

    /**
     *
     */
    public ID2CL01WAR() {
    }

    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {

        double valorAtualContrato = 0;
        double novoValorMensal = 0;
        Date dataAssinatura = null;
        double pagamentos = 0;
        double totaisEmpenho = 0;
        double totalValorPrevisto;
        double valorTotalEmpenho = 0;
        double valorAtualGarantia = 0;
        Date vencimentoGarantia = null;
        Date vencimento = null;
        double valorASerPrestado = 0;

        if (getMbo().getMboSet("RL01TBTERADI").count() > 0) {
            //System.out.println("----> rem aditivo");
            valorAtualContrato = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("AMNVVLGLO");
            novoValorMensal = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("AMNVVLMEN");
            dataAssinatura = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDate("DTDATASS");
            valorAtualGarantia = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("AMVALPRES");
            vencimentoGarantia = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDate("DTVENCFIAN");
            vencimento = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDate("DTVENC");
            valorASerPrestado = getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("AMNVVLGLO") * (getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("FLPERCON") / 100);
            //System.out.println("----> iniciou variaveis");

            //Início das atribuições
            getMbo().setValue("DTASS", dataAssinatura, MboConstants.NOACTION);
            //System.out.println("----> a");
            getMbo().setValue("DTVENC", vencimento, MboConstants.NOACTION);
            //System.out.println("----> b");
            getMbo().setValue("AMVLA", valorAtualContrato, MboConstants.NOACTION);
            //System.out.println("----> c");
            getMbo().setValue("AMVLMT", novoValorMensal, MboConstants.NOACTION);
            //System.out.println("----> d");
            getMbo().setValue("AMVLAG", valorAtualGarantia, MboConstants.NOACTION);
            //System.out.println("----> e");
            getMbo().setValue("DTVENG", vencimentoGarantia, MboConstants.NOACTION);
            //System.out.println("----> f");
            getMbo().getMboSet("RL01TBTERADI").getMbo(0).setValue("AMVALPRES", valorASerPrestado, MboConstants.NOACTION);
            //System.out.println("----> g");
            //segunda requisição do Ivan, feita através do spark
            getMbo().setValue("DTASSN", getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDate("DTDATASS"), MboConstants.NOACTION);
            //System.out.println("----> h");
            getMbo().setValue("DTVENCM", getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDate("DTVENC"), MboConstants.NOACTION);
            //System.out.println("----> i");
            getMbo().setValue("AMVLAG", getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("AMVALPRES"), MboConstants.NOACTION);



            /*não utilizado
             *
             * totalValorPrevisto = Executa.somaValor("MTVALPRE", getMbo().getMboSet("MTRL01PROPAG"));
             * getMbo().setValue("MTPAG", pagamentos, MboConstants.NOACTION);
             * getMbo().setValue("MTSALATU", getMbo().getDouble("MTVALVIG") - pagamentos, MboConstants.NOACTION);
             *         pagamentos = Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));
            try {
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
            totaisEmpenho += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL01PO"));
            }
            } catch (Exception e) {
            totaisEmpenho = 0D;
            }
            try {
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
            valorTotalEmpenho = Executa.somaValor(getMbo().getMboSet("MTRL01PROPAG"), "MTVALNOTFIS", "MTNUMEMP", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getString("MTNUMEMP"));
            getMbo().getMboSet("MTRL01CONEMP").getMbo(i).setValue("AMPAGAVINC", valorTotalEmpenho);
            }
            } catch (Exception e) {
            totaisEmpenho = 0D;
            }
             *             getMbo().setValue("MTEMPVALTOT", totaisEmpenho, MboConstants.NOACTION);
            getMbo().setValue("MTPROPAGTOTPAG", pagamentos, MboConstants.NOACTION);
            getMbo().setValue("MTEMPSALATU", totaisEmpenho - pagamentos, MboConstants.NOACTION);
            getMbo().setValue("MTEMPPAGVIN", pagamentos, MboConstants.NOACTION);
            getMbo().setValue("MTPROPAGTOTPRE", totalValorPrevisto, MboConstants.NOACTION);
            getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);
             *
             */



        } else {

            //segunda requisição do Ivan, feita através do spark
            getMbo().setValue("DTASSN", getMbo().getDate("DTASS"), MboConstants.NOACTION);
            getMbo().setValue("DTVENCM", getMbo().getDate("DTVENC"), MboConstants.NOACTION);
            getMbo().setValue("AMVLA", getMbo().getDouble("AMVLTL"), MboConstants.NOACTION);
            getMbo().setValue("AMVLMT", getMbo().getDouble("DCVLM"), MboConstants.NOACTION);
            getMbo().setValue("AMVLAG", 0D, MboConstants.NOACTION);


            /*
            valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
            novoValorMensal = getMbo().getDouble("MTVALMEN");
            dataAssinatura = getMbo().getDate("DTASS");
            valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
            vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            vencimento = getMbo().getDate("DTVENC");

             */
        }

        if (getMbo().getMboSet("RL01TBEXCON").count() > 0) {
            //Ivan requsitou double qtdAnual = getMbo().getMboSet("RL01TBEXCON").getMbo(0).getDouble("FLQTMEN") * 12;
            //Ivan requsitou double valorTotalMensal = getMbo().getMboSet("RL01TBEXCON").getMbo(0).getDouble("AMVALUNIMEN") * getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("FLQTMEN");
            //Ivan requsitou double valorTotalAnual = getMbo().getMboSet("RL01TBEXCON").getMbo(0).getDouble("AMVALUNIMEN") * getMbo().getMboSet("RL01TBTERADI").getMbo(0).getDouble("FLQTANU");
            //Início das atribuições
            //Ivan requisitou getMbo().setValue("FLQTANU", qtdAnual, MboConstants.NOACTION);
            //Ivan requsitou getMbo().setValue("FLVLTLMEN", valorTotalMensal, MboConstants.NOACTION);
            //Ivan requsitou getMbo().setValue("FLVLTLAN", valorTotalAnual, MboConstants.NOACTION);
        }


        //atribuições idependente de ter aditivos
        //valor a ser prestado
        //System.out.println("----> k");
        getMbo().setValue("AMVLPR", getMbo().getDouble("AMVLTL") * (getMbo().getDouble("DCDESM") / 100), MboConstants.NOACTION);
        //System.out.println("----> l");
        //Data limite da vigência
        Date aData = getMbo().getDate("DTASS");
        //System.out.println("----> m");
        try {
            //System.out.println("----> n");
            aData.setDate(aData.getDate() + getMbo().getInt("ITPVIG"));
        } catch (Exception e) {
            throw new MXApplicationException("company", "dataAssinaturaInvalida");
        }
        //System.out.println("----> o");
        getMbo().setValue("DTLVIG", aData, MboConstants.NOACTION);
        //Quantidade Anual
        //Ivan requisitou getMbo().setValue("FLQTANU", aData, MboConstants.NOACTION);






        //super.validate();
        double valorAMVLA = 0;

        valorAMVLA += Executa.somaValor("TOTALCOST", getMbo().getMboSet("PURCHVIEW"));
        valorAMVLA += Executa.somaValor("TOTALCOST", getMbo().getMboSet("LABORVIEW"));

        getMbo().setValue("AMVLA", valorAMVLA, MboConstants.NOVALIDATION_AND_NOACTION);

        return super.SAVE();
    }
}
