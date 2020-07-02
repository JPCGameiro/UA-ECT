#include <stdio.h>

static unsigned int niter1=0, niter2=0, niter3=0, niter4=0;

unsigned int f1 (unsigned int n)
{
	unsigned int i, j, r=0;
	for(i=1;i<=n;i++)
		for(j=1;j<=n;j++){
			r+=1;
			niter1++;
		}
	return r;
}

unsigned int f2 (unsigned int n)
{
	unsigned int i, j, r=0;
	for(i=1;i<=n;i++)
		for(j=1;j<=i;j++) {
			r+=1;
			niter2++;
		}
	return r;
}

unsigned int f3 (unsigned int n)
{
	unsigned int i, j, r=0;
	for(i=1;i<=n;i++)
		for(j=i;j<=n;j++){
			r+=j;
			niter3++;
		}
	return r;
}

unsigned int f4 (unsigned int n)
{
	unsigned int i, j, r=0;
	for(i=1;i<=n;i++)
		for(j=i;j>=1;j/=10){
			r+=i;
			niter4++;
		}
	return r;
			
}


int main(void)
{
	unsigned int n=0;
	
	printf("-------------------------------------------------------------------------\n");
	printf("  n | f1(n) | NºIter | f2(n) | NºIter | f3(n) | NºIter | f4(n) | NºIter |\n");
	printf("----|-------|--------|-------|--------|-------|--------|-------|--------|\n");
	for(n=1;n<=15;n++) {
		unsigned int result1=f1(n), result2=f2(n), result3=f3(n), result4=f4(n);
		printf(" %2d | %5d | %6d | %5d | %6d | %5d | %6d | %5d | %6d |\n", n, result1, niter1, result2, niter2, result3, niter3, result4, niter4);
		niter1=0, niter2=0, niter3=0, niter4=0;
	}
	printf("----|-------|--------|-------|--------|-------|--------|-------|--------|\n");
	printf("O(n)|   n²  |    n²  |   n²  |    n²  |   n³  |   n²   | n²logn|  nlogn |\n");              
	printf("-------------------------------------------------------------------------\n");
	
}
