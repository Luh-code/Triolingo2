package com.triolingo.app.data;

import java.util.HashMap;
import java.util.Map;

import static com.triolingo.app.utils.Logger.logError;

//import static utils.Logger.*;

public class ResourceManager {
	public interface IResourceArray {

	}

	public class ResourceArray<T> implements IResourceArray {
		//----- Members -----

		private Map<String, T> data = new HashMap<>();

		//----- Methods -----

		public T getResource(String key) {
			return data.get(key);
		}

		public void setResource(String key, Object value) {
			data.put(key, (T) value);
		}

		public void deleteResource(String key) {
			data.remove(key);
		}

		public void deleteAll() {
			data.clear();
		}
	}

	//----- Members -----

	private Map<String, IResourceArray> resourceArrays = new HashMap<>();

	//----- Methods -----

	public <T> ResourceArray<T> getResourceArray(Class<? extends T> c) {
		String typeName = c.getSimpleName();

		if (!resourceArrays.containsKey(typeName)) {
			//logError("Tried to retrieve unregistered recource array");
			//assert (false);
			return null;
		}

		return (ResourceArray<T>) resourceArrays.get(typeName);
	}

	public <T> void registerResourceType(Class<? extends T> c) {
		String typeName = c.getSimpleName();
		if (resourceArrays.containsKey(typeName)) {
			logError("Tried to register same resource type multiple times");
			assert (false);
		}

		resourceArrays.put(typeName, (IResourceArray) new ResourceArray<T>());
	}

	public <T> T getResource(String key, Class<? extends T> c) {
		return getResourceArray(c).getResource(key);
	}

	public <T> void setResource(String key, T value) {
		getResourceArray(value.getClass()).setResource(key, value);
	}

	public <T> void deleteResource(String key, Class<? extends T> c) {
		getResourceArray(c).deleteResource(key);
	}

	public void deleteAll() {
		resourceArrays.clear();
	}
}
