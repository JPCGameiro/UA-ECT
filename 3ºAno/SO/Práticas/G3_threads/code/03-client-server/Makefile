CXXFLAGS = -Wall -ggdb -pthread

LDFLAGS = -pthread -lm

TARGETS = clientServer

all:	$(TARGETS)

clientServer:  fifo_safe.o poolBuffers.o clientServerApp.o thread.o utils.o delays.o
		g++ -o $@ $^ $(LDFLAGS)

fifo_safe.o:	fifo.h

poolBuffers.o: poolBuffers.h

thread.o utils.o:	%.o:	%.h dbc.h

delays.o:	%.o:	%.h

clean:
		rm -f *.o *~

cleanall:	clean
		rm -f $(TARGETS)