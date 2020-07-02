//
// Joaquim Madeira, AlgC, June 2020
// João Manuel Rodrigues, AlgC, June 2020
//
// GraphShortestPathsWithQueue - QUEUE algorithm for the Shortest Paths Tree
//

#ifndef _GRAPH_SHORTEST_PATHS_WITH_QUEUE_
#define _GRAPH_SHORTEST_PATHS_WITH_QUEUE_

#include "Graph.h"
#include "IntegersStack.h"

typedef struct _GraphShortestPathsWithQueue GraphShortestPathsWithQueue;

GraphShortestPathsWithQueue* GraphShortestPathsWithQueueExecute(
    Graph* g, unsigned int startVertex);

void GraphShortestPathsWithQueueDestroy(GraphShortestPathsWithQueue** p);

// Getting the result

unsigned int GraphShortestPathsWithQueueHasPathTo(
    const GraphShortestPathsWithQueue* p, unsigned int v);

int GraphShortestPathsWithQueueDistanceTo(const GraphShortestPathsWithQueue* p,
                                          unsigned int v);

Stack* GraphShortestPathsWithQueuePathTo(const GraphShortestPathsWithQueue* p,
                                         unsigned int v);

// DISPLAYING on the console

void GraphShortestPathsWithQueueShowPath(const GraphShortestPathsWithQueue* p,
                                         unsigned int v);

void GraphShortestPathsWithQueueDisplay(const GraphShortestPathsWithQueue* p);

#endif  // _GRAPH_SHORTEST_PATHS_WITH_QUEUE_
