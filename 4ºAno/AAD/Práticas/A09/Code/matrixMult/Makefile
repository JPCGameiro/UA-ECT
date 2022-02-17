CU_APPS=matrixMult

all: ${CU_APPS}

%: %.cu
	nvcc -O2 -Wno-deprecated-gpu-targets -o $@ $< -lm 
clean:
	rm -f ${CU_APPS}
