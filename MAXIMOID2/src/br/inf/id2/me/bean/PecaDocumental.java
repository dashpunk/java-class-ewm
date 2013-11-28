package br.inf.id2.me.bean;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import br.inf.id2.common.util.Uteis;

/**
 * 
 * @author Dyogo
 *
 */

public class PecaDocumental extends psdi.webclient.beans.asset.AssetAppBean
{

	public PecaDocumental() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int INSERT() throws MXException, RemoteException {
		int ret = super.INSERT();
		System.out.println("##################### INSERT()... definindo Location");
		System.out.println("### Databean + MboSet = " + app.getDataBean());
		System.out.println("### Databean + MboSet = " + app.getDataBean("MAINRECORD"));
		System.out.println("### Databean + MboSet = " + app.getDataBean().getMbo());
		System.out.println("### Databean + MboSet = " + app.getDataBean("MAINRECORD").getMbo());
		if (app.getDataBean().getMbo().getMboSet("RL02PERSON") != null ||
			app.getDataBean().getMbo().getMboSet("RL02PERSON").count() > 0) {
			//No caso da VALEC será setado no campo Location da Asset
			//getMbo().setValue("LOCATION", app.getDataBean().getMbo().getMboSet("RL02PERSON").getMbo(0).getString("NUCODUAPROT"));
			//No Caso do ME será setado no campo NUCODUAID (referencia para MXTBUA)
			getMbo().setValue("NUCODUAID", app.getDataBean().getMbo().getMboSet("RL02PERSON").getMbo(0).getString("NUCODUAPROT"));
			refreshTable();
			reloadTable();
		}
		System.out.println("##################### Location definida, chamando super...");
		return ret;
	}

	
	@Override
	public int SAVE() throws RemoteException, MXException {
	
		long lMbo = getMbo().getUniqueIDValue();
		
		salvarRegistro();
		
		int iSave = super.SAVE();
		getMbo().getThisMboSet().getMboForUniqueId(lMbo);

		return iSave;
	}
	
	/* TODO Testar essa funcionalidade
	@Override
	public synchronized void save() throws MXException {
		
		try {
			System.out.println("################ Entrei no save()...");
			salvarRegistro();
		} catch (Exception e) {
			System.out.println("################### Ocorreu um erro no save()... " + e.getMessage());
			throw new MXApplicationException("protocolo", "errosaveminusculo");
		}
		
		super.save();
	}
	
	@Override
	public synchronized void save(MboRemote mbo) throws MXException,
			RemoteException {

		System.out.println("################ Entrei no save(MboRemote mbo)...");
		salvarRegistro();
		
		super.save(mbo);
	}

	*/
	
