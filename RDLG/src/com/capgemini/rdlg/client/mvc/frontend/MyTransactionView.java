package com.capgemini.rdlg.client.mvc.frontend;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Transaction;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.frontend.transaction.MyTransactionPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class MyTransactionView extends View {

	private MyTransactionPanel myTransactionPanel;
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
			//XXX might need to be a class field
			final PagingLoader<PagingLoadResult<ModelData>> loader 
	    		= new BasePagingLoader<PagingLoadResult<ModelData>>(proxy); 
			loader.setRemoteSort(true);
			myTransactionPanel = new MyTransactionPanel(loader);
		}
		
		wrapper.removeAll();
		wrapper.add(myTransactionPanel);
		wrapper.layout();
	}
}
