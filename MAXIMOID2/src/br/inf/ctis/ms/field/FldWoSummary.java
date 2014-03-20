/**
 * 
 */
package br.inf.ctis.ms.field;

/**
 * @author willians.andrade
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

public class FldWoSummary extends MboValueAdapter
{
  public FldWoSummary(MboValue mbv) throws MXException
  {
    super(mbv);
  }

  public void initValue() throws MXException, RemoteException
  {
    super.initValue();

    MboRemote wo = this.getMboValue().getMbo();
    StringBuilder val = new StringBuilder(); 

    // **
    val.append("ID: " + wo.getInt("MSTBCADDEMSVSID") + "<br>");
    val.append("Memorando: " + wo.getString("MSALNMEMO") + "<br>");
    val.append("Tipo: " + wo.getString("MSALNTIPO") + "<br>");
    val.append("Status: " + wo.getString("STATUS") + "<br><br>");

    
    val.append("<table><tr><th>Status</th><th>Data Inicial</th><th>Data Final</th><th>Dias</th></tr>");
   
    
    // SELECT NO OBJETO MSLOGSVS
  
    MboSetRemote LogSvs = getMboValue().getMbo().getMboSet("MSLOGSVS");
    
    int iTamanho = LogSvs.count();
    int TotalDias = 0;
    
    for (int i = 0; i <= iTamanho; i++){
    	if (i < iTamanho - 1){
    		Date DataInicial =  LogSvs.getMbo(i).getDate("DATA");
    		Date DataFinal = LogSvs.getMbo(i+1).getDate("DATA");
    	   	int Dia = Data.recuperaDiasEntreDatas(DataInicial, DataFinal);
    	   	TotalDias += Dia;
    	   	
    	   	// print
    	   	
    	      val.append("<td>"+LogSvs.getMbo(i).getString("STATUS") + "</td><td>" + LogSvs.getMbo(i).getDate("DATA")  + "</td><td>" + LogSvs.getMbo(i+1).getDate("DATA")  + "</td><td>" + Dia + "</td></tr>");
    	}
    }
    val.append("</table><hr> Total de Dias: " + TotalDias);
    
    // **
    getMboValue().setValue(val.toString(), Mbo.NOACCESSCHECK | Mbo.NOVALIDATION);
  }

}