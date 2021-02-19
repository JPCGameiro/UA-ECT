CXXFLAGS = -Wall -ggdb -pthread

LDFLAGS = -pthread -lm

TARGETS = incrementer_unsafe incrementer_safe

all:		$(TARGETS)

incrementer_unsafe:    	incrementer.o inc_mod_unsafe.o bwdelay.o thread.o utils.o
			g++ -o $@ $^ $(LDFLAGS)

incrementer_safe:	incrementer.o inc_mod_safe.o bwdelay.o thread.o utils.o
			g++ -o $@ $^ $(LDFLAGS)

clean:
			rm -f *.o *~

cleanall:		clean
			rm -f $(TARGETS)
