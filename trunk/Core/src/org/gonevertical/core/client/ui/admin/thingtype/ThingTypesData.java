package org.gonevertical.core.client.ui.admin.thingtype;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ThingTypesData implements IsSerializable {

  private long total = 0;
  
  private ThingTypeData[] thingTypeData = null;
  
  public void setThingTypeData(ThingTypeData[] thingTypeData) {
  	this.thingTypeData = thingTypeData;
  }
  
  public ThingTypeData[] getThingTypeData() {
  	return thingTypeData;
  }
  
  public void setTotal(long total) {
  	this.total = total;
  }
  
  public long getTotal() {
  	return total;
  }
  
  /**
   * get the thing type by id
   * 
   * @param thingTypeId
   * @return
   */
  public ThingTypeData getThingType(long thingTypeId) {
  	if (thingTypeData == null) {
  		return null;
  	}
    ThingTypeData r = null;
    for (int i=0; i < thingTypeData.length; i++) {
      long id = thingTypeData[i].getId();
      if (id == thingTypeId) {
        r = thingTypeData[i];
        break;
      }
    }
    return r;
  }
  
}