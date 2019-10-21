// Copyright 2019 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package com.google.devtools.build.lib.bazel.rules.ninja;

import static com.google.common.truth.Truth.assertThat;

import com.google.devtools.build.lib.bazel.rules.ninja.file.NinjaSeparatorPredicate;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Tests for {@link NinjaSeparatorPredicate}. */
@RunWith(JUnit4.class)
public class NinjaSeparatorPredicateTest {
  @Test
  public void testIsSeparator() {
    doTestIsSeparator(" \n ", false);
    doTestIsSeparator(" \na", true);
    doTestIsSeparator("$\n ", false);
    doTestIsSeparator("$\n\n", false);
    doTestIsSeparator("$\na", false);
    doTestIsSeparator("b\na", true);
    doTestIsSeparator(" \na", true);
    doTestIsSeparator("b\n$", true);
    doTestIsSeparator(" \n\n", true);
    doTestIsSeparator("a\n\n", true);
    doTestIsSeparator("a\n ", false);
    doTestIsSeparator("a\n\t", false);
  }

  private static void doTestIsSeparator(String s, Boolean expected) {
    assertThat(s).hasLength(3);
    byte[] bytes = s.getBytes(StandardCharsets.ISO_8859_1);
    boolean result = NinjaSeparatorPredicate.INSTANCE.test(bytes[0], bytes[1], bytes[2]);
    assertThat(result).isEqualTo(expected);
  }
}
