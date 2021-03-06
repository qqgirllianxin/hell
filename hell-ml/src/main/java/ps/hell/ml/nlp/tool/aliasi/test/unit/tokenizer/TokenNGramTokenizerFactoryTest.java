package ps.hell.ml.nlp.tool.aliasi.test.unit.tokenizer;

import ps.hell.ml.nlp.tool.aliasi.tokenizer.RegExTokenizerFactory;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.TokenNGramTokenizerFactory;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.TokenizerFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class TokenNGramTokenizerFactoryTest  {

    @Test(expected=IllegalArgumentException.class)
        public void testConsEx1() {
        new TokenNGramTokenizerFactory(new RegExTokenizerFactory("\\S+"),-1,2);
    }

    @Test(expected=IllegalArgumentException.class)
        public void testConsEx2() {
        new TokenNGramTokenizerFactory(new RegExTokenizerFactory("\\S+"),2,1);
    }

    @Test
    public void testGetters() {
        TokenizerFactory rtf = new RegExTokenizerFactory("\\S+");
        TokenNGramTokenizerFactory tf = new TokenNGramTokenizerFactory(rtf,1,3);
        assertEquals(rtf,tf.baseTokenizerFactory());
        assertEquals(1,tf.minNGram());
        assertEquals(3,tf.maxNGram());
    }

    @Test
    public void test1() {
        TokenNGramTokenizerFactory factory
            = new TokenNGramTokenizerFactory(new RegExTokenizerFactory("\\S+"),1,1);
        
        TokenizerTest.assertFactory(factory,"");
        TokenizerTest.assertFactory(factory,"a","a");
        TokenizerTest.assertFactory(factory,"a b","a","b");
    }

    @Test
    public void test2() {
        TokenNGramTokenizerFactory factory
            = new TokenNGramTokenizerFactory(new RegExTokenizerFactory("\\S+"),2,2);
        
        TokenizerTest.assertFactory(factory,"");
        TokenizerTest.assertFactory(factory,"a");
        TokenizerTest.assertFactory(factory,"a b","a b");
        TokenizerTest.assertFactory(factory,"a b c","a b","b c");
    }

    @Test
    public void test23() {
        TokenNGramTokenizerFactory factory
            = new TokenNGramTokenizerFactory(new RegExTokenizerFactory("\\S+"),2,3);
        
        TokenizerTest.assertFactory(factory,"");
        TokenizerTest.assertFactory(factory,"a");
        TokenizerTest.assertFactory(factory,"a b","a b");
        TokenizerTest.assertFactory(factory,"a b c","a b","b c","a b c");
        TokenizerTest.assertFactory(factory,"a b c d","a b","b c","c d","a b c","b c d");
    }



}
