package ps.hell.ml.nlp.tool.aliasi.test.unit.tokenizer;

import ps.hell.ml.nlp.tool.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.TokenLengthTokenizerFactory;
import ps.hell.ml.nlp.tool.aliasi.tokenizer.TokenizerFactory;
import org.junit.Test;

import static ps.hell.ml.nlp.tool.aliasi.test.unit.Asserts.assertNotSerializable;
import static ps.hell.ml.nlp.tool.aliasi.test.unit.tokenizer.TokenizerTest.assertFactory;

public class TokenLengthTokenizerFactoryTest {

    @Test
    public void testFactory() {
        TokenizerFactory ieFactory  
            = IndoEuropeanTokenizerFactory.INSTANCE;
        TokenizerFactory factory
            = new TokenLengthTokenizerFactory(ieFactory,2,3);
        assertFactory(factory,
                      "");
        assertFactory(factory,
                      "a");

        assertFactory(factory,
                      "A bb cccc",
                      "bb");
        assertFactory(factory,
                      "a aaaa b bbbbbbbb 7474747");
    }

    @Test(expected=IllegalArgumentException.class) 
    public void textConstructException1() {
        new TokenLengthTokenizerFactory(TokenizerTest.UNSERIALIZABLE_FACTORY,
                                        3,2);
    }

    @Test(expected=IllegalArgumentException.class)
    public void textConstructException2() {
        new TokenLengthTokenizerFactory(TokenizerTest.UNSERIALIZABLE_FACTORY,
                                        -1,2);
    }

    @Test
    public void testNotSerializable() {
        TokenLengthTokenizerFactory unserializable
            = new TokenLengthTokenizerFactory(TokenizerTest
                                              .UNSERIALIZABLE_FACTORY,2,4);
        assertNotSerializable(unserializable);
    }

    // no tests of stemming itself as we're using Porter's port.
    // we don't  have definitions of what it's supposed to do.

}