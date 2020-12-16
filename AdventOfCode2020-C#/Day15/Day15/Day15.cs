using System;
using System.Collections.Generic;
using System.IO;

namespace Day15
{
    class Program
    {
        static void Main(string[] args)
        {
            const string path = "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-C#/Day15/Day15/Day15.txt";
            var input = File.ReadAllText(path);
            var splitInput = input.Split(",");
            var start = Array.ConvertAll(splitInput, int.Parse);
            Console.WriteLine(input);

            var prev = start[^1];
            var memory = new int[30_000_000];
            var c = 0;
            foreach (var i in start[..^1])
                memory[i] = ++c;
            var turn = start.Length;

            while (turn < 30000000)
            {
                if (memory[prev] == 0)
                {
                    memory[prev] = turn;
                    prev = 0;
                }
                else
                {
                    var diff = turn - memory[prev];
                    memory[prev] = turn;
                    prev = diff;
                }

                turn++;
            }
            
            Console.WriteLine(prev);
        }
    }
}