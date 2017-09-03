package com.zhaopin.plugin.common;

public class ItemPair<T, S> {
	// ¼ü
	private T key;
	// Öµ
	private S value;
	
	public ItemPair() {
		this(null, null);
	}
	
	public ItemPair(T key, S value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public S getValue() {
		return value;
	}

	public void setValue(S value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ItemPair [key=" + key + ", value=" + value + "]";
	}
}
