package br.inf.id2.common.field;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

/**
 * @author Patrick
 */
public class AnoAtualMaisUm extends MboValueAdapter {

	public AnoAtualMaisUm(MboValue mbv) throws MXException {
		super(mbv);
	}

	@Override
	public void initValue() throws MXException, RemoteException {
		super.initValue();
//		 System.out.println("*** AnoAtualMaisUm ***");

		if (getMboValue().getMbo().isNew()) {
//			System.out.println("*** isNew ***");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int anoCorrente = calendar.get(Calendar.YEAR);
//			 System.out.println("*** calendar YEAR "+anoCorrente);
			getMboValue().getMbo().setFieldFlag(getMboValue().getString(),MboConstants.READONLY, false);
			getMboValue().setValue(String.valueOf(anoCorrente+1));
			getMboValue().getMbo().setFieldFlag(getMboValue().getString(),MboConstants.READONLY, true);
		}
	}
}
