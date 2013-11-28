/*
 * RemanOrcRemote.java
 *
 * Created on 30 de Maio de 2006, 17:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package psdi.id2.mapa;
/**
 *
 * @author jesse.rovira
 */
import psdi.mbo.MboRemote;
import psdi.util.MXException;

public interface ID2MemOSRemote extends MboRemote {

	public abstract void add() throws MXException, java.rmi.RemoteException;
    
}
