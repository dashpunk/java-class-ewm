package br.inf.ctis.ms.field;

import java.rmi.RemoteException;
import javax.security.auth.Refreshable;
import org.apache.catalina.loader.Reloader;
import com.sun.enterprise.util.Utility;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
import psdi.webclient.system.controller.WebClientEvent;



/**
 * @author willians.andrade
 */

public class MsNumTaxaPrev extends MboValueAdapter {

	public MsNumTaxaPrev(MboValue mbv) {
		super(mbv);
	}

	@Override
	public void validate() throws MXException, RemoteException {

		System.out.print("********** Entrou na classe MsNumTaxaPrev.");
		super.validate();

		MboSetRemote MSTBCOTSVS;
		MSTBCOTSVS = getMboValue().getMbo().getMboSet("MSTBCOTSVS");

		// PUFOB(R$), Frete + Seguro (R$), 3,5% (R$), Buffer (R$), Total (R$)

		if (!getMboValue().getMbo().isNull("MSNUMTXPR")) {
			
			System.out.print("********** Entrou no IF().");
			MboRemote mboa = null;
			MboRemote mbob = null;

			// Calcula os valores baseado na Taxa Prevista USD > BRL
			for (int i = 0; ((mboa = MSTBCOTSVS.getMbo(i)) != null); i++) {
				System.out.print("********** Entrou no FOR(1).");
				float Taxa = getMboValue().getMbo().getFloat("MSNUMTXPR");

				float FobRS = mboa.getFloat("MSNUMFOBUSD") * Taxa;
				float FreteSeguroRS = mboa.getFloat("MSNUMFRETEESEGUSD") * Taxa;
				float PercentRS = mboa.getFloat("MSNUMPERCENT") * Taxa;
				float BufferRS = mboa.getFloat("MSNUMBUFFERUSD") * Taxa;
				float TotalRS = mboa.getFloat("MSNUMTOTALUSD") * Taxa;

				mboa.setValue("MSNUMFOBREAIS", FobRS,
						MboConstants.NOACCESSCHECK);
				mboa.setValue("MSNUMFRETEESEGREAIS", FreteSeguroRS,
						MboConstants.NOACCESSCHECK);
				mboa.setValue("MSNUMPERCENTREAIS", PercentRS,
						MboConstants.NOACCESSCHECK);
				mboa.setValue("MSNUMBUFFERREAIS", BufferRS,
						MboConstants.NOACCESSCHECK);
				mboa.setValue("MSNUMTOTALREAIS", TotalRS,
						MboConstants.NOACCESSCHECK);

				System.out.print("CTIS # " + i + ":" + FobRS + ", "
						+ FreteSeguroRS + ", " + PercentRS + ", " + BufferRS
						+ ", " + TotalRS);
			}

			mboa = null;
			mbob = null;

			// Totalizadores USD
			float FobUSD = 0;
			float FreteSeguroUSD = 0;
			float PercentUSD = 0;
			float BufferUSD = 0;
			float TotalUSD = 0;
			float CifUSD = 0;

			// Totalizadores BRL
			int QuantidadeRS = 0;

			float FobRS = 0;
			float FreteSeguroRS = 0;
			float PercentRS = 0;
			float BufferRS = 0;
			float TotalRS = 0;

			// Calcula os valores dos Totalizados de Todas as Linhas.
			for (int i = 0; ((mboa = MSTBCOTSVS.getMbo(i)) != null); i++) {

				// USD
				FobUSD += mboa.getFloat("MSNUMFOBUSD");
				FreteSeguroUSD += mboa.getFloat("MSNUMFRETEESEGUSD");
				PercentUSD += mboa.getFloat("MSNUMPERCENT");
				BufferUSD += mboa.getFloat("MSNUMBUFFERUSD");
				TotalUSD += mboa.getFloat("MSNUMTOTALUSD");
				CifUSD += mboa.getFloat("MSNUMCIF");

				// BRL
				FobRS += mboa.getFloat("MSNUMFOBREAIS");
				FreteSeguroRS += mboa.getFloat("MSNUMFRETEESEGREAIS");
				PercentRS += mboa.getFloat("MSNUMPERCENTREAIS");
				BufferRS += mboa.getFloat("MSNUMBUFFERREAIS");
				QuantidadeRS += mboa.getInt("MSNUMQNT");
				TotalRS += mboa.getFloat("MSNUMTOTALREAIS");

			}

			// Setando Valores USD / BRL
			getMboValue().getMbo().setValue("MSNUMTOTALFOB", FobUSD,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALFRETESEG",
					FreteSeguroUSD, MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTAL35", PercentUSD,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALBUFFER", BufferUSD,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTAL", TotalUSD,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALCIF", CifUSD,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALCIF35", CifUSD + PercentUSD,
					MboConstants.NOACCESSCHECK);			
			// *************************
			getMboValue().getMbo().setValue("MSNUMTOTALFOBREAIS", FobRS,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALFRETESEGREAIS",
					FreteSeguroRS, MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTAL35REAIS", PercentRS,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALBUFFERREAIS", BufferRS,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALQTD", QuantidadeRS,
					MboConstants.NOACCESSCHECK);
			getMboValue().getMbo().setValue("MSNUMTOTALREAIS", TotalRS,
					MboConstants.NOACCESSCHECK);

			System.out.print("CTIS # Fim de MsNumTaxaPrev");

		}

	}

}
