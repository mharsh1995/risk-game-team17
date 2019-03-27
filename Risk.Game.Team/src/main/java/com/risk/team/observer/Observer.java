package com.risk.team.observer;

/**
 * This is observer class
 * @author Jenny
 */
public abstract class Observer {
    protected Subject observerSubject;

    /**
     * this method update new changes
     */
    public abstract void update();
    /**
     * This method update to print events taken during fortification phase
     */
    public abstract void fortificationUpdate();

    /**
     * This method update to print events taken during attack phase
     */
    public abstract void attackUpdate();

    /**
     * This method update card changes
     */
    public abstract void tradeInCardUpdate();

    /**
     * This method update card changes for bot player
     */
    public abstract void botTradeInCardUpdate();

    /**
     * This method update to print events taken during reinforcement phase
     */
    public abstract void reinforcementUpdate();

    /**
     * This method is used to print player details
     */
    public abstract void playerLogUpdate();
    
    /**
     * This method is used to print current player details
     */
    public abstract void currentPlayerUpdate();
    
    /**
     * This method is used to print action details
     */
    public abstract void actionsUpdate();
    
    /**
     * This method is used to print army count after reinforcement
     */
	public abstract void armyCountUpdate();
	
	/**
     * This method is used to print attack action details
     */
	public abstract void attackActionUpdate();
	/**
     * This method is used to print fortification action details
     */
	public abstract void fortificationActionUpdate();
	/**
     * This method is used to print trade army count details
     */
	public abstract void tradeArmyUpdate();
	
	/**
     * This method is used to print army count owned by player details
     */
	public abstract void playerOwnedArmyCountUpdate();
	/**
     * This method is used to print continents owned by player details
     */
	public abstract void playerOwnedArmyContinentUpdate();
	/**
     * This method is used to print percentage of map owned by player details
     */
	public abstract void playerTerPercentUpdate();
}

