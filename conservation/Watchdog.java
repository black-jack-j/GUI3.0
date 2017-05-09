package conservation;

import gui.Keeper;

public interface Watchdog {
	public void save(Keeper k);
	public Keeper load();
}
