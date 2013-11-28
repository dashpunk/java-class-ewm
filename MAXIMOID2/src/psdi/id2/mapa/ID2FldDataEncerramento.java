// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java
package psdi.id2.mapa;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.app.company.FldCompanyCompany;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import java.util.Date;
import psdi.util.MXException;

import psdi.app.common.FldChangeStatus;

import psdi.app.rfq.virtual.*;

// Referenced classes of package psdi.app.financial:
//FinancialService
public class ID2FldDataEncerramento extends psdi.app.location.FldLocLocation {

    /**
     * Método construtor de ID2FldDataEncerramento
     */
    public ID2FldDataEncerramento(MboValue mbv) throws MXException {
        super(mbv);
    }

    /*@Override
    public void action() throws MXException, RemoteException {
        super.action();
        javax.swing.text.MaskFormatter formatDate = null;
        String aData = getMboValue().getString();
        aData.replaceAll("/", "").replaceAll("-", "");
        try {
            formatDate = new javax.swing.text.MaskFormatter("**//**//****");
        } catch (ParseException ex) {
            Uteis.espera("-------- "+ex.getMessage());
        }
        formatDate.setValueContainsLiteralCharacters(false);
        switch (getMboValue().getString().length()) {
            case 8:
                try {
                    getMboValue().setValue(formatDate.valueToString(aData), MboConstants.NOACCESSCHECK);
                } catch (ParseException ex) {
                    Uteis.espera("-------- "+ex.getMessage());
                }
            //caso o mbo esteja com a data redusida
            case 6:
                try {
                    formatDate.setValueContainsLiteralCharacters(false);
                    //antes de 50 século 21
                    if (Integer.valueOf(aData.substring(4, 2)) < 50) {
                        aData = aData.substring(0,4)+"20"+aData.substring(4,2);
                    //caso contrário século 20
                    } else {
                        aData = aData.substring(0,4)+"19"+aData.substring(4,2);
                    }
                    getMboValue().setValue(formatDate.valueToString(aData), MboConstants.NOACCESSCHECK);
                } catch (ParseException ex) {
                    Logger.getLogger(ID2FldDataEncerramento.class.getName()).log(Level.SEVERE, null, ex);
                }
            case 4:
                try {
                    formatDate.setValueContainsLiteralCharacters(false);
                    getMboValue().setValue(formatDate.valueToString(aData), MboConstants.NOACCESSCHECK);
                } catch (ParseException ex) {
                    Logger.getLogger(ID2FldDataEncerramento.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }*/

    /**
     *
     * Sobrescrita do método validate
     *<p>
     * Valida se a data de encerrament é posterior a data de status
     * @since 30/03/2010
     */
    public void validate() throws MXException, RemoteException {
        Date aDataEncerramento = getMboValue().getDate();
        Date aDataStatus = getMboValue("ID2DATSUS").getDate();

        super.validate();
        String param[] = {aDataEncerramento.toString()};
        if (aDataEncerramento.before(aDataStatus)) {
            return;
        } else {
            //busca mensagem do maximo, grupo mensagem e chave mensagem
            throw new MXApplicationException("company", "DataEncerramentoInvalida", param);
        }
    }
}
