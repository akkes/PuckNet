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

// State
typedef struct state_struct{
        Player players[PLAYERSMAX];
        int* dots;
        int* gums;
}* State;

// Delta
typedef struct delta_struct{

}* Delta;

//public
State createInitialState();
State createState(State, Delta);
Delta makeDeltaFromCommand(String*, int);
Delta makeDeltaFromStates(State, State);
void freeState(State);

#endif
