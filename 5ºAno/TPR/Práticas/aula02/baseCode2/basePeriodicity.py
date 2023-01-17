import argparse
import scipy.stats as stats
import scipy.signal as signal
import numpy as np
import matplotlib.pyplot as plt
import scalogram
from baseObsWindows import slidingMultObsWindow
import os

def extractPeriodicityFeatures(dirname,basename,nObs,allwidths):
    for o in range(0,nObs):
        features=np.array([])
        for oW in allwidths:
            obsfilename=dirname+"/"+basename+str(o)+"_w"+str(oW)+".dat"
            print(obsfilename)
            subdata=np.loadtxt(obsfilename)[:,1]    #Loads data, only second column
            
            scales=np.arange(2,50)                  
            S,scales=scalogram.scalogramCWT(subdata,scales)   #periodogram using CWT (Morlet wavelet)
            features=np.hstack((features,S))
            
            #f,psd=signal.periodogram(subdata)      #periodogram using the Welch's method
            #features=np.hstack((features,pad))
            
            #fft=np.fft.fft(subdata)                   #periodogram using basic modulus-squared of the discrete FFT
            #psd=abs(fft)**2
            #features=np.hstack((features,psd))
            
        if o==0:
            obsFeatures=features
        else:
            obsFeatures=np.vstack((obsFeatures,features))

    return obsFeatures

def main():
    parser=argparse.ArgumentParser()
    parser.add_argument('-i', '--input', nargs='?',required=True, help='input dir')
    parser.add_argument('-w', '--widths', nargs='*',required=True, help='list of observation windows widths')
    args=parser.parse_args()
    
    dirname=args.input
    if isinstance(args.widths, list):
        allwidths=args.widths
    else:
        allwidths=list(args.widths)
    
    allfiles=os.listdir(dirname)
    nObs=len([f for f in allfiles if '_w{}.'.format(allwidths[0]) in f])
    lbn=allfiles[0].rfind("obs")+3
    basename=allfiles[0][:lbn]
    
    features=extractPeriodicityFeatures(dirname,basename,nObs,allwidths)
    
    outfilename=basename+"_per_features.dat"
    np.savetxt(outfilename,features,fmt='%f')
    
    print(features.shape)

        

if __name__ == '__main__':
    main()
