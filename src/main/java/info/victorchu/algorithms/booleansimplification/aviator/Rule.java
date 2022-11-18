package info.victorchu.algorithms.booleansimplification.aviator;

import info.victorchu.algorithms.booleansimplification.core.boolexp.AbstractBooleanExpression;

import java.util.List;


public class Rule {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String originExp;
    private AbstractBooleanExpression booleanExpression;
    private List<List<AbstractBooleanExpression>> booleanMatrix;

    public String getOriginExp() {
        return originExp;
    }

    public void setOriginExp(String originExp) {
        this.originExp = originExp;
    }

    public AbstractBooleanExpression getBooleanExpression() {
        return booleanExpression;
    }

    public void setBooleanExpression(AbstractBooleanExpression booleanExpression) {
        this.booleanExpression = booleanExpression;
    }

    public List<List<AbstractBooleanExpression>> getBooleanMatrix() {
        return booleanMatrix;
    }

    public void setBooleanMatrix(List<List<AbstractBooleanExpression>> booleanMatrix) {
        this.booleanMatrix = booleanMatrix;
    }

    public String print(){
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append("name:").append(name).append("\n");
        stringBuilder.append("originExp:").append(originExp).append("\n");
        stringBuilder.append("booleanExpression:").append(booleanExpression.print()).append("\n");
        stringBuilder.append("booleanMatrix:").append("\n");
        for (int i = 0;i<booleanMatrix.size();i++) {
            List<AbstractBooleanExpression> list= booleanMatrix.get(i);
            stringBuilder.append("(");
            for (int j=0;j<list.size();j++) {
                stringBuilder.append(list.get(j).print());
                if(j== list.size()-1){

                }else {
                    stringBuilder.append(" and ");
                }
            }
            stringBuilder.append(")");
            if(i == booleanMatrix.size()-1){

            }else {
                stringBuilder.append("\n or \n");
            }
        }
        return stringBuilder.toString();
    }
}
