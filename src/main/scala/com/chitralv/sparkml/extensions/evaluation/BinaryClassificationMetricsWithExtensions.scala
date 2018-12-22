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

package com.chitralv.sparkml.extensions.evaluation

import breeze.linalg
import com.chitralv.sparkml.extensions.utils.Utils.digitize
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.rdd.RDD

class BinaryClassificationMetricsWithExtensions(scoreAndLabels: RDD[(Double, Double)], numBins: Int)
  extends BinaryClassificationMetrics(scoreAndLabels, numBins) {

  val nbins: Int = if (numBins < 10) numBins else 10

  /**
    * Compute true and predicted probabilities for a calibration curve.
    * The method assumes the inputs come from a binary classifier.
    *
    * Calibration curves may also be referred to as reliability diagrams.
    *
    * @see <a href="https://scikit-learn.org/stable/modules/generated/sklearn.calibration.calibration_curve.html">
    *          Calibration Curve (skLearn)
    */

  def calibration(): RDD[(Double, Double)] = {
    val labelsAndPredictions = scoreAndLabels.map(x => (x._2, x._1))

    val minProb = labelsAndPredictions.map(_._2).min()
    val maxProb = labelsAndPredictions.map(_._2).max()

    assert(minProb >= 0.0 || maxProb <= 1.0, "Probability values outside [0, 1]")

    val bins = linalg.linspace(0.0, 1.0, nbins + 1).toArray
    val a = labelsAndPredictions.map {
      case (l: Double, p: Double) => (digitize(p, bins), (l, p))
    }

    a.map(x => (x._1, (x._2, 1L)))
      .reduceByKey((x, y) => ((x._1._1 + y._1._1, x._1._2 + y._1._2), x._2 + y._2))
      .map(x => (x._1, x._2._1._1, x._2._1._2, x._2._2))
      .filter(_._4 != 0)
      .map(x => (x._2 / x._4, x._3 / x._4))
  }
}
