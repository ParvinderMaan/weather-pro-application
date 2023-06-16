package com.jpmorgan.networks.weather.gsonzipper.`object`

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken

class ZippedObjectTypeAdapterFactory : TypeAdapterFactory {
	override fun <T> create(gson: Gson, typeToken: TypeToken<T>): TypeAdapter<T>? {
		// Not a class we must handle ourselves? Let Gson pick another best type adapter itself
		return if (!ZippedObject::class.java.isAssignableFrom(typeToken.rawType)) {
			null
		} else ZippedObjectTypeAdapter<Any, Any>(gson).nullSafe() as TypeAdapter<T>
		// Narrowing down the scope of @SuppressWarnings("unchecked") and making the type adapter to take care for nulls automatically
	}
}
