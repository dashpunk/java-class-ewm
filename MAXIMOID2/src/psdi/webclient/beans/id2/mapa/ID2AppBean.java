/*
 * Esta classe é a bean que apoia a tela de passagem de parâmetros para o relatório e faz a chamada do servlet.
 * Para que essa classe funcione, algumas interações em tela devem ocorrer, como por exemplo passar o nome do relatório incluindo
 * a extensão .jasper no atributo "Valor do Evento" no botão de OK da caixa de dialogo
 */

/**
 *
 * @author Jessé Rovira
 */

package psdi.webclient.beans.id2.mapa;

import psdi.util.*;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.*;
import psdi.server.*;

import java.util.*;
import java.io.*;
import java.rmi.*;

public class ID2AppBean extends AppBean
{

    public ID2AppBean()
    {

    }

	public void ADDROWONTABLE()
	{
		WebClientEvent event = sessionContext.getCurrentEvent();
		System.out.println("Teste 1");
		Utility.sendEvent(new WebClientEvent("addrow", (String)event.getValue(), event.getValue(), sessionContext));
		System.out.println("Teste 2");
	}

}