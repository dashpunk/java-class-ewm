/*
 * RemanOrcRemote.java
 *
 * Created on 30 de Maio de 2006, 17:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package psdi.id2.stf;
/**
 *
 * @author jesse.rovira
 */
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.app.ticket.*;

public interface SRCustomRemote extends SRRemote {

	public abstract void save() throws MXException, java.rmi.RemoteException;
    
}
