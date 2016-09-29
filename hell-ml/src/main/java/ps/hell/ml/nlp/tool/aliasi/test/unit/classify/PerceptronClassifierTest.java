package ps.hell.ml.nlp.tool.aliasi.test.unit.classify;

import ps.hell.ml.nlp.tool.aliasi.classify.*;
import ps.hell.ml.nlp.tool.aliasi.corpus.Corpus;
import ps.hell.ml.nlp.tool.aliasi.corpus.ObjectHandler;
import ps.hell.ml.nlp.tool.aliasi.matrix.PolynomialKernel;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.TokenFeatureExtractor;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.Tokenizer;
import ps.hell.ml.nlp.tool.aliasi.util.AbstractExternalizable;
import ps.hell.ml.nlp.tool.aliasi.util.FeatureExtractor;
import ps.hell.ml.nlp.tool.aliasi.util.ObjectToDoubleMap;
import ps.hell.ml.nlp.tool.aliasi.util.Strings;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;


public class PerceptronClassifierTest  {

    static final String ACC = BinaryLMClassifier.DEFAULT_ACCEPT_CATEGORY;
    static final String REJ = BinaryLMClassifier.DEFAULT_REJECT_CATEGORY;

    @Test
    public void testOne() throws ClassNotFoundException, IOException {
        PerceptronClassifier<CharSequence> pc
            = create(INSTANCES1,CATS1,2,2);
        // System.out.println("\npc=" + pc + "\n");

        PerceptronClassifier<CharSequence> pc2
            = (PerceptronClassifier<CharSequence>)
            AbstractExternalizable.serializeDeserialize(pc);

        for (int i = 0; i < INSTANCES1.length; ++i) {
            // System.out.println("\nPC(" + INSTANCES1[i] + "=" + pc.classify(INSTANCES1[i]));
            // System.out.println("PC2=" + pc2.classify(INSTANCES1[i]));

            ScoredClassification c1 = pc.classify(INSTANCES1[i]);
            ScoredClassification c2 = pc2.classify(INSTANCES1[i]);

            assertEquals(CATS1[i],c1.bestCategory());
            assertEquals(CATS1[i],c2.bestCategory());
            assertEquals(SCORES1[i],c1.score(0),0.0001);
            assertEquals(-SCORES1[i],c1.score(1),0.0001);
            assertEquals(SCORES1[i],c2.score(0),0.0001);
            assertEquals(-SCORES1[i],c2.score(1),0.0001);
        }

    }

    static PerceptronClassifier<CharSequence> create(String[] instances,
                                                     String[] cats,
                                                     int degree,
                                                     int numIterations)
        throws IOException {

        return new PerceptronClassifier<CharSequence>(new TestCorpus(instances,cats),
                                                      new TokenFeatureExtractor(IndoEuropeanTokenizerFactory.INSTANCE),
                                                      new PolynomialKernel(degree),
                                                      ACC,
                                                      numIterations,
                                                      ACC,
                                                      REJ);
    }

    static final String[] INSTANCES1
        = new String[] {
        "a a b",
        "a b b",
        "b b b"
    };
    static final String[] CATS1
        = new String[] {
        ACC,
        REJ,
        REJ
    };
    static double[] SCORES1
        = new double[] {
        91.0,
        30.0,
        149.0,
    };


    static final String[] INSTANCES2
        = new String[] {
        "a a b",
        "b b b",
        "a a a",
        "a b a",
        "b b a",
        "b a a",
        "b b a"
    };

    static final String[] CATS2
        = new String[] {
        ACC,
        REJ,
        ACC,
        ACC,
        REJ,
        ACC,
        REJ
    };

    static class TestFeatureExtractor
        implements FeatureExtractor<CharSequence>, Serializable {

        public Map<String,Double> features(CharSequence in) {
            ObjectToDoubleMap<String> map = new ObjectToDoubleMap<String>();
            char[] cs = Strings.toCharArray(in);
            Tokenizer tokenizer
                = IndoEuropeanTokenizerFactory.INSTANCE.tokenizer(cs,0,cs.length);
            for (String token : tokenizer)
                map.increment(token,1.0);
            // System.out.println(in + "=" + map);
            return map;
        }

    }

    static class TestCorpus 

        extends Corpus<ObjectHandler<Classified<CharSequence>>> {

        final String[] mInstances;
        final String[] mCats;

        TestCorpus(String[] instances, String[] cats) {
            if (instances.length != cats.length)
                throw new IllegalStateException("length diff");
            mInstances = instances;
            mCats = cats;
        }

        @Override
        public void visitTrain(ObjectHandler<Classified<CharSequence>> handler) {
            for (int i = 0; i < mInstances.length; ++i) {
                Classification c = new Classification(mCats[i]);
                Classified<CharSequence> classified
                    = new Classified<CharSequence>(mInstances[i],c);
                handler.handle(classified);
            }
        }

    }


}
