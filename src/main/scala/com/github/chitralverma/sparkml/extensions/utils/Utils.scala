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

package com.github.chitralverma.sparkml.extensions.utils

object Utils {

  /**
    * Return the indices of the bins to which each value in input array belongs.
    * If values in x are beyond the bounds of bins, 0 or len(bins)-1 is returned as appropriate.
    *
    * @param xs Input array to be binned.
    * @param bins Array of bins.
    * @return Output array of indices, of same shape as x.
    */
  def digitize(xs: Array[Double], bins: Array[Double]): Array[Double] =
    for (p <- xs) yield digitize(p, bins)

  def digitize(x: Double, bins: Array[Double]): Double = {
    import scala.annotation.tailrec

    @tailrec
    def go(start: Int, end: Int): Int = {
      val pos = ((end - start) / 2) + start
      val curr = bins(pos)

      if ((end - start) <= 1)
        if (x < bins(end - 1)) pos
        else if (pos + 1 >= bins.length) bins.length - 1
        else pos + 1
      else if (x >= curr) go(pos, end)
      else go(start, pos)
    }

    go(0, bins.length)
  }

}
