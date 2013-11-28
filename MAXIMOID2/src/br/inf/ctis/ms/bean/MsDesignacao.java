package br.inf.ctis.ms.bean;

import java.rmi.RemoteException;
import java.util.Date;
import psdi.app.common.AvailCalc;
import psdi.app.ticket.TicketRemote;
import psdi.app.workorder.WORemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.BitFlag;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.ResultsBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;

public class MsDesignacao extends DataBean
{
	
	/**
	 * @author marcelosydney.lima
	 */
	public MsDesignacao() {
	}
  
	private String qbeAtorDemandaGroup;
	private static final MXLogger logger = MXLoggerFactory.getLogger("maximo.application");

	public void setupBean(WebClientSession wcs)
	{
		super.setupBean(wcs);
		try
		{
			String atorDemandaGroup = getAtorDemandaMbo().getString("ownergroup");
			setDefaultQbe("persongroup", atorDemandaGroup);
	    } catch (RemoteException e) {
	    	logger.warn("Unable to retrieve ownergroup from underlying mbo. Operation will proceed, but the dialog will not filter by the underlying mbo bean");
	    } catch (MXException e) {
	    	logger.warn("Unable to retrieve ownergroup from underlying mbo. Operation will proceed, but the dialog will not filter by the underlying mbo bean");
	    }
	}
	
	protected MboSetRemote getMboSetRemote() throws MXException, RemoteException
	{
		MboSetRemote personset = super.getMboSetRemote();
		
		if (personset == null)
		{
			return personset;
		}

		personset.setQbe("persongroup", this.qbeAtorDemandaGroup);

		String rel = personset.getRelationship();
		
		if ((rel == null) || (rel.equals("")))
		{
				rel = "status in (select value from synonymdomain where domainid='PERSONSTATUS' and maxvalue = 'ACTIVE')";		
		}
		else
		{
				rel = rel + " and  status in (select value from synonymdomain where domainid='PERSONSTATUS' and maxvalue = 'ACTIVE')";
		}
		StringBuffer additionalWhere = new StringBuffer(rel);

		addCalendarFilter(additionalWhere);

		personset.setUserWhere(additionalWhere.toString());
		personset.reset();
		return personset;
	}
	
	private void addCalendarFilter(StringBuffer additionalWhere) throws MXException, RemoteException
	{
	    MboRemote mbo = this.app.getAppBean().getMboOrZombie();
	
	    if ((mbo != null) && (!mbo.isNull("FILTERDATE"))) {
	    	Date filterdatetime = mbo.getDate("FILTERDATE");
	    	String calorgid = mbo.getString("orgid");
	    	if ((calorgid == null) || (calorgid.equals(""))) {
	    		calorgid = mbo.getInsertOrganization();
	    	}
	
	    	String availsql = " and (exists ( select c.personid from personcal c, workperiod w where c.calnum = w.calnum and persongroupview.personid = c.personid and c.orgid = w.orgid and w.orgid = :1 and ((w.workdate =:2 and w.starttime<= :3  and w.endtime>= :3 )  or ((w.workdate = :4 and (w.starttime<= :3 or w.endtime >= :3 ) and  w.starttime>=w.endtime   ) )) and workhours > 0 and (not exists (select personid m from modavail m where m.personid = persongroupview.personid) or (exists (select personid  from modavail m  where m.personid = persongroupview.personid  and ((m.workdate = :2 and m.starttime <= :3  or m.endtime>= :5) or ((m.workdate = :2 and m.starttime>= :3  and m.endtime <= :3 ) )) and workhours > 0 ))) )) or exists (select m.personid m from modavail m where m.personid = persongroupview.personid  and ((m.workdate = :2 and m.starttime<=:3 and m.endtime>=:3 ) or ((m.workdate =:4 and m.starttime>= :3 and m.endtime<= :3 ) )) and workhours > 0 )";
	
	    	AvailCalc avalCalc = new AvailCalc();
	    	String calendarWhere = avalCalc.getSqlSqfFormat(availsql, filterdatetime, calorgid);
	
	    	additionalWhere.append(calendarWhere);
	    }
	}

