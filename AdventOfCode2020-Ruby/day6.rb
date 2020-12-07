require 'set'

total = 0
family_size = 0
family_yes_map = Hash.new
File.readlines('day6.txt').each do |line|
  if line == "\n"
    family_yes_map.each { |k, v|
      if v == family_size
        total = total + 1
      end
    }
    family_yes_map = Hash.new
    family_size = 0
  else
    line = line.strip
    family_size = family_size + 1
    chars = line.split('')
    chars.each { |c|
      if family_yes_map[c] != nil
        family_yes_map[c] = family_yes_map[c] + 1
      else
        family_yes_map[c] = 1
      end
    }
  end
end

puts total