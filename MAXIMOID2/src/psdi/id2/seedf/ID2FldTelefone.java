package psdi.id2.seedf;

import psdi.id2.*;
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
            throw new MXApplicationException("company", "TelInvalido", param);
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
            throw new MXApplicationException("company", "TelInvalido", param);
        }
    }
}
