// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WFInstance.java

package psdi.workflow;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.server.MXServer;
import psdi.txn.MXTransaction;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;

// Referenced classes of package psdi.workflow:
//            WFCallStackSet, WFAssignmentSet, WaitNodeDetail, WorkFlowServiceRemote, 
//            WFInteractionRemote, WFNodeSet, WFInstanceRemote, WFNode, 
//            WFActionSetRemote, WFAction, WFAssignment, WFCallStack, 
//            WFActionRemote, WFActionSet, NodeDetail, WFProcess, 
//            WFViewInfo, WFAssignmentRemote

public class WFInstance extends Mbo
    implements WFInstanceRemote
{

    public WFInstance(MboSet ms)
        throws MXException, RemoteException
    {
        super(ms);
        controlledMbo = null;
    }

    public void init()
        throws MXException
    {
        super.init();
        setFieldFlag(alwaysReadOnly, 7L, true);
    }

    public void add()
        throws MXException, RemoteException
    {
        setValue("ACTIVE", true, 2L);
        java.util.Date startTime = MXServer.getMXServer().getDate();
        setValue("STARTTIME", startTime, 2L);
        setValue("CURRTASKSTARTTIME", startTime, 2L);
    }

    public MboRemote getControlledMbo()
        throws MXException, RemoteException
    {
        if(controlledMbo == null)
        {
            MboSetRemote msr = getMboSet("$GETCONTROLLED", getString("OWNERTABLE"), "");
            controlledMbo = msr.getMboForUniqueId(getLong("OWNERID"));
        }
        return controlledMbo;
    }

    public void setControlledMbo(MboRemote mbo)
        throws MXException, RemoteException
    {
        controlledMbo = mbo;
    }

    public void initiateWorkflow(String memo, WFProcess wfProcess)
        throws RemoteException, MXException
    {
        try
        {
            ((WFCallStackSet)getMboSet("CALLSTACK")).initiateWorkflow(memo, wfProcess);
        }
        catch(MXException e)
        {
            getThisMboSet().getMXTransaction().rollback();
            throw e;
        }
    }

    public WFActionSetRemote getActions()
        throws RemoteException, MXException
    {
        return getCurrentNode().getWorkflowActions();
    }

    public void completeWorkflowAssignment(int assignment, int action, String memo)
        throws RemoteException, MXException
    {
        try
        {
            WFCallStackSet callStack = (WFCallStackSet)getMboSet("CALLSTACK");
            WFAssignmentSet assignments = (WFAssignmentSet)getMboSet("ACTIVEASSIGNMENTS");
            WFAssignment assignMbo = assignments.getAssignment(assignment);
            callStack.completeWorkflowAssignment(assignMbo, action, memo);
        }
        catch(MXException e)
        {
            getThisMboSet().getMXTransaction().rollback();
            throw e;
        }
    }

    public void applyWorkflowAction(int actionID)
        throws RemoteException, MXException
    {
        try
        {
            applyWorkflowAction(actionID, null);
        }
        catch(MXException e)
        {
            getThisMboSet().getMXTransaction().rollback();
            throw e;
        }
    }

    public void applyWorkflowAction(int actionID, String memo)
        throws RemoteException, MXException
    {
        WFAction applyAction = getActions().getActionForID(actionID);
        if(!applyAction.getBoolean("AVAILABLE"))
            throw new MXApplicationException("workflow", "NotAvailableAction");
        try
        {
            ((WFCallStackSet)getMboSet("CALLSTACK")).applyWorkflowAction(applyAction, memo);
        }
        catch(MXException e)
        {
            getThisMboSet().getMXTransaction().rollback();
            throw e;
        }
    }

    public WFViewInfo getWFDiagramInfo(int callSeq)
        throws RemoteException, MXException
    {
        return ((WFCallStackSet)getMboSet("CALLSTACK")).getWFDiagramInfo(callSeq);
    }

    public void cancelWorkflowAssignment(WFAssignmentRemote assignment, String actionMemo)
        throws RemoteException, MXException
    {
        WFAssignmentSet activeAssignments = (WFAssignmentSet)getMboSet("ACTIVEASSIGNMENTS");
        WFAssignment cancelAsgn = activeAssignments.getAssignment(getInt("ASSIGNID"));
        cancelAsgn.cancel(actionMemo);
    }

    public void stopWorkflow(String memo)
        throws RemoteException, MXException
    {
        ((WFCallStackSet)getMboSet("CALLSTACK")).stopWorkflow(memo);
    }

    private WFNode getCurrentNode()
        throws RemoteException, MXException
    {
        WFCallStackSet callStack = (WFCallStackSet)getMboSet("CALLSTACK");
        WFCallStack stackTop = callStack.getWFCallStack();
        return stackTop.getCurrentNode();
    }

    public void interactionComplete()
        throws RemoteException, MXException
    {
        WFActionRemote waitAction = getNextAction();
        applyWorkflowAction(waitAction.getInt("ACTIONID"));
    }

    private WFActionRemote getNextAction()
        throws RemoteException, MXException
    {
        WFNode currentNode = getCurrentNode();
        WFActionSet actionSet = currentNode.getWorkflowActions();
        return actionSet.getAction(true);
    }

    public void waitComplete()
        throws RemoteException, MXException
    {
        WFNode currentNode = getCurrentNode();
        WaitNodeDetail waitDetail = (WaitNodeDetail)currentNode.getNodeDetail();
        waitDetail.deactivate();
        WFActionRemote waitAction = getActions().getAction(true);
        applyWorkflowAction(waitAction.getInt("ACTIONID"));
        if(!atStoppingPoint())
        {
            Object params[] = {
                getString("WFID"), getString("PROCESSNAME"), getString("REVISION")
            };
            MXException mxe = new MXApplicationException("workflow", "WaitBgFailure", params);
            MXLogger log = ((WorkFlowServiceRemote)MXServer.getMXServer().lookup("WORKFLOW")).getWFLogger();
            if(log.isErrorEnabled())
                log.error(mxe);
            throw mxe;
        } else
        {
            return;
        }
    }

    public boolean atInteraction()
        throws RemoteException, MXException
    {
        if(atStoppingPoint())
            return false;
        else
            return getCurrentNode().getNodeDetail().isInteractive();
    }

    public WFInteractionRemote getInteraction()
        throws RemoteException, MXException
    {
        return (WFInteractionRemote)getCurrentNode().getCompanionSet().getMbo(0);
    }

    public void escalateAssignment(int assignID, String memo)
        throws RemoteException, MXException
    {
        try
        {
            WFAssignmentSet assignments = (WFAssignmentSet)getMboSet("ACTIVEASSIGNMENTS");
            WFAssignment assignMbo = assignments.getAssignment(assignID);
            assignMbo.escalate(memo);
        }
        catch(MXException e)
        {
            getThisMboSet().getMXTransaction().rollback();
            throw e;
        }
    }

    public boolean atStoppingPoint()
        throws RemoteException, MXException
    {
        if(!getBoolean("ACTIVE"))
        {
            return true;
        } else
        {
            WFNode node = getCurrentNode();
            NodeDetail detail = node.getNodeDetail();
            return detail.isTask() || detail.isWait();
        }
    }

    public boolean canAutoCompleteInteraction()
        throws RemoteException, MXException
    {
        WFCallStackSet callStackSet = (WFCallStackSet)getMboSet("CALLSTACK");
        if(callStackSet.count() > 1)
            return false;
        WFActionRemote action = getNextAction();
        if(!action.isNull("ACTION"))
            return false;
        WFCallStack callStack = callStackSet.getWFCallStack();
        WFNode nextNode = ((WFNodeSet)callStack.getMboSet("NODES")).getNode(action.getInt("MEMBERNODEID"));
        return nextNode.getNodeDetail().isStop();
    }

    void saveInstance()
        throws RemoteException, MXException
    {
        MboRemote controlled = getControlledMbo();
        controlled.getMXTransaction().add(getMXTransaction());
        controlled.getThisMboSet().save();
    }

    private MboRemote controlledMbo;
    private static final String alwaysReadOnly[] = {
        "ACTIVE", "WFID", "ORIGINATOR", "REVISION", "STARTTIME", "CURRTASKSTARTTIME", "PROCESSNAME", "OWNERTABLE", "OWNERID"
    };

}
