package ps.hell.ml.nlp.tool.aliasi.test.unit.features;

import ps.hell.ml.nlp.tool.aliasi.features.InteractionFeatureExtractor;
import ps.hell.ml.nlp.tool.aliasi.util.FeatureExtractor;
import org.junit.Test;

import java.util.Map;

import static ps.hell.ml.nlp.tool.aliasi.test.unit.features.MockFeatureExtractor.assertFeats;
import static org.junit.Assert.*;

public class InteractionFeatureExtractorTest {

    @Test
    public void testTwo() {
        MockFeatureExtractor fe1 = new MockFeatureExtractor();
        fe1.put(15,
                new String[] { "a", "b", "c" },
                new double[] { 1.5, 2.0, 3.0});
        MockFeatureExtractor fe2 = new MockFeatureExtractor();
        fe2.put(15,
                new String[] { "d", "e", },
                new double[] { 1.0, 0.25});
        
        FeatureExtractor<Integer> ife
            = new InteractionFeatureExtractor<Integer>("I:","*",fe1,fe2);
        
        Map<String,? extends Number> featMap = ife.features(Integer.valueOf(15));

        assertFeats(ife,
                    Integer.valueOf(15),
                    new String[] { "I:a*d", "I:a*e", "I:b*d", "I:b*e", "I:c*d", "I:c*e"},
                    new double[] { 1.5, 0.375, 2.0, 0.5, 3.0, 0.75});
    }


    @Test
    public void testOne() {
        MockFeatureExtractor fe = new MockFeatureExtractor();
        fe.put(27,
               new String[] { "x", "y", "z" },
               new double[] { -1.5, 2.0, 1.0});
        
        FeatureExtractor<Integer> ife
            = new InteractionFeatureExtractor<Integer>("Z__","++",fe);
        
        Map<String,? extends Number> featMap = ife.features(Integer.valueOf(27));

        assertFeats(ife,
                    Integer.valueOf(27),
                    new String[] {"Z__x++x", "Z__x++y", "Z__x++z", "Z__y++y", "Z__y++z", "Z__z++z"},
                    new double[] { 2.25, -3.0, -1.5, 4.0, 2.0, 1.0});
    }


        

}