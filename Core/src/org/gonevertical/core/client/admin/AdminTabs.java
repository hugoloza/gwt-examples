package org.gonevertical.core.client.admin;

import org.gonevertical.core.client.ClientPersistence;
import org.gonevertical.core.client.admin.thing.Things;
import org.gonevertical.core.client.admin.thingstufftype.ThingStuffTypes;
import org.gonevertical.core.client.admin.thingtype.ThingTypes;
import org.gonevertical.core.client.global.QueryString;
import org.gonevertical.core.client.global.QueryStringData;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class AdminTabs extends Composite implements BeforeSelectionHandler<Integer>, SelectionHandler<Integer>, ValueChangeHandler<String> {

  private ClientPersistence cp = null;
  
  private TabPanel pWidget = new TabPanel();
  
  // tabed widgets
  private AdminHome wHome = null;
  private ThingTypes wTypes = null;
  private ThingStuffTypes wStuffTypes = null;
  private Things wThings = null;
  
  public AdminTabs(ClientPersistence cp) {
    this.cp = cp;
    
    wHome = new AdminHome(cp);
    wTypes = new ThingTypes(cp);
    wStuffTypes = new ThingStuffTypes(cp);
    wThings = new Things(cp);
    
    pWidget.add(wHome, "Home");
    pWidget.add(wTypes, "Thing Types");
    pWidget.add(wStuffTypes, "Stuff Types");
    pWidget.add(wThings, "Things");

    initWidget(pWidget);
    
    pWidget.setWidth("100%");
    
    pWidget.addBeforeSelectionHandler(this);
    pWidget.addSelectionHandler(this);
    
    initHistory();
  }
  
  private void initHistory() {
    History.addValueChangeHandler(this);
  }
  
  /**
   * draw tab
   */
  public void draw() {
    
    QueryStringData qsd = QueryString.getQueryStringData();
    
    String ht = qsd.getHistoryToken();
    if (ht.equals("core_home") == true) {
      pWidget.selectTab(0);
      
    } else if (ht.equals("core_types") == true) {
      pWidget.selectTab(1);
      
    } else if (ht.equals("core_stufftype") == true) {
      pWidget.selectTab(2);
      
    } else if (ht.equals("core_things") == true) {
      pWidget.selectTab(3);
    }
    
  }
  
  private void drawHome() {
  	wHome.draw();
  }
  
  private void drawTypes() {
    wTypes.draw();
  }
  
  private void drawStuffType() {
    wStuffTypes.draw();
  }

  private void drawThings() {
    wThings.draw();
  }
  
  public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
    
    int tab = event.getItem();
    
    if (tab == 0) {
      drawHome();
      
    } else if (tab == 1) {
      drawTypes();
      
    } else if (tab == 2) {
      drawStuffType();
      
    } else if (tab == 3) {
      drawThings();
      
    } 
    
  }

  public void onSelection(SelectionEvent<Integer> event) {
  
    int tab = event.getSelectedItem();
    
    if (tab == 0) {
      History.newItem("core_home");
      
    } else if (tab == 1) {
      History.newItem("core_types");
      
    } else if (tab == 2) {
      History.newItem("core_stufftype");
      
    } else if (tab == 3) {
      History.newItem("core_things");
      
    } 
    
  }


  public void onValueChange(ValueChangeEvent<String> event) {
    //draw();
  }
  
  
}
