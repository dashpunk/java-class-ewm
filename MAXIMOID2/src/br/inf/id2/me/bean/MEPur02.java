package br.inf.id2.me.bean;

import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboSet;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;

/**
 * @author Patrick
 */
public class MEPur02 extends ContPurchAppBean {

    public MEPur02() {
    }

    /**
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {

        System.out.println("*** MEPur02 ***");

    	//Valida se j· existe um registro ou n„o.
        String meNumCont = getMbo().getString("MENUMCONT");
        String altipoCont = getMbo().getString("ALTIPOCONT");
        int contractId = getMbo().getInt("CONTRACTID");
        System.out.println("########### Numero do contrato atual = " + contractId);
        
    	
        System.out.println("############# Valores da tela: MENUMCONT=" + meNumCont + " | ALTIPOCONT=" + altipoCont);
        
    	MboSet mboSetCont;
    	mboSetCont = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("CONTRACT", sessionContext.getUserInfo());

    	mboSetCont.setWhere("MENUMCONT = '" + meNumCont + "' and ALTIPOCONT = '" + altipoCont + "' and CONTRACTID <> " + contractId);
        mboSetCont.reset();
        
        System.out.println("############# Tamanho do MboSet apÛs verificaÁ„o: " + mboSetCont.count());

        if (mboSetCont.count() > 0 ) {
            throw new MXApplicationException("mepur02", "registroExistente");
        }

    	
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
            //tipo==1 - Prorroga√ß√£o   status==01 - Aprovado
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
            }else if(status.equals("01") && !tipo.equals("1")){
            	System.out.println("*** else if ***");
                getMbo().setValue("MTATCONT", novoValorGlobal, MboConstants.NOACTION);
                getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
                getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
                if (garantia.equals("01")) {
                    System.out.println("*** else if interno ***");
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

            getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
            getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);
            getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);
            getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
            getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);
            getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);
        }


        double totaisEmpenho = 0;
        double totaisPagamentos = 0;
        
        

       //Atualizado por Leysson e solicitado pelo Ivan Kimura 17/11/2011 
       for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
    	   totaisEmpenho += Executa.somaValor("MEVALOR", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL01NOTAEMP"));
    	   System.out.println("Total de Empenho " + totaisEmpenho);
       }
       
        totaisPagamentos += Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));
        
        

        getMbo().setValue("AMSOMEMP", totaisEmpenho, MboConstants.NOVALIDATION_AND_NOACTION);
        getMbo().setValue("AMSOMPAG", totaisPagamentos, MboConstants.NOVALIDATION_AND_NOACTION);

        return super.SAVE();

    }
}
