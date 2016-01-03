/* player.c
 *
 * functions for players objects
 */

#include "player.h"

void sendToPlayer(ListeningPort connection, Player player, String message){
	sendMessage(connection, player->addr, message);
}

Player createPlayer(String name, Color color, struct sockaddr_in addr){
	Player newPlayer;
	puts("createPlayer");

	//Init struct
	newPlayer = malloc(sizeof(struct player_struct));
	newPlayer->name = malloc((strlen(name) + 1) * sizeof(char));
	bzero(newPlayer->name, (strlen(name) + 1) * sizeof(char));

	//put informations inside
	printf("%s\n", newPlayer->name);
	strcpy(newPlayer->name, name);
	printf("%s\n", newPlayer->name);
	memcpy(newPlayer->color, color, 6);
	newPlayer->addr = addr;
	newPlayer->lifes = 1;
	newPlayer->spawnX = 2 * 15;
	newPlayer->spawnY = 14 * 15;
	newPlayer->lastACK = 0;

	return newPlayer;
}

String getPlayerName(Player player){
	puts("getPlayerName");
	return player->name;
}

Color getPlayerColor(Player player){
	puts("getPlayerColor");
	return player->color;
}

Boolean isAlive(Player player){
	return 0 < player->lifes;
}

struct sockaddr_in getPlayerHost(Player player){
	puts("getPlayerAddr");
	return player->addr;
}

void freePlayer(Player player){
	printf("freePlayer: %s\n", player->name);

	//free content
	free(player->name);

	//free struct
	free(player);
}
