package br.inf.id2.ms.field;

import br.inf.id2.common.util.Executa;
import psdi.mbo.*;
import java.rmi.RemoteException;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author ID2
 *
 * Antes extendia de: psdi.app.common.purchasing.FldPurOrderQty
 * 
 */
public class MSTbIteRecMSQtd extends MboValueAdapter {

    /**
     * Método construtor de ID2FldOrderQty
     * @param mbv 
     * @throws MXException
     */
    public MSTbIteRecMSQtd(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();

        System.out.println("---------> nome do atributo "+getMboValue().getName());
        
        //Double valor = Executa.somaValor(getMboValue().getName(), getMboValue().getMbo().getThisMboSet());
        Double valor = Executa.somaValor(getMboValue().getName(), getMboValue().getMbo().getMboSet("MS_RL01ITEREC"));

        valor += getMboValue().getDouble();
        
        System.out.println("---------> valor = " + valor);

        if (valor > getMboValue().getMbo().getMboSet("PRLINE").getMbo(0).getDouble("ID2QNTPEDIDO")) {
            throw new MXApplicationException("company", "ID2QNTENTREGAInvalida1");
        }

    }
}
