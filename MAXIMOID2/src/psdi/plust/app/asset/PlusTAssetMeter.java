// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.plust.app.asset;

import java.rmi.RemoteException;
import java.util.*;
import psdi.app.asset.AssetMeter;
import psdi.app.meter.MeterRemote;
import psdi.app.meter.ReadingRemote;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.server.MaxVarServiceRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

// Referenced classes of package psdi.plust.app.asset:
//            PlusTAsset, PlusTMtrChngSetRemote, PlusTMtrChngRemote, PlusTAssetMeterSet, 
//            PlusTAssetServiceRemote, PlusTAssetMeterRemote

public class PlusTAssetMeter extends AssetMeter
    implements PlusTAssetMeterRemote
{

    public PlusTAssetMeter(MboSet mboset)
        throws MXException, RemoteException
    {
    	
        super(mboset);
        deployKey = "";
        readingTable = "";
        avgCalcKey = "";
        coreAvgCalcSql = "";
        newMeterReading = null;
        modifiedTRReadings = new Hashtable();
        canRolldownFromSource = true;
        importFlag = false;
        ignoreWarns = false;
    }

    public boolean getImportFlag()
    {
        return importFlag;
    }

    public void setImportFlag(boolean flag)
    {
        importFlag = flag;
    }

    public boolean getIgnoreWarnsFlag()
    {
        return ignoreWarns;
    }

    public void setIgnoreWarnsFlag(boolean flag)
    {
        ignoreWarns = flag;
    }

    public MboSetRemote getMboSet(String s)
        throws MXException, RemoteException
    {
        MboSetRemote mbosetremote = super.getMboSet(s);
        if(s.equals("METERREADING"))
        {
            MboRemote mboremote = getOwner();
            if(mboremote != null && mboremote.isBasedOn("PLUSTCLAIM") && !mboremote.isNull("incidentdate"))
            {
                SqlFormat sqlformat = new SqlFormat("readingdate <= :1");
                sqlformat.setTimestamp(1, mboremote.getDate("incidentdate"));
                mbosetremote.setWhere(sqlformat.format());
                mbosetremote.setFlag(39L, true);
                mbosetremote.reset();
                mbosetremote.setOrderBy("readingdate desc");
            }
        }
        return mbosetremote;
    }

    public void add()
        throws MXException, RemoteException
    {
        super.add();
        setValue("plustinitltd", 0, 3L);
        setValue("plustinitrdng", 0, 3L);
    }

    public void setTypeSpecificFlags()
        throws MXException, RemoteException
    {
        super.setTypeSpecificFlags();
        MeterRemote meterremote = getTheMeter();
        String as[] = {
            "plustitem", "plustcommodity", "plustcapacity", "plustdailylimit", "plustinitltd", "plustinitrdng"
        };
        if(meterremote != null && meterremote.isContinuous())
        {
            setFieldFlag("rolldownsource", 7L, !canRolldownFromSource());
            if(toBeAdded())
            {
                setFieldFlag(as, 7L, false);
                if(!isNull("readingtype") && "DELTA".equalsIgnoreCase(getTranslator().toInternalString("READINGTYPE", getString("readingtype"))))
                {
                    setFieldFlag("rollover", 128L, false);
                    setValueNull("rollover", 11L);
                    setFieldFlag("rollover", 7L, true);
                    setValue("plustinitltd", 0, 11L);
                    setValue("plustinitrdng", 0, 11L);
                    setFieldFlag("plustinitltd", 7L, true);
                    setFieldFlag("plustinitrdng", 7L, true);
                    if(isNull("plustitem") && isNull("plustcommodity"))
                    {
                        setFieldFlag("plustitem", 7L, false);
                        setFieldFlag("plustcommodity", 7L, false);
                    } else
                    if(isNull("plustitem"))
                    {
                        setFieldFlag("plustitem", 7L, true);
                        setFieldFlag("plustcommodity", 7L, false);
                    } else
                    if(isNull("plustcommodity"))
                    {
                        setFieldFlag("plustitem", 7L, false);
                        setFieldFlag("plustcommodity", 7L, true);
                    }
                    if(!canRolldownFromSource())
                        setFieldFlag("rolldownsource", 7L, true);
                } else
                {
                    if(!isNull("readingtype") && "ACTUAL".equalsIgnoreCase(getTranslator().toInternalString("READINGTYPE", getString("readingtype"))))
                    {
                        setFieldFlag("rollover", 7L, false);
                        setFieldFlag("rollover", 128L, true);
                        setValueNull("plustcapacity", 11L);
                        setFieldFlag("plustcapacity", 7L, true);
                        setFieldFlag("plustinitltd", 7L, false);
                        setFieldFlag("plustinitltd", 128L, true);
                        getMboValue("plustinitrdng").setFlag(128L, true);
                        if(getDouble("rollover") > 0.0D)
                            setFieldFlag("plustinitrdng", 7L, false);
                        else
                            setFieldFlag("plustinitrdng", 7L, true);
                    }
                    setFieldFlag("plustitem", 7L, true);
                    setFieldFlag("plustcommodity", 7L, true);
                }
            } else
            {
                setFieldFlag("readingtype", 7L, true);
            }
        } else
        {
            setValueNull("readingtype", 11L);
            setValueNull("avgcalcmethod", 11L);
            setValueNull("plustdailylimit", 11L);
            setValueNull("plustcommodity", 11L);
            setValueNull("plustitem", 11L);
            setValueNull("plustcapacity", 11L);
            setValue("plustinitltd", 0, 11L);
            setValue("plustinitrdng", 0, 11L);
            setValueNull("rollover", 11L);
            setFieldFlag("rollover", 128L, false);
            setFieldFlag(as, 7L, true);
        }
    }

    public void init()
        throws MXException
    {
        super.init();
        try
        {
            if(hasActualParent())
            {
                setCanRolldownFromSource(false);
                setFieldFlag("rolldownsource", 7L, true);
            }
            Object aobj[] = getDeploymentInfo();
            deployKey = (String)aobj[0];
            readingTable = (String)aobj[1];
            avgCalcKey = (String)aobj[2];
            coreAvgCalcSql = (new StringBuilder()).append("metername=:metername and ").append(avgCalcKey).append("=:").append(avgCalcKey).append(" and orgid=:orgid").toString();
            if(!isNew())
            {
                setFieldFlag("plustmtrstartdt", 7L, true);
                setFieldFlag("plustinitltd", 7L, true);
                setFieldFlag("plustinitrdng", 7L, true);
                setFieldFlag("plustitem", 7L, true);
                setFieldFlag("plustcommodity", 7L, true);
                if(getMboServer().getMaxVar().getBoolean("PLUSTMETERS", getString("orgid")))
                {
                    setFieldFlag("readingtype", 7L, true);
                } else
                {
                    setFieldFlag("plustdailylimit", 7L, true);
                    setFieldFlag("plustref", 7L, true);
                    setFieldFlag("plustrefid", 7L, true);
                    setFieldFlag("plustcapacity", 7L, true);
                    setFieldFlag("plustser", 7L, true);
                }
                setFieldFlag("isdelta", 7L, true);
                if(!getMboValue("readingtype").isNull() && getTranslator().toInternalString("READINGTYPE", getMboValue("readingtype").getString()).equalsIgnoreCase("ACTUAL"))
                    setFieldFlag("plustcapacity", 7L, true);
                else
                    setFieldFlag("plustser", 7L, true);
            }
        }
        catch(RemoteException remoteexception)
        {
            remoteexception.printStackTrace();
            throw new MXApplicationException("system", "remoteexception");
        }
    }

    public MboRemote addReading()
        throws MXException, RemoteException
    {
        MboRemote mboremote = getOwner();
        if(!(mboremote instanceof PlusTAsset) || !((PlusTAsset)mboremote).assetBeingMoved)
        {
            newMeterReading = super.addReading();
            MeterRemote meterremote = getTheMeter();
            if(meterremote != null && meterremote.isContinuous())
            {
                if(toBeAdded())
                    newMeterReading.copyValue(this, "plustinitltd", "delta", 11L);
                newMeterReading.copyValue(this, "plustrefnew", "plustref", 11L);
                newMeterReading.copyValue(this, "plustrefidnew", "plustrefid", 11L);
                newMeterReading.copyValue(this, "plustitem", "plustitem", 11L);
                newMeterReading.copyValue(this, "plustcommodity", "plustcommodity", 11L);
                newMeterReading.setValue("plustltd", calculatePlusTLTD(), 11L);
            }
            String s = getTranslator().toInternalString("PLUSTMTRS", getString("plustref"));
            if("INITIAL READING".equals(s) && getString("plustrefid").equals("INITIAL"))
                setValue("lastreadingdate", getDate("plustmtrstartdt"), 11L);
        }
        return newMeterReading;
    }

    public void save()
        throws MXException, RemoteException
    {
        MboRemote mboremote = getOwner();
        if(!(mboremote instanceof PlusTAsset) || !((PlusTAsset)mboremote).assetBeingMoved)
        {
            MeterRemote meterremote = getTheMeter();
            if(meterremote != null && meterremote.isContinuous())
            {
                if(toBeAdded() && getTranslator().toInternalString("READINGTYPE", getMboValue("readingtype").getString()).equalsIgnoreCase("ACTUAL"))
                {
                    String s = getTranslator().toExternalDefaultValue("PLUSTMTRS", "INITIAL READING", this);
                    setValue("lastreading", getString("plustinitrdng"), 2L);
                    setValue("plustrefnew", s, 11L);
                    setValue("plustrefidnew", "INITIAL", 11L);
                    setValue("lifetodate", getString("plustinitltd"), 2L);
                    addChangeMeterStartEntry();
                }
                if(getDate("newreadingdate") != null && isReadingMostRecentReading(getDate("newreadingdate")))
                {
                    setValue("plustref", getString("plustrefnew"), 11L);
                    setValue("plustrefid", getString("plustrefidnew"), 11L);
                }
            }
        } else
        {
            moveMeterChanges();
        }
        MboSetRemote mbosetremote = getThisMboSet();
        if(mbosetremote != null && mbosetremote.getParentApp() != null && ("PLUSTCONST".equalsIgnoreCase(mbosetremote.getParentApp()) || originalApp(mbosetremote.getParentApp(), "PLUSTCONST")) && toBeAdded())
        {
            String s1 = getTranslator().toExternalDefaultValue("ROLLDOWNSOURCE", "NONE", this);
            setValue("rolldownsource", s1, 11L);
        }
        super.save();
    }

    private void addChangeMeterStartEntry()
        throws MXException, RemoteException
    {
        PlusTMtrChngSetRemote plustmtrchngsetremote = (PlusTMtrChngSetRemote)getMboSet("plustmtrchng");
        PlusTMtrChngRemote plustmtrchngremote = (PlusTMtrChngRemote)plustmtrchngsetremote.add();
        plustmtrchngremote.setValue("change", 0, 11L);
        String as[] = {
            "assetnum", "orgid", "siteid", "lifetodate", "plustser", "plustmtrstartdt", "plustinitrdng", "lifetodate", "plustser"
        };
        String as1[] = {
            "assetnum", "orgid", "siteid", "endltdt", "endserialnum", "startdate", "startreading", "startltdt", "serialnum"
        };
        plustmtrchngremote.copyValue(this, as, as1, 11L);
        plustmtrchngremote.copyValue(this, "metername", "metername", 3L);
    }

    protected double getElapsedTime(Date date, Date date1)
        throws MXException, RemoteException
    {
        if(date1 == null || date == null)
        {
            return 1.0D;
        } else
        {
            long l = date.getTime();
            long l1 = date1.getTime();
            double d = Math.round((double)(l1 - l) / 86400000D);
            d++;
            return d;
        }
    }

    protected void refreshAverage()
        throws MXException, RemoteException
    {
        if(!getMboServer().getMaxVar().getBoolean("PLUSTMETERS", getString("orgid")))
        {
            super.refreshAverage();
        } else
        {
            MeterRemote meterremote = getTheMeter();
            if(meterremote != null && meterremote.isContinuous())
            {
                String s = getAvgCalcMethod();
                if("STATIC".equalsIgnoreCase(s))
                    return;
                if("ALL".equalsIgnoreCase(s))
                    setValue("AVERAGE", calculateTRAverageALL(), 2L);
                else
                if("SLIDING-READINGS".equalsIgnoreCase(s))
                    setValue("AVERAGE", calculateTRAverageSLIDINGREADINGS(), 2L);
                else
                if("SLIDING-DAYS".equalsIgnoreCase(s))
                    setValue("AVERAGE", calculateTRAverageSLIDINGDAYS(), 2L);
            }
        }
    }

    private double calculateTRAverageALL()
        throws MXException, RemoteException
    {
        Date date = null;
        Date date1 = null;
        double d = 0.0D;
        double d1 = 1.0D;
        int i = getInt("SlidingWindowSize");
        if(newMeterReading != null)
            date1 = newMeterReading.getDate("readingdate");
        MboSetRemote mbosetremote = null;
        SqlFormat sqlformat = new SqlFormat(this, coreAvgCalcSql);
        mbosetremote = getMboSet("$meterReadingsForAvgCalc", readingTable, sqlformat.format());
        mbosetremote.setOrderBy("readingdate asc");
        mbosetremote.setFlag(39L, true);
        int j = mbosetremote.count();
        int k = 0;
        if(newMeterReading != null)
            k = 1;
        int l = j + k;
        if(l < 2)
            return 0.0D;
        d = getDouble("LIFETODATE") - getDouble("plustinitltd");
        MboRemote mboremote = mbosetremote.moveFirst();
        if(mboremote != null)
        {
            Date date2 = mboremote.getDate("readingdate");
            if(date1 != null && date1.before(date2))
                date = getEarliestDate(date, date1);
            else
                date = getEarliestDate(date, date2);
        }
        d1 = getElapsedTime(date, getDate("lastreadingdate"));
        return d / d1;
    }

    private double calculateTRAverageSLIDINGREADINGS()
        throws MXException, RemoteException
    {
        Date date = null;
        Date date1 = null;
        double d = 0.0D;
        double d1 = 1.0D;
        int i = 0;
        int j = getInt("SlidingWindowSize");
        if(newMeterReading != null)
        {
            date1 = newMeterReading.getDate("readingdate");
            i = 1;
        }
        MboSetRemote mbosetremote = null;
        SqlFormat sqlformat = new SqlFormat(this, coreAvgCalcSql);
        mbosetremote = getMboSet("$meterReadingsForAvgCalc", readingTable, sqlformat.format());
        mbosetremote.setOrderBy("readingdate desc");
        mbosetremote.setFlag(39L, true);
        int k = mbosetremote.count();
        int l = k + i;
        if(l < 2)
            return 0.0D;
        if(l <= j)
            return calculateTRAverageALL();
        MboRemote mboremote = null;
        int i1 = 0;
        boolean flag = false;
        while((mboremote = mbosetremote.getMbo(i1)) != null) 
        {
            date = mboremote.getDate("readingdate");
            if(i1 + 1 == j)
                break;
            double d3 = mboremote.getDouble("delta");
            if(modifiedTRReadings.containsKey(mboremote.getString("meterreadingid")))
            {
                ReadingRemote readingremote = (ReadingRemote)modifiedTRReadings.get(mboremote.getString("meterreadingid"));
                d3 = readingremote.getDouble("delta");
            }
            if(!flag && date1 != null && date1.after(mboremote.getDate("readingdate")))
            {
                flag = true;
                if(i1 + 2 >= j)
                {
                    d += newMeterReading.getDouble("delta");
                    break;
                }
                d += newMeterReading.getDouble("delta");
                j--;
            }
            d += d3;
            i1++;
        }
        if(date1 != null && date1.before(date))
        {
            return getDouble("average");
        } else
        {
            double d2 = getElapsedTime(date, getDate("lastreadingdate"));
            return d / d2;
        }
    }

    protected Date getEarliestDate(Date date, Date date1)
        throws MXException, RemoteException
    {
        if(date == null)
            return date1;
        if(date1 == null)
            return date;
        if(date1.before(date))
            return date1;
        else
            return date;
    }

    private double calculateTRAverageSLIDINGDAYS()
        throws MXException, RemoteException
    {
        Date date = null;
        Date date1 = null;
        Date date2 = null;
        double d = 0.0D;
        double d1 = 1.0D;
        int i = 0;
        int j = getInt("SlidingWindowSize");
        if(newMeterReading != null)
        {
            date1 = newMeterReading.getDate("readingdate");
            i = 1;
        }
        date2 = getEarliestDateInGivenRange(j);
        if(date1 != null && date1.before(date2) && modifiedTRReadings != null && modifiedTRReadings.isEmpty())
            return getDouble("Average");
        String s = "";
        MboSetRemote mbosetremote = null;
        s = coreAvgCalcSql;
        SqlFormat sqlformat;
        if(date2 != null)
        {
            s = (new StringBuilder()).append(s).append(" and readingdate>:1").toString();
            sqlformat = new SqlFormat(this, s);
            sqlformat.setTimestamp(1, date2);
        } else
        {
            sqlformat = new SqlFormat(this, s);
        }
        mbosetremote = getMboSet("$meterReadingsForAvgCalc", readingTable, sqlformat.format());
        mbosetremote.setOrderBy("readingdate desc");
        mbosetremote.setFlag(39L, true);
        int k = mbosetremote.count();
        int l = k + i;
        if(l < 2)
            return 0.0D;
        MboRemote mboremote = null;
        if(date1 != null && !date1.before(date2))
        {
            d = newMeterReading.getDouble("delta");
            date = getEarliestDate(date, newMeterReading.getDate("readingdate"));
        }
        int i1 = 0;
        while((mboremote = mbosetremote.getMbo(i1)) != null && !mboremote.getDate("readingdate").before(date2)) 
        {
            date = getEarliestDate(date, mboremote.getDate("readingdate"));
            if(!mboremote.isNull("plustref") && getTranslator().toInternalString("PLUSTMTRS", mboremote.getString("plustref")).equalsIgnoreCase("INITIAL READING"))
            {
                i1++;
            } else
            {
                double d2 = mboremote.getDouble("delta");
                if(modifiedTRReadings.containsKey(mboremote.getString("meterreadingid")))
                {
                    ReadingRemote readingremote = (ReadingRemote)modifiedTRReadings.get(mboremote.getString("meterreadingid"));
                    d2 = readingremote.getDouble("delta");
                }
                d += d2;
                i1++;
            }
        }
        d1 = getElapsedTime(date, getDate("lastreadingdate"));
        return d / d1;
    }

    public void addModifiedReadingsForAvgCalc(MboRemote mboremote)
        throws MXException, RemoteException
    {
        super.addModifiedReadingsForAvgCalc(mboremote);
        String s = mboremote.getString("meterreadingid");
        modifiedTRReadings.remove(s);
        modifiedTRReadings.put(s, mboremote);
    }

    protected Date getEarliestDateInGivenRange(int i)
        throws MXException, RemoteException
    {
        String s = getAvgCalcMethod();
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        Date date = gregoriancalendar.getTime();
        byte byte0 = 0;
        gregoriancalendar.setTime(date);
        if("SLIDING-DAYS".equalsIgnoreCase(s))
            byte0 = 6;
        gregoriancalendar.add(byte0, -i);
        return gregoriancalendar.getTime();
    }

    public void appValidate()
        throws MXException, RemoteException
    {
        if(getImportFlag())
        {
            double d = getDouble("NewReading");
            double d1 = getDouble("PreviousReading");
            boolean flag = getBoolean("IsDelta");
            if(flag || d >= d1)
                super.appValidate();
        } else
        {
            super.appValidate();
        }
    }

    public String getPreviousSerial(int i)
        throws RemoteException, MXException
    {
        PlusTMtrChngSetRemote plustmtrchngsetremote = (PlusTMtrChngSetRemote)getMboSet("plustmtrchng");
        plustmtrchngsetremote.setUserWhere((new StringBuilder()).append("change < ").append(i).toString());
        plustmtrchngsetremote.setOrderBy("change desc");
        if(!plustmtrchngsetremote.isEmpty())
        {
            PlusTMtrChngRemote plustmtrchngremote = (PlusTMtrChngRemote)plustmtrchngsetremote.getMbo(0);
            if(!plustmtrchngremote.isNull("serialnum"))
                return plustmtrchngremote.getString("serialnum");
        }
        return "";
    }

    public void canDelete()
        throws MXException, RemoteException
    {
        super.canDelete();
        MboRemote mboremote = getOwner();
        if(mboremote.isBasedOn("ASSET"))
        {
            MboSetRemote mbosetremote = mboremote.getMboSet("PLUSTDEPASSETMETER");
            MboRemote mboremote1 = mbosetremote.moveFirst();
            if(mboremote1 != null && getUniqueIDValue() == mboremote1.getUniqueIDValue())
                throw new MXApplicationException("plustasset", "CannotDeleteMeter");
        }
    }

    public boolean isDuplicated()
        throws RemoteException, MXException
    {
        String s = getString("metername");
        int i = 0;
        MboSetRemote mbosetremote = getThisMboSet();
        for(MboRemote mboremote = null; (mboremote = mbosetremote.getMbo(i)) != null; i++)
        {
            if(!mbosetremote.getMbo(i).getString("metername").equals(s) || mboremote == this)
                continue;
            MboRemote mboremote1 = getOwner();
            if(mboremote1 instanceof PlusTAsset)
                return true;
        }

        return false;
    }

    public double calculatePlusTLTD()
        throws MXException, RemoteException
    {
        return calculatePlusTLTD(getDate("newreadingdate"), newMeterReading, 0.0D);
    }

    public double calculatePlusTLTD(Date date, MboRemote mboremote)
        throws MXException, RemoteException
    {
        return calculatePlusTLTD(date, mboremote, 0.0D);
    }

    public double calculatePlusTLTD(Date date, MboRemote mboremote, double d)
        throws MXException, RemoteException
    {
        double d1 = 0.0D;
        double d2 = 0.0D;
        if(date != null && mboremote != null)
        {
            String s = "assetnum = :assetnum and metername =:metername and readingdate <= :1";
            SqlFormat sqlformat = new SqlFormat(this, s);
            sqlformat.setTimestamp(1, date);
            MboSetRemote mbosetremote = getMboSet("$plustltd", "METERREADING", sqlformat.format());
            mbosetremote.setFlag(39L, true);
            d1 = mbosetremote.sum("delta");
            d2 = d + mboremote.getDouble("delta");
        }
        return d1 + d2;
    }

    protected void processContinuousReadings()
        throws MXException, RemoteException
    {
        super.processContinuousReadings();
        PlusTAssetMeterSet plustassetmeterset = (PlusTAssetMeterSet)getInstanciatedMboSet("$histAddToAssetMeters");
        if(plustassetmeterset != null)
        {
            int i = 0;
            for(PlusTAssetMeter plustassetmeter = null; (plustassetmeter = (PlusTAssetMeter)plustassetmeterset.getMbo(i)) != null;)
            {
                String s = getTranslator().toInternalString("ROLLDOWNSOURCE", plustassetmeter.getString("rolldownsource"), plustassetmeter);
                if(plustassetmeter.getBoolean("active") && "ASSET".equalsIgnoreCase(s))
                {
                    plustassetmeter.setValue("plustrefnew", getString("plustrefnew"), 11L);
                    plustassetmeter.setValue("plustrefidnew", getString("plustrefidnew"), 11L);
                }
                i++;
            }

        }
    }

    protected void rolloverIsInProgress()
        throws MXException, RemoteException
    {
        try
        {
            super.rolloverIsInProgress();
        }
        catch(MXException mxexception)
        {
            PlusTAssetServiceRemote plustassetserviceremote = (PlusTAssetServiceRemote)MXServer.getMXServer().lookup("ASSET");
            PlusTMeterReadingRemote plustmeterreadingremote = plustassetserviceremote.getMeterChangeAfter(this, getDate("newreadingdate"));
            if(mxexception.getErrorKey().equals("didRollOver") && plustmeterreadingremote != null)
            {
                getMboSet("NEWMETERREADING").reset();
                throw new MXApplicationException("plustasset", "rolloverMeterChange");
            } else
            {
                throw mxexception;
            }
        }
    }

    void moveMeterChanges()
        throws RemoteException, MXException
    {
        MboSetRemote mbosetremote = getMboSet("$assettrans", "ASSETTRANS", "assetnum=:assetnum");
        mbosetremote.setOrderBy("datemoved desc");
        mbosetremote.reset();
        MboRemote mboremote = mbosetremote.getMbo(0);
        if(mboremote != null)
        {
            String s = mboremote.getString("tositeid");
            String s1 = mboremote.getString("toorgid");
            String s2 = getString("siteid");
            String s3 = getString("orgid");
            MboSetRemote mbosetremote1 = getMboSet("$toMeterChanges", "PLUSTMTRCHNG", "assetnum=:assetnum and metername=:metername and siteid=:siteid and orgid=:orgid");
            if(!mbosetremote1.isEmpty())
                mbosetremote1.deleteAll();
            SqlFormat sqlformat = new SqlFormat(this, "assetnum=:assetnum and metername=:metername and siteid=:1 and orgid=:2");
            sqlformat.setObject(1, "ASSETMETER", "SITEID", s);
            sqlformat.setObject(2, "ASSETMETER", "ORGID", s1);
            MboSetRemote mbosetremote2 = getMboSet("$fromMeterChanges", "PLUSTMTRCHNG", sqlformat.format());
            mbosetremote2.copy(mbosetremote1);
            Object obj = null;
            MboRemote mboremote1;
            for(int i = 0; (mboremote1 = mbosetremote1.getMbo(i)) != null; i++)
            {
                mboremote1.setValue("siteid", s2, 11L);
                mboremote1.setValue("orgid", s3, 11L);
            }

        }
    }

    public MboRemote getNewMeterReading()
        throws RemoteException, MXException
    {
        return newMeterReading;
    }

    public boolean canRolldownFromSource()
    {
        return canRolldownFromSource;
    }

    public void setCanRolldownFromSource(boolean flag)
    {
        canRolldownFromSource = flag;
    }

    public boolean hasActualParent()
        throws MXException, RemoteException
    {
        MboRemote mboremote = getOwner();
        if(mboremote != null && (mboremote instanceof PlusTAsset) && !mboremote.isNull("parent"))
        {
            PlusTAssetServiceRemote plustassetserviceremote = (PlusTAssetServiceRemote)MXServer.getMXServer().lookup("ASSET");
            PlusTAssetRemote plustassetremote = plustassetserviceremote.getAsset(mboremote, mboremote.getString("parent"));
            if(plustassetremote != null)
            {
                SqlFormat sqlformat = new SqlFormat(plustassetremote, "assetnum=:assetnum and metername=:1 and siteid=:siteid");
                sqlformat.setObject(1, "ASSETMETER", "METERNAME", getMboValue("metername").getString());
                MboSetRemote mbosetremote = plustassetremote.getMboSet("$assetMeterSet", "ASSETMETER", sqlformat.format());
                MboRemote mboremote1 = mbosetremote.getMbo(0);
                if(mboremote1 != null && "ACTUAL".equalsIgnoreCase(getTranslator().toInternalString("READINGTYPE", mboremote1.getString("readingtype"))))
                    return true;
            }
        }
        return false;
    }

    public void setDefaultValue()
    {
        super.setDefaultValue();
	/*
        try
        {
            MaxVarServiceRemote maxvarserviceremote = getMboServer().getMaxVar();
            boolean flag = maxvarserviceremote.getBoolean("PLUSTMPBILLING", getString("orgid"));
            if(!flag)
                setFieldFlag("plustmpinclude", 7L, true);
        }
        catch(MXException mxexception)
        {
            mxexception.printStackTrace();
            throw new RuntimeException(mxexception);
        }
        catch(RemoteException remoteexception)
        {
            remoteexception.printStackTrace();
            throw new RuntimeException(remoteexception);
        }
	*/
    }

    public void delete(long l)
        throws MXException, RemoteException
    {
        getMboSet("METERREADING").deleteAll(2L);
        getMboSet("PLUSTMTRCHNG").deleteAll();
        super.delete(l);
    }

    public void undelete()
        throws MXException, RemoteException
    {
        getMboSet("METERREADING").undeleteAll();
        getMboSet("PLUSTMTRCHNG").undeleteAll();
        super.undelete();
    }

    private boolean originalApp(String s, String s1)
        throws RemoteException, MXException
    {
        MboSetRemote mbosetremote = MXServer.getMXServer().getMboSet("MAXAPPS", getUserInfo());
        mbosetremote.setWhere((new StringBuilder()).append("originalapp = '").append(SqlFormat.getSQLString(s1)).append("' and app='").append(SqlFormat.getSQLString(s)).append("'").toString());
        mbosetremote.reset();
        return !mbosetremote.isEmpty();
    }

    private String deployKey;
    private String readingTable;
    private String avgCalcKey;
    private String coreAvgCalcSql;
    private MboRemote newMeterReading;
    private Hashtable modifiedTRReadings;
    private boolean canRolldownFromSource;
    private boolean importFlag;
    private boolean ignoreWarns;
}
