APPS=hello

all: ${APPS}

%: %.cu
	nvcc -O2 -Wno-deprecated-gpu-targets -o $@ $<
clean:
	rm -f ${APPS}
