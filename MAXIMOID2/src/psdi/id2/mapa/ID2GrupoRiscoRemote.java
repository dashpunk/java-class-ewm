/*
 * ID2GrupoRiscoRemote.java
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
import psdi.util.*;
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;

public interface ID2GrupoRiscoRemote extends MboRemote {
 
	public void add() throws MXException, RemoteException;
}
