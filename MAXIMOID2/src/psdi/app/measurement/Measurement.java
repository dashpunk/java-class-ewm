package psdi.app.measurement;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.app.asset.*;
import psdi.app.meter.DeployedMeterRemote;
import psdi.app.meter.MeterRemote;
import psdi.mbo.*;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;

// Referenced classes of package psdi.app.measurement:
//            MeasurePointRemote, MeasurePoint, MeasurementRemote

public class Measurement extends Mbo
    implements MeasurementRemote
{

    private static final long serialVersionUID = 1L;
    private MeterRemote thisMeter;
    private static final Vector measAttrsForInitFieldFlagsOnMbo;
    private boolean meterInfoHasBeenSet;

    public Measurement(MboSet ms)
        throws RemoteException
    {
        super(ms);
        thisMeter = null;
        meterInfoHasBeenSet = false;
    }

    public void init()
        throws MXException
    {
        super.init();
        String alwaysReadOnly[] = {
            "pointnum", "assetnum", "location", "metername", "meterreadingid", "locmeterreadingid", "measurementid"
        };
        setFieldFlag(alwaysReadOnly, 7L, true);
        setFieldFlag("measuredate", 7L, !toBeAdded());
        meterInfoHasBeenSet = false;
        try
        {
            AssetServiceRemote assetService = (AssetServiceRemote)MXServer.getMXServer().lookup("ASSET");
            assetService.initLinearAttrs(this, "");
        }
        catch(RemoteException re) { }
    }

    public boolean isChangeByUserWhenSetFromLookup(String lookupAttrName, String attributeName)
    {
        boolean ret = !attributeName.equalsIgnoreCase("STARTBASEMEASURE") && !attributeName.equalsIgnoreCase("ENDBASEMEASURE") && !attributeName.equalsIgnoreCase("BASEMEASUREUNITID") && !attributeName.equalsIgnoreCase("STARTMEASURE") && !attributeName.equalsIgnoreCase("ENDMEASURE") && !attributeName.equalsIgnoreCase("STARTOFFSET") && !attributeName.equalsIgnoreCase("ENDOFFSET") && !attributeName.equalsIgnoreCase("STARTMEASUREUNITID") && !attributeName.equalsIgnoreCase("ENDMEASUREUNITID");
        return ret;
    }

    public void initFieldFlagsOnMbo(String attrName)
        throws MXException
    {
        try
        {
            if(measAttrsForInitFieldFlagsOnMbo.contains(attrName))
            {
                MboRemote owner = getOwner();
                if(owner != null && owner.isBasedOn("MEASUREPOINT"))
                {
                    setMeterRelatedInfo();
                }
            }
        }
        catch(RemoteException re) { }
    }

    protected void setMeterRelatedInfo()
        throws MXException, RemoteException
    {
        if(meterInfoHasBeenSet && thisMeter != null && thisMeter.getString("metername").equalsIgnoreCase(getString("metername")))
        {
            return;
        }
        thisMeter = getTheMeter();
        if(thisMeter == null)
        {
            return;
        }
        meterInfoHasBeenSet = true;
        if(thisMeter.isCharacteristic())
        {
            setValue("domainid", thisMeter.getString("domainid"), 11L);
            if(!isNull("MEASUREMENTVALUE"))
            {
                setValueNull("MEASUREMENTVALUE", 2L);
            }
            setFieldFlag("MEASUREMENTVALUE", 7L, true);
            setFieldFlag("OBSERVATION", 7L, false);
            setFieldFlag("MEASUREMENTVALUE", 128L, false);
            setFieldFlag("OBSERVATION", 128L, true);
        } else
        if(thisMeter.isGauge())
        {
            if(!isNull("domainid"))
            {
                setValueNull("domainid", 11L);
            }
            if(!isNull("OBSERVATION"))
            {
                setValueNull("OBSERVATION", 2L);
            }
            setFieldFlag("OBSERVATION", 7L, true);
            setFieldFlag("MEASUREMENTVALUE", 7L, false);
            setFieldFlag("MEASUREMENTVALUE", 128L, true);
            setFieldFlag("OBSERVATION", 128L, false);
        } else
        {
            setFieldFlag("OBSERVATION", 7L, true);
            setFieldFlag("MEASUREMENTVALUE", 7L, true);
        }
    }

    public void add()
        throws MXException, RemoteException
    {
    	System.out.println("######################## Entrei no método ADD - Measurement");
        MboRemote owner = getOwner();
        setFieldFlag("startmeasure", 7L, true);
        setFieldFlag("endmeasure", 7L, true);
        MboRemote asset = null;
        MboRemote assetmeter = null;
        if(owner != null)
        {
        	System.out.println("######################## Measurement - siteid");
            setValue("siteid", owner.getString("siteid"), 11L);
            System.out.println("######################## Measurement - orgid");
            setValue("orgid", owner.getString("orgid"), 11L);
            if(!(owner instanceof DeployedMeterRemote))
            {
                String inspector = getUserInfo().getUserName();
                if(owner instanceof MeasurePointRemote)
                {
                	System.out.println("######################## Measurement - poitnum");
                    setValue("pointnum", owner.getString("pointnum"), 11L);
                    System.out.println("######################## Measurement - assetnum");
                    setValue("assetnum", owner.getString("assetnum"), 11L);
                    System.out.println("######################## Measurement - location");
                    setValue("location", owner.getString("location"), 11L);
                    System.out.println("######################## Measurement - metername");
                    setValue("metername", owner.getString("metername"), 11L);
                    asset = owner.getMboSet("ASSET").getMbo(0);
                    owner.setFieldFlag("metername", 7L, true);
                } else
                {
                    if(owner.isBasedOn("WORKORDER") && !owner.isNull("inspector"))
                    {
                        inspector = owner.getString("inspector");
                    }
                    System.out.println("######################## Measurement - pointnum 2");
                    setValue("pointnum", owner.getString("pointnum"), 11L);
                    MboRemote measurepoint = getMboSet("$MEASUREPOINT_FOR_MEASUREMENT", "MEASUREPOINT", "pointnum=:pointnum and siteid=:siteid").getMbo(0);
                    if(measurepoint != null)
                    {
                    	System.out.println("######################## Measurement - assetnum 2");
                        setValue("assetnum", measurepoint.getString("assetnum"), 11L);
                        System.out.println("######################## Measurement - location 2");
                        setValue("location", measurepoint.getString("location"), 11L);
                        System.out.println("######################## Measurement - metername 2");
                        setValue("metername", measurepoint.getString("metername"), 11L);
                    }
                    System.out.println("######################## Measurement - inspector");
                    setValue("inspector", inspector, 11L);
                }
                MboRemote ownersOwner = owner.getOwner();
                if(ownersOwner != null && (ownersOwner instanceof AssetRemote) && ownersOwner.toBeAdded())
                {
                    asset = ownersOwner;
                } else
                if(owner.isBasedOn("WORKORDER"))
                {
                    asset = getMboSet("$ASSET_FOR_MEASUREMENT", "ASSET", "assetnum=:assetnum and siteid=:siteid").getMbo(0);
                }
            } else
            if(owner instanceof AssetMeterRemote)
            {
                asset = owner.getMboSet("ASSET").getMbo(0);
                assetmeter = owner;
            }
            if(asset != null)
            {
            	System.out.println("######################## Measurement - assetid");
                setValue("assetid", asset.getInt("assetid"), 11L);
                if(asset.getBoolean("islinear"))
                {
                    setFieldFlag("startmeasure", 7L, false);
                    setFieldFlag("endmeasure", 7L, false);
                    if(owner instanceof MeasurePointRemote)
                    {
                        MboSetRemote assetmeterSet = owner.getMboSet("ASSETMETER");
                        if(!assetmeterSet.isEmpty())
                        {
                            assetmeter = assetmeterSet.getMbo(0);
                        }
                    }
                    if(assetmeter != null)
                    {
                    	System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDMEASURE", assetmeter.getString("ENDMEASURE"), 2L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTMEASURE", assetmeter.getString("STARTMEASURE"), 2L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTOFFSET", assetmeter.getString("STARTOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDOFFSET", assetmeter.getString("ENDOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTYOFFSET", assetmeter.getString("STARTYOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDYOFFSET", assetmeter.getString("ENDYOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTZOFFSET", assetmeter.getString("STARTZOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDZOFFSET", assetmeter.getString("ENDZOFFSET"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTYOFFSETREF", assetmeter.getString("STARTYOFFSETREF"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDYOFFSETREF", assetmeter.getString("ENDYOFFSETREF"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTZOFFSETREF", assetmeter.getString("STARTZOFFSETREF"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDZOFFSETREF", assetmeter.getString("ENDZOFFSETREF"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTFEATURELABEL", assetmeter.getString("STARTFEATURELABEL"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDFEATURELABEL", assetmeter.getString("ENDFEATURELABEL"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("STARTASSETFEATUREID", assetmeter.getString("STARTASSETFEATUREID"), 11L);
                        System.out.println("######################## Measurement - ENDMEASURE");
                        setValue("ENDASSETFEATUREID", assetmeter.getString("ENDASSETFEATUREID"), 11L);
                    }
                }
            }
        }
    }

    public void delete()
        throws MXException, RemoteException
    {
        super.delete();
        MboRemote point = getOwner();
        if((point instanceof MeasurePointRemote) && point.toBeAdded())
        {
            MboSetRemote measurements = point.getMboSet("MEASUREMENT");
            int additions = measurements.count(2);
            MboRemote measurement = null;
            int newMeasuresMarkedForDeletion = 0;
            if(!measurements.isEmpty() && additions > 0)
            {
                while((measurement = measurements.getMbo(newMeasuresMarkedForDeletion)) != null && measurement.toBeDeleted()) 
                {
                    newMeasuresMarkedForDeletion++;
                }
                point.setFieldFlag("metername", 7L, newMeasuresMarkedForDeletion != additions);
            }
        }
    }

    protected MeterRemote getTheMeter()
        throws MXException, RemoteException
    {
        if(thisMeter == null || !thisMeter.getString("metername").equalsIgnoreCase(getString("metername")))
        {
            MboRemote owner = getOwner();
            if(owner != null && owner.isBasedOn("MEASUREPOINT"))
            {
                thisMeter = ((MeasurePoint)owner).getTheMeter();
            } else
            {
                thisMeter = (MeterRemote)getMboSet("METER").getMbo(0);
            }
        }
        return thisMeter;
    }

    public void save()
        throws MXException, RemoteException
    {
        MboRemote owner = getOwner();
        if(owner != null && !(owner instanceof DeployedMeterRemote))
        {
            String reading = "";
            if(!isNull("observation"))
            {
                reading = getString("observation");
            } else
            {
                reading = getString("measurementvalue");
            }
            MboRemote dm = null;
            if(owner instanceof MeasurePointRemote)
            {
                dm = ((MeasurePoint)owner).getDeployedMeter();
            } else
            {
                MboRemote measurepoint = getMboSet("$MEASUREPOINT_FOR_MEASUREMENT", "MEASUREPOINT", "pointnum=:pointnum and siteid=:siteid").getMbo(0);
                if(measurepoint != null)
                {
                    dm = ((MeasurePoint)measurepoint).getDeployedMeter();
                }
            }
            if(dm != null && ((DeployedMeterRemote)dm).isReadingMostRecentReading(getDate("measuredate")))
            {
                dm.setValue("lastreading", reading, 11L);
                dm.setValue("lastreadingdate", getDate("measuredate"), 11L);
                dm.setValue("lastreadinginspctr", getString("inspector"), 11L);
            }
        }
        super.save();
    }

    static 
    {
        measAttrsForInitFieldFlagsOnMbo = new Vector();
        measAttrsForInitFieldFlagsOnMbo.add("DOMAINID");
        measAttrsForInitFieldFlagsOnMbo.add("MEASUREMENTVALUE");
        measAttrsForInitFieldFlagsOnMbo.add("OBSERVATION");
    }
}