	public int selectrecord() throws MXException, RemoteException
	{
	    MboRemote mbo = getAtorDemandaMbo();
	    if (mbo != null) {
	    	applySelectAtorDemanda(mbo);
	    
	    	WebClientEvent closeEvt = new WebClientEvent("dialogok", this.app.getCurrentPageId(), null, this.clientSession);
	
	    	WebClientRuntime.sendEvent(closeEvt);
	    }
	
	    return 1;
	}
	
	private MboRemote getAtorDemandaMbo() throws MXException, RemoteException
	{
		DataBean atorDemandaBean = this.app.getDataBean("msalnatordemanda");
	
	    MboRemote mbo = null;
	    if (atorDemandaBean != null) {
	    	mbo = atorDemandaBean.getMbo();
	    }
	    if (mbo == null) {
	    	DataBean appBean = this.app.getAppBean();
	    	mbo = appBean.getMboOrZombie();
	    }
	    return mbo;
	}
	
	  private void applySelectAtorDemanda(MboRemote mbo) throws MXException, RemoteException
	  {
	    WebClientEvent event = this.app.getWebClientSession().getCurrentEvent();
	    int row = getRowIndexFromEvent(event);
	    ResultsBean resultsBean = this.app.getResultsBean();
	
	    if ((!mbo.isBasedOn("WORKORDER")) && (!mbo.isBasedOn("TICKET")))
	    {
	      mbo.setValue("msalnatordemanda", getMbo(row).getString("personid"), 2L);
	      mbo.setValue("msalflgescalacao", "1");
	      
	      return;
	    }
	
	    if (this.app.onListTab())
	    {
	      resultsBean.hasRecordsForAction();
	
	      if (resultsBean.getTableStateFlags().isFlagSet(32768L))
	      {
	        int i = 0;
	        MboRemote ticket = resultsBean.getMbo(i);
	        while (ticket != null) {
	          if (ticket.isSelected()) {
	            try {
	              ticket.sigOptionAccessAuthorized("DIST");
	              if (mbo.isBasedOn("TICKET"))
	              {
	            	  ticket.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	            	  ticket.setValue("msalflgescalacao", "1");
	              }
	              else if (mbo.isBasedOn("WORKORDER"))
	              {
	            	  ticket.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	            	  ticket.setValue("msalflgescalacao", "1");
	              }
	            }
	            catch (MXException e) {
	              ((MboSet)ticket.getThisMboSet()).addWarning(e);
	            }
	          }
	
	          i++;
	          ticket = resultsBean.getMbo(i);
	        }
	
	      }
	      else
	      {
	        int i = 0;
	        MboRemote ticket = resultsBean.getMbo(i);
	        while (ticket != null) {
	          try {
	            ticket.sigOptionAccessAuthorized("DIST");
	            if (mbo.isBasedOn("TICKET"))
	            {
	            	ticket.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	            	ticket.setValue("msalflgescalacao", "1");
	            }
	            else if (mbo.isBasedOn("WORKORDER"))
	            {
	            	ticket.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	            	ticket.setValue("msalflgescalacao", "1");
	            }
	          }
	          catch (MXException e) {
	            ((MboSet)ticket.getThisMboSet()).addWarning(e);
	          }
	
	          i++;
	          ticket = resultsBean.getMbo(i);
	        }
	      }
	      MboSet tkset = (MboSet)resultsBean.getMboSet();
	      this.clientSession.addMXWarnings(tkset.getWarnings());
	      this.clientSession.handleMXWarnings(false);
	      resultsBean.save();
	    }
	    else
	    {
	      if (mbo.isBasedOn("TICKET")) {
	    	  mbo.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	    	  mbo.setValue("msalflgescalacao", "1");
	      }
	      else if (mbo.isBasedOn("WORKORDER")) {
	    	  mbo.setValue("msalnatordemanda", getMbo(row).getString("personid"));
	    	  mbo.setValue("msalflgescalacao", "1");
	      }
	      DataBean appBean = this.app.getAppBean();
	      appBean.validateMbo();
	    }
	  }
	
	  public int REFRESHLIST()
	    throws MXException, RemoteException
	  {
	    this.qbeAtorDemandaGroup = getQbe("persongroup");
	    structureChangedEvent(this);
	    fireChildChangedEvent();
	    return 1;
	  } 
}