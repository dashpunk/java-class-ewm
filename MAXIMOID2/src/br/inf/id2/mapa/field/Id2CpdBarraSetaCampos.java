package br.inf.id2.mapa.field;

import java.rmi.RemoteException;

import psdi.mbo.MboConstants;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Leysson Barbosa Moreira
 *  
 */
public class Id2CpdBarraSetaCampos extends MboValueAdapter {

	private enum SiglaSerie {
		A("01"),B("02"),C("03"),D("04"),E("05"),F("06"),G("07"),H("08"),I("09"),J("10"),K("11"),
		L("12"),M("13"),N("14"),O("15"),P("16"),Q("17"),R("18"),S("19"),T("20"),U("21"),V("22"),
		W("23"),X("24"),Y("25"),Z("26");
		
		String sigla;
		
		SiglaSerie(String sigla){
			this.sigla = sigla;
		}
		
		public String getSigla() {
			return sigla;
		}
	}
	
	private enum Estado {
		AC("12"), AL("27"), AM("13"), AP("16"), BA("29"), CE("23"), DF("53"), ES("32"), GO("52"), MA("21"), MG("31"), MS("51"), MT("50"), 
		PA("15"), PB("25"), PE("26"), PI("22"), PR("41"), RJ("33"), RN("24"), RO("11"), RR("14"), RS("43"), SC("42"), SE("28"), SP("35"), TO("17");
		
		String estado;
		
		Estado(String estado){
			this.estado = estado;
		}
		
		public String getEstado() {
			return estado;
		}
	}
	
    public Id2CpdBarraSetaCampos(MboValue mbv) {
        super(mbv);
    }

    @Override
    public void validate() throws MXException, RemoteException {
    	super.validate();
    	
    	if(!getMboValue().getMbo().getString("ID2CODBARRA").equals("") && getMboValue().getMbo().getBoolean("MATEMCODBAR")) {
	    	String codBarrasRecebido = getMboValue().getMbo().getString("ID2CODBARRA");
	    	String codBarrasFormatado = Uteis.retiraCaracteresEspeciais(codBarrasRecebido);
	    	
	    	String numGTA = codBarrasFormatado.substring(4,10);
	    	String serie = codBarrasFormatado.substring(2,4);
	    	String uf = codBarrasFormatado.substring(0,2);
	    	
	    	getMboValue().getMbo().setFieldFlag("ID2NUMGTA", MboConstants.READONLY, false);
	    	getMboValue().getMbo().setValue("ID2NUMGTA", numGTA);
	    	getMboValue().getMbo().setFieldFlag("ID2NUMGTA", MboConstants.READONLY, true);
	    	
	    	for (SiglaSerie siglaSerie : SiglaSerie.values()){  
	             if(siglaSerie.getSigla().equals(serie)) {
	            	getMboValue().getMbo().setFieldFlag("ID2SERIE", MboConstants.READONLY, false);
	       	    	getMboValue().getMbo().setValue("ID2SERIE", siglaSerie.name());
	       	    	getMboValue().getMbo().setFieldFlag("ID2SERIE", MboConstants.READONLY, true);
	       	    	break;
	             } 
	        }
	    	
	    	for (Estado estado : Estado.values()){  
	             if(estado.getEstado().equals(uf)) {
	            	getMboValue().getMbo().setFieldFlag("ID2ADDUF", MboConstants.READONLY, false);
	       	    	getMboValue().getMbo().setValue("ID2ADDUF", estado.name());
	       	    	getMboValue().getMbo().setFieldFlag("ID2ADDUF", MboConstants.READONLY, true);
	       	    	break;
	            }
	        }
    	}
    	
    }
}
