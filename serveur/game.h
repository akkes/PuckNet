/* game.h
 *
 * header for game manipulation
 */

#ifndef GAME
#define GAME

#include "miulib.h"
#include "netlib.h"
#include "player.h"
#include "state.h"

#define STATESCONSERVED 1024

//struct
typedef struct game_struct{
	int numberOfPlayers;
	Player players[PLAYERSMAX];
	int state;
	State initialState;
	State states[STATESCONSERVED];
}* Game;

//public
Game createGame();
void sendToAllPlayers(ListeningPort, Game game, String string);
int addPlayer(Game, Player);
int removePlayer(Game, Player);
void freeGame(Game);
int getNumberOfPlayers(Game);
int numberOfPlayersAlive(Game);
int findPlayerID(Game, struct sockaddr_in);
int getLastStateID(Game);
Player getPlayer(Game, int);
State addState(Game, State);
State getState(Game, int);
State getLastState(Game);
void disconnectPlayers(Game);

#endif
