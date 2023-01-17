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


def waitforEnter(fstop=False):
    if fstop:
        if sys.version_info[0] == 2:
            raw_input("Press ENTER to continue.")
        else:
            input("Press ENTER to continue.")
            
## -- 4 -- ##
def plotFeatures(features,oClass,f1index=0,f2index=1):
    nObs,nFea=features.shape
    colors=['b','g','r']
    for i in range(nObs):
        plt.plot(features[i,f1index],features[i,f2index],'o'+colors[int(oClass[i])])

    plt.show()
    waitforEnter()
    
def logplotFeatures(features,oClass,f1index=0,f2index=1):
    nObs,nFea=features.shape
    colors=['b','g','r']
    for i in range(nObs):
        plt.loglog(features[i,f1index],features[i,f2index],'o'+colors[int(oClass[i])])

    plt.show()
    waitforEnter()
    
## -- 11 -- ##
def distance(c,p):
    return(np.sqrt(np.sum(np.square(p-c))))

########### Main Code #############
Classes={0:'YouTube',1:'Browsing',2:'Mining'}
plt.ion()
nfig=1

## -- 3 -- ##
features_browsing=np.loadtxt("Browsing_obs_features.dat")
features_yt=np.loadtxt("YouTube_obs_features.dat")
features_mining=np.loadtxt("Mining_obs_features.dat")

oClass_browsing=np.ones((len(features_browsing),1))*0
oClass_yt=np.ones((len(features_yt),1))*1
oClass_mining=np.ones((len(features_mining),1))*2

features=np.vstack((features_yt,features_browsing,features_mining))
oClass=np.vstack((oClass_yt,oClass_browsing,oClass_mining))

print('Train Stats Features Size:',features.shape)

## -- 4 -- ##
plt.figure(4)
plotFeatures(features,oClass,0,1)#0,8

## -- 5 -- ##
features_browsingS=np.loadtxt("Browsing_obs_sil_features.dat")
features_ytS=np.loadtxt("YouTube_obs_sil_features.dat")
features_miningS=np.loadtxt("Mining_obs_sil_features.dat")

featuresS=np.vstack((features_ytS,features_browsingS,features_miningS))
oClass=np.vstack((oClass_yt,oClass_browsing,oClass_mining))

print('Train Silence Features Size:',featuresS.shape)
plt.figure(5)
plotFeatures(featuresS,oClass,0,2)


## -- 7 -- ##
features_browsingW=np.loadtxt("Browsing_obs_per_features.dat")
features_ytW=np.loadtxt("YouTube_obs_per_features.dat")
features_miningW=np.loadtxt("Mining_obs_per_features.dat")

featuresW=np.vstack((features_ytW,features_browsingW,features_miningW))
oClass=np.vstack((oClass_yt,oClass_browsing,oClass_mining))

print('Train Wavelet Features Size:',featuresW.shape)
plt.figure(7)
plotFeatures(featuresW,oClass,3,6)

## -- 8 -- ##
#:1
percentage=0.5
pB=int(len(features_browsing)*percentage)
trainFeatures_browsing=features_browsing[:pB,:]
pYT=int(len(features_yt)*percentage)
trainFeatures_yt=features_yt[:pYT,:]
trainFeatures=np.vstack((trainFeatures_browsing,trainFeatures_yt))

trainFeatures_browsingS=features_browsingS[:pB,:]
trainFeatures_ytS=features_ytS[:pYT,:]
trainFeaturesS=np.vstack((trainFeatures_browsingS,trainFeatures_ytS))


trainFeatures_browsingW=features_browsingW[:pB,:]
trainFeatures_ytW=features_ytW[:pYT,:]
trainFeaturesW=np.vstack((trainFeatures_browsingW,trainFeatures_ytW))

o2trainClass=np.vstack((oClass_browsing[:pB],oClass_yt[:pYT]))
#i2trainFeatures=np.hstack((trainFeatures,trainFeaturesS,trainFeaturesW))
#i2trainFeatures=np.hstack((trainFeatures,trainFeaturesS))
i2trainFeatures=np.hstack((trainFeatures))

#:2
percentage=0.5
pB=int(len(features_browsing)*percentage)
trainFeatures_browsing=features_browsing[:pB,:]
pYT=int(len(features_yt)*percentage)
trainFeatures_yt=features_yt[:pYT,:]
pM=int(len(features_mining)*percentage)
trainFeatures_mining=features_mining[:pM,:]

trainFeatures=np.vstack((trainFeatures_browsing,trainFeatures_yt,trainFeatures_mining))

trainFeatures_browsingS=features_browsingS[:pB,:]
trainFeatures_ytS=features_ytS[:pYT,:]
trainFeatures_miningS=features_miningS[:pM,:]

