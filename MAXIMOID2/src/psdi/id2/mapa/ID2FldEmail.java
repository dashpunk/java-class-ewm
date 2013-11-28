// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java
package psdi.id2.mapa;

import psdi.app.pr.*;
import psdi.app.common.FldChangeStatus;
import psdi.app.rfq.virtual.*;
import psdi.app.rfq.*;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService
public class ID2FldEmail extends MboValueAdapter {

    public ID2FldEmail(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    public void validate()
            throws MXException, RemoteException {
        String param[] = {new String()};
        super.validate();
        if (getMboValue().getString().indexOf("@") > -1) {
            return;
        } else {
            throw new MXApplicationException("company", "EmailInvalido", param);
        }
    }
}
