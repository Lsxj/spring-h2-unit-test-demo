package com.citi.training.spring.emailer;

/**
 * Created by sxj on 2017/3/1.
 */
public class JapaneseSpellChecker implements SpellChecker {
    @Override
    public void check() {
        System.out.println("Japanese checking... ");
    }
}
