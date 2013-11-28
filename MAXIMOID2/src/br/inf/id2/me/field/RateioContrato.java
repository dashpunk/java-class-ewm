package br.inf.id2.me.field;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo Dantas
 *  
 */
public class RateioContrato extends MboValueAdapter {

    public RateioContrato(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();

        //Verifica os demais rateios (porcentagens)
        int percentualAtual = getMboValue().getInt();
        int percentualSoma = 0;
        System.out.println("############## Porcentagem do contrato atual=" + percentualAtual);

        MboSet mboSetRateio = (MboSet) getMboValue().getMbo().getThisMboSet();
        System.out.println("################ Mbo do Rateio=" + mboSetRateio);
        if (mboSetRateio != null) {
            System.out.println("############## Tamanho do MBO = " + mboSetRateio.count());
            for (int i = 0; i < mboSetRateio.count(); i++) {
                Mbo mboRateio = (Mbo) mboSetRateio.getMbo(i);
                percentualSoma += mboRateio.getInt("MXPERCONT");
            }
        }
        System.out.println("############### Soma do percentual=" + percentualSoma);

        if (percentualSoma > 100) {
            throw new MXApplicationException("contrato", "RateioMaiorQueCemPorcento");
        }

        //Calcula a porcentagem com base no valor do contrato
        System.out.println(getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW"));
        System.out.println(getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW").getMbo(0));
        //System.out.println(getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW").getMbo(0).getDouble("MTATCONT"));
        if (getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW").getMbo(0) == null) {
        	throw new MXApplicationException("contrato", "NaoExisteValorContrato");
        }
        
        double valorContrato = getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW").getMbo(0).getDouble("MTATCONT");
        System.out.println("############ Valor do contrato=" + valorContrato);
        double valorRateio = (valorContrato * percentualAtual) / 100;
        getMboValue().getMbo().setValue("MXVALRAT", valorRateio);


        // Solicitado pelo Leonardo  -  MXSALATU = MXVALRAT - MXSALDO
//        double saldoAtual = getMboValue().getMbo().getDouble("MXVALRAT") - getMboValue().getMbo().getDouble("MXSALDO");
//        getMboValue().getMbo().setValue("MXSALATU", saldoAtual);

        Double saldoAtual = 0D;
        saldoAtual = getMboValue().getMbo().getMboSet("MXRL01PURCHVIEW").getMbo(0).getDouble("MTATCONT");

        MboRemote mboRateio;

        if (mboSetRateio != null) {
            System.out.println("___ rateio saldo count() " + mboSetRateio.count());
            for (int i = 0; ((mboRateio = mboSetRateio.getMbo(i)) != null); i++) {
                System.out.println("___ rateio saldo i " + i);
                System.out.println("___ rateio saldo v1 " + getMboValue().getMbo().getDouble("MXVALRAT"));
                saldoAtual -= getMboValue().getMbo().getDouble("MXVALRAT");
                getMboValue().getMbo().setValue("MXSALATU", saldoAtual);
            }
        }

    }
}
