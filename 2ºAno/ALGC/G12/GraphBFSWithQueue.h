//
// Joaquim Madeira, AlgC, May 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// GraphBFSWithQueue - QUEUE-based Breadth-First Search
//

#ifndef _GRAPH_BFS_WITH_QUEUE_
#define _GRAPH_BFS_WITH_QUEUE_

#include "Graph.h"
#include "IntegersStack.h"

typedef struct _GraphBFSWithQueue GraphBFSWithQueue;

GraphBFSWithQueue* GraphBFSWithQueueExecute(Graph* g, unsigned int startVertex);

void GraphBFSWithQueueDestroy(GraphBFSWithQueue** p);

// Getting the result

unsigned int GraphBFSWithQueueHasPathTo(const GraphBFSWithQueue* p,
                                        unsigned int v);

Stack* GraphBFSWithQueuePathTo(const GraphBFSWithQueue* p, unsigned int v);

// DISPLAYING on the console

void GraphBFSWithQueueShowPath(const GraphBFSWithQueue* p, unsigned int v);

void GraphBFSWithQueueDisplay(const GraphBFSWithQueue* p);

#endif  // _GRAPH_BFS_WITH_QUEUE_
