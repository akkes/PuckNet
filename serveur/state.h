/* state.h
 *
 * header for state Object
 */

#ifndef STATE
#define STATE

#include <stdlib.h>
#include <string.h>
#include "miulib.h"
#include "player.h"

#define DOTSNUMBER 80
#define WIDTH 28
#define HEIGHT 31
#define SQUARESIZE 15

// Player state
typedef struct playerState_struct {
	int lifes;
	int power;
	//positions in units
	int posX;
	int posY;
}* PlayerState;

// Dot
typedef struct dot_struct {
	int x;
	int y;
}* Dot;

// Gum
typedef struct gum_struct {
	int x;
	int y;
}* Gum;

// State
typedef struct state_struct {
	int id;
        PlayerState players[PLAYERSMAX];
        Dot dots[DOTSNUMBER];
        Gum gums[PLAYERSMAX];
}* State;

//public
State createInitialState();
State createState(State, String*, int);
State duplicateState(State);
String makeDeltaFromStates(State, State);
void freeState(State);
State createStateAcceptPlayer(State, Player, int);

//private
Dot createDot(int x, int y);
Gum createGum(int x, int y);
PlayerState createPlayerState(Player);
PlayerState duplicatePlayerState(PlayerState);
Player addPlayerToState(State, Player, int);
Player removePlayerFromState(State, int);

#endif
