package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 *
 * @author Ricardo S Gomes
 * 
 */
public class CompaniesCompany extends psdi.app.company.FldCompanyCompany {

    public CompaniesCompany(MboValue mbv) throws MXException {
        super(mbv);
    }
    

    @Override
    public void validate() throws MXException, java.rmi.RemoteException {
        if (getMboValue().getMbo().isNull("MTTIPPER") || getMboValue("MTTIPPER").getString().equals("")) {
            throw  new MXApplicationException("companies", "tipoPessoaIsNull");
        }
        super.validate();
    }

}
