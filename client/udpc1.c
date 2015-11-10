#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <strings.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>

extern int errno ;

int main (){
  int sock ;
  struct sockaddr_in recv_addr, exp_addr ;
  int recv_len ;
  char buf [80] ;

  sock = socket (PF_INET, SOCK_DGRAM, 0) ;
  bzero ((char *) &exp_addr, sizeof exp_addr) ;
  exp_addr.sin_family = AF_INET ;
  exp_addr.sin_addr.s_addr = INADDR_ANY ;
  exp_addr.sin_port = 0 ;
  bind (sock, (struct sockaddr *)&exp_addr, sizeof exp_addr) ;

  bzero ((char *) &recv_addr, sizeof recv_addr) ;
  recv_addr.sin_family = AF_INET ;
  recv_addr.sin_addr.s_addr = inet_addr ("127.0.0.1") ;
  recv_addr.sin_port = htons (7825) ;

        for(;;){
  /* Envoi d'un message */
  bzero (buf, 80) ;
  strncpy (buf, "Coucou, c'est le client", 24) ;
  sendto (sock, buf, 24, 0, (struct sockaddr *)&recv_addr, sizeof (struct sockaddr)) ;

  /* Reception d'un message */
  bzero (buf, 80) ;
  recvfrom (sock, buf, 80, 0, (struct sockaddr *)&recv_addr, (socklen_t *)&recv_len) ;
  printf ("%s (port %d) a envoye le message %s\n", inet_ntoa (recv_addr.sin_addr), ntohs (recv_addr.sin_port), buf) ;
        }

  close (sock) ;
  return 0 ;
}
