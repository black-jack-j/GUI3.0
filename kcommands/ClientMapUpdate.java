package kcommands;

import bdata.KMap;

public class ClientMapUpdate extends ClientCommand {

	private KMap[] maps;
	
	public ClientMapUpdate(KMap[] map,int priority) {
		super(priority);
		maps = map;
	}

	@Override
	public void execute() {
		viewer.setInput(maps);
		//viewer.refresh();
	}

}