trainFeaturesS=np.vstack((trainFeatures_browsingS,trainFeatures_ytS,trainFeatures_miningS))


trainFeatures_browsingW=features_browsingW[:pB,:]
trainFeatures_ytW=features_ytW[:pYT,:]
trainFeatures_miningW=features_miningW[:pM,:]

trainFeaturesW=np.vstack((trainFeatures_browsingW,trainFeatures_ytW,trainFeatures_miningW))

o3trainClass=np.vstack((oClass_browsing[:pB],oClass_yt[:pYT],oClass_mining[:pM]))
#i3trainFeatures=np.hstack((trainFeatures,trainFeaturesS,trainFeaturesW))
#i3trainFeatures=np.hstack((trainFeatures,trainFeaturesS))
i3trainFeatures=np.hstack((trainFeatures))

#:3
testFeatures_browsing=features_browsing[pB:,:]
testFeatures_yt=features_yt[pYT:,:]
testFeatures_mining=features_mining[pM:,:]

testFeatures=np.vstack((testFeatures_browsing,testFeatures_yt,testFeatures_mining))

testFeatures_browsingS=features_browsingS[pB:,:]
testFeatures_ytS=features_ytS[pYT:,:]
testFeatures_miningS=features_miningS[pM:,:]

testFeaturesS=np.vstack((testFeatures_browsingS,testFeatures_ytS,testFeatures_miningS))


testFeatures_browsingW=features_browsingW[pB:,:]
testFeatures_ytW=features_ytW[pYT:,:]
testFeatures_miningW=features_miningW[pM:,:]

testFeaturesW=np.vstack((testFeatures_browsingW,testFeatures_ytW,testFeatures_miningW))

o3testClass=np.vstack((oClass_browsing[pB:],oClass_yt[pYT:],oClass_mining[pM:]))
#i3testFeatures=np.hstack((testFeatures,testFeaturesS,testFeaturesW))
#i3testFeatures=np.hstack((testFeatures,testFeaturesS))
i3testFeatures=np.hstack((testFeatures))

## -- 9 -- ##
from sklearn.preprocessing import MaxAbsScaler

i2trainScaler = MaxAbsScaler().fit(i2trainFeatures)
i2trainFeaturesN=i2trainScaler.transform(i2trainFeatures)

i3trainScaler = MaxAbsScaler().fit(i3trainFeatures)  
i3trainFeaturesN=i3trainScaler.transform(i3trainFeatures)

i3AtestFeaturesN=i2trainScaler.transform(i3testFeatures)
i3CtestFeaturesN=i3trainScaler.transform(i3testFeatures)

print(np.mean(i2trainFeaturesN,axis=0))
print(np.std(i2trainFeaturesN,axis=0))

## -- 10 -- ##
from sklearn.decomposition import PCA

pca = PCA(n_components=3, svd_solver='full')

i2trainPCA=pca.fit(i2trainFeaturesN)
i2trainFeaturesNPCA = i2trainPCA.transform(i2trainFeaturesN)

i3trainPCA=pca.fit(i3trainFeaturesN)
i3trainFeaturesNPCA = i3trainPCA.transform(i3trainFeaturesN)

i3AtestFeaturesNPCA = i2trainPCA.transform(i3AtestFeaturesN)
i3CtestFeaturesNPCA = i3trainPCA.transform(i3CtestFeaturesN)

print(i2trainFeaturesNPCA.shape,o2trainClass.shape)
plt.figure(8)
plotFeatures(i2trainFeaturesNPCA,o2trainClass,0,1)

## -- 11 -- ##
from sklearn.preprocessing import MaxAbsScaler
centroids={}
for c in range(2):  # Only the first two classes
    pClass=(o2trainClass==c).flatten()
    centroids.update({c:np.mean(i2trainFeaturesN[pClass,:],axis=0)})
print('All Features Centroids:\n',centroids)

AnomalyThreshold=1.2
print('\n-- Anomaly Detection based on Centroids Distances --')
nObsTest,nFea=i3AtestFeaturesN.shape
for i in range(nObsTest):
    x=i3AtestFeaturesN[i]
    dists=[distance(x,centroids[0]),distance(x,centroids[1])]
    if min(dists)>AnomalyThreshold:
        result="Anomaly"
    else:
        result="OK"
       
    print('Obs: {:2} ({}): Normalized Distances to Centroids: [{:.4f},{:.4f}] -> Result -> {}'.format(i,Classes[o3testClass[i][0]],*dists,result))

## -- 12 -- ##
centroids={}
for c in range(2):  # Only the first two classes
    pClass=(o2trainClass==c).flatten()
    centroids.update({c:np.mean(i2trainFeaturesNPCA[pClass,:],axis=0)})
