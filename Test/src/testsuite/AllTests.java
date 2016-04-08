package testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import algorithm.TestPattern;

@RunWith(Suite.class)
@SuiteClasses({TestPattern.class})
public class AllTests {
}
