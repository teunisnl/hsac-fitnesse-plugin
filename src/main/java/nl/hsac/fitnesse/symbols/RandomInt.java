package nl.hsac.fitnesse.symbols;

import fitnesse.wikitext.parser.Matcher;
import fitnesse.wikitext.parser.Parser;
import fitnesse.wikitext.parser.Rule;
import fitnesse.wikitext.parser.Symbol;
import fitnesse.wikitext.parser.Translation;
import fitnesse.wikitext.parser.Translator;
import nl.hsac.fitnesse.util.RandomUtil;
import util.Maybe;

/**
 * Generates random positive integer with an (inclusive) minimum  and (non-inclusive) maximum.
 * Usage: !randomInt [(min,max)]
 * The usage of parentheses around the max is because I could not get it to work otherwise. :-)
 *
 * To be able to use it, please ensure the class name is added to the list of SymbolTypes in
 * plugins.properties
 */
public class RandomInt extends SymbolBase implements Rule, Translation {
    private static final RandomUtil RANDOM_UTIL = new RandomUtil();
    private static final String RANDOM_MIN_MAX = "RandomMinMax";

    public RandomInt() {
        super("RandomInt");
        wikiMatcher(new Matcher().string("!randomInt"));
        wikiRule(this);
        htmlTranslation(this);
    }

    public Maybe<Symbol> parse(Symbol current, Parser parser) {
        return storeParenthesisContent(current, parser, RANDOM_MIN_MAX);
    }

    public String toTarget(Translator translator, Symbol symbol) {
        int result = 0;
        String param = symbol.getProperty(RANDOM_MIN_MAX, null);
        if (param == null) {
            result = RANDOM_UTIL.random(Integer.MAX_VALUE);
        } else {
            String[] values = param.split(",");
            int min = parseInt(values[0]);
            int max = parseInt(values[1]);
            int randomBase = max - min;
            int random = RANDOM_UTIL.random(randomBase);
            result = random + min;
        }
        return Integer.toString(result);
    }

}