print('All Features Centroids:\n',centroids)

AnomalyThreshold=1.2
print('\n-- Anomaly Detection based on Centroids Distances (PCA Features) --')
nObsTest,nFea=i3AtestFeaturesNPCA.shape
for i in range(nObsTest):
    x=i3AtestFeaturesNPCA[i]
    dists=[distance(x,centroids[0]),distance(x,centroids[1])]
    if min(dists)>AnomalyThreshold:
        result="Anomaly"
    else:
        result="OK"
       
    print('Obs: {:2} ({}): Normalized Distances to Centroids: [{:.4f},{:.4f}] -> Result -> {}'.format(i,Classes[o3testClass[i][0]],*dists,result))


## -- 13 -- ##
from scipy.stats import multivariate_normal
print('\n-- Anomaly Detection based Multivariate PDF (PCA Features) --')
means={}
for c in range(2):
    pClass=(o2trainClass==c).flatten()
    means.update({c:np.mean(i2trainFeaturesNPCA[pClass,:],axis=0)})
#print(means)

covs={}
for c in range(2):
    pClass=(o2trainClass==c).flatten()
    covs.update({c:np.cov(i2trainFeaturesNPCA[pClass,:],rowvar=0)})
#print(covs)

AnomalyThreshold=0.05
nObsTest,nFea=i3AtestFeaturesNPCA.shape
for i in range(nObsTest):
    x=i3AtestFeaturesNPCA[i,:]
    probs=np.array([multivariate_normal.pdf(x,means[0],covs[0]),multivariate_normal.pdf(x,means[1],covs[1])])
    if max(probs)<AnomalyThreshold:
        result="Anomaly"
    else:
        result="OK"
    
    print('Obs: {:2} ({}): Probabilities: [{:.4e},{:.4e}] -> Result -> {}'.format(i,Classes[o3testClass[i][0]],*probs,result))


## -- 14 -- ##
from sklearn import svm

print('\n-- Anomaly Detection based on One Class Support Vector Machines (PCA Features) --')
ocsvm = svm.OneClassSVM(gamma='scale',kernel='linear').fit(i2trainFeaturesNPCA)  
rbf_ocsvm = svm.OneClassSVM(gamma='scale',kernel='rbf').fit(i2trainFeaturesNPCA)  
poly_ocsvm = svm. OneClassSVM(gamma='scale',kernel='poly',degree=2).fit(i2trainFeaturesNPCA)  

L1=ocsvm.predict(i3AtestFeaturesNPCA)
L2=rbf_ocsvm.predict(i3AtestFeaturesNPCA)
L3=poly_ocsvm.predict(i3AtestFeaturesNPCA)

AnomResults={-1:"Anomaly",1:"OK"}

nObsTest,nFea=i3AtestFeaturesNPCA.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Kernel Linear->{:<10} | Kernel RBF->{:<10} | Kernel Poly->{:<10}'.format(i,Classes[o3testClass[i][0]],AnomResults[L1[i]],AnomResults[L2[i]],AnomResults[L3[i]]))

## -- 15 -- ##
from sklearn import svm

print('\n-- Anomaly Detection based on One Class Support Vector Machines --')
ocsvm = svm.OneClassSVM(gamma='scale',kernel='linear').fit(i2trainFeaturesN)  
rbf_ocsvm = svm.OneClassSVM(gamma='scale',kernel='rbf').fit(i2trainFeaturesN)  
poly_ocsvm = svm. OneClassSVM(gamma='scale',kernel='poly',degree=2).fit(i2trainFeaturesN)  

L1=ocsvm.predict(i3AtestFeaturesN)
L2=rbf_ocsvm.predict(i3AtestFeaturesN)
L3=poly_ocsvm.predict(i3AtestFeaturesN)

AnomResults={-1:"Anomaly",1:"OK"}

nObsTest,nFea=i3AtestFeaturesN.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Kernel Linear->{:<10} | Kernel RBF->{:<10} | Kernel Poly->{:<10}'.format(i,Classes[o3testClass[i][0]],AnomResults[L1[i]],AnomResults[L2[i]],AnomResults[L3[i]]))


## -- 16 -- ##
centroids={}
for c in range(3):  # All 3 classes
    pClass=(o3trainClass==c).flatten()
    centroids.update({c:np.mean(i3trainFeaturesNPCA[pClass,:],axis=0)})
print('PCA Features Centroids:\n',centroids)

