
/*****************************************************************************
 ***
 *** Algoritmos e Complexidade
 ***
 *** Ano lectivo 2019/2020 --- 2o. Semestre
 ***
 *** DETI - Universidade de Aveiro
 ***
 *****************************************************************************
 ***
 *** Ficheiro:	01_exemplo_IntegersSTACK.c
 *** Autor:     Joaquim Madeira
 *** Data:      22/04/2020
 *** Versao:    1.00
 ***
 *****************************************************************************
 ***
 *** Informacao:
 ***
 ***   Escrever os algarismos de um numero pela ordem inversa.
 ***
 ***   STACK de inteiros: exemplo de utilizacao
 ***
 *****************************************************************************
 ***
 *** Alteracoes:   0.0    22/04/2020   Autor
 ***
 ***   Sem alteracoess.
 ***
 *****************************************************************************/

#include <stdio.h>

#include "IntegersStack.h"

int main(void) {
  int number;

  printf("Write a positive integer value:\n");

  scanf("%d", &number);

  // Creating an empty STACK for integers

  Stack* s1 = StackCreate(20);

  // Get each digit and push it into the stack
  // LS digit to MS digit

  while (number != 0) {
    int digit = number % 10;

    StackPush(s1, digit);

    number /= 10;
  }

  // Get the reversed order using a second stack

  Stack* s2 = StackCreate(20);

  while (!StackIsEmpty(s1)) {
    int digit = StackPop(s1);

    StackPush(s2, digit);
  }

  // Destroy the empty stack

  StackDestroy(&s1);

  // Pop from the stack and print

  printf("The reversed number:\n");

  while (!StackIsEmpty(s2)) {
    int digit;

    digit = StackPop(s2);

    printf("%d", digit);
  }

  printf("\n");

  return 0;
}
