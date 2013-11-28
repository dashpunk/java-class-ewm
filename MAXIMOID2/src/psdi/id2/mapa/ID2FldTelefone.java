// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:FldPartialGLAccount.java
package psdi.id2.mapa;


import psdi.mbo.*;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

// Referenced classes of package psdi.app.financial:
//FinancialService
public class ID2FldTelefone extends psdi.mbo.MboValueAdapter {

    public ID2FldTelefone(MboValue mbv)
            throws MXException, RemoteException {
        super(mbv);
    }

    public void action()
            throws MXException, RemoteException {
        String param[] = {new String()};
        String tel = getMboValue().getString();
        javax.swing.text.MaskFormatter formatTel;
        tel = tel.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "");
        if (!tel.matches("^[0-9\\s]+$")) {
            throw new MXApplicationException("person", "TelInvalido", param);
        } else if (tel.length() == 8) {
            try {
                formatTel = new javax.swing.text.MaskFormatter("****-****");
                formatTel.setValueContainsLiteralCharacters(false);
                getMboValue().setValue(formatTel.valueToString(tel), 11L);
                super.action();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (tel.length() == 10 || tel.length() == 11) {
            try {
                formatTel = new javax.swing.text.MaskFormatter("(***)****-****");
                formatTel.setPlaceholderCharacter('x');
                formatTel.setValueContainsLiteralCharacters(false);
                getMboValue().setValue(formatTel.valueToString(tel), 11L);
                super.action();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (tel.length() == 12 || tel.length() == 13) {
            try {
                formatTel = new javax.swing.text.MaskFormatter("(*****)****-****");
                formatTel.setPlaceholderCharacter('x');
                formatTel.setValueContainsLiteralCharacters(false);
                getMboValue().setValue(formatTel.valueToString(tel), 11L);
                super.action();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new MXApplicationException("person", "TelInvalido", param);
        }
    }
}
