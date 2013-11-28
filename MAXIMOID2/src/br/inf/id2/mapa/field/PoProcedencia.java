package br.inf.id2.mapa.field;

import java.rmi.RemoteException;
import psdi.mbo.Mbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;

public class PoProcedencia extends MboValueAdapter
{
  public PoProcedencia(MboValue mbv)
  {
    super(mbv);
  }

  public void validate() throws MXException, RemoteException
  {
    super.validate();


    getMboValue("ID2DESTIPEST").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESNUMCONT").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESABA").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESAGLCNPJ").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESTIPEVE").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESAGLNOMEVE").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESAGL").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESEXPPER").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESEXPPROP").setValueNull(MboConstants.NOACCESSCHECK);
    getMboValue("ID2DESEXP").setValueNull(MboConstants.NOACCESSCHECK);

    
    if (getMboValue().getMbo().getMboSet("POLINE").count() > 0) {
      MboSetRemote linhas = getMboValue().getMbo().getMboSet("POLINE");
      MboRemote mbo;
      for (int i = 0; (mbo = linhas.getMbo(i)) != null; i++)
      {
        mbo.setValue("ORDERQTY", 0);
      }

      linhas.deleteAll();
    }
  }
}