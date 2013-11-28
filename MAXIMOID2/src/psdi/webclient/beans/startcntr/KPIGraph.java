// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KPIGraph.java

package psdi.webclient.beans.startcntr;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import netcharts.pro.charts.bar.NFBarchart;
import netcharts.pro.charts.dial.*;
import netcharts.pro.common.*;
import netcharts.pro.common.barset.NFBarSeries;
import netcharts.pro.common.barset.NFBarSet;
import netcharts.pro.common.rectangular.NFAxis;
import netcharts.util.*;
import psdi.app.kpi.KPIRemote;
import psdi.app.kpi.KPISet;
import psdi.app.kpi.KPISetRemote;
import psdi.app.kpi.KPIUtil;
import psdi.security.UserInfo;
import psdi.util.*;
import psdi.webclient.beans.common.ChartUtil;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

public class KPIGraph
{

    public KPIGraph()
    {
        vecData = null; 
        URL = "";
        forStartCenter = false;
        sChartType = "";
        iHeight = 0;
        iWidth = 0;
        INNER_RADIUS = 75;
        OUTER_RADIUS = 100;
        MAX_TICS = 10;
        sessionContext = null;
        activeLabelTarget = "_top";
        MAX_VALUE = 0.0D;
        graphFont = "";
        userLocale = null;
        datasize = 0;
        TITLE_CHUNK = 45;
        warningColor = "";
        okColor = "";
        alertColor = "";
        innerDialColor = "";
        kpiValueHandColor = "";
        targetHandColor = "";
    }

