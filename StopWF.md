# Stopping Maximo workflow in bulk #



Recently I came across a situation where I had to stop the workflow for a large number of records as workflow took a wrong route due to some configuration issue. It happened on work orders which were auto-generated from PMs (Preventive Maintenance). There was a problem in workflow configuration due to a recent modification and it resulted in hundreds of work orders routed to wrong persons.

In Maximo workflow administration application, we can stop workflow but it has to be done one by one. There is no way to do it for a list of records in one go. So I wrote a SQL script to make the relevant updates directly in database to stop the workflow on desired records. Following passages explain how this can be achieved.

There are four tables involved in stopping a workflow which are:

WFINSTANCE
WFASSIGNMENT
WFCALLSTACK
WFTRANSACTION

Whenever workflow is started on an object, a new workflow instance is created. In other words, a record is created in WFINSTANCE table. WFID field is the key field to identify a workflow instance.

The workflow instance stores the information about the object on which it is running using two fields. OWNERTABLE containts the object name like WORKORDER or SR and OWNERID contains the object ID which is the WOID in case of work orders.

First thing we need to do is to find the WFID for the workflow instance we want to stop. For finding a workflow instance for work order, we could use a SQL like this.

SELECT **FROM WFINSTANCE WHERE OWNERTABLE = 'WORKORDER' and OWNERID =**

&lt;WFID&gt;



You should replace 

&lt;WOID&gt;

 with the WOID of your work order. The query should return one row, take note of the WFID. It is the key field which will be used to update the four tables mentioned above.

Once we know the WFID, we will run the following updates to deactivate the workflow.


UPDATE MAXIMO.WFINSTANCE SET ACTIVE = 0 WHERE WFID = 

&lt;WFID&gt;



UPDATE MAXIMO.WFASSIGNMENT SET ASSIGNSTATUS = 'INACTIVE' WHERE WFID = 

&lt;WFID&gt;



UPDATE MAXIMO.WFCALLSTACK SET ACTIVE = 0 WHERE WFID = 

&lt;WFID&gt;



Next we have to insert a row in WFTRANSACTION table. This table stores the history of workflow instance in terms of all the nodes and actions it went through. We will have to insert a row in this table to record the workflow stop transaction. Following table describes the fields of WFTRANSACTION table which we will help us in building our insert statement.


Following is a sample insert SQL for WFTRANSACTION table.

Insert into MAXIMO.WFTRANSACTION   (TRANSID, NODEID, WFID, TRANSTYPE, TRANSDATE, NODETYPE, PROCESSREV, PROCESSNAME, PERSONID, OWNERTABLE, OWNERID)
> (select maximo.wftransactionseq.nextval, 

&lt;NODEID&gt;

, WFID, 'WFUSERSTOPPED',  sysdate, 'TASK', 

&lt;PROCESSREV&gt;

, 

&lt;PROCESSNAME&gt;

, 'MAXADMIN', 

&lt;OWNERTABLE&gt;

, 

&lt;OWNERID&gt;

  from maximo.wfinstance where wfid = 

&lt;WFID&gt;

 )