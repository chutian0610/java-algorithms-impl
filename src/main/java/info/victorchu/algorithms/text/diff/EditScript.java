package info.victorchu.algorithms.text.diff;


import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class EditScript {
    private final Text textA;
    private final Text textB;

    public List<Edit> getEditList() {
        return editList;
    }

    private List<Edit> editList = new ArrayList<Edit>();

    public EditScript(Text textA, Text textB) {
        this.textA = textA;
        this.textB = textB;
    }

    public void appendEqual(int a ,int b){
        Edit edit = new Edit();
        edit.lineA = a;
        edit.lineB = b;
        edit.operation = Operation.EQUAL;
        editList.add(edit);
    }

    public void appendInsert(int a ,int b){
        Edit edit = new Edit();
        edit.lineA = a;
        edit.lineB = b;
        edit.operation = Operation.INSERT;
        editList.add(edit);
    }
    public void appendDelete(int a ,int b){
        Edit edit = new Edit();
        edit.lineA = a;
        edit.lineB = b;
        edit.operation = Operation.DELETE;
        editList.add(edit);
    }

    public String printDiff(){
        StringBuilder sb = new StringBuilder();
        Collections.reverse(editList);
        for(Edit edit : editList) {
            switch (edit.operation) {
                case INSERT:
                    sb.append("+ ").append(textB.getLine(edit.lineB)).append("\n");
                    break;
                case DELETE:
                    sb.append("- ").append(textA.getLine(edit.lineA)).append("\n");
                    break;
                case EQUAL:
                    sb.append("  ").append(textA.getLine(edit.lineA)).append("\n");
                    break;
            }
        }
        return sb.toString();

    }
}
enum Operation {
    INSERT, DELETE, EQUAL
}

class Edit {
    int lineA;
    int lineB;
    Operation operation;

    @Override
    public String toString() {
        String op = null;
        switch (operation) {
            case INSERT:
                op = "+";
                break;
            case DELETE:
                op = "-";
                break;
            case EQUAL:
                op = "=";
                break;
        }
        return op + lineA + "," + lineB;
    }
}
