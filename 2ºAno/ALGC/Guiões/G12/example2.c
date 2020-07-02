//
// Joaquim Madeira, AlgC, May 2020
// João Manuel Rodrigues, AlgC, May 2020
//
// Graph EXAMPLE
//

#include "Graph.h"
#include "GraphDFSRec.h"
#include "GraphDFSWithStack.h"
#include "GraphBFSWithQueue.h"

int main(void) {
  Graph* g01 = GraphCreate(6, 0, 0);

  GraphAddEdge(g01, 0, 5);
  GraphAddEdge(g01, 2, 4);
  GraphAddEdge(g01, 2, 3);
  GraphAddEdge(g01, 1, 2);
  GraphAddEdge(g01, 0, 1);
  GraphAddEdge(g01, 3, 4);
  GraphAddEdge(g01, 3, 5);
  GraphAddEdge(g01, 0, 2);

  GraphDisplay(g01);

  printf("\nTesting Recursive DFS\n");
  GraphDFSRec* traversal = GraphDFSRecExecute(g01, 4);
  printf("Path from 0 to 5: ");
  GraphDFSRecShowPath(traversal, 5);
  printf("\n");
  GraphDFSRecDestroy(&traversal);

  printf("\nTesting Stack DFS\n");
  GraphDFSWithStack* traversalStack = GraphDFSWithStackExecute(g01, 4);
  printf("Path from 0 to 5: ");
  GraphDFSWithStackShowPath(traversalStack, 5);
  printf("\n");
  GraphDFSWithStackDestroy(&traversalStack);

  
  Graph* g02 = GraphCreate(11, 0, 0);
  GraphAddEdge(g02, 0, 5);
  GraphAddEdge(g02, 2, 4);
  GraphAddEdge(g02, 2, 3);
  GraphAddEdge(g02, 1, 2);
  GraphAddEdge(g02, 0, 1);
  GraphAddEdge(g02, 3, 4);
  GraphAddEdge(g02, 3, 5);
  GraphAddEdge(g02, 0, 2);
  printf("\nTesting Queue BFS\n");
  GraphBFSWithQueue* traversalQueue = GraphBFSWithQueueExecute(g02, 3);
  printf("Path from 0 to 5: ");
  GraphBFSWithQueueShowPath(traversalQueue, 5);
  printf("\n");
  GraphBFSWithQueueDestroy(&traversalQueue);

  GraphDestroy(&g01);

  return 0;
}
