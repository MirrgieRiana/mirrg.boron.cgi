use CGI;
my $q = CGI->new;
print "content-type: text/html\n\n";
print "id=", scalar($q->param("id"));