package com.jpmorgan.networks.weather.gsonzipper.list

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ZippedListTypeAdapter<T1, T2> constructor(
	private val gson: Gson
) : TypeAdapter<ZippedList<T1, T2>?>() {

	override fun write(
		writer: JsonWriter?,
		zippedList: ZippedList<T1, T2>?
	) {

		writer ?: return
		zippedList ?: return

		// Write [ to the output
		writer.beginArray()
		val iterator1 = zippedList.list1.iterator()
		val iterator2 = zippedList.list2.iterator()
		// Iterate over two sequences trying to merge their respective elements JSON representations
		while (iterator1.hasNext() || iterator2.hasNext()) {
			val i1 = if (iterator1.hasNext()) iterator1.next() else null
			val i2 = if (iterator2.hasNext()) iterator2.next() else null
			// This is not very efficient because it builds in-memory JSON trees thus consuming memory
			// It would be nice if it would be possible to decorate JsonWriter to control its beginObject and endObject
			// The latter control would help to suppress { and } at the top level, and delegate the real serialization to Gson with the decorated JsonWriter
			// But JsonWriter constructor requires a Writer, not at JsonWriter, and we do not have where to obtain a writer instance from
			// So we can just merge the trees...
			val tree: JsonElement = mergeInto(gson.toJsonTree(i1), gson.toJsonTree(i2))
			gson.toJson(tree, writer)
		}
		// Write ] to the output
		writer.endArray()
	}

	override fun read(reader: JsonReader?): ZippedList<T1, T2> {
		throw UnsupportedOperationException()
	}

	private fun mergeInto(e1: JsonElement, e2: JsonElement): JsonElement {
		return if (e1.isJsonNull) {
			if (e2.isJsonObject) {
				mergeInto(e1.asJsonNull, e2.asJsonObject)
			} else {
				throw AssertionError("TODO: " + e2.javaClass)
			}
		} else if (e1.isJsonObject) {
			if (e2.isJsonObject) {
				mergeInto(e1.asJsonObject, e2.asJsonObject)
			} else {
				throw AssertionError("TODO: " + e2.javaClass)
			}
		} else {
			throw AssertionError("TODO: " + e1.javaClass)
		}
	}

	// A bunch of specialized mergeInto overloads letting javac to pick the best one
	private fun mergeInto(jsonNull1: JsonNull, jsonObject2: JsonObject): JsonObject {
		return jsonObject2
	}

	private fun mergeInto(jsonObject1: JsonObject, jsonObject2: JsonObject): JsonObject {
		for ((key, value) in jsonObject2.entrySet()) {
			jsonObject1.add(key, value)
		}
		return jsonObject1
	}
}
