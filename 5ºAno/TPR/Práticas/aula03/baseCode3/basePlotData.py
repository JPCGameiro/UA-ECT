import numpy as np
import scipy.stats as stats
import scipy.signal as signal
import matplotlib.mlab as mlab
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler
from sklearn.decomposition import PCA
import time
import sys
import warnings
warnings.filterwarnings('ignore')


def waitforEnter(fstop=True):
    if fstop:
        if sys.version_info[0] == 2:
            raw_input("Press ENTER to continue.")
        else:
            input("Press ENTER to continue.")
            

## -- 1 -- ##
def plot3Classes(data1,name1,data2,name2,data3,name3):
    plt.subplot(3,1,1)
    plt.plot(data1)
    plt.title(name1)
    plt.subplot(3,1,2)
    plt.plot(data2)
    plt.title(name2)
    plt.subplot(3,1,3)
    plt.plot(data3)
    plt.title(name3)
    plt.show()
    waitforEnter()

########### Main Code #############
Classes={0:'YouTube',1:'Browsing',2:'Mining'}
plt.ion()
nfig=1

## -- 1 -- ##
yt=np.loadtxt('YouTube.dat')[:,1:]
browsing=np.loadtxt('Browsing.dat')[:,1:]
mining=np.loadtxt('Mining.dat')[:,1:]

plt.figure(1)
plot3Classes(yt,'YouTube',browsing,'Browsing',mining,'Mining')
