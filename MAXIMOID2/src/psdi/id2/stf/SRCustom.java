// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package psdi.id2.stf;

import java.rmi.RemoteException;
import psdi.mbo.*;
import psdi.util.*;
import psdi.app.ticket.*;

/*
Atividade 1030

Quando a seção for STRA e o status for alterado para FECHADA, 
verificar se os campos VEÍCULO, TARGETSTART, KMSAIDA, TARGETFINISH e KMCHEGADA 
estão preenchidos, caso algum esteja em branco, apresentar o erro: (-20300,'Antes de Fechar Requisição, preencha Dados do Veículo.'
*/

public class SRCustom extends SR
    implements SRCustomRemote
{

    public SRCustom(MboSet mboset)
        throws MXException, RemoteException
    {
        super(mboset);
    }

	public void save()
          throws MXException, java.rmi.RemoteException
	{
		if(!isNull("OWNERGROUP"))
		{
			if(getString("OWNERGROUP").equals("STRA"))
			{
				if(getString("STATUS").equals("FECHADA"))
				{
					if(isNull("TARGETFINISH") || isNull("TARGETSTART") || isNull("KMSAIDA") || isNull("KMCHEGADA") || isNull("VEICULO"))
						throw new MXApplicationException("id2", "ID2CamposBrancos");
					if(!isNull("TARGETFINISH") && !isNull("TARGETSTART"))
						if((getDate("TARGETSTART").after(getDate("TARGETFINISH"))))
							throw new MXApplicationException("id2", "ID2DataFinalMaior");
					if((getDouble("KMSAIDA") >= getDouble("KMCHEGADA")))
						throw new MXApplicationException("id2", "ID2KMSaidaMaior");
				}
			}
		}
		super.save();
		return;
	}
}
