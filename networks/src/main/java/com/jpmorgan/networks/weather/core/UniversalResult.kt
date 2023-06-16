package com.jpmorgan.networks.weather.core


class UniversalResult<ITEM> constructor(
	var code: Int,
	var message: String,
	var item: ITEM? = null,
	var items: List<ITEM>? = null) {
	var manualError = false

	fun isSuccess() = code in 200..299

	fun isError() = code < 200 || code >= 300 || manualError

	fun setManualError() {
		manualError = true
	}

	private fun hasItem(): Boolean = item != null
	fun hasNoItem(): Boolean = !hasItem()

	fun hasItems(): Boolean = items?.isNotEmpty() ?: false
	fun hasNoItems(): Boolean = !hasItems()

	fun requireItem(): ITEM {
		return requireNotNull(item)
	}

	fun requireItems(): List<ITEM> {
		return requireNotNull(items)
	}

	override fun toString(): String {
		return "UniversalResult(" +
				"code=$code, " +
				"message='$message', " +
				"item=$item, " +
				"items=$items, " +
				"manualError=$manualError" +
				")"
	}
}
