//
// Joaquim Madeira, AlgC, May 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// GraphDFSWithStack - STACK-based Depth-First Search
//

#ifndef _GRAPH_DFS_WITH_STACK_
#define _GRAPH_DFS_WITH_STACK_

#include "Graph.h"
#include "IntegersStack.h"

typedef struct _GraphDFSWithStack GraphDFSWithStack;

GraphDFSWithStack* GraphDFSWithStackExecute(Graph* g, unsigned int startVertex);

void GraphDFSWithStackDestroy(GraphDFSWithStack** p);

// Getting the result

unsigned int GraphDFSWithStackHasPathTo(const GraphDFSWithStack* p,
                                        unsigned int v);

Stack* GraphDFSWithStackPathTo(const GraphDFSWithStack* p, unsigned int v);

// DISPLAYING on the console

void GraphDFSWithStackShowPath(const GraphDFSWithStack* p, unsigned int v);

void GraphDFSWithStackDisplay(const GraphDFSWithStack* p);

#endif  // _GRAPH_DFS_WITH_STACK_
