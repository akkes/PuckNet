/* netlib.c
 *
 * functions for network abstraction
 */

#include "netlib.h"

ListeningPort listeningSetup(int port){
	ListeningPort connection;

	connection = malloc(sizeof(struct connection_struct));

	connection->sock = socket (PF_INET, SOCK_DGRAM, 0);
	bzero ((char *) &connection->recv_addr, sizeof connection->recv_addr);
	connection->recv_addr.sin_family = AF_INET;
	connection->recv_addr.sin_addr.s_addr = htonl (INADDR_ANY);
	connection->recv_addr.sin_port = htons (port);
	bind (connection->sock, (struct sockaddr *)&connection->recv_addr, sizeof connection->recv_addr);

	return connection;
}

Message receiveMessage(ListeningPort connection){
	int exp_len;
	Message message;

	message = malloc(sizeof(struct message_struct));
	exp_len = sizeof message->source;

	bzero (message->buf, 80);
	recvfrom (connection->sock, message->buf, 80, 0, (struct sockaddr *)&message->source, (socklen_t *)&exp_len);

	return message;
}

void sendMessage(ListeningPort connection, struct sockaddr_in destination, String message){
	printf("Send Message: \"%s\" to %s (port %d)\n", message, inet_ntoa(destination.sin_addr), ntohs(destination.sin_port));
	sendto (connection->sock, message, strlen(message), 0, (struct sockaddr *)&destination, sizeof (destination));
}

void closeListening(ListeningPort connection){
	close (connection->sock);
	free(connection);
}
void displayMessage(Message message){
	printf ("%s (port %d) a envoye le message \"%s\"\n", inet_ntoa(message->source.sin_addr), ntohs(message->source.sin_port), message->buf);
}

Boolean compareHosts(struct sockaddr_in host1, struct sockaddr_in host2){
	DEBUG("compareHosts");
	if ((host1.sin_addr.s_addr == host2.sin_addr.s_addr) && (host1.sin_port == host2.sin_port)) {
		return true;
	}
	return false;
}
