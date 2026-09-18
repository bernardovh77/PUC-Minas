#include <stdio.h>
#include <stdlib.h>

int main() {

    char s1[100];
    char s2[100];

    while (scanf("%s %s", s1, s2) == 2) {  // le duas strings ate o input acabar

    int len1 = 0;
    while (s1[len1] != '\0') {   // conta o tamanho da primeira string
    len1++;
        }

        int len2 = 0;
    while (s2[len2] != '\0') {  // conta o tamanho da segunda string
        len2++;
    }

    int freq1[26] = {0};
    int freq2[26] = {0};

    for (int i = 0; i < len1; i++) {  // conta quantas vezes cada letra aparece na primeira string
        char c1 = s1[i];
        if (c1 >= 'A' && c1 <= 'Z') c1 = c1 - 'A' + 'a'; // normaliza maiuscula para minuscula
        freq1[c1 - 'a']++;
    }

    for (int i = 0; i < len2; i++) {   // conta quantas vezes cada letra aparece na segunda string
    char c2 = s2[i];
    if (c2 >= 'A' && c2 <= 'Z') c2 = c2 - 'A' + 'a'; // normaliza maiuscula para minuscula
    freq2[c2 - 'a']++;
        }

    int igual = 1;   // vira 0 se tiver diferenca nas strings

    if (len1 != len2) {
    igual = 0;
        }

        for (int i = 0; i < 26; i++) {  // compara as letras nas 2 strings
    if (freq1[i] != freq2[i]) {
            igual = 0;
    }
        }

    if (igual) {
        printf("SIM\n");
    } else {
    printf("NAO\n");
        }

    }

    return 0;
}
