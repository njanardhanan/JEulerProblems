package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JEulerProblem_0836 extends EulerSolver {

    public JEulerProblem_0836(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        String problemStatement = getProblemStatement();
        List<String> boldStatements = splitUsingRegex(problemStatement, "\\<b>(.*?)\\</b>");
        List<String> boldWords = new ArrayList<>();
        for (String boldStatement : boldStatements) {
            boldWords.addAll(splitUsingRegex(boldStatement, "\\w+"));
        }

        StringBuilder sb = new StringBuilder();
        for (String boldWord : boldWords) {
            sb.append(boldWord.charAt(0));
        }
        //"aprilfoolsjoke"
        return sb.toString();
    }

    private List<String> splitUsingRegex(String data, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        List<String> result = new ArrayList<>();
        while(m.find()) {
            result.add(m.group(m.groupCount()).trim());
        }
        return result;
    }

    @Override
    public String getProblemStatement() {
        return "<p>Let $A$ be an <b>affine plane</b> over a <b>radically integral local field</b> $F$ with residual characteristic $p$.</p>\n" +
                "<p>We consider an <b>open oriented line section</b> $U$ of $A$ with normalized Haar measure $m$.</p>\n" +
                "<p>Define $f(m, p)$ as the maximal possible discriminant of the <b>jacobian</b> associated to the <b>orthogonal kernel embedding</b> of $U$ <span style=\"white-space:nowrap;\">into $A$.</span></p>\n" +
                "<p>Find $f(20230401, 57)$. Give as your answer the concatenation of the first letters of each bolded word.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
