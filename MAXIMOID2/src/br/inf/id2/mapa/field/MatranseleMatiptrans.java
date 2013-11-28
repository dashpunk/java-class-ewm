package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;


/**
 * 
 * @author Ricardo S Gomes
 *
 */
public class MatranseleMatiptrans extends MboValueAdapter
{

	public MatranseleMatiptrans(MboValue mbv) {
		super(mbv);
	}
	
	@Override
	public void validate() throws MXException, RemoteException {
		
		super.validate();
		
		getMboValue("MAPONUM").setValueNull(MboConstants.NOACCESSCHECK);
		
		getMboValue("MAACEITATERMOS").setValue(false, MboConstants.NOACCESSCHECK);
		getMboValue("MALOCALDEST").setValueNull(MboConstants.NOACCESSCHECK);
		getMboValue("MALOCALORI").setValueNull(MboConstants.NOACCESSCHECK);
		
		getMboValue().getMbo().getMboSet("MARLTIPO01").deleteAndRemoveAll();
		getMboValue().getMbo().getMboSet("MARLTRANSELELINE").deleteAndRemoveAll();
		getMboValue().getMbo().getMboSet("MARLTIPO02").deleteAndRemoveAll();
		getMboValue().getMbo().getMboSet("MARLTIPO04").deleteAndRemoveAll();
		
//		getMboValue().getMbo().getThisMboSet().save();
		
	}	
	
}