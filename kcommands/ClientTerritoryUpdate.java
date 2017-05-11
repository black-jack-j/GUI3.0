package kcommands;

import bdata.KPlace;

public class ClientTerritoryUpdate extends ClientCommand {
	
	private KPlace[] places;
	
	public ClientTerritoryUpdate(KPlace[] arr, int priority) {
		super(priority);
		this.places = arr;
	}

	@Override
	public void execute() {
		viewer.setInput(places);
		//viewer.refresh();  probably won't work due to SWT archetecture
	}

}
