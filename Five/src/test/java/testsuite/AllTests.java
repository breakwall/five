package testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import algorithm.TestPattern;
import algorithm.TestLineParser;

@RunWith(Suite.class)
@SuiteClasses({TestPattern.class, TestLineParser.class})
public class AllTests {
}
