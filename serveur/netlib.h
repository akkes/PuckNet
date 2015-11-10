/* netlib.h
*
* headers for network abstraction
*/

#ifndef NETLIB
#define NETLIB

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "miuLib.h"

//Struct
typedef struct connection_struct{
	int sock;
	struct sockaddr_in recv_addr;
}* ListeningPort;

typedef struct message_struct{
	char buf[80];
	struct sockaddr_in source;
}* Message;

//private


//public
ListeningPort listeningSetup(int);
Message receiveMessage(ListeningPort);
void sendMessage(ListeningPort, struct sockaddr_in, String);
void closeListening(ListeningPort);
void displayMessage(Message);
Boolean compareHosts(struct sockaddr_in, struct sockaddr_in);

#endif
