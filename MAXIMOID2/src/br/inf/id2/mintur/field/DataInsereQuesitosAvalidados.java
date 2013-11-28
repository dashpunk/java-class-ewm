package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;

/**
 * @author Dyogo
 */
public class DataInsereQuesitosAvalidados extends MboValueAdapter {

    public DataInsereQuesitosAvalidados(MboValue mbv) throws MXException {
        super(mbv);
        System.out.println("########### DataInsereQuesitosAvalidados");
    }

    @Override
    public void validate() throws MXException, RemoteException {

        System.out.println("############# validate");
        //Verifica se uma data foi preenchida
        Date dataAval = getMboValue().getDate();
        //Tabela de quesitos avaliados
        MboSetRemote mboSetQues = getMboValue().getMbo().getMboSet("RHRLQUESAV01");
        System.out.println("########## Data de avaliacao = " + dataAval);
        System.out.println("########## MboSet Quesitos avaliados = " + mboSetQues);

        if (dataAval != null) {

            String yesNoId = getClass().getName();
            int userInput = MXApplicationYesNoCancelException.getUserInput(yesNoId, MXServer.getMXServer(), getMboValue().getMbo().getUserInfo());
            System.out.println("retorno = " + userInput);
            switch (userInput) {

                case MXApplicationYesNoCancelException.NULL:
                    System.out.println("---------------- userImpot null");
                    throw new MXApplicationYesNoCancelException(yesNoId, "uo", "DataAvaliacaoNull", new String[]{getMboValue().getMbo().getMboSet("RHRLAVAL01").getMbo(0).getString("RHSTNOMAVALIACAO"),
                                getMboValue().getMbo().getMboSet("RHRLCASE01").getMbo(0).getMboSet("RHRLPERSON01").getMbo(0).getString("DISPLAYNAME")});

                case MXApplicationYesNoCancelException.NO:
                    System.out.println("############# NADA");
                    break;

                case MXApplicationYesNoCancelException.YES:
//                    getMboValue().getMbo().getThisMboSet().save();
                    System.out.println("################# Confirmou QUESITOS");
//                    mboSetQues.deleteAndRemoveAll();
//                    mboSetQues.save();
                    //Pega o quesito atrav�s de um relacionamento
                    MboSetRemote mboSetAval = getMboValue().getMbo().getMboSet("RHRLQUES01");
                    System.out.println("######### MboSetAval count = " + mboSetAval.count());

                    for (int i = 0; i < mboSetAval.count(); i++) {
                        System.out.println("############### i=" + i);
                        //Seta na tabela de quesitos os relacionamentos recuperados...
                        MboRemote mboQues = mboSetQues.add();
                        mboQues.setValue("RHTBAVSE01ID", getMboValue().getMbo().getInt("RHTBAVSE01ID"));
                        mboQues.setValue("RHTBQUES01ID", mboSetAval.getMbo(i).getInt("RHTBQUES01ID"));
//                        mboSetQues.save();
                    }

                //Deixando readonly os campos
//	    		System.out.println("############# Bloqueando e salvando...");
//	    		getMboValue().getMbo().getThisMboSet().save();
//	    		getMboValue().getMbo().setFieldFlag("RHTBAVAL01ID", MboConstants.READONLY, true);
//	    		getMboValue().getMbo().setFieldFlag("RHTBCASE01ID", MboConstants.READONLY, true);
//	    		getMboValue().getMbo().setFieldFlag("RHDTDTAAVALIACAO", MboConstants.READONLY, true);
//	    		getMboValue().getMbo().setFieldFlag("RHSTCODNUMAVAL", MboConstants.READONLY, true);


            }
        }

    }
}
