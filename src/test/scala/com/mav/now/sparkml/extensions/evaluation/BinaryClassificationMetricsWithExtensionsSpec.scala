/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mav.now.sparkml.extensions.evaluation

import com.mav.now.sparkml.extensions.TestBase

class BinaryClassificationMetricsWithExtensionsSpec extends TestBase {

  "Compute correct values for calibration curve" in {
    val scoreAndLabels = Seq(
      (0.69560362, 1.0),
      (0.19199448, 0.0),
      (0.75013186, 1.0),
      (0.728601, 0.0),
      (0.76626446, 1.0),
      (0.20415395, 0.0),
      (0.18287868, 0.0),
      (0.71927679, 1.0),
      (0.19432532, 0.0),
      (0.728601, 1.0),
      (0.19849298, 0.0),
      (0.73499137, 1.0),
      (0.16097357, 0.0),
      (0.19199448, 1.0),
      (0.728601, 1.0),
      (0.19432532, 1.0),
      (0.22033627, 0.0),
      (0.19989707, 0.0),
      (0.18353727, 0.0),
      (0.7384061, 1.0)
    )

    val rdd = sc.parallelize(scoreAndLabels)
    val result = new BinaryClassificationMetricsWithExtensions(rdd, 10).calibration().collect()

    val expected = Array(
      (0.2222222222222222, 0.1887132411111111),
      (1.0, 0.69560362),
      (0.0, 0.21224511000000001),
      (0.875, 0.7368591975000001)
    )

    result should contain theSameElementsAs expected
  }
}
