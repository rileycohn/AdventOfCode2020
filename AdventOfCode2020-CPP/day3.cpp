#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

void solve() {
    ifstream input ("/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-CPP/day3.txt");

    if (!input) {
        cout << "Error opening file\n";
    }

    vector<vector<char>> treeArray;
    string token;
    while (!input.eof()) {
        getline(input, token);

        vector<char> row;
        row.assign(token.begin(), token.end());

        treeArray.push_back(row);
    }

    int rows =  treeArray.size();
    int cols = treeArray[0].size();

    vector<tuple<int, int>> slopes =
            {make_tuple(1, 1),
             make_tuple(3, 1),
             make_tuple(5, 1),
             make_tuple(7, 1),
             make_tuple(1, 2)
            };

    // For each slope type
    long multiplied_trees = 1;
    for (int i = 0; i< slopes.size(); i++) {
        int horizontal = 0;
        int vertical = 0;
        int answer = 0;
        while (vertical < rows) {
            char value = treeArray[vertical][horizontal % cols];
            horizontal = horizontal + get<0>(slopes[i]);
            vertical = vertical + get<1>(slopes[i]);

            if (value == '#') {
                answer++;
            }
        }
        multiplied_trees = multiplied_trees * answer;
    }
    cout << multiplied_trees;
}


int main() {
    solve();
    return 0;
}