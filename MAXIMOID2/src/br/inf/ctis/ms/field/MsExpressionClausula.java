package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.TimeZone;
import psdi.common.expbuilder.ExpressionBuilderSetRemote;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.mbo.SqlFormat;
import psdi.util.MXException;
import psdi.util.MXSystemException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.runtime.WebClientRuntime;

/**
 * 
 * @author andrel.almeida
 *
 */
public class MsExpressionClausula extends MboValueAdapter {
	
	  private String where = "";
	  private String oldWhere = "";
	  private boolean isModified = false;
	  

	public MsExpressionClausula(MboValue mbv) throws MXException {
		super(mbv);
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro da Classe MsExpressionClausula versao 00");
	}
	public void validate() throws MXException, RemoteException {
		
		//String expression = getMboValue().getMbo().getString("DESCRIPTION");
		System.out.println(">>>>>>>>>>>>>>>>>>>> 1-Dentro do metodo validate  ");
		//getWhere(expression);
		oldWhere = getWhere();
		System.out.println(">>>>>>>>>>>>>>>>>>>> 4-Resultado do oldWhere:  "+oldWhere);
		Mbo thisValue = getMboValue().getMbo();
		thisValue.createComm();
		thisValue.setValue("DESCRIPTION", true);
		
	}
	public String getWhere() throws MXException, RemoteException
	  {	    
	    String expression = getMboValue().getMbo().getString("DESCRIPTION");
	    System.out.println(">>>>>>>>>>>>>>>>>>>> 2-Resultado do expression antes do decode:  "+expression);
	    //String userAndQbeWhere = daBean.getUserAndQbeWhere();
	    if (expression != null) {
	    	expression = WebClientRuntime.decodeSafevalue(expression);
	    	System.out.println(">>>>>>>>>>>>>>>>>>>> 3-Resultado do expression apos o decode:  "+expression);	
	    }
	    return expression;
	  }
	
	/*private String getWhere(String expression)
			     throws RemoteException, MXException
			     {
			     if ((this instanceof ExpressionBuilderSetRemote))
			     {
			     if ((!((ExpressionBuilderSetRemote)((Mbo) this.currentMbo).getThisMboSet()).isDotNotationOn()) && (expression.indexOf(":") != -1))
			       {
			         throw new MXSystemException("expbuilder", "nocolonallowef");
			       }
			     }
			 
			     System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do metodo getWhere de expression: "+expression);
			     
			     //MboSetRemote msr = getDiscardableMboSet(this.objectName, this.currentMbo);
			   
			     SqlFormat sqlFormat = new SqlFormat(this.locale, this.timeZone, expression);
			     String formattedExp = sqlFormat.formatRaw();
			     System.out.println(">>>>>>>>>>>>>>>>>>>> Expressao em formattedExp: "+formattedExp);
			     //msr.setWhere(formattedExp);
			     // msr.reset();
			     String mboWhere = "MSTBCLACAP" + " where " + formattedExp;
			     //System.out.println(">>>>>>>>>>>>>>>>>>>> Expressao em mboWhere: "+mboWhere);
			 
			     String where = "Select * from " + mboWhere;
			     
			     System.out.println(">>>>>>>>>>>>>>>>>>>> Expressao em where: "+where);
			     return where;
			   }*/
	
	/*private MboSetRemote getDiscardableMboSet(Object objectName2, Object currentMbo2)
			     throws RemoteException, MXException
			     {
					System.out.println(">>>>>>>>>>>>>>>>>>>> Dentro do metodo getDiscarddableMboSet");
				     MboSetRemote mboset = ((Mbo) currentMbo2).getMboServer().getMboSet((String) objectName2, ((Mbo) currentMbo2).getUserInfo());
				     mboset.setFlag(39L, true);
				     return mboset;
			     }
	*/		     
	/*public void validate(String expression, int flag) throws RemoteException, MXException, SQLException
	  {
	   // String where = getWhere(expression);
	    ConnectionKey ck = ((Mbo) this.currentMbo).getUserInfo().getConnectionKey();
	    Connection conn = ((Mbo) this.currentMbo).getMboServer().getDBConnection(ck);
	    Statement stmt = conn.createStatement();
	   try
	    {
        stmt.execute(expression);
	    }
	    catch (SQLException e)
	    {
	       if (flag == 2)
	        {
	         Object[] params = { e.getMessage().trim() };
	         throw new MXSystemException("expbuilder", "validationfailure", params, e);
	        }
	  
	       throw e;
	     }
	     finally
	     {
	      ((Mbo) this.currentMbo).getMboServer().freeDBConnection(ck);
	      stmt.close();
	     }
	   }*/

}
