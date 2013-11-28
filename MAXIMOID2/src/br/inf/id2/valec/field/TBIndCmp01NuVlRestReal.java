package br.inf.id2.valec.field;

import java.rmi.RemoteException;
import java.util.Date;

import br.inf.id2.common.util.Data;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * 
 * @author Ricardo S Gomes
 *  
 */
public class TBIndCmp01NuVlRestReal extends MboValueAdapter {

    public TBIndCmp01NuVlRestReal(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
        super.validate();
        if (getMboValue().isNull()) {
            System.out.println("------- TBIndCmp01NuVlRest ou TBINDCOMP01.NUVLRREAL is null");
            return ;
        } else {
            double valor = getMboValue().getDouble();
            if (valor <= 0) {
                throw new MXApplicationException("id2message", "valorInferiorIgualZero");
            }
        }
 
    }
}
