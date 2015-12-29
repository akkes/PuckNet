/* game.c
 *
 * functions for game manipulation
 */

#include "game.h"

//public

Game createGame(){
        Game newGame = malloc(sizeof(struct game_struct));
	bzero(newGame, sizeof(struct game_struct));
        newGame->numberOfPlayers = 0;
        newGame->state = 0;

        bzero(newGame->states, STATESCONSERVED * sizeof(State));

        newGame->states[newGame->state] = createInitialState();

        return newGame;
}

void sendToAllPlayers(ListeningPort connection, Game game, String string){
        for (size_t i = 0; i < PLAYERSMAX; i++) {
#ifdef DEBUG
                printf("Joueur: %zd\n", i);
#endif
                if (getPlayer(game, i) != NULL) {
#ifdef DEBUG
                        printf("Existe\n");
#endif
                        sendToPlayer(connection, getPlayer(game,i), string);
                }
        }
}

int addPlayer(Game game, Player player){
        size_t index;
        index = 0;
        while (getPlayer(game, index) != NULL && index < PLAYERSMAX) {
#ifdef DEBUG
                printf("On continue %zd\n", index);
#endif
                index++;
        }

        if (index < PLAYERSMAX) {
		game->players[index] = player;
                addPlayerToState(getLastState(game), player, index);
                game->numberOfPlayers++;
        }

        return index;
}

int removePlayer(Game game, Player player){
        int index;
        index = 0;
        while (getPlayer(game, index) != player && index < PLAYERSMAX) {
                index++;
        }

        if (index != PLAYERSMAX) {
                getState(game, getLastStateID(game))->players[index] = NULL;
        }

	return index;
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
	game->state++;
	printf("    stateID: %d\n", game->state);

	//already in memory
	if(NULL != game->states[game->state%STATESCONSERVED]) {
		freeState(game->states[game->state%STATESCONSERVED]);
	}

	//add state
	game->states[game->state%STATESCONSERVED] = state;

        return state;
}

State getState(Game game, int stateID){
        DEBUG("getState");
        return game->states[stateID];
}

State getLastState(Game game){
        DEBUG("getLastState");
        return game->states[game->state];
}

int getLastStateID(Game game){
        DEBUG("getLastStateID");
        return game->state;
}

Player getPlayer(Game game, int playerID){
        DEBUG("getPlayer");
        return game->players[playerID];
}

int findPlayerID(Game game, struct sockaddr_in addr){
        for (size_t i = 0; (i < PLAYERSMAX) && (NULL != getPlayer(game, i)); i++) {
		if (compareHosts(addr, getPlayerHost(getPlayer(game, i)))) {
			return i;
		}
	}
        return -1;
}

void freeGame(Game game){
        DEBUG("freeGame");

}
