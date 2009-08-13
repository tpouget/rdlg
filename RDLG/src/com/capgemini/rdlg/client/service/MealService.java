package com.capgemini.rdlg.client.service;

import java.util.List;

import com.capgemini.rdlg.client.model.Meal;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("plat")
public interface MealService extends RemoteService {
	public Meal persistPlat(Meal plat);
	public List<Meal> persistPlats(List<Meal> plats);
	public List<Meal> getPlatsMenuSemaine();
	public List<Meal> getPlatsRemplacement();
}