	private void salvarRegistro() throws RemoteException, MXException {
		System.out.println("######################### Entrei no Save()");
		MboSetRemote sequenciaSet;
		int iNumero = 0;
		
		if (getMbo().getMboSet("RL02TRANS") != null && getMbo().getMboSet("RL02TRANS").count() > 0) {
			getMbo().setValue("DTRES", getMbo().getMboSet("RL02TRANS").getMbo(0).getDate("DTRES"));
		}
		
		System.out.println("################# Registro novo? = " + getMbo().isNew());
		
		if (getMbo().isNew()) {
			int iAno = new GregorianCalendar().get(GregorianCalendar.YEAR);
			//Recupera o nï¿½mero da tabela TBSERIAL para verificar qual he o ultimo numero gerado e incrementa.
			sequenciaSet = getMbo().getMboSet("RL01PREFUP");
			String valorSemForm = "";
			String valorFinal = "";
		
			if (sequenciaSet.count() > 0) {
				if(!sequenciaSet.getMbo(0).getMboSet("RL01PREFIX").getMbo(0).getMboValueData("NUSEQ").isNull())
					iNumero = sequenciaSet.getMbo(0).getMboSet("RL01PREFIX").getMbo(0).getInt("NUSEQ");
				iNumero++;
				System.out.println("############### NUMERO: " + iNumero);

				//valorFinal = sequenciaSet.getMbo(0).getString("CODPREFIXO")+"." + Uteis.adicionaValorEsquerda(iNumero+"", "0", 6) + "/" + iAno;
				valorSemForm = sequenciaSet.getMbo(0).getString("CODPREFIXO") + Uteis.adicionaValorEsquerda(iNumero+"", "0", 6) + iAno;
				System.out.println("###################### Codigo atual = " + valorSemForm);
				valorFinal = calculaDigito(valorSemForm);
				
				setValue("SERIALNUM", valorFinal);
				setValue("ASSETNUM", valorFinal);
				
				sequenciaSet.getMbo(0).getMboSet("RL01PREFIX").getMbo(0).setValue("NUSEQ", iNumero);
				sequenciaSet.getMbo(0).setValue("NUSEQ", iNumero);
				sequenciaSet.getMbo(0).getMboSet("RL01PREFIX").save();
				sequenciaSet.save();
			}
			else if (getMbo().getString("DSTIPPRO") != null && (getMbo().getString("DSTIPPRO").equalsIgnoreCase("PROCANTIGO") ||
					 getMbo().getString("DSTIPPRO") != null && getMbo().getString("DSTIPPRO").equalsIgnoreCase("PROCEXT"))) {
				valorSemForm = Uteis.getApenasNumeros(getMbo().getString("SERIALNUM"));
				System.out.println("##################### PROCANTIGO ou PROCEXT = " + valorSemForm);
				if (valorSemForm.length() == 17) {
					valorFinal = Uteis.getValorMascarado("99999.999999/9999-99", valorSemForm, true);
				} else if (valorSemForm.length() == 15) {
					valorFinal = calculaDigito(valorSemForm);
				} else {
					throw new MXApplicationException("protocolo", "ValorSerialNumInvalido");				
				}
				
				setValue("SERIALNUM", valorFinal);
				setValue("ASSETNUM", valorFinal);
				
			}

			System.out.println("############# VALOR FINAL: " + valorFinal);
			System.out.println("################ Foi salvo!");
			
		}
	}

	private String calculaDigito(String valorSemForm) {
		
		String valorFinal = "";
		//Gerando o dï¿½gito verificador
		if (valorSemForm.length() == 15) {
			int iTotal = 0;
			for (int i=0; i< 15; i++) {
				int peso = i + 2;
				int posicao = 14 - i;
				System.out.println("############ Entrei(" + i + ") vezes, posicao= " + posicao + "|peso=" + peso);
				iTotal += Integer.parseInt(valorSemForm.charAt(posicao) + "") * peso;
				
			}
			//Verifica o MOD 11 do DV1
			int mod11 = iTotal % 11;
			int iDV1 = 11 - mod11;
			System.out.println("#################### Primeiro digito Verificador= " + iDV1);
			
			//Gerando o segundo DV
			valorSemForm += iDV1;
			iTotal = 0;
			System.out.println("###################### Codigo atual = " + valorSemForm);
			for (int i=0; i< 16; i++) {
				int peso = i + 2;
				int posicao = 15 - i;
				System.out.println("############ Entrei (" + i + ") vezes, posicao= " + posicao + "|peso=" + peso);
				iTotal += Integer.parseInt(valorSemForm.charAt(posicao) + "") * peso;
			}
			//Verifica o MOD 11 do DV2
			mod11 = iTotal % 11;
			int iDV2 = 11 - mod11;
			System.out.println("#################### Segundo digito Verificador= " + iDV2);
			valorSemForm += iDV2;

			valorFinal = Uteis.getValorMascarado("99999.999999/9999-99", valorSemForm, true);
		}
		return valorFinal;
	}
	
	public void autuar() throws RemoteException, MXException{
		System.out.println("*** autuar ");
		
		getMbo().setFieldFlag("DSTIPPRO", MboConstants.READONLY, false);
		getMbo().setValue("DSTIPPRO", "PROCESSO", MboConstants.NOACCESSCHECK);
		getMbo().setFieldFlag("DSTIPPRO", MboConstants.READONLY, true);
		
		refreshTable();
		reloadTable();
	}
}