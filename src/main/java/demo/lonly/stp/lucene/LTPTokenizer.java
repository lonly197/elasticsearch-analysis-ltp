/**
 * ltp word segmenter version 0.8.2
 * 玻森中文分词 版本 0.8.2
 */
package demo.lonly.stp.lucene;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.core.LTPWordSegmenter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.json.JSONException;

import java.io.IOException;
import java.util.Iterator;

/**
 * Implementation of LTP word segmenter on Lucene Tokenizer interface
 *
 */
public final class LTPTokenizer extends Tokenizer {
    // ltp word segmenter
    private LTPWordSegmenter LTPSeg;
    private Iterator<String> wordToken;
    // Attributes to be added
    private final CharTermAttribute charTermAttr;
    private final OffsetAttribute offsetAttr;
    private final TypeAttribute typeAttr;
    private final PositionIncrementAttribute piAttr;

    // others
    private int endPosition = -1;
    private int extraIncrement = 0;

    /**
     * Lucene constructor
     *
     * @throws UnirestException
     * @throws JSONException
     * @throws IOException
     */
    public LTPTokenizer(String URL)
            throws IOException, JSONException, UnirestException {
        super();
        // Add token offset attribute
        offsetAttr = addAttribute(OffsetAttribute.class);
        // Add token content attribute
        charTermAttr = addAttribute(CharTermAttribute.class);
        // Add token type attribute
        typeAttr = addAttribute(TypeAttribute.class);
        // Add token position attribute
        piAttr = addAttribute(PositionIncrementAttribute.class);
        // Create a new word segmenter to get tokens
        LTPSeg = new LTPWordSegmenter(input, URL);
    }

    @Override
    public boolean incrementToken() throws IOException {
        // clear all the attributes
        clearAttributes();
        if (wordToken.hasNext()) {
            String word = wordToken.next();
            piAttr.setPositionIncrement(extraIncrement + 1);
            charTermAttr.append(word);
            charTermAttr.setLength(word.length());
            offsetAttr.setOffset(endPosition + 1, endPosition + word.length() + 1);
            // The type can be extended later
            typeAttr.setType("word");
            endPosition += word.length();
            return true;
        }
        // No more token
        return false;
    }

    @Override
    public void reset() throws IOException {
        try {
            super.reset();
            LTPSeg.reset(input);
            wordToken = LTPSeg.getWordsIter();
            extraIncrement = 0;
            endPosition = -1;
        } catch (JSONException | UnirestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void end() throws IOException {
        super.end();
        if (endPosition < 0) {
            endPosition = 0;
        }
        int finalOffset = correctOffset(endPosition);
        offsetAttr.setOffset(finalOffset, finalOffset);
        piAttr.setPositionIncrement(piAttr.getPositionIncrement() + extraIncrement);
    }

}
