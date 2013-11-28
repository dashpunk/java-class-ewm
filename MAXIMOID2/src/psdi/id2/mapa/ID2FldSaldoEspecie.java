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
/**
 * Esta classe é responsável por obter o saldo total por espécie
 * foi aplicada em ID2STOCOMM.ID2SALDO
 * @since 13/04/2010
 */
public class ID2FldSaldoEspecie extends MboValueAdapter {

    /**
     * Método construtor de ID2PropriedadeRuralAppBean
     */
    public ID2FldSaldoEspecie(MboValue mbv) throws MXException {
        super(mbv);
    }

    /**
     *
     * Sobrescrita do m?todo validate  BMXZZ0001E
     *<p>
     * Apresenta o saldo de uma esp?cie para um grupo de esp?cie
     *@since 10/03/2010
     */
    public void initValue() throws MXException, java.rmi.RemoteException {

        super.initValue();
        //Uteis.espera("---------apos super");
        Double total = 0D;
        for (int i = 0; i < getMboValue().getMbo().getMboSet("ID2SALDOESP").count(); i++) {
            total += getMboValue().getMbo().getMboSet("ID2SALDOESP").getMbo(i).getDouble("CURBALTOTAL");
        }
        //Uteis.espera("---------Total " + total);
        //Uteis.espera("---------obter sum() de ID2SALDOESP.CURBALTOTAL " + getMboValue().getMbo().getMboSet("ID2SALDOESP").sum("CURBALTOTAL") + "");
        //Uteis.espera("---------obter o primeiro ID2SALDOESP.INVBALANCES.CURBAL " + getMboValue().getMbo().getMboSet("ID2SALDOESP").getMbo(0).getMboSet("INVBALANCES").getMbo(0).getDouble("CURBAL") + "");
        //Uteis.espera("---------obter o primeiro de ID2SALDOESP.CURBALTOTAL " + getMboValue().getMbo().getMboSet("ID2SALDOESP").getMbo(0).getDouble("CURBALTOTAL") + "");
        getMboValue().setValue(total, MboConstants.NOACCESSCHECK);

    }

}
