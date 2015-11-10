#ifndef BOOLEAN
#define BOOLEAN

typedef int Boolean;

#define true 1
#define false 0

#endif

#ifndef CONSTANTS
#define CONSTANTS

#define PLAYERSMAX 4
#define STATESCONSERVED 1024
#define COLORWIDTH 6

#endif

#ifndef DEBUG
#include <stdio.h>
#define DEBUG(TEXT) puts(TEXT)
#endif

#ifndef STRING
#define STRING

typedef char* String;

#endif
