/* commands.h
 *
 * header for commands validation & interpretation
 */

#ifndef COMMANDS
#define COMMANDS

#include <stdlib.h>
#include "miulib.h"
#include "netlib.h"
#include "state.h"
#include "game.h"

typedef struct command_struct {
        String* content;
        struct sockaddr_in source;
}* Command;

//Should exist in string.h
int strcnt(String, char);

//Technical
Boolean isFromAPlayer(Command, Game);
Command splitMessage(Message);
void freeCommand(Command);
Boolean isAValidCommand(Command);

//Validation
Boolean isWhereCommand(Command command);
Boolean isJoinCommand(Command command);
Boolean isAckCommand(Command command);
Boolean isResetCommand(Command command);
Boolean isTurnedCommand(Command command);
Boolean isSuperCommand(Command command);
Boolean isEatenCommand(Command command);

//interpretation
void interpretJoiningCommand(Command, Game, ListeningPort);
void interpretGameCommand(Command, Game, ListeningPort);
void interpretWhere(Command, Game, ListeningPort);
void interpretJoin(Command, Game, ListeningPort);
void interpretReJoin(Command, Game, ListeningPort);
void interpretReset(Command, Game, ListeningPort);
void interpretAck(Command, Game, ListeningPort);

//send Command commands
void sendHere(ListeningPort, struct sockaddr_in);
void sendAccept(ListeningPort, struct sockaddr_in, int);
void sendDelta(ListeningPort, struct sockaddr_in, State, State, int);
#endif
