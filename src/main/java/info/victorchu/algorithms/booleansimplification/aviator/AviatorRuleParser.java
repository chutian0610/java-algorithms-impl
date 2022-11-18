package info.victorchu.algorithms.booleansimplification.aviator;

import info.victorchu.algorithms.booleansimplification.aviator.antlr4.AviatorLexer;
import info.victorchu.algorithms.booleansimplification.aviator.antlr4.AviatorParser;
import info.victorchu.algorithms.booleansimplification.core.simplification.BooleanMatrixSimplifierV1;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;


/**
 * Aviator规则的parser
 * @author victorchu
 */
public class AviatorRuleParser {

    public Rule parseAviatorRule(String exp,String ruleName){
        // 1. parse rule
        Rule rule = parseExp(exp);
        rule.setName(ruleName);

        // 2. simplicity rule
        BooleanMatrixSimplifierV1 booleanMatrixSimplifierV1 =new BooleanMatrixSimplifierV1();
        rule.setBooleanMatrix(booleanMatrixSimplifierV1.handleBooleanExpression(rule.getBooleanExpression()));

        return rule;
    }

    public Rule parseExp(String exp){
        // parse aviator rule
        AviatorLexer lexer = new AviatorLexer(CharStreams.fromString(exp));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        AviatorParser parser = new AviatorParser(commonTokenStream);
        ParseTree parseTree = parser.statement();

        BooleanExpVisitor booleanExpVisitor = new BooleanExpVisitor();
        return (Rule) parseTree.accept(booleanExpVisitor);
    }

}
