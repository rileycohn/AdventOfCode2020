#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

void solve();
const char* filename = "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-C/day2.txt";

int main() {
    solve();
    return 0;
}

void solve() {
    char * buffer = 0;
    long length;
    FILE * f = fopen (filename, "rb");

    if (f)
    {
        fseek (f, 0, SEEK_END);
        length = ftell (f);
        fseek (f, 0, SEEK_SET);
        buffer = malloc(length);
        if (buffer)
        {
            fread (buffer, 1, length, f);
        }
        fclose (f);
    }

    // File was read successfully
    if (buffer)
    {
        int total = 0;
        int min;
        int max;
        char letter;
        char* pass;

        char* end_string;
        char* split_request  = strtok_r(buffer, "\n", &end_string);
        while(split_request != NULL)
        {
            int line_split_iter = 0;
            char* end_line;
            char* line_split = strtok_r(split_request, " ", &end_line);
            while(line_split != NULL)
            {
                if (line_split_iter == 0) {
                    char* end_range;
                    char* range = strtok_r(line_split, "-", &end_range);
                    int isFirst = 1;
                    while(range != NULL)
                    {
                        if (isFirst == 1) {
                            min = atoi(range);
                        } else {
                            max = atoi(range);
                        }
                        isFirst = 0;
                        range = strtok_r(NULL, "-", &end_range);
                    }
                }
                else if (line_split_iter == 1)
                {
                    letter = line_split[0];
                }
                else
                    {
                    pass = line_split;
                }

                line_split_iter++;
                line_split = strtok_r(NULL, " ", &end_line);
            }

            split_request = strtok_r(NULL, "\n", &end_string);

            bool first = pass[min - 1] == letter;
            bool second = pass[max - 1] == letter;

            if ((first && !second) || second && !first) {
                total++;
            }
        }

        // Print the answer
        printf("%d\n", total);
    }
}
