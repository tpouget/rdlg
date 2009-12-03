package com.capgemini.rdlg.client.mvc.frontend;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.transaction.MyTransactionBalance;
import com.capgemini.rdlg.client.widget.frontend.transaction.MyTransactionPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;

public class MyTransactionView extends View {

	private MyTransactionPanel myTransactionPanel;
	private PagingLoader<PagingLoadResult<Transaction>> loader;
	private LayoutContainer wrapper = Registry.get(AppView.CENTER_PANEL);
	
	public MyTransactionView(Controller controller) {
		super(controller);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
	}
	
	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType().equals(AppEvents.ViewMyTransaction))
			onViewMyTransaction(event);
	}

	private void onViewMyTransaction(AppEvent event) {
		if (myTransactionPanel==null){
			RpcProxy<PagingLoadResult<Transaction>> proxy = 
				event.getData("proxy");
			loader = new BasePagingLoader<PagingLoadResult<Transaction>>(proxy); 
			loader.setRemoteSort(true);
			myTransactionPanel = new MyTransactionPanel(loader);
		}
		
		double balance = event.getData("balance");
		int tickets = event.getData("tickets");
		
		MyTransactionBalance panel = myTransactionPanel.getTransactionBalance();
		panel.setBalance(balance);
		panel.setTickets(tickets);
		panel.removeAll();
		panel.add(new Text("\n\nVotre solde est actuellement de "+balance+" €"));
		if (panel.getBalance()<0){
			String warning = "\n\nVotre solde est négatif, pensez à payez !" +
					"\n\nIl vous faut au moins "+tickets+" chèque";
			if (tickets>1) 
				warning+="s";
			warning+=" déjeuner pour avoir un solde positif.";
			panel.add(new Text(warning));
		}
		
		wrapper.removeAll();
		wrapper.add(myTransactionPanel);
		wrapper.layout();
	}
}
