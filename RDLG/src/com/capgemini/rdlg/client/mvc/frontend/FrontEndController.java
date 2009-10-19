package com.capgemini.rdlg.client.mvc.frontend;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;

public class FrontEndController extends Controller {
	
	private FrontEndView frontEndView;
	
	@Override
	protected void initialize() {
		super.initialize();
		
		frontEndView = new FrontEndView(this);
	}
	
	public FrontEndController(){
		
	}

	@Override
	public void handleEvent(AppEvent event) {
		
	}
}
