/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   a graph which includes the relationship and value between words represents the corpus.
    // Representation invariant:
    //   the words in graph can't be null.
    // Safety from rep exposure:
    //   all the fields are private final.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    
	public GraphPoet(File corpus) throws IOException {
		StringBuilder sb = new StringBuilder();
        String s = "";
    	try {
		FileReader reader = new FileReader(corpus);
        BufferedReader bReader = new BufferedReader(reader);
        while ((s =bReader.readLine()) != null) {
        	s=s.replace(",",",\n");
            s=s.replace(".", ".\n");
            s=s.replace("?", "?\n");
            s=s.replace("!", "!\n");
            sb.append(s + "\n");
        }
        bReader.close();}
    	catch (Exception e) {
    		throw new IOException();
		}
        String[] splitString=sb.toString().toLowerCase().split("\\s+|\n+");
        for(int i=0;i<splitString.length-1;i++) {
        	int pweight=graph.set(splitString[i], splitString[i+1], 1);
        	graph.set(splitString[i], splitString[i+1], pweight+1);
        }
        checkRep();
    }
    
    public void checkRep()
    {
    	for(String key:graph.vertices()) {
    		assert key!=null;
    	}
    }
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
    	//String temp=new String(input);
    	input=input.replace(",",",\n");
        input=input.replace(".", ".\n");
        input=input.replace("?", "?\n");
        input=input.replace("!", "!\n");
        String[] sb=input.toLowerCase().split("\\s+|\n+");
        String[] sb1=input.split("\\s+|\n+");
        String anString=sb1[0];
        int big=0;
        Map<String, Integer>map1=null;
        Map<String, Integer>map2=null;
        String temp="";
        for(int i=0;i<sb.length-1;i++) {
        	big=0;
        	map1=graph.sources(sb[i+1]);
        	map2=graph.targets(sb[i]);
        	for(String key:map2.keySet()) {
        		if(map1.keySet().contains(key)) {
        			if(big<map2.get(key)+map1.get(key)) {
        				big=map2.get(key)+map1.get(key);
        				temp=key;
        			}
        		}
        	}
        	if(temp.equals(""))anString=anString+temp+" "+sb1[i+1];
        	else anString=anString+" "+temp+" "+sb1[i+1];
        	temp="";
        }
        checkRep();
        return anString;
    }
    @Override
    public String toString() {
    	checkRep();
    	return graph.toString();
    }
    
}
