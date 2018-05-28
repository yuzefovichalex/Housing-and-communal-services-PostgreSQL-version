package by.grsu.yuzefovich.datamodel;

import java.util.ArrayList;


public class Dispatcher {
	
	private ArrayList<Tenant> allTenants;
	
	public void processingRequest(Long id, int numberOfWorkers) {
		//for (Tenant tenant : allTenants)
			//if (tenant.getId() == id) {
				//ArrayList<Request> newRequests = tenant.getNewRequests();
				//ArrayList<Request> acceptedRequests = tenant.getAcceptedRequests();
				//String type = newRequests.get(0).getTypeOfWork();
				//int scope = newRequests.get(0).getScopeOfWork();
				//int time = newRequests.get(0).getLeadTime();
				//acceptedRequests.add(new Request(type, scope, time, numberOfWorkers));
				//newRequests.remove(0);
				//tenant.setAcceptedRequests(acceptedRequests);
				//tenant.setNewRequests(newRequests);
				//break;
			//}
				
	}

}
