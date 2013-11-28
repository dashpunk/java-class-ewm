package br.inf.id2.mintur.bean;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

/**
 *
 * @author Ricardo S Gomes
 */
public class Id2ap01pcoPrestacaocontasB extends psdi.webclient.system.beans.DataBean {

    public Id2ap01pcoPrestacaocontasB() {
        System.out.println("... Id2ap01pcoPrestacaocontas");
    }

    public int gerarParecer() throws MXException, RemoteException {
        System.out.println("...gerarParecer");

        int posicao = getMbo().getThisMboSet().getCurrentPosition();
        System.out.println("___ " + getMbo().getThisMboSet().getMbo(posicao).getString("FKRHSTSGLUO").length());
        if (getMbo().getThisMboSet().getMbo(posicao).getString("FKRHSTSGLUO").length() == 0 || getMbo().getThisMboSet().getMbo(posicao).getString("MXSTNUMANO").length() == 0) {
            throw new MXApplicationException("parecer", "anouOuUoVazio");
        }

        if (getMbo().getThisMboSet().getMbo(posicao).getString("MXSTNUMPAR").length() > 0) {
            throw new MXApplicationException("parecer", "parecerGerado");
        }

        String ano = getMbo().getThisMboSet().getMbo(posicao).getString("MXSTNUMANO");
        System.out.println("... ano " + ano);
        String doc = getMbo().getThisMboSet().getMbo(posicao).getString("MXSTNOMTIPNOT");
        System.out.println("... doc " + doc);

//        MboSet parecerMboSet;
//        parecerMboSet = (MboSet) psdi.server.MXServer.getMXServer().getMboSet("MXTBPARPRECON", sessionContext.getUserInfo());
//
//        parecerMboSet.setWhere("MXSTNUMANO = \'"+ano+"\' AND MXSTNOMTIPNOT = \'"+doc+"\'" + sessionContext.getUserInfo().getUserName() + "'");
//        parecerMboSet.setOrderBy("MXNUCODSEQ");
//        parecerMboSet.reset();

        MboSetRemote parecerMboSet = getMbo().getMboSet("RL01MXTBPARPRECON");
        
        long sequenciador = 1;
        if (parecerMboSet.count() > 0) {
            sequenciador = parecerMboSet.getMbo(parecerMboSet.count() - 1).getInt("MXNUCODSEQ")+1;
            if (sequenciador == 0) {
                sequenciador = 1;
            }
            System.out.println("... sequenciador " + sequenciador);
        }
        getMbo().getThisMboSet().getMbo(posicao).setValue("MXNUCODSEQ", sequenciador, MboConstants.NOACCESSCHECK);

        String uo = getMbo().getThisMboSet().getMbo(posicao).getString("FKRHSTSGLUO");
        System.out.println("... uo " + uo);
        String valor = uo + "/" + sequenciador + "/" + ano;
        System.out.println("... valor " + valor);
        getMbo().getThisMboSet().getMbo(posicao).setValue("MXSTNUMPAR", valor, MboConstants.NOACCESSCHECK);
        Utility.sendEvent(new WebClientEvent("refreshTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("reloadTable", app.getApp(), null, sessionContext));
        Utility.sendEvent(new WebClientEvent("SAVE", app.getApp(), null, sessionContext));
        return EVENT_HANDLED;
    }
}