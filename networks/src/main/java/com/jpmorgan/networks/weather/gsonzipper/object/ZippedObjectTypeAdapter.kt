package com.jpmorgan.networks.weather.gsonzipper.`object`

import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ZippedObjectTypeAdapter<T1, T2> constructor(
	private val gson: Gson
) : TypeAdapter<ZippedObject<T1, T2>?>() {

	override fun write(
		writer: JsonWriter?,
		zippedObject: ZippedObject<T1, T2>?
	) {

		writer ?: return
		zippedObject ?: return

		// This is not very efficient because it builds in-memory JSON trees thus consuming memory
		// It would be nice if it would be possible to decorate JsonWriter to control its beginObject and endObject
		// The latter control would help to suppress { and } at the top level, and delegate the real serialization to Gson with the decorated JsonWriter
		// But JsonWriter constructor requires a Writer, not at JsonWriter, and we do not have where to obtain a writer instance from
		// So we can just merge the trees...
		val tree: JsonElement =
			mergeInto(gson.toJsonTree(zippedObject.object1), gson.toJsonTree(zippedObject.object2))
		gson.toJson(tree, writer)
	}

	override fun read(reader: JsonReader?): ZippedObject<T1, T2> {
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
