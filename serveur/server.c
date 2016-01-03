#include "miulib.h"
#include "netlib.h"
#include "player.h"
#include "commands.h"
#include "game.h"

int main ()
{
        Boolean quit = 0;
        ListeningPort connection;
        Message message;

        Command command;
        Game game = createGame();

        connection = listeningSetup(7825);

        // Game
        do {
                // Reception d'un message
		puts("\n");
                message = receiveMessage(connection);
                displayMessage(message);

                //split command
                command = splitMessage(message);

                if (isAValidCommand(command)) {
                        if (isFromAPlayer(command, game)) {
                                interpretGameCommand(command, game, connection);
                        } else {
                                interpretJoiningCommand(command, game, connection);
                        }
                }

                //free message & command
                free(message);
                freeCommand(command);

		// Disconnect players
		disconnectPlayers(game);

                //Si on a fini
                // //quit = 1;
        } while (0 == quit);

        sendMessage(connection, message->source, "END 1028 best");

        closeListening(connection);
        return 0 ;
}
