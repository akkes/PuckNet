#include "state.h"

State createInitialState() {
	DEBUG("createInitialState");
	State initialState = malloc(sizeof(struct state_struct));
	bzero(initialState, sizeof(struct state_struct));

	// add dots
	initialState->originalDot = createDot(20, 20);
	for (size_t i = 0; i < DOTSNUMBER; i++) {
		if (NULL == initialState->dots[i]) {
			initialState->dots[i] = initialState->originalDot;
		}
	}

	// add gums
	initialState->originalGum = createGum(4, 4);
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL == initialState->gums[i]) {
			initialState->gums[i] = initialState->originalGum;
		}
	}

	initialState->id = 0;

	return initialState;
}

State createResetState(State oldState) {
	State resetState = duplicateState(oldState);

	addDotsAndGums(resetState);

	// reset scores
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL != resetState->players[i]) {
			resetState->players[i]->score = 0;
		}
	}

	return resetState;
}

State addDotsAndGums(State state) {

	return state;
}

Dot createDot(int x, int y) {
	Dot dot = malloc(sizeof(struct dot_struct));
	bzero(dot, sizeof(struct dot_struct));

	dot->x = x;
	dot->y = y;
	return dot;
}

Gum createGum(int x, int y) {
	Gum gum = malloc(sizeof(struct gum_struct));
	bzero(gum, sizeof(struct gum_struct));

	gum->x = x;
	gum->y = y;
	return gum;
}

PlayerState createPlayerState(Player player) {
	PlayerState playerState = malloc(sizeof(struct playerState_struct));
	bzero(playerState, sizeof(struct playerState_struct));

	playerState->posX = player->spawnX;
	playerState->posY = player->spawnY;
	playerState->lifes = player->lifes;
	playerState->power = 0;
	playerState->powerTime = time(NULL);
	playerState->score = 0;
	playerState->streak = 0;
	return playerState;
}

PlayerState duplicatePlayerState(PlayerState oldPlayerState) {
	//DEBUG("duplicatePlayerState");
	PlayerState newPlayerState = NULL;

	if (NULL != oldPlayerState) {
		newPlayerState = malloc(sizeof(struct playerState_struct));
		bzero(newPlayerState, sizeof(struct playerState_struct));

		newPlayerState->posX = oldPlayerState->posX;
		newPlayerState->posY = oldPlayerState->posY;
		newPlayerState->lifes = oldPlayerState->lifes;
		newPlayerState->power = oldPlayerState->power;
		newPlayerState->powerTime = oldPlayerState->powerTime;
		newPlayerState->score = oldPlayerState->score;
		newPlayerState->streak = oldPlayerState->streak;
	}

	return newPlayerState;
}

State duplicateState(State originalState) {
	DEBUG("duplicateState");
	State duplicateState = malloc(sizeof(struct state_struct));
	bzero(duplicateState, sizeof(struct state_struct));

	// add players
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		duplicateState->players[i] = duplicatePlayerState(originalState->players[i]);
	}

	// add dots
	for (size_t i = 0; i < DOTSNUMBER; i++) {
		duplicateState->dots[i] = originalState->dots[i];
	}

	// add gums
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		duplicateState->gums[i] = originalState->gums[i];
	}

	duplicateState->id = originalState->id;

	return duplicateState;
}

State createState(State oldState, String* command, int player) {
	DEBUG("createState");
	printf("    oldStateID: %d\n", oldState->id);

	// Make the state
	State newState = duplicateState(oldState);
	newState->id++;

	// end of Super
	time_t now;
	now = time(NULL);
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL != newState->players[i]) {
			printf("%ld - %ld = %lf\n", now, newState->players[i]->powerTime, difftime(now, newState->players[i]->powerTime));
			if (difftime(now, newState->players[i]->powerTime) > 10) {
				newState->players[i]->power = 0;
				newState->players[i]->streak = 0;
			}
		}
	}

	// parse new events
	printf("    newStateID: %d\n", newState->id);
	int index = 0;
	while (NULL != command[index]) {
		if (0 == strcmp(command[index], "X")
				&& NULL != command[index+1]) {
			printf("    X");
			newState->players[player]->posX = atoi(command[index+1]);
			printf(" %d\n", newState->players[player]->posX);
			index++;
		} else if (0 == strcmp(command[index], "Y")
				&& NULL != command[index+1]) {
			printf("    Y");
			newState->players[player]->posY = atoi(command[index+1]);
			printf(" %d\n", newState->players[player]->posY);
			index++;
		} else if (0 == strcmp(command[index], "Dot")
				&& NULL != command[index+1]) {
			printf("    Dot");
			newState->players[player]->score += 10;
			newState->dots[atoi(command[index+1])] = NULL;
			index++;
		} else if (0 == strcmp(command[index], "Gum")
				&& NULL != command[index+1]) {
			printf("    Gum");
			newState->players[player]->score += 50;
			newState->players[player]->streak = 0;
			newState->gums[atoi(command[index+1])] = NULL;
			newState->players[player]->power = 1;
			newState->players[player]->powerTime = time(NULL);
			index++;
		} else if (0 == strcmp(command[index], "Eat")
				&& NULL != command[index+1]) {
			printf("    Eat");
			newState->players[player]->streak++;
			newState->players[player]->score += newState->players[player]->streak * 200 + 200;
			newState->players[atoi(command[index+1])]->lifes--;
			newState->players[atoi(command[index+1])]->posX = 2*15;
			newState->players[atoi(command[index+1])]->posY = 14*15;
			index++;
		} else {
			DEBUG("    Erreur dans la validation");
			printf("    %s\n", command[index]);
		}
		index++;
	}
	return newState;
}

