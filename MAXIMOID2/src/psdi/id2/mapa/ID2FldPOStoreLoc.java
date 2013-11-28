// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2FldPOStoreLoc.java

package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.FldCrossSiteLocation;
import psdi.app.pr.PRRemote;
import psdi.mbo.*;
import psdi.util.MXException;
import psdi.app.po.*;

// Referenced classes of package psdi.app.po:
//            PORemote

public class ID2FldPOStoreLoc extends FldCrossSiteLocation
{

    public ID2FldPOStoreLoc(MboValue mbv)
        throws MXException, RemoteException
    {
        super(mbv, "CROSSSITESTOREROOM");
        sqlWhere = getListCriteria();
        setLookupKeyMapInOrder(new String[] {
            "storelocsiteid", "storeloc"
        }, new String[] {
            "siteid", "location"
        });
    }

    public void init()
        throws MXException, RemoteException
    {
        MboRemote mboRemote = getMboValue().getMbo();
        if(mboRemote.toBeAdded())
            return;
        MboSetRemote lineSet = null;
        if(mboRemote instanceof PRRemote)
            lineSet = mboRemote.getMboSet("PRLINE");
        else
        if(mboRemote instanceof PORemote)
            lineSet = mboRemote.getMboSet("POLINE");
        if(!lineSet.isEmpty())
            mboRemote.setFieldFlag("storeloc", 7L, true);
    }

    public MboSetRemote getList()
        throws MXException, RemoteException
    {
        MboRemote mboRemote = getMboValue().getMbo();
        if(mboRemote.isZombie())
        {
            SqlFormat sqlf = new SqlFormat(mboRemote, (new StringBuilder()).append(sqlWhere).append(" and useinpopr = :yes ").toString());
            setListCriteria(sqlf.format());
        } else
        if(mboRemote.getString("storelocsiteid").equals(""))
            setListCriteria((new StringBuilder()).append(sqlWhere).append(" and useinpopr = :yes and orgid in (select a.orgid from organization a, organization b where a.itemsetid = b.itemsetid and b.orgid = :orgid) and location in (select location from id2locationusercust where personid=:id2pessoa and id2tipolocal=:id2tipolocal)").toString());
        else
            setListCriteria((new StringBuilder()).append(sqlWhere).append(" and useinpopr = :yes and siteid = :storelocsiteid and location in (select location from id2locationusercust where personid=:id2pessoa and id2tipolocal=:id2tipolocal)").toString());
        return super.getList();
    }

    String sqlWhere;
}
