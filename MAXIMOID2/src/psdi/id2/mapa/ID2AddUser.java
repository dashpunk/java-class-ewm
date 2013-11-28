// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ID2AddUser.java

package psdi.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.person.PersonRemote;
import psdi.app.signature.HiddenValueSet;
import psdi.app.signature.MaxUserRemote;
import psdi.mbo.*;
import psdi.server.*;
import psdi.util.MXException;
import psdi.workflow.WorkFlowServiceRemote;

// Referenced classes of package psdi.app.signature.virtual:
//            AddUserRemote

public class ID2AddUser extends NonPersistentMbo
    implements psdi.app.signature.virtual.AddUserRemote
{

    public ID2AddUser(MboSet ms)
        throws MXException, RemoteException
    {
        super(ms);
        userMbo = null;
        personMbo = null;
        hiddenValues = null;
    }

    public void init()
        throws MXException
    {
        super.init();
        try
        {
            hiddenValues = new HiddenValueSet(this, new String[] {
                "password", "passwordcheck"
            });
        }
        catch(Exception e) { }
    }

    void setRelatedValues()
        throws MXException, RemoteException
    {
        setChildValues();
        userMbo.validate();
        personMbo.validate();
        String newregStatus = getTranslator().toExternalDefaultValue("MAXUSERSTATUS", "NEWREG", null);
        if(userMbo.getString("status").equalsIgnoreCase(newregStatus))
        {
            WorkFlowServiceRemote wfs = (WorkFlowServiceRemote)MXServer.getMXServer().lookup("WORKFLOW");
            String processName = null;
            if(MXServer.getMXServer().getMXServerInfo().useAppServerSecurity())
                processName = getMboServer().getMaxVar().getString("LDAPUSERWF", "");
            else
                processName = getMboServer().getMaxVar().getString("SELFREGWF", "");
            boolean isActiveProcess = false;
            try
            {
                isActiveProcess = wfs.isActiveProcess(processName, userMbo.getName(), userMbo.getUserInfo());
            }
            catch(Exception e) { }
            if(isActiveProcess)
                wfs.initiateWorkflow(processName, userMbo);
        }
    }

    public void setChildValues()
        throws MXException, RemoteException
    {
        try
        {
            if(userMbo == null)
                userMbo = (MaxUserRemote)getMboSet("$UserSet", "MAXUSER").add();
            userMbo.setValue("defsite", getString("siteid"), 2L);
            userMbo.setValue("storeroomsite", getString("siteid"), 2L);
            userMbo.setValue("defstoreroom", getString("defaultstoreloc"), 2L);
            userMbo.setValue("storeroomsite", getString("siteid"), 2L);
            userMbo.setValue("userid", getString("username").toUpperCase(), 11L);
            userMbo.setValue("loginid", getString("username"), 2L);
            if(!MXServer.getMXServer().getMXServerInfo().useAppServerSecurity())
            {
                userMbo.setValue("passwordinput", hiddenValues.getMboValue("password").getString(), 2L);
                userMbo.setValue("passwordcheck", hiddenValues.getMboValue("passwordcheck").getString(), 2L);
            }
            userMbo.setValue("PWHINTQUESTION", getString("PWHINTQUESTION"), 2L);
            userMbo.setValue("PWHINTANSWER", getString("PWHINTANSWER"), 2L);
            String userStatus = getMboServer().getMaxVar().getString("REGSTATUS", "");
            if(userStatus != null)
                userMbo.setValue("status", userStatus, 2L);
            else
                userMbo.setValue("status", getTranslator().toExternalDefaultValue("MAXUSERSTATUS", "NEWREG", null), 2L);
            userMbo.setValue("forceexpiration", false, 11L);
            userMbo.setValue("emailpswd", false, 11L);
            userMbo.setValue("memo", getString("additionalinfo"), 11L);
            userMbo.setValue("personid", getString("username").toUpperCase(), 11L);
            MboSetRemote tempPersonSet = userMbo.getMboSet("$Not_the_same_as_PERSON", "person", "personid=:personid");
            tempPersonSet.setFlag(8L, true);
            tempPersonSet.close();
            /*
            personMbo = (PersonRemote)tempPersonSet.add();
            String id = personMbo.isNull("personid") ? getString("username").toUpperCase() : personMbo.getString("personid");
             */
            String id = getString("username").toUpperCase();
            userMbo.setValue("personid", id, 11L);
            MboSetRemote theTruePersonSet = userMbo.getMboSet("PERSON");
            theTruePersonSet.setAutoKeyFlag(false);
            personMbo = (PersonRemote)theTruePersonSet;
            /*
            personMbo = (PersonRemote)theTruePersonSet.add();
            personMbo.setValue("personid", id);
            personMbo.setValue("status", getTranslator().toExternalDefaultValue("PERSONSTATUS", "ACTIVE", null), 2L);
            personMbo.setValue("supervisor", getString("supervisor"), 2L);
            personMbo.setValue("language", getString("langcode"), 2L);
            personMbo.setValue("locale", getString("locale"), 2L);
            personMbo.setValue("timezone", getString("timezone"), 2L);
            if(!MXServer.getMXServer().getMXServerInfo().useAppServerSecurity())
            {
                personMbo.setValue("firstname", getString("firstname"), 2L);
                personMbo.setValue("lastname", getString("lastname"), 2L);
                personMbo.setValue("primaryemail", getString("pagepin"), 2L);
            } else
            {
                personMbo.setValue("acceptingwfmail", false, 2L);
            }
            setMorePersonFields(personMbo);
             */
            userMbo.addGroupUser();
            /*
            if(!MXServer.getMXServer().getMXServerInfo().useAppServerSecurity())
            {
                MboSetRemote emailSet = personMbo.getMboSet("EMAIL");
                MboRemote email = emailSet.getMbo(0);
                if(email == null)
                    email = emailSet.add();
                email.setValue("emailaddress", getString("pagepin"), 2L);
            }
            if(!isNull("phonenum"))
            {
                MboSetRemote phoneSet = personMbo.getMboSet("PHONE");
                MboRemote phone = phoneSet.getMbo(0);
                if(phone == null)
                    phone = phoneSet.add();
                phone.setValue("phonenum", getString("phonenum"), 2L);
            }
             *
             */
        }
        catch(MXException e)
        {
            if(personMbo != null)
                personMbo.getThisMboSet().reset();
            if(userMbo != null)
                userMbo.getThisMboSet().reset();
            personMbo = null;
            userMbo = null;
            throw e;
        }
    }

    public void setMorePersonFields(MboRemote personMbo)
        throws MXException, RemoteException
    {
        if(!isNull("pcardnum"))
            personMbo.setValue("pcardnum", getString("pcardnum"), 2L);
        if(!isNull("pcardtype"))
            personMbo.setValue("pcardtype", getString("pcardtype"), 2L);
        if(!isNull("pcardexpdate"))
            personMbo.setValue("pcardexpdate", getString("pcardexpdate"), 2L);
        if(!isNull("pcardverification"))
            personMbo.setValue("pcardverification", getString("pcardverification"), 2L);
        if(!isNull("address1"))
            personMbo.setValue("addressline1", getString("address1"), 2L);
        if(!isNull("address2"))
            personMbo.setValue("addressline2", getString("address2"), 2L);
        if(!isNull("address1"))
            personMbo.setValue("addressline3", getString("address3"), 2L);
        if(!isNull("birthdate"))
            personMbo.setValue("birthdate", getString("birthdate"), 2L);
        if(!isNull("droppoint"))
            personMbo.setValue("droppoint", getString("droppoint"), 2L);
    }

    public MboRemote getUserMbo()
        throws MXException, RemoteException
    {
        return userMbo;
    }

    public MboRemote getPersonMbo()
        throws MXException, RemoteException
    {
        return personMbo;
    }

    public String[] getValidateOrder()
    {
        return (new String[] {
            "firstname", "lastname", "username", "password", "passwordcheck", "langcode", "locale", "timezone", "pagepin", "emailtype", 
            "supervisor", "siteid", "defaultstoreloc", "phonenum", "phonetype", "additionalinfo"
        });
    }

    public void setValue(String attributeName, String val, long accessModifier)
        throws MXException, RemoteException
    {
        int index = attributeName.indexOf('.');
        attributeName = attributeName.toUpperCase();
        if(index >= 0 || !attributeName.equals("PASSWORD") && !attributeName.equals("PASSWORDCHECK") || val.startsWith(hiddenValue))
        {
            super.setValue(attributeName, val, accessModifier);
            return;
        } else
        {
            hiddenValues.setValue(attributeName, val, accessModifier);
            return;
        }
    }

    static final long serialVersionUID = 0x1c9b55c7ac43eea2L;
    MaxUserRemote userMbo;
    PersonRemote personMbo;
    static String hiddenValue = "QLZ";
    HiddenValueSet hiddenValues;

}
