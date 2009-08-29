package com.gawkat.core.server;

import com.gawkat.core.client.account.UserData;
import com.gawkat.core.client.account.thingtype.ThingTypeData;
import com.gawkat.core.client.account.thingtype.ThingTypeFilterData;
import com.gawkat.core.client.account.thingtype.ThingTypesData;
import com.gawkat.core.client.oauth.OAuthTokenData;
import com.gawkat.core.client.rpc.RpcCoreService;
import com.gawkat.core.server.db.Db_ThingType;
import com.gawkat.core.server.db.Db_User;
import com.gawkat.core.server.db.oauth.OAuthServer;
import com.gawkat.core.server.jdo.SetDefaults;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RpcCoreServiceImpl extends RemoteServiceServlet implements RpcCoreService {

	/**
	 * clear a eclipse notification with this
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * store server persistence items
	 */
	private ServerPersistence sp = new ServerPersistence();
	
	/**
	 * test method for rpc
	 */
	public String testMethod(String s) {
	  sp.start(getThreadLocalRequest());
		s += " was modified on the server.";
		sp.end();
		return s;
	}

	/**
	 * A. ->(B.?) grant request token?
	 */
	public OAuthTokenData requestToken(OAuthTokenData tokenData) {
	  sp.start(getThreadLocalRequest());
		OAuthServer oauth = new OAuthServer(sp);
		OAuthTokenData r = oauth.requestToken(tokenData);
		sp.end();
		return r;
	}

  public UserData createUser(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.createUser(userData);
    sp.end();
    return r;
  }
  
  public UserData doesUserNameExist(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.getUserExist(userData);
    sp.end();
    return r;
  }
  
  public UserData forgotPassword(UserData userData) {
    sp.start(getThreadLocalRequest());
    Db_User db = new Db_User(sp);
    UserData r = db.forgotPassword(userData);
    sp.end();
    return r;
  }
  
  public OAuthTokenData getUserAccessToken(OAuthTokenData appAccessToken) {
    sp.start(getThreadLocalRequest());
    OAuthServer oauth = new OAuthServer(sp);
    OAuthTokenData rtnToken = oauth.getUserAccessToken(appAccessToken);
    sp.end();
    return rtnToken;
  }
	
  
  
  /**
   * get thing types
   * 
   * @param filter
   * @return
   */
  public ThingTypesData getThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter) {
    sp.start(getThreadLocalRequest());
    Db_ThingType thingType = new Db_ThingType(sp);
    ThingTypesData r = thingType.getThingTypes(filter);
    sp.end();
    return r;
  }
  
  /**
   * set default items in db
   */
  public boolean setDefaults(OAuthTokenData accessToken, int defaultType) {
    sp.start(getThreadLocalRequest());
    SetDefaults sd = new SetDefaults(sp);
    boolean r = sd.setDefaults(defaultType);
    sp.end();
    return r;
  }
  
  public ThingTypesData saveThingTypes(OAuthTokenData accessToken, ThingTypeFilterData filter, ThingTypeData[] thingTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingType t = new Db_ThingType(sp);
    ThingTypesData r = t.saveThingTypes(filter, thingTypeData);
    sp.end();
    return r;
  }
  
  public boolean deleteThingType(OAuthTokenData accessToken, ThingTypeData thingTypeData) {
    sp.start(getThreadLocalRequest());
    Db_ThingType t = new Db_ThingType(sp);
    boolean r = t.delete(accessToken, thingTypeData);
    sp.end();
    return r;
  }
  
}