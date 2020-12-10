-- Read input file into an array
local file = io.open("day9.txt", "r");
local arr = {}
for line in file:lines() do
  table.insert (arr, line);
end

local preamble = 25
local answer = nil
-- Start iterating the list
for i = 1, table.getn(arr) do
  
  -- skip the preable
  if (i <= preamble) then
    goto continue
  end
  
  -- 2 for loops for i - preable and i-preamble + 1
  local valid = false
  for x = (i - preamble), (i - 2) do
    for y = (i - preamble + 1), (i - 1) do 
      if (tonumber(arr[x]) + tonumber(arr[y]) == tonumber(arr[i])) then
        valid = true
        goto continue
      end
    end
  end
  
  if (not valid) then
    answer = tonumber(arr[i])
    print("Answer: " .. tostring(answer))
  end
  
  ::continue::

end

-- part 2
-- Now that we have our invalid number, loop through and find the numbers which add up to it
-- Start iterating the list
local sum_contents = {}
local found_set = false;
local start = 1
while (true) do
  local sum = 0;
  sum_contents = {}
  for i = start, table.getn(arr) do
    
    table.insert(sum_contents, arr[i]);
    sum = sum + tonumber(arr[i]);
    if (sum == answer) then
      found_set = true
      break
    end
    
    if (sum > answer) then
      break
    end
  end
  
  if (found_set) then
    break
  end  
  
  start = start + 1
end

-- Now we have the array of sum elements
-- Sort
table.sort(sum_contents)
-- Get the min
local min = sum_contents[1];
-- Get the max
local max = sum_contents[table.getn(sum_contents)];
-- Add them
print("Sum of max and min: " .. tostring(max + min))