    public KPIGraph(Vector vecData, boolean forStartCenter, WebClientSession sessionContext)
    {
        this.vecData = null;
        URL = "";
        this.forStartCenter = false;
        sChartType = "";
        iHeight = 0;
        iWidth = 0;
        INNER_RADIUS = 75;
        OUTER_RADIUS = 100;
        MAX_TICS = 10;
        this.sessionContext = null;
        activeLabelTarget = "_top";
        MAX_VALUE = 0.0D;
        graphFont = "";
        userLocale = null;
        datasize = 0;
        TITLE_CHUNK = 45;
        warningColor = "";
        okColor = "";
        alertColor = "";
        innerDialColor = "";
        kpiValueHandColor = "";
        targetHandColor = "";
        this.vecData = vecData;
        this.forStartCenter = forStartCenter;
        this.sessionContext = sessionContext;
        try {
			this.kpisetRemote = (KPISetRemote)sessionContext.getMXSession().getMXServerRemote().getMboSet("KPIMAIN", sessionContext.getUserInfo());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
        userLocale = sessionContext.getUserInfo().getLocale();
        if(vecData != null)
            datasize = vecData.size();
        Properties colors = ChartUtil.getChartUtil().getKPIChartColors(sessionContext);
        if(colors != null && !colors.isEmpty())
        {
            okColor = colors.get("good").toString();
            alertColor = colors.get("alert").toString();
            warningColor = colors.get("warning").toString();
            innerDialColor = colors.get("innerdial").toString();
            kpiValueHandColor = colors.get("kpivaluehand").toString();
            targetHandColor = colors.get("targethand").toString();
        }
    }

    public NFGraph makeGraph()
    {
        NFGraph chart = null;
        try
        {
            if(forStartCenter)
            {
                if(vecData != null)
                    if(vecData.size() == 1)
                    {
                        sChartType = "DIAL";
                        iHeight = 250;
                        iWidth = 275;
                    } else
                    {
                        sChartType = "BARCHART";
                        iHeight = 400;
                        iWidth = 500;
                    }
            } else
            {
                sChartType = "DIAL";
                iHeight = 375;
                iWidth = 400;
            }
            graphFont = ChartUtil.getChartUtil().getChartFont(sessionContext);
            chart = NFGraph.getGraphFromTemplate(getDefaultChart(sChartType));
            if(sChartType.equals("DIAL"))
            {
                setDialChartProperties(chart);
                chart.setSize(iWidth, iHeight);
            }
            if(sChartType.equals("BARCHART"))
            {
                chart.setSize(iWidth, iHeight);
                makeBarChart((NFBarchart)chart);
            }
        }
        catch(MXException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder()).append("Exception encountered while processing graph : ").append(e.getMessage()).toString());
        }
        return chart;
    }

    private String getDefaultChart(String sChartType)
        throws MXException
    {
        String sChart = "";
        double dHighestValue = 0.0D;
        double iTic = 0.0D;
        if(sChartType.equals("BARCHART"))
            sChart = (new StringBuilder()).append("ChartType       = BARCHART;Background    = (white,NONE,1,null,TILE,black);Grid            = (NULL,white, dimgray, mull, SIZE); BottomTics      = (ON, black, \"Arial Unicode MS\", 10,null);LeftTics        = (ON, black, \"Arial Unicode MS\", 10);BottomTicLayout    = (AUTO,0,1);BottomScale     = (null,null,null);DwellLabel      = (ON, black, \"").append(graphFont).append("\", 10);").append("BarLabels       = ").append(getBarLables()).append(";").append("DwellBox        = (xe3e3e3, BOX, 1);").append("ColorTable      = ").append(getColors()).append(";").append("Grid            = (lightgray,null,black,null,TILE);").append("BarBorder \t\t =  (NONE, 1, black);").toString();
        if(sChartType.equals("DIAL"))
        {
            Hashtable htKPI = (Hashtable)vecData.elementAt(0);
            double dKPIValue = ((Double)htKPI.get("kpivalue")).doubleValue();
            double dCautionMin = ((Double)htKPI.get("cautionmin")).doubleValue();
            double dCautionMax = ((Double)htKPI.get("cautionmax")).doubleValue();
            double dTarget = ((Double)htKPI.get("target")).doubleValue();
            dHighestValue = highestValueForDial(dCautionMin, dCautionMax, dTarget, dKPIValue);
            MAX_VALUE = ChartUtil.getChartUtil().roundUpWithLog(dHighestValue);
            iTic = ChartUtil.getChartUtil().tic(0.0D, MAX_VALUE, MAX_TICS);
            if(MAX_VALUE == dHighestValue)
                MAX_VALUE += iTic;
            sChart = (new StringBuilder()).append("ChartType        = DIALCHART;Background            = (white,NONE,4,null,TILE,black);Dials                 = (\"Dial\",-90,90,100,OUTSIDE),(\"Center Point\",-90,90,10,NONE);DialTics              = (\"Dial\",black,1,10),(\"Center Point\",white,0,0);DialTicLabelStyles    = (\"Dial\",\"ON\",1.1,black,\"Helvetica\",12,0),(\"Center Point\",\"OFF\",0,white,\"Helvetica\",2,0);DialScale             = (\"Dial\",0,").append(MAX_VALUE).append(",").append(iTic).append("),(\"Center Point\",0,").append(MAX_VALUE).append(",").append(iTic).append(");").append("DialTicLabels\t\t   = (\"Dial\",").append(makeDialLabels(MAX_VALUE, iTic)).append("), (\"Center Point\");").append("DialFills             = (\"Dial\",white,CENTER),(\"Center Point\",darkslategrey,CENTER);").append("Hands                 = (\"Target\",darkblue,darkblue,\"Dial\"),(\"Value\",orange,orange,\"Dial\");").append("HandData              = (\"Target\",30,100),(\"Value\",15,100);").append("HandStyles            = (\"Target\",SHARP,5,2),(\"Value\",SHARP,5,2);").append("DwellLabel            = (\"ON\",black,\"").append(graphFont).append("\",8,0);").append("DwellLabelBox         = (xe3e3e3,BOX,1,null,TILE,dimgrey);").append("DialBorders           = (\"Dial\",SOLID,1,black,CENTER),(\"Center Point\",NONE,1,silver,CENTER);").append("AntiAlias             = \"ON\";").append("HandDrag              = \"OFF\";").append("Header                = (\"\",black,\"").append(graphFont).append("\",11,0);").append("ScaleFactor           = 0;").append("HeaderBox             = (white,NONE,1,,TILE,black);").toString();
        }
        return sChart;
    }

    private String getBarLables()
    {
        String sLables = "";
        String sValue = "";
        for(int i = 0; i < datasize; i++)
        {
            Hashtable htKPI = (Hashtable)vecData.elementAt(i);
            try {
				this.kpisetRemote.setWhere("KPINAME = '"+htKPI.get("kpiid")+"'");
	            this.kpisetRemote.reset();
	            sValue = (String)this.kpisetRemote.getMbo(0).getString("legenda");
			} catch (RemoteException e) {
	            sValue = "N/D";				
				e.printStackTrace();
			} catch (MXException e) {
	            sValue = "N/D";				
				e.printStackTrace();
			}
            if(i == 0)
                sLables = (new StringBuilder()).append("\"").append(sValue).append("\"").toString();
            else
                sLables = (new StringBuilder()).append(sLables).append(",\"").append(sValue).append("\"").toString();
        }

        return sLables;
    }

    private String getColors()
    {
        String sColors = "";
        for(int i = 0; i < datasize; i++)
        {
            Hashtable htKPI = (Hashtable)vecData.elementAt(i);
            double dKPIValue = ((Double)htKPI.get("kpivalue")).doubleValue();
            double dCautionMin = ((Double)htKPI.get("cautionmin")).doubleValue();
            double dCautionMax = ((Double)htKPI.get("cautionmax")).doubleValue();
            String sColor = "";
            if(dCautionMax < dCautionMin)
            {
                if(dKPIValue <= dCautionMax)
                    sColor = alertColor;
                else
                if(dKPIValue <= dCautionMin)
                    sColor = warningColor;
                else
                    sColor = okColor;
            } else
            if(dKPIValue <= dCautionMin)
                sColor = okColor;
            else
            if(dKPIValue <= dCautionMax)
                sColor = warningColor;
            else
                sColor = alertColor;
            if(i == 0)
                sColors = (new StringBuilder()).append("x").append(sColor).toString();
            else
                sColors = (new StringBuilder()).append(sColors).append(",x").append(sColor).toString();
        }

        return sColors;
    }

    private void setDialChartProperties(NFGraph kpiChart)
    {
        try
        {
            String labels[] = sessionContext.getMXSession().getMessages("kpi", new String[] {
                "targetlbl", "currentvaluelbl", "alertlbl", "goodlbl", "warnlbl", "gotokpilbl"
            });
            boolean bReverse = false;
            Hashtable htKPI = (Hashtable)vecData.elementAt(0);
            double dTarget = ((Double)htKPI.get("target")).doubleValue();
            double dKPIValue = ((Double)htKPI.get("kpivalue")).doubleValue();
            double dCautionMin = ((Double)htKPI.get("cautionmin")).doubleValue();
            double dCautionMax = ((Double)htKPI.get("cautionmax")).doubleValue();
            String sFormat = htKPI.get("format").toString();
            double dHighestValue = ChartUtil.getChartUtil().roundUp(highestValueForDial(dCautionMin, dCautionMax, dTarget, dKPIValue));
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(userLocale);
            kpiChart.setNumberFormat(new NFNumberFormat((new Character(dfs.getDecimalSeparator())).toString(), (new Character(dfs.getGroupingSeparator())).toString(), ChartUtil.getChartUtil().numberOfDigits(dHighestValue, userLocale)));
            Color green = ChartUtil.getChartUtil().decodeColor(okColor);
            Color yellow = ChartUtil.getChartUtil().decodeColor(warningColor);
            Color red = ChartUtil.getChartUtil().decodeColor(alertColor);
            Color grey = ChartUtil.getChartUtil().decodeColor(innerDialColor);
            Color orange = ChartUtil.getChartUtil().decodeColor(kpiValueHandColor);
            Color targetColor = ChartUtil.getChartUtil().decodeColor(targetHandColor);
            NFDialchart dialchart = (NFDialchart)kpiChart;
            NFDialSeries dseries = dialchart.getDialSeries();
            NFDial dial = (NFDial)dseries.elementAt(0);
            NFDialHand hand = (NFDialHand)dial.getHands().elementAt(0);
            if(hand != null)
            {
                hand.setValue(dTarget);
                NFActiveLabel targetLabel = new NFActiveLabel(WebClientRuntime.stringToCodepoints((new StringBuilder()).append(labels[0]).append(":").append(KPIUtil.formatDouble(dTarget, userLocale)).toString(), true, true), "", "");
                if(forStartCenter)
                    targetLabel.setTarget(activeLabelTarget);
                hand.setActiveLabel(targetLabel);
                hand.setShaftColor(targetColor);
                hand.setTipColor(targetColor);
                targetLabel = null;
            }
            hand = (NFDialHand)dial.getHands().elementAt(1);
            if(hand != null)
            {
                hand.setShaftColor(orange);
                hand.setTipColor(orange);
                hand.setValue(dKPIValue);
                NFActiveLabel kpivalueLabel = new NFActiveLabel(WebClientRuntime.stringToCodepoints((new StringBuilder()).append(labels[1]).append(":").append(KPIUtil.formatDouble(dKPIValue, userLocale)).toString(), true, true), "", "");
                if(!forStartCenter)
                    kpivalueLabel.setTarget(activeLabelTarget);
                hand.setActiveLabel(kpivalueLabel);
            }
            NFTitle title = dialchart.getHeader();
            boolean isBidiEnabled = BidiUtils.isBidiEnabled();
            String sTitle = isBidiEnabled ? htKPI.get("kpinamebidi").toString() : htKPI.get("kpiname").toString();
            if(sTitle.equals(""))
                sTitle = isBidiEnabled ? htKPI.get("kpiidbidi").toString() : htKPI.get("kpiid").toString();
            if(sFormat.equals("PERCENT"))
                if(isBidiEnabled)
                    sTitle = BidiUtils.appendBidiString(sTitle, " (%)");
                else
                    sTitle = (new StringBuilder()).append(sTitle).append(" (%)").toString();
            int len = sTitle.length();
            if(WebClientRuntime.isAllLatin(sTitle))
            {
                if(forStartCenter)
                    TITLE_CHUNK = 36;
                else
                    TITLE_CHUNK = 50;
            } else
            if(forStartCenter)
                TITLE_CHUNK = 27;
            else
                TITLE_CHUNK = 32;
            if(len >= TITLE_CHUNK)
                sTitle = WebClientRuntime.wrapText(sTitle, TITLE_CHUNK);
            String kpiTitle = WebClientRuntime.stringToCodepoints(sTitle, false, false);
            title.setText(kpiTitle);
            title.setFont(new Font(graphFont, 0, 11));
            if(forStartCenter)
                title.setActiveLabel(new NFActiveLabel(WebClientRuntime.stringToCodepoints(sTitle, false, true), makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget));
            dialchart.setHeader(title);
            if(dCautionMax < dCautionMin)
            {
                double dTemp = dCautionMin;
                dCautionMin = dCautionMax;
                dCautionMax = dTemp;
                bReverse = true;
            }
            if(bReverse)
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[2], false, true), 0.0D, dCautionMin, red, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, INNER_RADIUS, OUTER_RADIUS));
            else
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[3], false, true), 0.0D, dCautionMin, green, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, INNER_RADIUS, OUTER_RADIUS));
            dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[4], false, true), dCautionMin, dCautionMax, yellow, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, INNER_RADIUS, OUTER_RADIUS));
            if(bReverse)
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[3], false, true), dCautionMax, MAX_VALUE, green, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, INNER_RADIUS, OUTER_RADIUS));
            else
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[2], false, true), dCautionMax, MAX_VALUE, red, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, INNER_RADIUS, OUTER_RADIUS));
            if(forStartCenter)
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(labels[5], false, true), 0.0D, MAX_VALUE, grey, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, 10D, 75D));
            else
                dial.addSector(makeSector(WebClientRuntime.stringToCodepoints(sTitle, false, true), 0.0D, MAX_VALUE, grey, makeURL(htKPI.get("kpiuid").toString()), activeLabelTarget, 10D, 75D));
            dialchart.setDialSeries(dseries);
            dseries = null;
            title = null;
            hand = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private double getHighestValue()
    {
        double dHighestValue = 0.0D;
        for(int i = 0; i < datasize; i++)
        {
            Hashtable htKPI = (Hashtable)vecData.elementAt(i);
            double dValue = ((Double)htKPI.get("kpivalue")).doubleValue();
            if(dValue > dHighestValue)
                dHighestValue = dValue;
        }

        return dHighestValue;
    }

    private String formatNumber(double dNumber)
    {
        DecimalFormat nf = new DecimalFormat();
        nf.setGroupingSize(0);
        nf.setMaximumFractionDigits(2);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(sessionContext.getLocale());
        nf.setDecimalFormatSymbols(dfs);
        char separator = dfs.getDecimalSeparator();
        String sNumber = nf.format(dNumber);
        if(!isZero(sNumber))
        {
            if(sNumber.indexOf(separator) != -1)
                if(sNumber.endsWith((new StringBuilder()).append(separator).append("0").toString()))
                    sNumber = sNumber.substring(0, sNumber.indexOf(separator));
                else
                if(sNumber.endsWith("0"))
                    sNumber = sNumber.substring(0, sNumber.indexOf("0"));
            return sNumber;
        } else
        {
            return "0";
        }
    }

    private String makeDialLabels(double dMaxValue, double step)
        throws MXApplicationException
    {
        String sLabels = "";
        Vector vec = ChartUtil.getChartUtil().computeTics(0.0D, dMaxValue, MAX_TICS, step);
        int size = vec.size();
        for(int i = 0; i < size; i++)
            if(sLabels.equals(""))
                sLabels = (new StringBuilder()).append("\"").append(formatNumber(MXFormat.stringToDouble(vec.elementAt(i).toString()))).append("\"").toString();
            else
                sLabels = (new StringBuilder()).append(sLabels).append(",\"").append(formatNumber(MXFormat.stringToDouble(vec.elementAt(i).toString()))).append("\"").toString();

        return sLabels;
    }

    private String makeURL(String sKPIId)
    {
        String sURL = (new StringBuilder()).append(URL).append("/ui/kpi?event=loadapp&targetid=startcntr&value=kpi&uniqueid=").append(sKPIId).append("&uisessionid=").append(sessionContext.getUISessionID()).toString();
        return sURL;
    }

    double highestValueForDial(double dMin, double dMax, double dTarget, double dValue)
    {
        return Math.max(dMin, Math.max(dMax, Math.max(dTarget, dValue)));
    }

    private NFDialSector makeSector(String sLabel, double start, double stop, Color color, String sURL, 
            String sTarget, double INNER_RADIUS, double OUTER_RADIUS)
    {
        NFDialSector sector = new NFDialSector();
        sector.setSectorName(sLabel);
        sector.setStartValue(start);
        sector.setStopValue(stop);
        sector.setInnerRadius(INNER_RADIUS);
        sector.setOuterRadius(OUTER_RADIUS);
        sector.setFillColor(color);
        NFActiveLabel activeLabel = new NFActiveLabel(sLabel, "", "");
        if(sLabel.equals(""))
        {
            sector.setActiveLabel(activeLabel);
            return sector;
        }
        if(forStartCenter)
        {
            activeLabel.setURL(sURL);
            activeLabel.setTarget(sTarget);
        }
        sector.setActiveLabel(activeLabel);
        activeLabel = null;
        return sector;
    }

    private static boolean isZero(String s)
    {
        DecimalFormatSymbols symb = new DecimalFormatSymbols(Locale.getDefault());
        for(int i = 0; i < s.length(); i++)
            if(s.charAt(i) != symb.getDecimalSeparator() && s.charAt(i) != symb.getGroupingSeparator() && s.charAt(i) != '0' && s.charAt(i) != symb.getMinusSign())
                return false;

        return true;
    }

    public void setURL(HttpServletRequest request)
    {
        WebClientRuntime wcr = WebClientRuntime.getWebClientRuntime();
        WebClientSession sc = wcr.getWebClientSession(request);
        URL = sc.getMaximoRequestContextURL();
    }

    private NFVector getActiveLablesForBars()
    {
        NFVector labels = new NFVector();
        for(int i = 0; i < datasize; i++)
        {
            Hashtable htKPI = (Hashtable)vecData.elementAt(i);
            String name = BidiUtils.isBidiEnabled() ? BidiUtils.keepBidiDirection(htKPI.get("kpinamebidi").toString(), "-") : htKPI.get("kpiname").toString();
            NFActiveLabel activeLabel = new NFActiveLabel(WebClientRuntime.stringToCodepoints(name, false, true), makeURL(htKPI.get("kpiuid").toString()), "_top");
            labels.addElement(activeLabel);
            activeLabel = null;
        }

        return labels;
    }

    public void makeBarChart(NFBarchart barchart)
    {
        double dHighestValue = 0.0D;
        double iTic = 0.0D;
        boolean bUseFloat = false;
        dHighestValue = ChartUtil.getChartUtil().roundUp(getHighestValue());
        if(dHighestValue <= 1.0D)
        {
            dHighestValue = 2D;
            bUseFloat = true;
        }
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(userLocale);
        barchart.setNumberFormat(new NFNumberFormat((new Character(dfs.getDecimalSeparator())).toString(), (new Character(dfs.getGroupingSeparator())).toString(), ChartUtil.getChartUtil().numberOfDigits(dHighestValue, userLocale)));
        iTic = ChartUtil.getChartUtil().tic(0.0D, ChartUtil.getChartUtil().roundUp(dHighestValue), 5);
        NFBarSeries bseries = barchart.getBarSeries();
        NFAxis left = new NFAxis();
        left.setScale(0.0D, dHighestValue, iTic);
        left.setTextFont(new Font("Helvetica", 0, 10));
        left.setTextColor(Color.black);
        if(bUseFloat)
            left.setTicFormat(new NFFormat(2, "%.1f"));
        else
            left.setTicFormat(new NFFormat(1, null));
        barchart.setLeftAxis(left);
        barchart.setDepth(4);
        NFBarSet barSet = new NFBarSet();
        for(int i = 0; i < datasize; i++)
        {
            barSet.setBarStyle(9);
            Hashtable htKPI = (Hashtable)vecData.get(i);
            barSet.addElement(((Double)htKPI.get("kpivalue")).doubleValue());
        }

        NFAxis bottom = new NFAxis();
        bottom.setTextFont(new Font(graphFont, 0, 10));
        bottom.setTextColor(Color.black);
        barchart.setBottomAxis(bottom);
        barSet.setActiveLabels(getActiveLablesForBars());
        bseries.addElement(barSet);
        barchart.setBarSeries(bseries);
        barSet = null;
        bseries = null;
        bottom = null;
        left = null;
    }

    private Vector vecData;
    private String URL;
    private boolean forStartCenter;
    private String sChartType;
    private int iHeight;
    private int iWidth;
    private int INNER_RADIUS;
    private int OUTER_RADIUS;
    private int MAX_TICS;
    private WebClientSession sessionContext;
    private String activeLabelTarget;
    private double MAX_VALUE;
    private String graphFont;
    private Locale userLocale;
    private int datasize;
    private int TITLE_CHUNK;
    private String warningColor;
    private String okColor;
    private String alertColor;
    private String innerDialColor;
    private String kpiValueHandColor;
    private String targetHandColor;
    private KPISetRemote kpisetRemote;
    private KPIRemote kpiRemote;

    static 
    {
        NFDebug.printStream = null;
        NFGraph.setLicenseKey(ChartUtil.getChartUtil().getNCPL());
        NFGraph.setLicenseKey(ChartUtil.getChartUtil().getNCL());
        NFContext.setUserAgentType(3);
        NFGlobal.setAllowUserInteraction(false);
    }
}
