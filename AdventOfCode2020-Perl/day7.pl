#!/usr/bin/perl
use warnings;

my $filename = 'day7.txt';

open(FH, '<', $filename) or die $!;

# Map<color, [colors it holds]>
my %bag_colors;
while(<FH>){
    my @arr = ();
    # print $_;
    my $color = substr($_, 0, index($_, " bags contain "));
    # print $color;
    # array stays empty, push and continue
    if (index($_, "no other") != -1) {
        $bag_colors{$color} = [];
        push($bag_colors{$color}, @arr);
        next;
    }

    # only one bag
    my $first;
    if (index($_, ",") == -1) {
        $first = trim(substr($_, index($_, "contain") + 7,  index($_, ".")));
        # remove the number
        my $with_bags = trim(substr($first, index($first, " ")));
        # remove bag(s)
        my $contains_color = trim(substr($with_bags, 0, index($with_bags, "bag")));
        push(@arr, $contains_color);
        # add all to array
        $bag_colors{$color} = [];
        push($bag_colors{$color}, @arr);
        next;
    } else {
        $first = trim(substr($_, index($_, "contain") + 7, index($_, ",") - index($_, "contain") - 7));
        # remove the number
        my $with_bags = trim(substr($first, index($first, " ")));
        # remove bag(s)
        my $contains_color = trim(substr($with_bags, 0, index($with_bags, "bag")));
        push(@arr, $contains_color);
    }

    # split by comma
    my @split_commas = split(", ", $_);
    if (@split_commas > 1) {
        my @a = (1..@split_commas - 1);
        foreach(@a){
            # remove the number
            my $with_bags = substr($split_commas[$_], index($split_commas[$_], " "));
            # remove bag(s)
            my $contains_color = trim(substr($with_bags, 0, index($with_bags, "bag")));
            push(@arr, $contains_color);
        }
    }

    # add all to array
    $bag_colors{$color} = [];
    push($bag_colors{$color}, @arr);
}

my $how_many_bags = 0;
for my $main_color (keys %bag_colors) {
    if (!($main_color eq "shiny gold")) {
        if (check_for_gold($main_color, %bag_colors)) {
            $how_many_bags++;
        }
    }
}

print($how_many_bags);

close(FH);

sub trim { my $s = shift; $s =~ s/^\s+|\s+$//g; return $s };

# Can the provided color carry gold?
# if yes, return true
# if no, can any of it's child bags? recursion
sub check_for_gold {
    my ($color, %hash) = (@_);

    if ($color eq "shiny gold") {
        return 1;
    } else {
        my @value = @{$hash{$color}};

        # if this color cannot hold others, return false
        if (@value == 0) {
            return 0;
        } else {
            my $can_child_hold_gold = 0;
            foreach ( @value ) {
                if (check_for_gold($_, %hash)) {
                    $can_child_hold_gold = 1;
                    next;
                }
            }
            return $can_child_hold_gold;
        }
    }


}