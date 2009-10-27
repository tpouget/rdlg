package com.capgemini.rdlg.client.mvc.frontend;

import java.util.List;

import com.capgemini.rdlg.client.AppEvents;
import com.capgemini.rdlg.client.model.Meal;
import com.capgemini.rdlg.client.mvc.AppView;
import com.capgemini.rdlg.client.widget.shared.PanelState;
import com.capgemini.rdlg.client.widget.shared.WeekMenuPanel;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

public class WeekMenuView extends View {

  private WeekMenuPanel weekMenuPanel;
  private LayoutContainer wrapper 
  	= (LayoutContainer) Registry.get(AppView.CENTER_PANEL);
  private GroupingStore<Meal> store = new GroupingStore<Meal>();

  public WeekMenuView(Controller controller) {
    super(controller);
  }

  @Override
  protected void initialize() {
	  weekMenuPanel = new WeekMenuPanel(PanelState.FRONTEND, store);
  }

  @Override
  protected void handleEvent(AppEvent event) {
    if (event.getType() == AppEvents.ViewFrontendMenuSemaine) {
      wrapper.removeAll();
      wrapper.add(weekMenuPanel);
      
      store.removeAll();
      store.add(event.<List<Meal>>getData());
      store.addListener(GroupingStore.Update, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				weekMenuPanel.getView().refresh(false);
			};
		});
      wrapper.layout();
      return;
      
    } 
  }
}
