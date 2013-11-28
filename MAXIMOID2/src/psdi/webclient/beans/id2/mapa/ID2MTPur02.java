package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.id2.Executa;
import psdi.id2.mapa.Uteis;
import psdi.mbo.MboConstants;
import psdi.util.*;
import psdi.webclient.beans.contpurch.ContPurchAppBean;

public class ID2MTPur02 extends ContPurchAppBean {

    public ID2MTPur02() {
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
 
        double valorAtualContrato;
        double novoValorMensal;
        Date dataAssinatura;
        double pagamentos;
        double totaisEmpenho = 0;
        double totalValorPrevisto;
        double valorAtualGarantia;
        Date vencimentoGarantia;
        Date vencimento;

        if (getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
            valorAtualContrato = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALGLO");
            novoValorMensal = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALMEN");
            dataAssinatura = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATASS");
            valorAtualGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTVALPRES");
            vencimentoGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATVENFIA");
            vencimento = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATVEN");

        } else {
            valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
            novoValorMensal = getMbo().getDouble("MTVALMEN");
            dataAssinatura = getMbo().getDate("MTDATASS");
            valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
            vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            vencimento = getMbo().getDate("MTDATVEN");
        }


        pagamentos = Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));

        System.out.println("--------------------- a regs = " + getMbo().getMboSet("MTRL01CONEMP").count());



        try {
        	System.out.println("--------------------- b regs = " + getMbo().getMboSet("MTRL01CONEMP").getMbo().getMboSet("MTRL01PO").count());

            //totaisEmpenho = Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo().getMboSet("MTRL01PO"));

            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
                totaisEmpenho += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL01PO"));
            }


        } catch (Exception e) {
            totaisEmpenho = 0D;
            System.out.println("--------------------- c exception = " + e.getMessage());
        }


        totalValorPrevisto = Executa.somaValor("MTVALPRE", getMbo().getMboSet("MTRL01PROPAG"));

        int result = super.SAVE();
        System.out.println("################ PRIMEIRO RESULTADO=" + result);
        
        getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);
        getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);

        getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);

        getMbo().setValue("MTPAG", pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTSALATU", getMbo().getDouble("MTVALVIG") - pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTEMPVALTOT", totaisEmpenho, MboConstants.NOACTION);

        getMbo().setValue("MTEMPPAGVIN", pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTPROPAGTOTPRE", totalValorPrevisto, MboConstants.NOACTION);

        getMbo().setValue("MTPROPAGTOTPAG", pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);

        getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);

        getMbo().setValue("MTEMPSALATU", totaisEmpenho - pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);

        super.validate();
        System.out.println("--------------------- FIM SAVE appBean");
        result = super.SAVE();
        System.out.println("################ SEGUNDO RESULTADO=" + result);
        return result;
    }
}
