#include "state.h"

State createInitialState(){
	DEBUG("createInitialState");
	State initialState = malloc(sizeof(struct state_struct));
	bzero(initialState, sizeof(struct state_struct));
	return initialState;
}

Delta makeDeltaFromCommand(String* command, int playerID){
	DEBUG("makeDeltaFromCommand");
	int index = 2;
	int placeHolder;
	while (NULL != command[index]) {
		printf("Property read:\"%s ", command[index]);
		if (0 == strcmp(command[index], "X")) {
			placeHolder = atoi(command[index+1]);
		} else if (0 == strcmp(command[index], "Y")) {
			placeHolder = atoi(command[index+1]);
		} else if (0 == strcmp(command[index], "Direction")) {
			placeHolder = atoi(command[index+1]);
		} else if (0 == strcmp(command[index], "Dot")) {
			placeHolder = atoi(command[index+1]);
		} else if (0 == strcmp(command[index], "Gum")) {
			placeHolder = atoi(command[index+1]);
		} else if (0 == strcmp(command[index], "Eat")) {
			placeHolder = atoi(command[index+1]);
		} else {
			DEBUG("Erreur dans la validation");
		}
		printf("%d\"\n", placeHolder);
		index += 2;
	}

	return NULL;
}

Delta makeDeltaFromStates(State oldState, State newState){
	DEBUG("makeDeltaFromStates");
	return NULL;
}

State createState(State state, Delta delta){
	DEBUG("createState");
	return state;
}

void freeState(State state){
	DEBUG("freeState");
	free(state);
}
