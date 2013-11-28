package br.inf.id2.mintur.field;

import java.rmi.RemoteException;
import java.util.Date;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

public class DiariasSGAP01 extends MboValueAdapter {
	
	
	public DiariasSGAP01(MboValue mbv) {
        super(mbv);
    }

	@Override
	public void validate() throws MXException, RemoteException {
		
		if((getMboValue().getMbo().getDate("SGHORINI").after(getMboValue().getMbo().getDate("SGHORFIN")))) {
			throw new MXApplicationException("horario", "horarioInicialMenorFinal");
		} 
			
		Date horas = Data.getDiferencaHoras(getMboValue().getMbo().getDate("SGHORINI"), getMboValue().getMbo().getDate("SGHORFIN"));
		getMboValue().getMbo().setValue("SGHORTOT", horas, MboConstants.NOACCESSCHECK);
		getMboValue().getMbo().setFieldFlag("SGHORTOT", MboConstants.READONLY, true);
		
			
			
			

		super.validate();
	}

}
