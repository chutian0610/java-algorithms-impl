package info.victorchu.algorithms.booleansimplification.aviator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description:
 * @Date:2022/11/18 13:35
 * @Author:victorchutian
 */
class RuleTest {
    @Test
    public void testRule1(){
        AviatorRuleParser aviatorRuleParser = new AviatorRuleParser();
        String line= "a==0 && b==1";
        Rule rule = aviatorRuleParser.parseAviatorRule(line,"test1");
        System.out.println(rule.print());
    }
    @Test
    public void testRule2(){
        AviatorRuleParser aviatorRuleParser = new AviatorRuleParser();
        String line= "a==0 && (b==1 && c==2)";
        Rule rule = aviatorRuleParser.parseAviatorRule(line,"test2");
        System.out.println(rule.print());
    }
    @Test
    public void testRule3(){
        AviatorRuleParser aviatorRuleParser = new AviatorRuleParser();
        String line= "a==0 && (b==1 || c==2)";
        Rule rule = aviatorRuleParser.parseAviatorRule(line,"test3");
        System.out.println(rule.print());
    }
}