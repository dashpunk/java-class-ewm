package br.inf.id2.me.bean;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.beans.common.StatefulAppBean;

/**
 * @author Patrick
 */
public class CadastroPessoaFisicaJuridica extends StatefulAppBean {

    public CadastroPessoaFisicaJuridica() {
//    	System.out.println("*** CadastroPessoaFisicaJuridica ***");
    }

    @Override
    public int SAVE() throws RemoteException, MXException {

//        System.out.println("*** SAVE ***");

        getMbo().setValue("MXSALVO", true);

        return super.SAVE();
    }
}
