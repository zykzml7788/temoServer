package com.creams.temo.service.testcase;

import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.bsh.BshScriptEvaluator;

import java.io.IOException;

/**
 * beanshell测试
 */
public class BeanShellTest {

    public static void main(String[] args) {
        String srciptText = "say(name){ return \"hello,\"+name;\n;}\nsay(\"hahaha\");";
        BshScriptEvaluator bshScriptEvaluator = new BshScriptEvaluator();
        Object object = bshScriptEvaluator.evaluate(new ScriptSource() {
            @Override
            public String getScriptAsString() throws IOException {
                return srciptText;
            }

            @Override
            public boolean isModified() {
                return false;
            }

            @Override
            public String suggestedClassName() {
                return null;
            }
        });
        System.out.println(object);
    }
}
