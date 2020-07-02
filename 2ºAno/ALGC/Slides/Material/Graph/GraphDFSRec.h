//
// Joaquim Madeira, AlgC, May 2020
//
// GraphDFS - RECURSIVE Depth-First Search
//

#ifndef _GRAPH_DFS_REC_
#define _GRAPH_DFS_REC_

#include "Graph.h"
#include "IntegersStack.h"

typedef struct _GraphDFS GraphDFS;

GraphDFS* GraphDFSExecute(Graph* g, unsigned int startVertex);

void GraphDFSDestroy(GraphDFS** p);

// Getting the result

unsigned int GraphDFSHasPathTo(const GraphDFS* p, unsigned int v);

Stack* GraphDFSPathTo(const GraphDFS* p, unsigned int v);

// DISPLAYING on the console

void GraphDFSShowPath(const GraphDFS* p, unsigned int v);

void GraphDFSDisplay(const GraphDFS* p);

#endif  // _GRAPH_DFS_REC_
