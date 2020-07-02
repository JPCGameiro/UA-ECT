//
// Joaquim Madeira, AlgC, May 2020
//
// Graph EXAMPLE
//

#include "Graph.h"

int main(void) {
  Graph* g01 = GraphCreate(6, 0, 0);
  GraphAddEdge(g01, 1, 2);
  GraphAddEdge(g01, 1, 4);
  GraphAddEdge(g01, 3, 4);
  GraphDisplay(g01);
  for (int i = 0; i < 6; i++) {
    GraphListAdjacents(g01, i);
  }
  Graph* dig01 = GraphCreate(6, 1, 0);
  GraphAddEdge(dig01, 1, 2);
  GraphAddEdge(dig01, 1, 4);
  GraphAddEdge(dig01, 3, 4);
  GraphDisplay(dig01);
  GraphDestroy(&g01);
  GraphDestroy(&dig01);
  return 0;
}