package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class FormaProcedimento extends MboValueAdapter {


    public FormaProcedimento(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        
        if (getMboValue().getString() == null) {
        	getMboValue().getMbo().setValueNull("MSALCODTIPOCONTRATACAO");
        	getMboValue().getMbo().setValueNull("MS_REGPRECO");
        	getMboValue().getMbo().setValueNull("MSALCODEMBASAMENTOLEGAL");
        	getMboValue().getMbo().setValueNull("MSTBEMBASAMENTOLEGALID");
        	getMboValue().getMbo().setValueNull("MSIRP");
        	getMboValue().getMbo().setValueNull("MSJUSTIRP");
        	getMboValue().getMbo().setValueNull("MS_JUSTIFICATIVA");
        } else if (getMboValue().getString().equals("CONTRATACAO DIRETA")) {
        	getMboValue().getMbo().setValueNull("MSALCODTIPOCONTRATACAO");
        	getMboValue().getMbo().setValueNull("MS_REGPRECO");
        	getMboValue().getMbo().setValueNull("MSALCODEMBASAMENTOLEGAL");
        	getMboValue().getMbo().setValueNull("MSTBEMBASAMENTOLEGALID");
        	getMboValue().getMbo().setValueNull("MSIRP");
        	getMboValue().getMbo().setValueNull("MSJUSTIRP");
        	getMboValue().getMbo().setValueNull("MS_JUSTIFICATIVA");
        } else if (getMboValue().getString().equals("LICITACAO")){
        	getMboValue().getMbo().setValueNull("MSALCODTIPOCONTRATACAO");
        	getMboValue().getMbo().setValueNull("MSALCODEMBASAMENTOLEGAL");
        	getMboValue().getMbo().setValueNull("MSTBEMBASAMENTOLEGALID");
        	getMboValue().getMbo().setValueNull("MSIRP");
        	getMboValue().getMbo().setValueNull("MSJUSTIRP");
        	getMboValue().getMbo().setValueNull("MS_JUSTIFICATIVA");
        }

    }

}