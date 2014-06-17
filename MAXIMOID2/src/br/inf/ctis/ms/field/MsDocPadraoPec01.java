/**
 * 
 */
package br.inf.ctis.ms.field;

/**
 * @author andrel.almeida
 *
 */
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Properties;
import java.util.Date;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.server.MXServer;
import psdi.util.DBConnect;
import psdi.util.MXException;
import br.inf.id2.common.util.Data;

public class MsDocPadraoPec01 extends MboValueAdapter {
	
	public MsDocPadraoPec01(MboValue mbv) throws MXException
	  {
	    super(mbv);
	  }

	public void initValue() throws MXException, RemoteException
	  {
	    super.initValue();

	    StringBuilder val = new StringBuilder(); 

	    // **
	    val.append("<table>");
	    val.append("<tbody><tr>");
	    val.append("<td width=\"300\">");
	    val.append("img src=\"../webclient/images/ab_appimg_createdr.gif\"");
	    val.append("<p><strong>MINIST�RIO DA SA�DE</strong></p>");
	    val.append("<p><strong>SECRETARIA EXECUTIVA</strong></p>");
	    val.append("<p><strong>DEPARTAMENTO DE LOG�STICA EM SA�DE</strong></p>");
	    val.append("<p><strong>COORDENA��O-GERAL DE AN�LISE DAS CONTRATA��ES DE INSUMOS ESTRAT�GICOS PARA SA�DE � CGIES</strong></p>");
	    val.append("<p>Esplanada dos Minist�rios. Bloco G, Anexo A, 4� Andar � Sala 471� - CEP: 70.058-900 � Bras�lia/DF</p>");
	    val.append("<p>Telefone: (61) 3315.3384�� Fax: (61) 3315.3958</p>");
	    val.append("</td>");
	    val.append("</tr>");
	    val.append("</tbody>");
	    val.append("</table>");	    
	    val.append("<p><strong>Of�cio n�. 19/2014/CGIES/DLOG/SE/MS</strong></p>");
	    
	    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
	    
	   
	  }
}
