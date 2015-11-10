/* game.c
 *
 * functions for game manipulation
 */

#include "game.h"

//public

Game createGame(){
        Game newGame = malloc(sizeof(struct game_struct));
        newGame->numberOfPlayers = 0;
        newGame->state = 0;

        bzero(newGame->states, STATESCONSERVED * sizeof(State));

        newGame->states[newGame->state] = createInitialState();

        return newGame;
}

void sendToAllPlayers(ListeningPort connection, Game game, String string){
        for (size_t i = 0; i < PLAYERSMAX; i++) {
                printf("Joueur: %zd\n", i);
                if (getPlayer(game, i) != NULL) {
                        printf("Existe\n");
                        sendToPlayer(connection, getPlayer(game,i), string);
                }
        }
}

int addPlayer(Game game, Player player){
        size_t index;
        index = 0;
        while (getPlayer(game, index) != NULL && index < PLAYERSMAX) {
                printf("On continue %zd\n", index);
                index++;
        }

        if (index < PLAYERSMAX) {
                getState(game, getLastStateID(game))->players[index] = player;
                game->numberOfPlayers++;
        }

        return index;
}

void removePlayer(Game game, Player player){
        int index;
        index = 0;
        while (getPlayer(game, index) != player && index < PLAYERSMAX) {
                index++;
        }

        if (index != PLAYERSMAX) {
                getState(game, getLastStateID(game))->players[index] = NULL;
        }
}

int getNumberOfPlayers(Game game){
        return game->numberOfPlayers;
}

int numberOfPlayersAlive(Game game){
        int playersAlive = 0;
        for (size_t i = 0; i < PLAYERSMAX; i++) {
                if (isAlive(getPlayer(game, i))) {
                        playersAlive++;
                }
        }
        return playersAlive;
}

State addState(Game game, State state){
        DEBUG("addState");
        return NULL;
}

State getState(Game game, int stateID){
        DEBUG("getState");
        return game->states[stateID];
}

int getLastStateID(Game game){
        DEBUG("getLastStateID");
        return game->state;
}

Player getPlayer(Game game, int playerID){
        DEBUG("getPlayer");
        return getState(game, getLastStateID(game))->players[playerID];
}

int findPlayerID(Game game, String name){
        for (size_t i = 0; (i < PLAYERSMAX) && (NULL != getPlayer(game, i)); i++) {
		if (0 == strcmp(name, getPlayerName(getPlayer(game, i)))) {
			return i;
		}
	}
        return -1;
}

void freeGame(Game game){
        DEBUG("freeGame");

}
