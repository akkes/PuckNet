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
	int lifes;
	int posX;
	int posY;
	int direction;
	struct sockaddr_in addr;
}* Player;

void sendToPlayer(ListeningPort, Player, String);
Player createPlayer(String, Color, struct sockaddr_in);
String getPlayerName(Player);
Color getPlayerColor(Player);
struct sockaddr_in getPlayerHost(Player);
Boolean isAlive(Player);
void freePlayer(Player);

#endif
