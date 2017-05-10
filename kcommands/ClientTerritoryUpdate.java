package kcommands;

import bdata.KPlace;

public class ClientTerritoryUpdate extends ClientCommand {
	
	private KPlace[] places;
	
	public ClientTerritoryUpdate(int priority) {
		super(priority);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		viewer.setInput(places);
		//viewer.refresh();  probably won't work due to SWT archetecture
	}

}
