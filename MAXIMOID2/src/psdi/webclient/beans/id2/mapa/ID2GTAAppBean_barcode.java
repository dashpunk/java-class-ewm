package psdi.webclient.beans.id2.mapa;

import java.rmi.RemoteException;
import psdi.app.location.LocationSetRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.*;
import psdi.webclient.system.controller.*;

public class ID2GTAAppBean_barcode extends psdi.webclient.beans.po.POAppBean
{

    public ID2GTAAppBean_barcode()
    {
    }

	public int SAVE() throws MXException, RemoteException
	{
		
		if(getMbo().getString("STATUS").equals("CRIAR") || getMbo().getString("STATUS").equals("CRIADO"))
		{
			getMbo().setFieldFlag("STORELOC",psdi.mbo.MboConstants.READONLY,false);

			if(getMbo().getString("id2tipolocal").equals("02"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROABA"));
			else if(getMbo().getString("id2tipolocal").equals("03"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROAGL"));
			else if(getMbo().getString("id2tipolocal").equals("04"))
				getMbo().setValue("STORELOC",getMbo().getString("ID2PROEXP"));

			int i = 0;
			do
			{
				MboRemote linhaAnimal = getMbo().getMboSet("POLINE").getMbo(i);

				if(getMbo().getString("id2tipolocaldest").equals("02"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESABA"));
				else if(getMbo().getString("id2tipolocaldest").equals("03"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESAGL"));
				else if(getMbo().getString("id2tipolocaldest").equals("04"))
					linhaAnimal.setValue("STORELOC",getMbo().getString("ID2DESEXP"));

				if(linhaAnimal == null)
					break;
				i++;
			} while (true);
		}
		return super.SAVE();
	}

	public String gerarCodigoBarras()
	{
		String codigoBarras = "";
		try
		{		
			String NUMGTA = "000000";
			String SERIE;
			String UF = getMbo().getString("ID2LOCUF");
			String DATAEMISSAO = ("00").substring(0,("00").length()-(getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getDate()+"").length())+getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getDate()+("00").substring(0,("00").length()-(getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getMonth()+"").length())+getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getMonth()+((getMbo().getMboSet("ID2EMISSAO").getDate("STATUSDATE").getYear()+1900)+"");
			int iQTD = new Double(getMbo().getMboSet("POLINE").sum("ORDERQTY")).intValue();
			String QTDANIMAL = ("0000000").substring(0,("0000000").length()-(iQTD+"").length())+iQTD;
			String ESPECIE = getMbo().getString("COMMODITY");
			String PROPRIEDADE = getMbo().getString("ID2LOCPROEXP.ID2VWLOC01.LOCATION").substring(getMbo().getMboSet("").getString("LOCATION").length()-10,getMbo().getMboSet("").getString("LOCATION").length());
			String MUNICIPIO = getMbo().getString("ID2LOCDESEXP.ID2VWLOC01.ID2CODMUN").substring(getMbo().getMboSet("").getString("ID2CODMUN").length()-6,getMbo().getMboSet("").getString("ID2CODMUN").length());
			String DIGITO1;
			String DIGITO2;
			String DIGITO3;

			char cSerie = getMbo().getString("ID2SERIE").charAt(0);
			int iSerie = (cSerie-65)+1;
			if(iSerie < 10)
				SERIE = "0"+iSerie;
			else
				SERIE = ""+iSerie;

			NUMGTA = NUMGTA.substring(0,(NUMGTA.length())-getMbo().getString("ID2NUMGTA").length())+getMbo().getString("ID2NUMGTA");

			String verificador = UF+SERIE+NUMGTA;
			
			int j = 2;
			int res=0;

			Integer digito;

			for(int i=verificador.length()-1;i>=0;i--)
			{
				digito = new Integer(verificador.substring(i,i+1));
				res = res + (digito.intValue() * j);
				j++;
				if(j>9) j = 2;
			}
			DIGITO1 = ((11-(res % 11))>9?0:11-(res % 11))+"";

			verificador = UF+SERIE+NUMGTA+DIGITO1+DATAEMISSAO+ESPECIE+QTDANIMAL;
			
			j = 2;
			res=0;

			for(int i=verificador.length()-1;i>=0;i--)
			{
				digito = new Integer(verificador.substring(i,i+1));
				res = res + (digito.intValue() * j);
				j++;
				if(j>9) j = 2;
			}
			DIGITO2 = ((11-(res % 11))>9?0:11-(res % 11))+"";

			verificador = UF+SERIE+NUMGTA+DIGITO1+DATAEMISSAO+ESPECIE+QTDANIMAL+DIGITO2+PROPRIEDADE+MUNICIPIO;

			j = 2;
			res=0;

			for(int i=verificador.length()-1;i>=0;i--)
			{
				digito = new Integer(verificador.substring(i,i+1));
				res = res + (digito.intValue() * j);
				j++;
				if(j>9) j = 2;
			}
			DIGITO3 = ((11-(res % 11))>9?0:11-(res % 11))+"";

			codigoBarras = verificador + DIGITO3;

		}
		catch (MXException mxe)
		{
		}
		catch (RemoteException re)
		{
		}
		return codigoBarras;
	}


}