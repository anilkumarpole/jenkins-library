package com.sap.piper.versioning

import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import util.BasePiperTest
import util.JenkinsReadJsonRule
import util.JenkinsWriteJsonRule
import util.Rules

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class SbtArtifactVersioningTest extends BasePiperTest{

    JenkinsReadJsonRule jrjr = new JenkinsReadJsonRule(this, 'test/resources/versioning/SbtArtifactVersioning/')
    JenkinsWriteJsonRule jwjr = new JenkinsWriteJsonRule(this)

    @Rule
    public RuleChain ruleChain = Rules
        .getCommonRules(this)
        .around(jrjr)
        .around(jwjr)

    @Test
    void testVersioning() {
        SbtArtifactVersioning av = new SbtArtifactVersioning(nullScript, [filePath: 'sbtDescriptor.json'])
        assertEquals('1.2.3', av.getVersion())
        av.setVersion('1.2.3-20180101')
        assertTrue(jwjr.files['sbtDescriptor.json'].contains('1.2.3-20180101'))
    }
}
