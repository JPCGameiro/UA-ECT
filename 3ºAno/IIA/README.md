# Introdução à Inteligência Artificial
## Conteúdos
* A noção de agente, arquitecturas de agentes, agentes reactivos, deliberativos e híbridos;
* Formalismos para a representação do conhecimento:
  * Lógica de primeira ordem; 
  * Redes semânticas e suas variantes, 
  * A linguagem KIF;
* Resolução de problemas e métodos de pesquisa: 
  * Pesquisa em largura, pesquisa em profundidade;
  * Pesquisa A*, pesquisa gulosa;
  * Pesquisa por propagação de restrições, pesquisa por melhorias sucessivas;
* Planeamento de sequências de acções:
  * STRIPS e PDDL;
  * Planeamento no espaço de estados e planeamento no espaço de soluções; 
  * Planeamento progressivo e regressivo; planeamento hierárquico.
* Aprendizagem Automática.
## Aulas
Slides das teóricas e resoluções dos guiões fornecidos nas aulas práticas (não devem ser interpretadas como soluções)
## Projeto
Desenvolvimento de um agente capaz de jogar de forma inteligente o jogo Sokoban.
Constituido por:
* treeSearch - Pesquisa A* para encontrar o melhor caminho entre duas posições;
* treeSolver - Módulo para encontrar a solução do nivel;
* student - Resolução do mapa e envio da mesma para um servidor;
* studentFunctions - Funções auxiliares;

Código do jogo desenvolvido pelo docente.
