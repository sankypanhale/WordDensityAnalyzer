# WordDensityAnalyzer
Courtesy: https://github.com/mjparangot/Word-Density-Analysis

I have used frequency of word(s) for density analysis of word.

Used following data structures.

#Node and Tree Structures

For this I decided to create my own data structure built upon the HashMap data structure.

In my implementation I made a more specific version called WordTree and WordNode to be used specifically for storing keywords (Strings) as opposed to a generic object. However the general structure is independent of whatever datatype needs to be scored. For this reason they can just be called NODE and TREE

A NODE has three components:

Value: the ojbect being stored
Size: the heuristic value associated with the NODE
Children: A map of all children of the NODE
Just like how each node in a linked list points to the next one, each NODE here points to each of its children using the map. The key-value pairing for the map is as follows:

(child object, child NODE)

The 'child object' is the key used to reference the child, and the 'child NODE' is another NODE that represents the child. Since the child is also a NODE, then it too can have it's own children.

A TREE is a collection of NODEs stored in a HashMap. The map contains key-value pairings of an Object and it's respective NODE (Object, NODE).

When adding to the TREE you can either add a single Object, or a collection of Objects where each successive Object is a "child" of the previous one.

For example:

Adding these four keywords

{"safeguard", "safeguard pretty", "safeguard privacy", "safeguard privacy liberty"} would look like this:

tree.add({"safeguard"});	// single-word keyword tree.add({"safeguard", "pretty"});	// multi-word keyword tree.add({"safeguard", "privacy"}); tree.add({"safeguard", "privacy", "liberty"});

And the tree looks like this:

("safeguard", 4) --> ("pretty", 1) --> ("privacy" 2) --> ("liberty", 1)

("pretty, 1)

("privacy" 2)	--> ("liberty", 1)

("liberty", 1)

In parentheses are NODEs with structure (value, size). The arrows represent all children of the NODE

The left-most nodes represent the top level of the TREE

If you added these new keywords {"safeguard pretty liberty"} the TREE would look like this:

("safeguard", 5) --> ("pretty", 2) --> ("liberty", 1) --> ("privacy", 2) --> ("liberty", 1)

("pretty, 2)	--> ("liberty", 1)

("privacy" 2)	--> ("liberty", 1)

("liberty", 2)

Note how the sizes of some of the NODEs increased because another instance of it was added.

Example interpretations of this TREE:

5 instances of "safeguard" 2 instances of "safeguard privacy" 1 instance of "safeguard privacy liberty" 2 instances of "privacy" 1 instance of "privacy liberty" 2 instances of "liberty"

#Keyword Extraction

In my implementation of the NODE/TREE structure, I created what is called a WordTree, made up of WordNodes that specifically hold a string keyword, and the size of which refers to the number of times that keyword appeared in the document.

After fetching the document from the URL, the document is parsed entirely and every single instance of a keyword is stored in the WordTree.

Each time a word is parsed and determined not to be a stopword or end of a keyword phrase because of punctuation, it is added to a family string that will eventually be added to the WordTree the next time a stopword or punctuation is found.

For example:

If the sentence parsed is:

"Edward Snowden might never live in the United States as a free man again."

The parsing would be as follows:

"Edward" "Snowden" STOPWORD @ "might" -> tree.add({"Edward", "Snowden"}) STOPWORD @ "never" STOPWORD @ "live" STOPWORD @ "in" STOPWORD @ "the" "United" "States" STOPWORD @ "as" -> tree.add({"United", "States"}) STOPWORD @ "a" "free" "man" STOPWORD @ "again." -> tree.add({"free", "man"})

And the tree would look like this:

("Edward", 1) --> ("Snowden", 1)

("Snowden", 1)

("United", 1) --> ("States", 1)

("States", 1)

("free", 1) --> ("man", 1)

("man", 1)

The top level of the tree contains the keywords

{"Edward", "Snowden", "United", "States", "free", "man"}

and further in the tree their children who represent the keywords

{"Edward Snowden", "United States", "free man"}

After parsing the entire document and constructing the WordTree, the keyword's NODEs with the largest sizes represent keywords that appeared the most in the document. These keywords represent the most relevant topics found in the document.

#Future Work

Weighing of words can be done in better way. Words appearing in URL, Title can have more weight than regular tet words. 