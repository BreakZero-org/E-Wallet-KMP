/**
 * reference https://medium.com/kotlin-and-kotlin-for-android/collapsing-toolbar-in-jetpack-compose-lazycolumn-version-f1b0a7924ffe
 */
package com.easy.wallet.home.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
interface CollapsingToolbarState {
  val offset: Float
  val height: Float
  val progress: Float
  val consumed: Float
  var scrollTopLimitReached: Boolean
  var scrollOffset: Float
}

@Composable
fun rememberCollapsingToolbarState(toolbarHeightRange: IntRange): CollapsingToolbarState = rememberSaveable(saver = ScrollState.Saver) {
  ScrollState(toolbarHeightRange)
}

abstract class ScrollFlagState(
  heightRange: IntRange
) : CollapsingToolbarState {
  init {
    require(heightRange.first >= 0 && heightRange.last >= heightRange.first) {
      "The lowest height value must be >= 0 and the highest height value must be >= the lowest value."
    }
  }

  protected val minHeight = heightRange.first
  protected val maxHeight = heightRange.last
  protected val rangeDifference = maxHeight - minHeight
  protected var consumedValue: Float = 0f

  protected abstract var scrollOffsetValue: Float

  final override val height: Float
    get() = (maxHeight - scrollOffset).coerceIn(minHeight.toFloat(), maxHeight.toFloat())

  final override val progress: Float
    get() = 1 - (maxHeight - height) / rangeDifference

  final override val consumed: Float
    get() = consumedValue

  final override var scrollTopLimitReached: Boolean = true
}

abstract class FixedScrollFlagState(
  heightRange: IntRange
) : ScrollFlagState(heightRange) {
  final override val offset: Float = 0f
}

class ScrollState(
  heightRange: IntRange,
  scrollOffset: Float = 0f
) : ScrollFlagState(heightRange) {
  override var scrollOffsetValue by mutableFloatStateOf(
    value = scrollOffset.coerceIn(0f, maxHeight.toFloat())
  )

  override val offset: Float
    get() = -(scrollOffset - rangeDifference).coerceIn(0f, minHeight.toFloat())

  override var scrollOffset: Float
    get() = scrollOffsetValue
    set(value) {
      if (scrollTopLimitReached) {
        val oldOffset = scrollOffsetValue
        scrollOffsetValue = value.coerceIn(0f, maxHeight.toFloat())
        consumedValue = oldOffset - scrollOffsetValue
      } else {
        consumedValue = 0f
      }
    }

  companion object {
    val Saver = run {

      val minHeightKey = "MinHeight"
      val maxHeightKey = "MaxHeight"
      val scrollOffsetKey = "ScrollOffset"

      mapSaver(
        save = {
          mapOf(
            minHeightKey to it.minHeight,
            maxHeightKey to it.maxHeight,
            scrollOffsetKey to it.scrollOffset
          )
        },
        restore = {
          ScrollState(
            heightRange = (it[minHeightKey] as Int)..(it[maxHeightKey] as Int),
            scrollOffset = it[scrollOffsetKey] as Float
          )
        }
      )
    }
  }
}
