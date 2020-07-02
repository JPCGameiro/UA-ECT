//
// Joaquim Madeira, AlgC, May 2020
//
// GraphDFS - RECURSIVE Depth-First Search
//

#include "GraphDFSRec.h"

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>

#include "Graph.h"
#include "IntegersStack.h"

struct _GraphDFS {
  unsigned int* marked;
  unsigned int* predecessor;
  Graph* graph;
  unsigned int startVertex;
};

static void _dfs(GraphDFS* traversal, unsigned int vertex) {
  traversal->marked[vertex] = 1;

  unsigned int* neighbors = GraphGetAdjacentsTo(traversal->graph, vertex);

  for (int i = 1; i <= neighbors[0]; i++) {
    unsigned int w = neighbors[i];
    if (traversal->marked[w] == 0) {
      traversal->predecessor[w] = vertex;
      _dfs(traversal, w);
    }
  }
}

GraphDFS* GraphDFSExecute(Graph* g, unsigned int startVertex) {
  assert(g != NULL);
  assert(0 <= startVertex && startVertex < GraphGetNumVertices(g));

  GraphDFS* result = (GraphDFS*)malloc(sizeof(struct _GraphDFS));
  assert(result != NULL);

  unsigned int numVertices = GraphGetNumVertices(g);

  result->marked = (unsigned int*)calloc(numVertices, sizeof(unsigned int));
  assert(result->marked != NULL);

  result->predecessor =
      (unsigned int*)malloc(numVertices * sizeof(unsigned int));
  assert(result->predecessor != NULL);
  for (int i = 0; i < numVertices; i++) {
    result->predecessor[i] = -1;
  }

  result->predecessor[startVertex] = 0;

  result->graph = g;
  result->startVertex = startVertex;

  // START THE RECURSIVE TRAVERSAL

  _dfs(result, startVertex);

  return result;
}

void GraphDFSDestroy(GraphDFS** p) {
  assert(*p != NULL);

  GraphDFS* aux = *p;

  free(aux->marked);
  free(aux->predecessor);

  free(*p);
  *p = NULL;
}

// Getting the result

unsigned int GraphDFSHasPathTo(const GraphDFS* p, unsigned int v) {
  assert(0 <= v && v < GraphGetNumVertices(p->graph));

  return p->marked[v];
}

Stack* GraphDFSPathTo(const GraphDFS* p, unsigned int v) {
  assert(0 <= v && v < GraphGetNumVertices(p->graph));

  Stack* s = StackCreate(GraphGetNumVertices(p->graph));

  if (p->marked[v] == 0) {
    return s;
  }

  // Store the path
  for (unsigned int current = v; current != p->startVertex;
       current = p->predecessor[current]) {
    StackPush(s, current);
  }

  StackPush(s, p->startVertex);

  return s;
}

// DISPLAYING on the console

void GraphDFSShowPath(const GraphDFS* p, unsigned int v) {
  assert(0 <= v && v < GraphGetNumVertices(p->graph));

  Stack* s = GraphDFSPathTo(p, v);

  while (StackIsEmpty(s) == 0) {
    printf("%d ", StackPop(s));
  }

  StackDestroy(&s);
}

void GraphDFSDisplay(const GraphDFS* p) {}
