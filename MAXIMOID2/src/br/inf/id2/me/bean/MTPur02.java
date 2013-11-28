package br.inf.id2.me.bean;

import br.inf.id2.common.util.Data;
import br.inf.id2.common.util.Executa;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.mbo.MboConstants;
import psdi.util.MXException;
import psdi.webclient.beans.contpurch.ContPurchAppBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 */
public class MTPur02 extends ContPurchAppBean {

    /**
     *
     */
    public MTPur02() {
    }

    @Override
    public int callMethod(WebClientEvent event) throws MXException, NoSuchMethodException, RemoteException {
        System.out.println(">>>>>>> "+event.getTargetId());
        System.out.println(">>>>>>> "+event.toString());
        return super.callMethod(event);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        System.out.println(">>>>> validate()");
        super.validate();
    }



    @Override
    public int REVCONT() throws RemoteException, MXException {
        System.out.println(">>>>>>> status = "+getMbo().getString("STATUS"));
        if (getMbo().getString("STATUS").equalsIgnoreCase("VIGENTE")) {
            getMbo().setValue("STATUS", "EM ALTERAÇÃO", MboConstants.NOACCESSCHECK);
        }
        System.out.println(">>>>>>> status = "+getMbo().getString("STATUS"));
        return super.REVCONT();
        
    }


    /**
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    @Override
    public int SAVE() throws MXException, RemoteException {
        System.out.println("--- MTPur02 SAVE()");
        double valorAtualContrato;
        double novoValorMensal;
        Date dataAssinatura;
        double pagamentos;
        double totaisEmpenho = 0;
        double totalValorPrevisto;
        double valorTotalEmpenho = 0;
        double valorAtualGarantia;
        Date vencimentoGarantia;
        Date vencimento;

        System.out.println("--- MTPur02 Vals1");
        if (getMbo().getMboSet("MTRL01MTTBTERADI").count() > 0) {
            valorAtualContrato = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALGLO");
            novoValorMensal = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTNOVVALMEN");
            valorAtualGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDouble("MTVALPRES");
            dataAssinatura = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATASS");
            vencimentoGarantia = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATVENFIA");
            vencimento = getMbo().getMboSet("MTRL01MTTBTERADI").getMbo(0).getDate("MTDATVEN");

        } else {
            valorAtualContrato = getMbo().getDouble("ID2CPTOTALVAL");
            novoValorMensal = getMbo().getDouble("MTVALMEN");

            valorAtualGarantia = getMbo().getDouble("ID2PCPVAL");
            dataAssinatura = getMbo().getDate("MTDATASS");
            vencimentoGarantia = getMbo().getDate("ID2DATAVENCFIANCA");
            vencimento = getMbo().getDate("MTDATVEN");
        }
        System.out.println("--- MTPur02 Vals2");

        //ok
        pagamentos = Executa.somaValor("MTVALNOTFIS", getMbo().getMboSet("MTRL01PROPAG"));



        //ok
        System.out.println("--- MTPur02 Vals3");
        try {
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
                totaisEmpenho += Executa.somaValor("ID2EMPVAL", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getMboSet("MTRL01PO"));
            }
        } catch (Exception e) {
            totaisEmpenho = 0D;
        }


        System.out.println("--- MTPur02 Vals4");
        //ok
        try {
            for (int i = 0; i < getMbo().getMboSet("MTRL01CONEMP").count(); i++) {
                valorTotalEmpenho = Executa.somaValor(getMbo().getMboSet("MTRL01PROPAG"), "MTVALNOTFIS", "MTNUMEMP", getMbo().getMboSet("MTRL01CONEMP").getMbo(i).getString("MTNUMEMP"));
                getMbo().getMboSet("MTRL01CONEMP").getMbo(i).setValue("AMPAGAVINC", valorTotalEmpenho);
            }
        } catch (Exception e) {
            totaisEmpenho = 0D;
        }

        System.out.println("--- MTPur02 Vals5");
        //ok
        totalValorPrevisto = Executa.somaValor("MTVALPRE", getMbo().getMboSet("MTRL01PROPAG"));

        //ok
        getMbo().setValue("MTPROPAGTOTPRE", valorAtualContrato, MboConstants.NOACTION);

        //ok
        getMbo().setValue("MTPAG", pagamentos, MboConstants.NOACTION);

        //ok
        getMbo().setValue("MTSALATU", getMbo().getDouble("MTVALVIG") - pagamentos, MboConstants.NOACTION);

        //ok
        getMbo().setValue("MTEMPVALTOT", totaisEmpenho, MboConstants.NOACTION);


        //ok
        getMbo().setValue("MTPROPAGTOTPAG", pagamentos, MboConstants.NOACTION);



        //ok
        getMbo().setValue("MTEMPSALATU", totaisEmpenho - pagamentos, MboConstants.NOACTION);

        getMbo().setValue("MTEMPPAGVIN", pagamentos, MboConstants.NOACTION);
        getMbo().setValue("MTPROPAGTOTPRE", totalValorPrevisto, MboConstants.NOACTION);
        getMbo().setValue("MTVALATGA", valorAtualGarantia, MboConstants.NOACTION);
        getMbo().setValue("MTVENGA", vencimentoGarantia, MboConstants.NOACTION);
        getMbo().setValue("MTATDATVEN", vencimento, MboConstants.NOACTION);

        getMbo().setValue("MTATCONT", valorAtualContrato, MboConstants.NOACTION);
        getMbo().setValue("MTVALMESAT", novoValorMensal, MboConstants.NOACTION);

        getMbo().setValue("MTATDATASS", dataAssinatura, MboConstants.NOACTION);

        System.out.println("--- MTPur02 validade()");
        super.validate();
        System.out.println("--- MTPur02 SAVE fim()");

        return super.SAVE();
    }

    //@Override
    public synchronized void reseta() throws MXException {
        System.out.println(">>> reseta()");
        super.reset();
        try {
            Date startDate = new Date();
            //System.out.println("+++ tam " + getMbo().getThisMboSet().count());
            for (int i = 0; i < getMbo().getThisMboSet().count(); i++) {
                //System.out.println("+++ i " + i);
                if (!getMbo().getThisMboSet().getMbo(i).isNull("MTDATLIMVIG")) {
                    Date endDate = getMbo().getThisMboSet().getMbo(i).getDate("MTDATLIMVIG");

                    //System.out.println("---- startDate " + startDate);
                    //System.out.println("---- endDate   " + endDate);
                    int dias = Data.recuperaDiasEntreDatas(startDate, endDate);

                    //System.out.println("---- dias " + dias);

                    getMbo().getThisMboSet().getMbo(i).setValue("MEDIAAPOS", dias);
                } else {
                    getMbo().getThisMboSet().getMbo(i).setValue("MEDIAAPOS", 0);
                }
            }
            getMbo().getThisMboSet().save();
            refreshTable();
            reloadTable();
        } catch (Exception e) {
            System.out.println("--- e " + e.getMessage());
        }

    }
}