print('\n-- Classification based on Centroids Distances (PCA Features) --')
nObsTest,nFea=i3CtestFeaturesNPCA.shape
for i in range(nObsTest):
    x=i3CtestFeaturesNPCA[i]
    dists=[distance(x,centroids[0]),distance(x,centroids[1]),distance(x,centroids[2])]
    ndists=dists/np.sum(dists)
    testClass=np.argsort(dists)[0]
    
    print('Obs: {:2} ({}): Normalized Distances to Centroids: [{:.4f},{:.4f},{:.4f}] -> Classification: {} -> {}'.format(i,Classes[o3testClass[i][0]],*ndists,testClass,Classes[testClass]))


## -- 17-- # 
from scipy.stats import multivariate_normal
print('\n-- Classification based on Multivariate PDF (PCA Features) --')
means={}
for c in range(3):
    pClass=(o3trainClass==c).flatten()
    means.update({c:np.mean(i3trainFeaturesNPCA[pClass,:],axis=0)})
#print(means)

covs={}
for c in range(3):
    pClass=(o3trainClass==c).flatten()
    covs.update({c:np.cov(i3trainFeaturesNPCA[pClass,:],rowvar=0)})
#print(covs)

nObsTest,nFea=i3CtestFeaturesNPCA.shape
for i in range(nObsTest):
    x=i3CtestFeaturesNPCA[i,:]
    probs=np.array([multivariate_normal.pdf(x,means[0],covs[0]),multivariate_normal.pdf(x,means[1],covs[1]),multivariate_normal.pdf(x,means[2],covs[2])])
    testClass=np.argsort(probs)[-1]
    
    print('Obs: {:2} ({}): Probabilities: [{:.4e},{:.4e},{:.4e}] -> Classification: {} -> {}'.format(i,Classes[o3testClass[i][0]],*probs,testClass,Classes[testClass]))


## -- 18 -- #
print('\n-- Classification based on Support Vector Machines --')
svc = svm.SVC(kernel='linear').fit(i3trainFeaturesN, o3trainClass)  
rbf_svc = svm.SVC(kernel='rbf').fit(i3trainFeaturesN, o3trainClass)  
poly_svc = svm.SVC(kernel='poly',degree=2).fit(i3trainFeaturesN, o3trainClass)  

L1=svc.predict(i3CtestFeaturesN)
L2=rbf_svc.predict(i3CtestFeaturesN)
L3=poly_svc.predict(i3CtestFeaturesN)
print('\n')

nObsTest,nFea=i3CtestFeaturesN.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Kernel Linear->{:<10} | Kernel RBF->{:<10} | Kernel Poly->{:<10}'.format(i,Classes[o3testClass[i][0]],Classes[L1[i]],Classes[L2[i]],Classes[L3[i]]))


## -- 19 -- #
print('\n-- Classification based on Support Vector Machines  (PCA Features) --')
svc = svm.SVC(kernel='linear').fit(i3trainFeaturesNPCA, o3trainClass)  
rbf_svc = svm.SVC(kernel='rbf').fit(i3trainFeaturesNPCA, o3trainClass)  
poly_svc = svm.SVC(kernel='poly',degree=2).fit(i3trainFeaturesNPCA, o3trainClass)  

L1=svc.predict(i3CtestFeaturesNPCA)
L2=rbf_svc.predict(i3CtestFeaturesNPCA)
L3=poly_svc.predict(i3CtestFeaturesNPCA)
print('\n')

nObsTest,nFea=i3CtestFeaturesNPCA.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Kernel Linear->{:<10} | Kernel RBF->{:<10} | Kernel Poly->{:<10}'.format(i,Classes[o3testClass[i][0]],Classes[L1[i]],Classes[L2[i]],Classes[L3[i]]))
    

## -- 20a -- ##
from sklearn.neural_network import MLPClassifier
print('\n-- Classification based on Neural Networks --')

alpha=1
max_iter=100000
clf = MLPClassifier(solver='lbfgs',alpha=alpha,hidden_layer_sizes=(20,),max_iter=max_iter)
clf.fit(i3trainFeaturesN, o3trainClass) 
LT=clf.predict(i3CtestFeaturesN) 

nObsTest,nFea=i3CtestFeaturesN.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Classification->{}'.format(i,Classes[o3testClass[i][0]],Classes[LT[i]]))

## -- 20b -- ##
from sklearn.neural_network import MLPClassifier
print('\n-- Classification based on Neural Networks (PCA Features) --')

alpha=1
max_iter=100000
clf = MLPClassifier(solver='lbfgs',alpha=alpha,hidden_layer_sizes=(20,),max_iter=max_iter)
clf.fit(i3trainFeaturesNPCA, o3trainClass) 
LT=clf.predict(i3CtestFeaturesNPCA) 

nObsTest,nFea=i3CtestFeaturesNPCA.shape
for i in range(nObsTest):
    print('Obs: {:2} ({:<8}): Classification->{}'.format(i,Classes[o3testClass[i][0]],Classes[LT[i]]))

