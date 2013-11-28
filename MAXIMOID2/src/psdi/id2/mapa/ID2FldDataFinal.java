package psdi.id2.mapa;

import psdi.mbo.*;
import psdi.server.MXServer;

import psdi.util.MXApplicationException;
import java.rmi.RemoteException;
import psdi.util.MXException;

public class ID2FldDataFinal extends MboValueAdapter
{

	public ID2FldDataFinal(MboValue mbv) 
		throws MXException, java.rmi.RemoteException
	{
		super(mbv);
	}

	public void validate()
            throws MXException,	java.rmi.RemoteException
	{
        java.util.Date aDataInicial = getMboValue().getMbo().getDate("ID2DATINI");
        java.util.Date aDataFinal = getMboValue().getMbo().getDate("ID2DATFIM");

        if (aDataFinal != null && aDataInicial != null) {
            if (!(aDataFinal.after(aDataInicial))) {
                throw new MXApplicationException("company", "DataFinalDoencaInvalida");
			}
        }
		super.validate();
	}
}