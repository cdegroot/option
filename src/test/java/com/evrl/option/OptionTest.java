package com.evrl.option;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Function;
import org.junit.Test;

public class OptionTest {

    @Test
    public void testNone() {
        Option none = Option.none();
        assertThat(none.isAvailable(), is(false));
        assertThat(none.get(), is((Object) null));
    }

    @Test
    public void testSome() {
        Option<String> some = Option.of("String");
        assertThat(some.isAvailable(), is(true));
        assertThat(some.get(), is("String"));
    }

    @Test
    public void createOptionFromNullReturnsNone() {
        Option none = Option.of(null);
        assertThat(none.isAvailable(), is(false));
    }

    @Test
    public void optionDefaults() {
        String noneValue = Option.of((String) null).orElse("noneValue");
        assertThat(noneValue, is("noneValue"));

        String someValue = Option.of("someValue").orElse("noneValue");
        assertThat(someValue, is("someValue"));
    }

    @Test
    public void canMapFunctionOverOption() {
        Option<String> some = Option.of("Hello");
        Function<String, Option<Integer>> f = new Function<String, Option<Integer>>() {
            @Override
            public Option<Integer> apply(java.lang.String s) {
                return Option.of(s.length());
            }
        };

        Option<Integer> result = some.bind(f);
        assertThat(result.isAvailable(), is(true));
        assertThat(result.get(), is(5));

        Option<String> none = Option.none();
        Option<Integer> noResult = none.bind(f);
        assertThat(noResult.isAvailable(), is(false));

        // for good measure, now with defaults
        Integer value = none.bind(f).orElse(0);
        assertThat(value, is(0));
    }

    @Test
    public void canIterateOverOptions() {
        Option<String> some = Option.of("Hello");

        int iterations = 0;
        for (String value: some) {
            assertThat(value, is("Hello"));
           iterations++;
        }
        assertThat(iterations, is(1));
    }

    // contrived example :-)
    Map<String, Integer> managerOfEmp = new HashMap<String, Integer>();
    {
        managerOfEmp.put("John", 1);
        managerOfEmp.put("Sally", 2);
        managerOfEmp.put("Jake", 0);
    }

    Map<Integer, String> empForManager = new HashMap<Integer, String>();
    {
        empForManager.put(1, "Sally");
        empForManager.put(2, "Jake");
    }

    Option<Integer> managerOfEmp(String emp) { return Option.of(managerOfEmp.get(emp)); }

    Option<String> empForManager(Integer empId) { return Option.of(empForManager.get(empId)); }

    @Test
    public void puttingItAllTogetherHappyFlow() {
        boolean valueSeen = false;
        for (Integer empId: managerOfEmp("John")) {
            for (String name: empForManager(empId)) {
                valueSeen = true;
                assertThat(name, is("Sally"));
            }
        }
        assertThat(valueSeen, is(true));
    }

    @Test
    public void puttingItAllTogetherSadFlow() {
        for (Integer empId: managerOfEmp("Jake")) {
            for (String ignored : empForManager(empId)) {
                fail("this bit should not be reached");
            }
        }
    }
}
