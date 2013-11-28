// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java
package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.app.company.FldCompanyCompany;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

import psdi.app.common.FldChangeStatus;

import psdi.app.rfq.virtual.*;

// Referenced classes of package psdi.app.financial:
//FinancialService
public class ID2FldCpfPessoaFisica extends psdi.app.location.FldLocLocation {

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     */
    public ID2FldCpfPessoaFisica(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do método initValue
     *<p>
     * Cria uma nova linha no relacionamento ID2PHONE, caso não haja nenhum
     *@since 15/04/2010
     */

    public void initValue() throws MXException, java.rmi.RemoteException {

        super.initValue();

        MboRemote person = getMboValue().getMbo();

        try {

            if ((person != null) && (getMboValue().getMbo().getMboSet("FONE").count() == 0)) {

                getMboValue().getMbo().getMboSet("FONE").add();
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}