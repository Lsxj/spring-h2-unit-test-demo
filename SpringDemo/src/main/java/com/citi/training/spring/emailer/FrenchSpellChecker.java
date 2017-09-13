package com.citi.training.spring.emailer;

/**
 * Created by sxj on 2017/3/1.
 */
public class FrenchSpellChecker implements SpellChecker {
    @Override
    public void check() {
        System.out.println("French checking...");
    }
}
