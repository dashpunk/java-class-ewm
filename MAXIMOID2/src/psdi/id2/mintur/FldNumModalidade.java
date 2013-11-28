package psdi.id2.mintur;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * 
 * @author Dyogo
 *
 */

public class FldNumModalidade extends MboValueAdapter
{

	public FldNumModalidade(MboValue mbv) {
		super(mbv);
	}

	public void validate()
		throws MXException, RemoteException	{
		
		super.validate();
		
		MboSetRemote mboSetRemote;
		String sModalidade = getMboValue().getMbo().getString("MTMODPROCOM");
		String sNumero = "001";
		if (sModalidade != null) {
			if (sModalidade.trim().equals("01")) {
				mboSetRemote = getMboValue().getMbo().getMboSet("MTRL01PROCOM");
				System.out.println("############ Dispensa:" +mboSetRemote.count());
				sNumero = recuperaNumero(mboSetRemote);
			} else {
				mboSetRemote = getMboValue().getMbo().getMboSet("MTRL01PROCOMINEX");
				System.out.println("############ Inex:" +mboSetRemote.count());
				sNumero = recuperaNumero(mboSetRemote);
			}
		}
		String sAno = new GregorianCalendar().get(GregorianCalendar.YEAR) + "";
		getMboValue().getMbo().setValue("MTNUMMOD",sNumero + "/" +sAno);
		
	}

	private String recuperaNumero(MboSetRemote mboSetRemote) throws MXException, RemoteException {
		mboSetRemote.setOrderBy("MTNUMMOD ASC");
		mboSetRemote.reset();
		String sNumero = "001";
		if (mboSetRemote.count() > 0) {
			sNumero = mboSetRemote.getMbo(mboSetRemote.count()-1).getString("MTNUMMOD");
			if (!sNumero.equals("")) {
				int iNumero = Integer.parseInt(sNumero.substring(0,3));
				iNumero++;
				sNumero = iNumero + "";
				if (sNumero.length() == 1) {
					sNumero = "00" + sNumero;
				} else if (sNumero.length() == 2) {
					sNumero = "0" + sNumero;
				}
			}
		}
		return sNumero;
	}
}