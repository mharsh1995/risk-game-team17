# Advanced-Programming-Practices

RISK game

Risk is a strategy board game for two to six players.

Maps can be Created/Loaded as per player's choice.

Distribution of armies takes place based on the control value of that country and every player gets turn to allocate their amry in Round Robin Fashion.

There are 3 phases in the game:
 - Reinforcement ( allocate armies )
 - Attack ( attack the neighbouring countries in order to conquer that territory )
 - Fortification ( to move armies from one territory to other as per player's strategy ) 
 
After every successful attack by a player he/she gets Bonus Card which he/she can trade-in for getting more armies.

The main goal for the game is to conquer all the territories present on that map and in doing so, eliminate the other players.

Features delivered in release 1 / Version 1.0 (build 1) :
 - Map Creation
 - Map Edit
 - Map Loading 
 - Map Validation
 - Map Saving
 - Reinforcement Phase 
 - Fortification Phase
 - Startup Phase 
 - Driver Interface

Features delivered in release 2 / Version 2.0 (build 2) :
 - Phase View using Observer pattern for displaying 
   (i) Details of player's name, (ii) Current phase being played and (iii) Actions performed by the player. 
 - Implementation of Player World Domination View for displaying the percentage of the map controlled by every player ,          continents controlled by every player and the total number of armies owned by every player.
 - Implemenation of Reinforcement, Attack and Fortification methods in player class.
 - Card Trade and card exchange view in Reinforcement phase. (increment of 5 per every trade taking place)
 - Attack Phase integrated with Dice roll feature and All out mode. (Max dice allowed at any time: Attacker-3 and Defender-2)
 - Update in allocating armies count in startup phase. ( using floor value of total countries / 3 ).
 
 Features delivered in release 3 / Version 3.0 (build 3) :

 - In this build, Implement different player behaviors using strategy pattern and based on player behavior, Implement      -      different versions of reinforcement, attack and fortification.
 - Implementation of single game mode, in which game proceeds until the one player has conquered the whole map without any        further interaction of user.
 - In tournament mode, tournament should proceeds without any user interaction and show the results at the end of the            tournament.
