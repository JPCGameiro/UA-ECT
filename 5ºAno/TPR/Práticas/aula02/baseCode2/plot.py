import numpy as np
import matplotlib.pyplot as plt

data=np.loadtxt("twitter.pcapng_d1.dat",dtype=int)
plt.plot(data[:,0],data[:,1],'k')
plt.show()
plt.plot(data[:,0],data[:,3],'b')
plt.show()