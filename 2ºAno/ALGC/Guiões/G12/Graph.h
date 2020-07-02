//
// Joaquim Madeira, AlgC, May 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// Graph - Using a list of adjacency lists representation
//

#ifndef _GRAPH_
#define _GRAPH_

#include <stdio.h>

typedef struct _GraphHeader Graph;

Graph* GraphCreate(unsigned short numVertices, unsigned short isDigraph,
                   unsigned short isWeighted);

Graph* GraphCreateComplete(unsigned short numVertices,
                           unsigned short isDigraph);

void GraphDestroy(Graph** p);

Graph* GraphCopy(const Graph* g);

Graph* GraphFromFile(FILE* f);

// Graph

unsigned short GraphIsDigraph(const Graph* g);

unsigned short GraphIsComplete(const Graph* g);

unsigned short GraphIsWeighted(const Graph* g);

unsigned int GraphGetNumVertices(const Graph* g);

unsigned int GraphGetNumEdges(const Graph* g);

//
// For a graph
//
double GraphGetAverageDegree(const Graph* g);

//
// For a graph
//
unsigned int GraphGetMaxDegree(const Graph* g);

//
// For a digraph
//
unsigned int GraphGetMaxOutDegree(const Graph* g);

// Vertices

unsigned int* GraphGetAdjacentsTo(const Graph* g, unsigned int v);

// *** NEW ***
int* GraphGetDistancesToAdjacents(const Graph* g, unsigned int v);

//
// For a graph
//
unsigned int GraphGetVertexDegree(Graph* g, unsigned int v);

//
// For a digraph
//
unsigned int GraphGetVertexOutDegree(Graph* g, unsigned int v);

//
// For a digraph
//
unsigned int GraphGetVertexInDegree(Graph* g, unsigned int v);

// Edges

unsigned short GraphAddEdge(Graph* g, unsigned int v, unsigned int w);

unsigned short GraphAddWeightedEdge(Graph* g, unsigned int v, unsigned int w,
                                    int weight);

// CHECKING

unsigned short GraphCheckInvariants(const Graph* g);

// DISPLAYING on the console

void GraphDisplay(const Graph* g);

void GraphListAdjacents(const Graph* g, unsigned int v);

#endif  // _GRAPH_
