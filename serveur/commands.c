/* commands.c
 *
 * functions for commands validation & interpretation
 *
 * TODO: Actual validations
 * TODO: finish interpretation
 */

#include "commands.h"

extern Game game;

//Should exist in string.h
int strcnt(String string, char character){
	DEBUG("strcnt");
	int count = 0;
	int position = 0;

	if (string == NULL) {
		return 0;
	}

	while (string[position] != '\0') {
		if (string[position] == character) {
			count ++;
		}
		position++;
	}

	return count;
}

//Technical
Boolean isFromAPlayer(Command command, Game game){
	DEBUG("isFromAPlayer");
	for (size_t i = 0; i < PLAYERSMAX; i++) {
		if (NULL != getPlayer(game,i)) {
			if (compareHosts(getPlayerHost(getPlayer(game,i)), command->source)) {
				return true;
			}
		}
	}
	return false;
}

Command splitMessage(Message message){
	DEBUG("splitMessage");
	int parts = strcnt(message->buf, ' ') + 2; //one because we count intersections, One for end of command (NULL)
	Command command = malloc(sizeof(struct command_struct));
	command->content = malloc(parts * sizeof(char*));
	bzero(command->content, parts * sizeof(char*));
	int position = 0;
	char * part;

	command->source = message->source;

	part = strtok(message->buf," \r\n");
	while (part != NULL) {
		command->content[position] = malloc(strlen(part) * sizeof(char));
		bzero(command->content[position], strlen(part) * sizeof(char));
		strncpy(command->content[position], part, strlen(part));
		part = strtok(NULL, " \r\n");
		position ++;
	}

	return command;
}

void freeCommand(Command command){
	int index = 0;
	DEBUG("freeCommand");

	if(command == NULL){
		return;
	}

	//free content
	if (command->content != NULL) {
		while (command->content[index] != NULL) {
			free(command->content[index]);
			index++;
		}
		free(command->content);
	}

	free(command);
}

Boolean isAValidCommand(Command command){
	DEBUG("isAValidCommand");
	if (command->content == NULL || command->content[0] == NULL) {
		return false;
	}
	return true;
}

//Validation
Boolean isWhereCommand(Command command){
	DEBUG("Test WHERE Command");
	if (0 == strcmp(command->content[0], "WHERE")) {
		return true;
	}
	return false;
}

Boolean isJoinCommand(Command command){
	DEBUG("Test JOIN Command");
	if (0 == strcmp(command->content[0], "JOIN")) {
		return true;
	}
	return false;
}

Boolean isAckCommand(Command command){
	DEBUG("Test ACK Command");
	if (0 == strcmp(command->content[0], "ACK")) {
		return true;
	}
	return false;
}

Boolean isResetCommand(Command command){
	DEBUG("Test RESET Command");
	if (0 == strcmp(command->content[0], "RESET")) {
		return true;
	}
	return false;
}

//interpretation
void interpretJoiningCommand(Command command, Game game, ListeningPort connection){
	DEBUG("interpretJoiningCommand");
	if (isWhereCommand(command)) {
		interpretWhere(command, game, connection);
	} else if (isJoinCommand(command)) {
		interpretJoin(command, game, connection);
	} else {
		DEBUG("Commande invalide reçue");
	}
}

void interpretGameCommand(Command command, Game game, ListeningPort connection){
	DEBUG("interpretGameCommand");
	if (isAckCommand(command)) {
		interpretAck(command, game, connection);
	} else if (isResetCommand(command)) {
		interpretReset(command, game, connection);
	} else if (isJoinCommand(command)) {
		interpretReJoin(command, game, connection);
	} else {
		DEBUG("Commande invalide reçue");
	}
}

void interpretWhere(Command command, Game game, ListeningPort connection){
	DEBUG("interpret Where");
	sendHere(connection, command->source);
}

void interpretJoin(Command command, Game game, ListeningPort connection){
	int playerNumber;
	DEBUG("interpret Join");

	if (game->numberOfPlayers < PLAYERSMAX) {
		// create player
		playerNumber = addPlayer(game, createPlayer(command->content[1], command->content[2], command->source, getLastStateID(game)));

		// create state
		State oldState = duplicateState(getLastState(game));
		State connectionState = createStateAcceptPlayer(oldState, getPlayer(game, playerNumber), playerNumber);

		// add it to the game
		addState(game, connectionState);

		sendAccept(connection, command->source, playerNumber);
	} else {
		sendMessage(connection, command->source, "DENY");
	}
}

void interpretReJoin(Command command, Game game, ListeningPort connection){
	DEBUG("interpret ReJoin");

	//find player number
	int playerID = findPlayerID(game, command->source);

	//send It
	if (-1 != playerID) {
		sendAccept(connection, command->source, playerID);
	} else {
		DEBUG("player sent wrong name");
	}
	return;
}

void interpretAck(Command command, Game game, ListeningPort connection){
	DEBUG("interpret Ack");

	//find player number and stateID
	int playerID = findPlayerID(game, command->source);
	int newLastACK = atoi(command->content[1]);

	if (newLastACK > game->players[playerID]->lastACK
			|| 0 == newLastACK) {
		// change lastACK heard of
		game->players[playerID]->lastACK = newLastACK;
		game->players[playerID]->lastACKTime = time(NULL);

		// create new State
		State newState = createState(getLastState(game), command->content+2, playerID);
		addState(game, newState);

		// get Old State
		printf("atoi(%s) -> %d\n", command->content[1], atoi(command->content[1]));
		State oldState = getState(game, atoi(command->content[1]) -1);

		// send delta
		sendDelta(connection, command->source, oldState, newState, getLastStateID(game));
	}
}

void interpretReset(Command command, Game game, ListeningPort connection){
	DEBUG("interpret Where");
	addState(game, createResetState(getLastState(game)));
}

//send commands
void sendHere(ListeningPort connection, struct sockaddr_in addr){
	DEBUG("sendHere");
	sendMessage(connection, addr, "HERE");
}

void sendAccept(ListeningPort connection, struct sockaddr_in addr, int playerNumber){
	DEBUG("sendAccept");
	char message[9] = "ACCEPT 0";
	sprintf(message+7, "%d", playerNumber);
	sendMessage(connection, addr, message);
}

void sendDelta(ListeningPort connection, struct sockaddr_in addr, State oldState, State newState, int newStateID){
	DEBUG("sendDelta");
	String delta = malloc(800 * sizeof(char));
	bzero(delta, 800 * sizeof(char));
	sprintf(delta, "DELTA %d", newStateID);

	strcat(delta, makeDeltaFromStates(oldState, newState));

	sendMessage(connection, addr, delta);
	return;
}
