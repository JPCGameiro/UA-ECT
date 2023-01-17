import argparse
import numpy as np
import matplotlib.pyplot as plt
import os

def seqObsWindow(data,lengthObsWindow,basename):
    iobs=0
    nSamples,nMetrics=data.shape
    obsData=np.zeros((0,lengthObsWindow,nMetrics))
    for s in np.arange(lengthObsWindow,nSamples,lengthObsWindow):
        subdata=data[s-lengthObsWindow:s,:]
        obsData=np.insert(obsData,iobs,subdata,axis=0)
        
        obsFname="{}_obs{}_w{}.dat".format(basename,iobs,lengthObsWindow)
        iobs+=1
        np.savetxt(obsFname,subdata,fmt='%d')
                
    return obsData # 3D arrays (obs, sample, metric)
        
def slidingObsWindow(data,lengthObsWindow,slidingValue,basename):
    iobs=0
    nSamples,nMetrics=data.shape
    obsData=np.zeros((0,lengthObsWindow,nMetrics))
    for s in np.arange(lengthObsWindow,nSamples,slidingValue):
        subdata=data[s-lengthObsWindow:s,:]
        obsData=np.insert(obsData,iobs,subdata,axis=0)
        
        obsFname="{}_obs{}_w{}.dat".format(basename,iobs,lengthObsWindow)
        iobs+=1
        np.savetxt(obsFname,subdata,fmt='%d')
               
    return obsData # 3D arrays (obs, sample, metric)
        
def slidingMultObsWindow(data,allLengthsObsWindow,slidingValue,basename):
    iobs=0
    nSamples,nMetrics=data.shape
    obsDataList=[]
    for i in range(len(allLengthsObsWindow)):
        obsData=np.zeros((0,allLengthsObsWindow[i],nMetrics))
        obsDataList.append(obsData)
    
    for s in np.arange(max(allLengthsObsWindow),nSamples,slidingValue):
            for i in range(len(allLengthsObsWindow)):
                oW=allLengthsObsWindow[i]
                subdata=data[s-oW:s,:]
                obsDataList[i]=np.insert(obsDataList[i],iobs,subdata,axis=0)
                
                obsFname="{}_obs{}_w{}.dat".format(basename,iobs,oW)
                np.savetxt(obsFname,subdata,fmt='%d')
                
            iobs+=1
    
    return obsDataList  # List of 3D arrays (obs, sample, metric)

def main():
    parser=argparse.ArgumentParser()
    parser.add_argument('-i', '--input', nargs='?',required=True, help='input file')
    parser.add_argument('-m', '--method', nargs='?',required=False, help='obs. window creation method',default=2)
    parser.add_argument('-w', '--widths', nargs='*',required=False, help='list of observation windows widths',default=60)
    parser.add_argument('-s', '--slide', nargs='?',required=False, help='observation windows slide value',default=10)
    args=parser.parse_args()
    
    fileInput=args.input
    method=int(args.method)
    lengthObsWindow=[int(w) for w in args.widths]
    slidingValue=int(args.slide)
        
    data=np.loadtxt(fileInput,dtype=int)
    fname=''.join(fileInput.split('.')[:-1])
    if method>1:
        dirname=fname+"_obs_s{}_m{}".format(slidingValue,method)
    else:
        dirname=fname+"_obs_m{}".format(method)
    os.mkdir(dirname)
    basename=dirname+"/"+fname
    
    if method==1:
        print("\n\n### SEQUENTIAL Observation Windows with Length {} ###".format(lengthObsWindow[0]))
        obsData=seqObsWindow(data,lengthObsWindow[0],basename)
        print(obsData)
    elif method==2:
        print("\n\n### SLIDING Observation Windows with Length {} and Sliding {} ###".format(lengthObsWindow[0],slidingValue))
        obsData=slidingObsWindow(data,lengthObsWindow[0],slidingValue,basename)
        print(obsData)
    elif method==3:
        print("\n\n### SLIDING Observation Windows with Lengths {} and Sliding {} ###".format(lengthObsWindow,slidingValue))    
        obsData=slidingMultObsWindow(data,lengthObsWindow,slidingValue,basename)
        print(obsData)
            
        

if __name__ == '__main__':
    main()
