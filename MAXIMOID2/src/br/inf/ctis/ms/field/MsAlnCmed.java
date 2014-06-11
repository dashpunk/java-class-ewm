package br.inf.ctis.ms.field;

import java.rmi.RemoteException;

import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class MsAlnCmed extends MboValueAdapter {


    public MsAlnCmed(MboValue mbv) throws MXException {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {

        super.validate();
        
        if (getMboValue().getString() == null) {
			 getMboValue().getMbo().setValueNull("MSTBGGREMID");
			 getMboValue().getMbo().setValueNull("MSALNCNPJ");
			 getMboValue().getMbo().setValueNull("MSALNCONV");
			 getMboValue().getMbo().setValueNull("MSALNCONV2");
			 getMboValue().getMbo().setValueNull("MSALNCONVENIO");
			 getMboValue().getMbo().setValueNull("MSALNFAB");
			 getMboValue().getMbo().setValueNull("MSALNGENERICO");
			 getMboValue().getMbo().setValueNull("MSALNNOMCOMERCIAL");
			 getMboValue().getMbo().setValueNull("MSALNPRATIVO");
			 getMboValue().getMbo().setValueNull("MSCODGGREM");
			 getMboValue().getMbo().setValueNull("MSNUMFATEMB");
			 getMboValue().getMbo().setValueNull("MSNUMPMVG");
			 getMboValue().getMbo().setValueNull("MSNUMPREC");
			 getMboValue().getMbo().setValueNull("MSNUMQNT");
			 getMboValue().getMbo().setValueNull("MSALNAPRESENTACAO");
		 } else if (getMboValue().getString().equalsIgnoreCase("NÃO")) {
			 getMboValue().getMbo().setValueNull("MSTBGGREMID");
			 getMboValue().getMbo().setValueNull("MSALNCNPJ");
			 getMboValue().getMbo().setValueNull("MSALNCONV");
			 getMboValue().getMbo().setValueNull("MSALNCONV2");
			 getMboValue().getMbo().setValueNull("MSALNCONVENIO");
			 getMboValue().getMbo().setValueNull("MSALNFAB");
			 getMboValue().getMbo().setValueNull("MSALNGENERICO");
			 getMboValue().getMbo().setValueNull("MSALNNOMCOMERCIAL");
			 getMboValue().getMbo().setValueNull("MSALNPRATIVO");
			 getMboValue().getMbo().setValueNull("MSCODGGREM");
			 getMboValue().getMbo().setValueNull("MSNUMFATEMB");
			 getMboValue().getMbo().setValueNull("MSNUMPMVG");
			 getMboValue().getMbo().setValueNull("MSNUMPREC");
			 getMboValue().getMbo().setValueNull("MSNUMQNT");
			 getMboValue().getMbo().setValueNull("MSALNAPRESENTACAO");
		 } else if (getMboValue().getString().equalsIgnoreCase("SIM")) {
			 getMboValue().getMbo().setValueNull("MSTBGGREMID");
			 getMboValue().getMbo().setValueNull("MSALNCNPJ");
			 getMboValue().getMbo().setValueNull("MSALNCONV");
			 getMboValue().getMbo().setValueNull("MSALNCONV2");
			 getMboValue().getMbo().setValueNull("MSALNCONVENIO");
			 getMboValue().getMbo().setValueNull("MSALNFAB");
			 getMboValue().getMbo().setValueNull("MSALNGENERICO");
			 getMboValue().getMbo().setValueNull("MSALNNOMCOMERCIAL");
			 getMboValue().getMbo().setValueNull("MSALNPRATIVO");
			 getMboValue().getMbo().setValueNull("MSCODGGREM");
			 getMboValue().getMbo().setValueNull("MSNUMFATEMB");
			 getMboValue().getMbo().setValueNull("MSNUMPMVG");
			 getMboValue().getMbo().setValueNull("MSNUMPREC");
			 getMboValue().getMbo().setValueNull("MSNUMQNT");
			 getMboValue().getMbo().setValueNull("MSALNAPRESENTACAO");
		 }
     

    }

}