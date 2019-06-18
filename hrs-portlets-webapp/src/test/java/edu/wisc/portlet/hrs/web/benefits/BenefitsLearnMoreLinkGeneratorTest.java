package edu.wisc.portlet.hrs.web.benefits;

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;


public class BenefitsLearnMoreLinkGeneratorTest {

  BenefitsLearnMoreLinkGenerator generator =
    new BenefitsLearnMoreLinkGenerator();

  @Test
  public void testRolelessNonMadisonUser() {

    assertEquals("https://www.wisconsin.edu/ohrwd/benefits/",
      generator.learnMoreLinkFor(new HashSet<String>(), false));

  }

}
