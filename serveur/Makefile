CC=gcc
CFLAGS=-Wall
#DEBUG=
DEBUG=-g
LDFLAGS=

OBJDIR=.objects
HEADERS= $(wildcard *.h)
SOURCES= $(wildcard *.c)
DONOTUSE_TEMPOBJ= $(addprefix $(OBJDIR)/, $(SOURCES))
OBJECTS= $(DONOTUSE_TEMPOBJ:.c=.o)

.PHONY=clean

all: server

server: $(OBJECTS) *.h
	$(CC) $(LDFLAGS) $(DEBUG) $(OBJECTS) -o server

.objects/%.o: %.c *.h
	$(CC) $(CFLAGS) $(DEBUG) -c $< -o $@

clean:
	rm .objects/*.o