State createStateAcceptPlayer(State oldState, Player newPlayer, int playerID) {
	DEBUG("createStateAcceptPlayer");
	State newState = duplicateState(oldState);
	newState->id++;

	addPlayerToState(newState, newPlayer, playerID);

	return newState;
}

Player addPlayerToState(State state, Player newPlayer, int playerID) {
	DEBUG("addPlayerToState");
	if (playerID < PLAYERSMAX) {
        	state->players[playerID] = createPlayerState(newPlayer);
	}
	return newPlayer;
}

Player removePlayerFromState(State state, int playerID) {
	DEBUG("removePlayerFromState");
	if (playerID < PLAYERSMAX) {
        	state->players[playerID] = NULL;
	}

	return NULL;
}

String makeDeltaFromStates(State oldState, State newState) {
	DEBUG("makeDeltaFromStates");
	String delta = malloc(4000 * sizeof(char));
	bzero(delta, 4000 * sizeof(char));

	String temp = malloc(20 * sizeof(char));
	bzero(temp, 20 * sizeof(char));

	printf("%d < %d\n", oldState->id, newState->id);

	// network, positions & directions
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL == oldState->players[i] && NULL != newState->players[i]) {
			DEBUG("    born");

			sprintf(temp, " NewPlayer %d %d %d fake FA7E55",
				(int) i, newState->players[i]->posX, newState->players[i]->posY);
			strcat(delta, temp);
		} else if(NULL != oldState->players[i] && NULL != newState->players[i]) {
			DEBUG("    still alive");

			printf("%d != %d\n", oldState->players[i]->posX, newState->players[i]->posX);
			if (oldState->players[i]->posX != newState->players[i]->posX) {
				sprintf(temp, " X %d %d",
					(int) i, newState->players[i]->posX);
				strcat(delta, temp);
			}

			printf("%d != %d\n", oldState->players[i]->posY, newState->players[i]->posY);
			if (oldState->players[i]->posY != newState->players[i]->posY) {
				sprintf(temp, " Y %d %d",
					(int) i, newState->players[i]->posY);
				strcat(delta, temp);
			}

			printf("%d != %d\n", oldState->players[i]->power, newState->players[i]->power);
			if (oldState->players[i]->power != newState->players[i]->power) {
				if (1 == newState->players[i]->power) {
					sprintf(temp, " Super %d",
						(int) i);
				} else {
					sprintf(temp, " Normal %d",
						(int) i);
				}
				strcat(delta, temp);
			}

			printf("%d != %d\n", oldState->players[i]->posY, newState->players[i]->posY);
			if (oldState->players[i]->lifes > newState->players[i]->lifes) {
				sprintf(temp, " Eaten %d", (int) i);
				strcat(delta, temp);
			}

			printf("%d != %d\n", oldState->players[i]->score, newState->players[i]->score);
			if (oldState->players[i]->score != newState->players[i]->score) {
				sprintf(temp, " Score %d %d",
					(int) i, newState->players[i]->score);
				strcat(delta, temp);
			}
		} else if (NULL != oldState->players[i] && NULL == newState->players[i]) {
			DEBUG("    left");
			sprintf(temp, " Leave %d", (int) i);
			strcat(delta, temp);
		} else {
			DEBUG("    still dead");
		}
	}

	// gums
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL == newState->gums[i] && NULL != oldState->gums[i]) {
			sprintf(temp, " Gum %d",
				(int) i);
			strcat(delta, temp);
		} else if (NULL != newState->gums[i] && NULL == oldState->gums[i]){
			sprintf(temp, " NewGum %d",
				(int) i);
			strcat(delta, temp);
		}
	}

	// dots
	for (size_t i = 0; i < DOTSNUMBER; i++) {
		if (NULL == newState->dots[i] && NULL != oldState->dots[i]) {
			sprintf(temp, " Dot %d",
				(int) i);
			strcat(delta, temp);
		} else if (NULL != newState->dots[i] && NULL == oldState->dots[i]){
			sprintf(temp, " NewDot %d",
				(int) i);
			strcat(delta, temp);
		}
	}

	return delta;
}

void freeState(State state) {
	DEBUG("freeState");
	free(state);
}
