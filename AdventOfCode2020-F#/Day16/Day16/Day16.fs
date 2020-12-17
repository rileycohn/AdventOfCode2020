// Learn more about F# at http://docs.microsoft.com/dotnet/fsharp

open System
open System.IO

// Define a function to construct a message to print
let from whom =
    sprintf "from %s" whom
    
let readLines (filePath:string) = seq {
    use sr = new StreamReader (filePath)
    while not sr.EndOfStream do
        yield sr.ReadLine ()
}

type Rule(ruleName, one, two, three, four) = 
    member this.RuleName = ruleName
    member this.One = one
    member this.Two = two
    member this.Three = three
    member this.Four = four
    
let part1 =
    let rules = seq { yield! File.ReadLines "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-Rules.txt" }
    let yourTicket = seq { yield! File.ReadLines "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-YourTicket.txt" }
    let nearbyTickets = seq { yield! File.ReadLines "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-NearbyTickets.txt" }
    
    // Init the rules array
    let mutable parsedRules = []
    for line in rules do
        // Extract the rule
        let ruleName = line.[0..line.IndexOf(":") - 1]
        
        let afterRule = line.[line.IndexOf(":") + 2..]
        
        let oneAndTwo = afterRule.[0..afterRule.IndexOf(" ") - 1]
        let one = oneAndTwo.[0..oneAndTwo.IndexOf("-") - 1]
        let two = oneAndTwo.[oneAndTwo.IndexOf("-") + 1..]
                
        let threeAndFour = afterRule.[afterRule.IndexOf("or ") + 3..]
        let three = threeAndFour.[0..threeAndFour.IndexOf("-") - 1]
        let four = threeAndFour.[threeAndFour.IndexOf("-") + 1..]
        
        let newRule = Rule(ruleName, one |> int, two |> int, three |> int, four |> int)
        
        parsedRules <- [newRule] |> List.append parsedRules
        
    // Now we have all the rules
    // For each nearby ticket, check if it fits any of the rules
    let mutable sumOfInvalidTickets = 0
    for ticket in nearbyTickets do
        let ticketNums = ticket.Split(",")
        // For each number, check against the rules
        for num in ticketNums do
            let mutable isValid: bool = false
            let numInt = num |> int
            for rule in parsedRules do
                if ((numInt >= rule.One && numInt <= rule.Two) || (numInt >= rule.Three && numInt <= rule.Four)) then
                    isValid <- true
        
            if not isValid then
                sumOfInvalidTickets <- sumOfInvalidTickets + numInt

    printf "Answer: %d\n" sumOfInvalidTickets
    0 // return an integer exit code
    
let part2 =
    let rules = seq { yield! File.ReadLines "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-Rules.txt" }
    let yourTicket = File.ReadAllText "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-YourTicket.txt"
    let nearbyTickets = seq { yield! File.ReadLines "/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-F#/Day16/Day16/Day16-NearbyTickets.txt" }
    
    // Init the rules array
    let mutable parsedRules = []
    for line in rules do
        // Extract the rule
        let ruleName = line.[0..line.IndexOf(":") - 1]
        
        let afterRule = line.[line.IndexOf(":") + 2..]
        
        let oneAndTwo = afterRule.[0..afterRule.IndexOf(" ") - 1]
        let one = oneAndTwo.[0..oneAndTwo.IndexOf("-") - 1]
        let two = oneAndTwo.[oneAndTwo.IndexOf("-") + 1..]
                
        let threeAndFour = afterRule.[afterRule.IndexOf("or ") + 3..]
        let three = threeAndFour.[0..threeAndFour.IndexOf("-") - 1]
        let four = threeAndFour.[threeAndFour.IndexOf("-") + 1..]
        
        let newRule = Rule(ruleName, one |> int, two |> int, three |> int, four |> int)
        
        parsedRules <- [newRule] |> List.append parsedRules
        
    // Now we have all the rules
    // For each nearby ticket, check if it fits any of the rules
    let mutable validTickets = []
    for ticket in nearbyTickets do
        let mutable validTicket = true
        let ticketNums = ticket.Split(",")
        // For each number, check against the rules
        for num in ticketNums do
            let mutable isValid: bool = false
            let numInt = num |> int
            for rule in parsedRules do
                if ((numInt >= rule.One && numInt <= rule.Two) || (numInt >= rule.Three && numInt <= rule.Four)) then
                    isValid <- true
        
            if not isValid then
                validTicket <- false
        
        if validTicket then
            validTickets <- [ticket] |> List.append validTickets

    // At this point we only have valid tickets. The work can begin
    // We need to find possible combinations of rules
    // For each number in each ticket we can track which rules it satisfies. If a number does not satisfy one, remove that rule from the position
    // map<index, set<rule names>>
    let mutable possibleTicketParams = Map.empty
    // init all possibilities
    let ticketNums = validTickets.[0].Split(",")
    for index in 0.. ticketNums.Length - 1 do
        let mutable ruleNames = Set.empty
        for ruleName in parsedRules do
            ruleNames <- ruleNames.Add(ruleName.RuleName)
        possibleTicketParams <- possibleTicketParams.Add(index, ruleNames)
       
    for ticket in validTickets do
        let ticketNums = ticket.Split(",")
        for index in 0.. ticketNums.Length - 1 do
            let numInt = ticketNums.[index] |> int
            // Now for each number, check which rules are satisfied
            for rule in parsedRules do
                if not ((numInt >= rule.One && numInt <= rule.Two) || (numInt >= rule.Three && numInt <= rule.Four)) then
                    // Rule does not match. Remove from possibleTicketParams
                    possibleTicketParams <-  possibleTicketParams.Add(index, possibleTicketParams.[index].Remove(rule.RuleName))
    
    // Now decide which rule each position gets
    let mutable availableRuleNames = Set.empty
    for rule in parsedRules do
        availableRuleNames <- availableRuleNames.Add(rule.RuleName)
    
    let mutable results = Map.empty
    while not availableRuleNames.IsEmpty do
        for index in 0 .. possibleTicketParams.Count - 1 do
            // Have to filter down to only available rules
            for possibleRule in possibleTicketParams.[index] do
                if not (availableRuleNames.Contains possibleRule) then
                    let newRules = possibleTicketParams.[index].Remove(possibleRule)
                    possibleTicketParams <- possibleTicketParams.Remove(index)
                    possibleTicketParams <- possibleTicketParams.Add(index, newRules)
                    
            if possibleTicketParams.[index].Count = 1 then
                results <- results.Add(index, possibleTicketParams.[index])
                // remove this rule from the available rules
                let toRemove = Set.toArray possibleTicketParams.[index]
                availableRuleNames <- availableRuleNames.Remove(toRemove.[0])
    
    // Now we have the rules by index
    // Time to loop through my ticket
    let mutable answer: int64 = int64 1
    let ticketNums = yourTicket.Split(",")
    for index in 0.. ticketNums.Length - 1 do
        let ruleNames = results.[index]
        let arr = Set.toArray ruleNames
        let ruleName = arr.[0]
        if ruleName.Contains("departure") then
            answer <- answer * (ticketNums.[index] |> int64)

    printf "Answer: %d\n" answer
    0 // return an integer exit code

[<EntryPoint>]
let main argv =       
    part1
    part2


