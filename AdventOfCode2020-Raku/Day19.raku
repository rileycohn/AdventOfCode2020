sub matches($messages, %rules) {
    my $regex = %rules.kv.map(-> $k, $v {
        "regex r$k \{ { $v.subst(/\d+/, { "<r$/>" }, :g) } \}"
    }).join('; ');
    my $grammar = "my grammar g \{ rule TOP \{ <r0> \}; $regex \}".&EVAL;
    return $messages.lines.grep(-> $msg { $grammar.parse: $msg }).elems;
}

my ($rules, $messages) = 'Day19.txt'.IO.slurp.split("\n\n");
my %rules = $rules.lines.map({ |.split(': ') });

put matches($messages, %rules);

%rules<8> = '42 | 42 8';
%rules<11> = '42 31 | 42 11 31';

put matches($messages, %rules);
