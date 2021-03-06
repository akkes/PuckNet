/* state.h
 *
 * header for state Object
 */

#ifndef STATE
#define STATE

#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "miulib.h"
#include "player.h"

#define DOTSNUMBER 330
#define WIDTH 28
#define HEIGHT 31
#define SQUARESIZE 15

// Player state
typedef struct playerState_struct {
	int lifes;
	int power;
	time_t powerTime;
	//positions in units
	int posX;
	int posY;
	int score;
	int streak;
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
	Dot originalDot;
	Gum originalGum;
	int id;
        PlayerState players[PLAYERSMAX];
        Dot dots[DOTSNUMBER];
        Gum gums[PLAYERSMAX];
}* State;

//public
State createInitialState();
State addDotsAndGums(State);
State createResetState(State);
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
