#=
Day18:
- Julia version: 1.5.3
- Author: cohriley
- Date: 2020-12-19
=#
input = readlines("src/Day18.txt")

⨦(a,b) = a * b
⨱(a,b) = a + b

println(sum(l -> eval(Meta.parse(replace(l, "*" => "⨦"))), input))
println(sum(l -> eval(Meta.parse(replace(replace(l, "*" => "⨦"), "+" => "⨱"))), input))
