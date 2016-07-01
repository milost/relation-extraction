package edu.washington.cs.knowitall.util;


import com.google.common.collect.Lists;
import edu.washington.cs.knowitall.extractor.dependency_parse_tree.DepReVerbExtractor;
import edu.washington.cs.knowitall.nlp.dependency_parse_tree.DependencyParseTree;
import edu.washington.cs.knowitall.nlp.dependency_parse_tree.ParZuSentenceParser;
import edu.washington.cs.knowitall.nlp.extraction.dependency_parse_tree.TreeBinaryExtraction;

import java.util.*;


/**
 * Utility class to call Dep ReVerb.
 * Dep ReVerb uses dependency parse trees to extract relations from strings.
 */
public class DepReVerb extends Extractor<DependencyParseTree, TreeBinaryExtraction> {

    private DepReVerbExtractor extractor;

    /**
     * Constructor of Dep ReVerb
     */
    public DepReVerb() {
        this(false);
    }

    /**
     * Constructor of Dep ReVerb
     * @param debug  enable debug mode?
     */
    public DepReVerb(boolean debug) {
        super(debug);
        this.extractor = new DepReVerbExtractor();
    }

    /**
     * Constructor of Dep ReVerb with arguments
     * @param debug                enable debug mode?
     * @param considerAllArguments consider arguments of child nodes for root nodes?
     * @param weSubject            extract we as subject?
     */
    public DepReVerb(boolean debug, boolean considerAllArguments, boolean weSubject) {
        super(debug);
        this.extractor = new DepReVerbExtractor(considerAllArguments, weSubject);
    }

    protected Iterable<TreeBinaryExtraction> extract(DependencyParseTree tree) {
        return this.extractor.extract(tree);
    }


    /**
     * Extract relations from the given sentence.
     * @param sentStr the sentence as string
     * @return the extracted relations
     */
    public Iterable<TreeBinaryExtraction> extractRelationsFromString(String sentStr) {
        // Convert sentence into a dependency parse tree
        ParZuSentenceParser parser = new ParZuSentenceParser();
        List<DependencyParseTree> trees = parser.parseSentence(sentStr);

        // Extract relations
        List<TreeBinaryExtraction> extractions = new ArrayList<>();
        for (DependencyParseTree tree : trees) {
            extractions.addAll(Lists.newArrayList(extract(tree)));
        }
        return extractions;
    }

    /**
     * Extract relations from the given list of sentences.
     * @param sentences a list of sentences
     * @return the extracted relations
     */
    public Map<String, Iterable<TreeBinaryExtraction>> extractRelationsFromStrings(List<String> sentences) {
        Map<String, Iterable<TreeBinaryExtraction>> sent2relations = new HashMap<>();
        ParZuSentenceParser parser = new ParZuSentenceParser();

        if (this.debug) System.out.println("Process sentences ...");
        int n = 0;
        for (String sentence : sentences) {
            // Output progress
            if (this.debug && n % 50 == 0) {
                System.out.print(n + " .. ");
            }
            n++;
            // parse sentence and extract relations
            List<DependencyParseTree> trees = parser.parseSentence(sentence);
            List<TreeBinaryExtraction> extractions = new ArrayList<>();
            for (DependencyParseTree tree : trees) {
                extractions.addAll(Lists.newArrayList(extract(tree)));
            }
            sent2relations.put(sentence, extractions);
        }
        if (this.debug) System.out.println("Done.");

        return sent2relations;
    }

    /**
     * Extract relations from the given sentence.
     * @param sentStr the sentence as string
     * @return the extracted relations
     */
    public Iterable<TreeBinaryExtraction> extractRelationsFromParsedString(String sentStr) {
        // Convert sentence into a dependency parse tree
        ParZuSentenceParser parser = new ParZuSentenceParser();
        List<DependencyParseTree> trees = parser.convert(Arrays.asList(sentStr.split("\n")));

        // Extract relations
        List<TreeBinaryExtraction> extractions = new ArrayList<>();
        for (DependencyParseTree tree : trees) {
            extractions.addAll(Lists.newArrayList(extract(tree)));
        }
        return extractions;
    }

    /**
     * Extract relations from the given list of sentences.
     * @param sentences a list of sentences
     * @return the extracted relations
     */
    public Map<String, Iterable<TreeBinaryExtraction>> extractRelationsFromParsedStrings(List<String> sentences) {
        Map<String, Iterable<TreeBinaryExtraction>> sent2relations = new HashMap<>();
        ParZuSentenceParser parser = new ParZuSentenceParser();

        if (this.debug) System.out.println("Process sentences ...");
        int n = 0;
        for (String sentence : sentences) {
            // Output progress
            if (this.debug && n % 50 == 0) {
                System.out.print(n + " .. ");
            }
            n++;
            // parse sentence and extract relations
            List<DependencyParseTree> trees = parser.convert(Arrays.asList(sentence.split("\n")));
            List<TreeBinaryExtraction> extractions = new ArrayList<>();
            for (DependencyParseTree tree : trees) {
                extractions.addAll(Lists.newArrayList(extract(tree)));
            }
            sent2relations.put(sentence, extractions);
        }
        if (this.debug) System.out.println("Done.");

        return sent2relations;
    }

    /**
     * Extract relations from the given list of parsed sentences.
     * A parsed sentence represents a dependency parse tree.
     * @param trees a list of parsed sentences
     * @return the extracted relations
     */
    public List<TreeBinaryExtraction> extractRelations(List<DependencyParseTree> trees) {
        List<TreeBinaryExtraction> relations = new ArrayList<>();

        if (this.debug) System.out.println("Process sentences ...");
        int n = 0;
        for (DependencyParseTree tree : trees) {
            // Output progress
            if (this.debug && n % 50 == 0) {
                System.out.print(n + " .. ");
            }
            n++;
            // Extract relations
            relations.addAll(Lists.newArrayList(extract(tree)));
        }
        if (this.debug) System.out.println("Done.");

        return relations;
    }

    /**
     * Extract relations from the dependency parse tree.
     * @param tree a dependency parse tree
     * @return the extracted relations
     */
    public Iterable<TreeBinaryExtraction> extractRelations(DependencyParseTree tree) {
        return extract(tree);
    }
}