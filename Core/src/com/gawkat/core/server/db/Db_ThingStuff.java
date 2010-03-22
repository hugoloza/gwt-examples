package com.gawkat.core.server.db;

import com.gawkat.core.client.account.thingstuff.ThingStuffData;
import com.gawkat.core.client.account.thingstuff.ThingStuffFilterData;
import com.gawkat.core.client.account.thingstuff.ThingStuffsData;
import com.gawkat.core.client.account.thingstufftype.ThingStuffTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.server.ServerPersistence;
import com.gawkat.core.server.jdo.data.ThingStuffAboutJdo;
import com.gawkat.core.server.jdo.data.ThingStuffJdo;

public class Db_ThingStuff {

  private ServerPersistence sp = null;
  
	private ThingStuffJdo dbThingJdo;

	private ThingStuffAboutJdo dbThingAboutJdo;
  
  public Db_ThingStuff(ServerPersistence sp) {
    this.sp = sp;
    dbThingJdo = new ThingStuffJdo(sp);
    dbThingAboutJdo = new ThingStuffAboutJdo(sp);
  }
  
  public ThingStuffsData getThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter) {
    
  	ThingStuffJdo tsj = new ThingStuffJdo(sp);
    ThingStuffData[] thingStuffData = tsj.query(filter);
    
    if (thingStuffData != null) {
      for (int i=0; i < thingStuffData.length; i++) {
      	long id = thingStuffData[i].getId();
      	ThingStuffFilterData f = new ThingStuffFilterData();
      	f.thingId = thingStuffData[i].getThingId();
      	f.thingStuffJdoId = id;
      	ThingStuffData[] tsds = dbThingAboutJdo.query(f);
      	ThingStuffsData tss = new ThingStuffsData();
      	tss.total = tsds.length;
      	tss.thingStuffData = tsds;
      	thingStuffData[i].setThingStuffsAbout(tss);
      }
    }
    
    Db_ThingStuffType t = new Db_ThingStuffType(sp);
    ThingStuffTypesData thingStuffTypesData = t.getThingStuffTypes(accessToken, null); // TODO filter ?
    
    ThingStuffsData r = new ThingStuffsData();
    r.total = 0;
    r.thingStuffData = thingStuffData;
    r.thingStuffTypesData = thingStuffTypesData;
    
    return r;
  }
  
  public ThingStuffsData saveThingStuffData(OAuthTokenData accessToken, ThingStuffFilterData filter, ThingStuffData[] thingStuffData) {
    
  	if (thingStuffData == null) {
  		return null;
  	}
  	
    for (int i=0; i < thingStuffData.length; i++) {
      save(thingStuffData[i]);
    }
    
    // load stuff and return it agian
    ThingStuffsData r = getThingStuffData(accessToken, filter);
    
    return r;
  }
  
  private boolean save(ThingStuffData thingStuffData) {
  
  	if (thingStuffData == null) {
  		return true;
  	}
  	
    long id = dbThingJdo.save(thingStuffData);
    
    // ***** below this will save the multi dem About
    
    // save about the thingStuffs
    ThingStuffsData thingStuffsData = thingStuffData.getThingStuffsAbout();

    if (thingStuffsData == null || thingStuffsData.getThingStuffData() == null) {
    	return true;
    }
    
    ThingStuffData[] tsds = thingStuffsData.getThingStuffData();
    
    // save thingstuff About
    for (int i=0; i < tsds.length; i++) {
    	dbThingAboutJdo.save(id, thingStuffData);
    }
    
    return true;
  }

  public boolean deleteThingStuffData(OAuthTokenData accessToken, long thingStuffId) {
    boolean b = dbThingJdo.delete(sp, thingStuffId);
    return b;
  }
  
}
