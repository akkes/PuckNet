/* game.h
 *
 * header for game manipulation
 */

#ifndef GAME
#define GAME

#include "miulib.h"
#include "player.h"
#include "state.h"

//struct
typedef struct game_struct{
	int numberOfPlayers;
	int state;
	State states[STATESCONSERVED];
}* Game;

//public
Game createGame();
void sendToAllPlayers(ListeningPort, Game game, String string);
int addPlayer(Game, Player);
void removePlayer(Game, Player);
void freeGame(Game);
int getNumberOfPlayers(Game);
int numberOfPlayersAlive(Game);
int findPlayerID(Game, String);
int getLastStateID(Game);
Player getPlayer(Game, int);
State addState(Game, State);
State getState(Game, int);

#endif
