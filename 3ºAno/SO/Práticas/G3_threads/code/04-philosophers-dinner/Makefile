CXXFLAGS = -Wall -ggdb -pthread

LDFLAGS = -pthread -lm

TARGETS = dinner

all:	$(TARGETS)

dinner:  table.o dinner.o thread.o utils.o delays.o
		g++ -o $@ $^ $(LDFLAGS)

dinner_unsafe: table_unsafe.o dinner.o thread.o utils.o delays.o
		g++ -o $@ $^ $(LDFLAGS)

table.o table_unsafe.o:	table.h

thread.o utils.o:	%.o:	%.h dbc.h

delays.o:	%.o:	%.h

clean:
		rm -f *.o *~

cleanall:	clean
		rm -f $(TARGETS)