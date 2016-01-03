/* player.h
 *
 * header for players objects
 */

#ifndef PLAYER
#define PLAYER

#include <sys/socket.h>
#include "netlib.h"
#include "miulib.h"

typedef char* Color;

typedef struct player_struct{
	String name;
	char color[COLORWIDTH];
	struct sockaddr_in addr;
	int spawnX;
	int spawnY;
	int lifes;
	int lastACK;
}* Player;

void sendToPlayer(ListeningPort, Player, String);
Player createPlayer(String, Color, struct sockaddr_in, int);
String getPlayerName(Player);
Color getPlayerColor(Player);
struct sockaddr_in getPlayerHost(Player);
Boolean isAlive(Player);
void freePlayer(Player);

#endif
