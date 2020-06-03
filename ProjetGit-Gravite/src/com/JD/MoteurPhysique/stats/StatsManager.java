package com.JD.MoteurPhysique.stats;

import java.util.HashMap;

public class StatsManager {
	private static StatsManager singletonStats;
	
	private HashMap<Long,StatSimulationInstance> stats = new HashMap<Long,StatSimulationInstance>();
	
	private StatsManager() {}
	
	//renvoi l'instance du manageur d'objet statistique
	public static StatsManager getSingletonStat() {
		if(singletonStats == null) {
			singletonStats = new StatsManager();
		}	
		return(singletonStats);
	}
	
	//renvoi l'objet statistique de l'instance "key"
	public StatSimulationInstance getStats(long key) {
		if(!this.stats.containsKey(key))
			this.stats.put(key, new StatSimulationInstance());
		return(this.stats.get(key));
	}
}