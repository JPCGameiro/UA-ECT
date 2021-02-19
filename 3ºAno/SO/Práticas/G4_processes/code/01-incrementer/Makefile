CXXFLAGS = -Wall -ggdb -pthread

LDFLAGS = -lm -pthread

TARGETS = incrementer_unsafe incrementer_safe

all:		$(TARGETS)

incrementer_unsafe:    	incrementer.o inc_mod_unsafe.o bwdelay.o process.o utils.o
			g++ -o $@ $^ $(LDFLAGS)

incrementer_safe:	incrementer.o inc_mod_safe.o bwdelay.o process.o utils.o
			g++ -o $@ $^ $(LDFLAGS)

incrementer.o:	bwdelay.h inc_mod.h process.h

inc_mod_unsafe inc_mod_safe:	inc_mod.h bwdelay.h 

process.o utils.o:	%.o:	%.h dbc.h

bwdelay.o:	%.o:	%.h

clean:
			rm -f *.o *~

cleanall:		clean
			rm -f $(TARGETS)
